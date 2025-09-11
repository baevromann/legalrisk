package com.bank.legalrisk.core;

import com.bank.legalrisk.analysis.common.OwnershipAnalyzer;
import com.bank.legalrisk.analysis.common.RegistrationAnalyzer;
import com.bank.legalrisk.analysis.ptype.PropertyTypeAnalysisFactory;
import com.bank.legalrisk.analysis.ptype.PropertyTypeAnalyzer;
import com.bank.legalrisk.domain.*;
import com.bank.legalrisk.events.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Central orchestrator (UML: LegalRiskProcessor).
 * <p>Implements Event Sourcing (Task #1): instead of calling external APIs directly,
 * it appends command events into EventStore. Integration services consume them via CDC/Kafka.</p>
 */
@Service
public class LegalRiskProcessor {

    private final EventStore eventStore;
    private final PropertyTypeAnalysisFactory factory;
    private final OwnershipAnalyzer ownershipAnalyzer;
    private final RegistrationAnalyzer registrationAnalyzer;
    private final ObjectMapper mapper = new ObjectMapper();

    public LegalRiskProcessor(EventStore eventStore,
                              PropertyTypeAnalysisFactory factory,
                              OwnershipAnalyzer ownershipAnalyzer,
                              RegistrationAnalyzer registrationAnalyzer) {
        this.eventStore = eventStore;
        this.factory = factory;
        this.ownershipAnalyzer = ownershipAnalyzer;
        this.registrationAnalyzer = registrationAnalyzer;
    }

    /**
     * High-level entry point.
     * 1) Create command events to fetch external data (Rosreestr, NBKI) — append-only.
     * 2) Run common analyzers.
     * 3) Create a specific analyzer by property type and run it.
     * 4) Aggregate into a final assessment.
     */
    public LegalRiskAssessment analyzeLegalRisks(String cadastralNumber) {
        String aggregateId = "CAD:" + cadastralNumber;
        String correlationId = UUID.randomUUID().toString();

        // (Task #1) Publish commands instead of direct calls
        publishEvent(new RosreestrDataRequestedEvent(aggregateId, correlationId, json(Map.of("cadastralNumber", cadastralNumber))));
        publishEvent(new NBKIDataRequestedEvent(aggregateId, correlationId, json(Map.of("scope", "ALL_OWNERS"))));

        // In real life we would wait for results via events.
        // For the demo we'll build a minimal EGRNData stub.
        EGRNData egrn = new EGRNData(cadastralNumber, PropertyType.GARAGE,
                List.of(new PersonData("p1", "Иван Иванов", List.of("4010 123456"), "7701")),
                new PropertyHistory(List.of("Иван Иванов"), List.of()), "LEASE", 3, true, "ACTIVE", 120, 35);

        // Common analysis
        CommonRiskData common = coordinateCommonAnalysis(egrn);

        // Property-type specific analysis
        PropertyType type = determinePropertyType(egrn);
        PropertyTypeAnalyzer analyzer = createPropertyTypeAnalyzer(type);
        PropertySpecificRisks specific = coordinatePropertySpecificAnalysis(analyzer, egrn, common);

        // Aggregate
        return aggregateAllRisks(type, common, specific);
    }

    /**
     * Create an event and append it to the store.
     */
    public void publishEvent(LegalRiskEvent event) {
        eventStore.append(event);
    }

    /**
     * Detect property type from EGRN dataset.
     */
    public PropertyType determinePropertyType(EGRNData egrnData) {
        return egrnData.propertyType() != null ? egrnData.propertyType() : PropertyType.RESIDENTIAL_APARTMENT;
    }

    /**
     * Factory method to produce a specialized analyzer for the given type.
     */
    public PropertyTypeAnalyzer createPropertyTypeAnalyzer(PropertyType propertyType) {
        return factory.createAnalyzer(propertyType);
    }

    /**
     * Run common analyzers and return aggregated results.
     */
    public CommonRiskData coordinateCommonAnalysis(EGRNData egrn) {
        CommonRiskData common = new CommonRiskData();
        common.ownershipRisks.addAll(ownershipAnalyzer.analyzeOwnershipHistory(egrn.history()));
        common.registrationRisks.addAll(registrationAnalyzer.analyzeRegistrationData(egrn.owners()));
        return common;
    }

    /**
     * Run property-type-specific analysis using the Strategy instance.
     */
    public PropertySpecificRisks coordinatePropertySpecificAnalysis(PropertyTypeAnalyzer analyzer, EGRNData egrn, CommonRiskData common) {
        return analyzer.analyzePropertySpecificRisks(egrn, common);
    }

    /**
     * Build the final assessment, compute an integrated score and risk level.
     */
    public LegalRiskAssessment aggregateAllRisks(PropertyType type, CommonRiskData common, PropertySpecificRisks specific) {
        LegalRiskAssessment result = new LegalRiskAssessment();
        result.setPropertyType(type);
        result.setCommonRisks(common);
        result.setPropertySpecificRisks(specific);
        int score = 0;
        score += specific.getSpecificRiskScore();
        score += common.ownershipRisks.size() * 5;
        score += common.registrationRisks.size() * 5;
        score = Math.min(100, score);
        result.setIntegratedScore(score);
        result.setRiskLevel(score >= 70 ? RiskLevel.CRITICAL : score >= 40 ? RiskLevel.HIGH : score >= 20 ? RiskLevel.MEDIUM : RiskLevel.LOW);
        result.getAllContractClauses().addAll(specific.getClauses());
        result.getRecommendations().add("Провести углубленную юридическую проверку по перечню рисков.");
        return result;
    }

    /**
     * Create a CheckPerson verification request (Task #1) and return a stub result.
     */
    public PersonCheckResult requestCheckPersonVerification(String ownerId, String justification) {
        String agg = "PERSON:" + ownerId;
        String corr = UUID.randomUUID().toString();
        publishEvent(new CheckPersonVerificationRequestedEvent(agg, corr, json(Map.of("ownerId", ownerId, "justification", justification))));
        return new PersonCheckResult(ownerId, "REQUESTED", justification, java.time.OffsetDateTime.now());
    }

    private String json(Object o) {
        try {
            return mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            return "{}";
        }
    }
}
