package com.bank.legalrisk.core;

import com.bank.legalrisk.analysis.common.OwnershipAnalyzer;
import com.bank.legalrisk.analysis.common.RegistrationAnalyzer;
import com.bank.legalrisk.analysis.ptype.PropertyTypeAnalysisFactory;
import com.bank.legalrisk.analysis.ptype.PropertyTypeAnalyzer;
import com.bank.legalrisk.domain.*;
import com.bank.legalrisk.events.InMemoryEventStore;
import com.bank.legalrisk.events.LegalRiskEvent;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class LegalRiskProcessorTest {

    @Test
    void analyzeLegalRisksPublishesCommandsAndAggregatesResults() {
        InMemoryEventStore eventStore = new InMemoryEventStore();
        StubPropertyAnalyzer analyzer = new StubPropertyAnalyzer();
        PropertyTypeAnalysisFactory factory = new PropertyTypeAnalysisFactory() {
            @Override
            public PropertyTypeAnalyzer createAnalyzer(PropertyType type) {
                return analyzer;
            }
        };
        LegalRiskProcessor processor = new LegalRiskProcessor(
                eventStore,
                factory,
                new OwnershipAnalyzer(),
                new RegistrationAnalyzer()
        );

        String cadastralNumber = "77:01:0004012:1234";
        LegalRiskAssessment assessment = processor.analyzeLegalRisks(cadastralNumber);

        List<LegalRiskEvent> events = eventStore.loadByAggregateId("CAD:" + cadastralNumber);
        assertEquals(2, events.size(), "Two command events should be published");
        Set<String> types = events.stream().map(LegalRiskEvent::getType).collect(java.util.stream.Collectors.toSet());
        assertEquals(Set.of("RosreestrDataRequested", "NBKIDataRequested"), types);
        events.forEach(event -> assertEquals("CAD:" + cadastralNumber, event.getAggregateId()));
        assertTrue(events.stream().allMatch(e -> e.getPayloadJson().contains(cadastralNumber)));

        assertEquals(PropertyType.GARAGE, assessment.getPropertyType());
        assertEquals(1, assessment.getCommonRisks().ownershipRisks.size(), "Ownership analyzer should add one risk item");
        assertEquals(0, assessment.getCommonRisks().registrationRisks.size());
        assertSame(assessment.getCommonRisks(), analyzer.lastCommon, "Common risks should be passed to the property analyzer");
        assertEquals(cadastralNumber, analyzer.lastEgrn.cadastralNumber());

        assertTrue(assessment.getPropertySpecificRisks() instanceof StubPropertySpecificRisks);
        assertEquals(40, assessment.getPropertySpecificRisks().getSpecificRiskScore());
        assertEquals(45, assessment.getIntegratedScore());
        assertEquals(RiskLevel.HIGH, assessment.getRiskLevel());
        assertEquals(1, assessment.getAllContractClauses().size());
        assertEquals("STUB-CLAUSE", assessment.getAllContractClauses().get(0).code());
        assertTrue(assessment.getRecommendations().contains("Провести углубленную юридическую проверку по перечню рисков."));
    }

    @Test
    void requestCheckPersonVerificationPublishesEvent() {
        InMemoryEventStore eventStore = new InMemoryEventStore();
        PropertyTypeAnalysisFactory factory = new PropertyTypeAnalysisFactory() {
            @Override
            public PropertyTypeAnalyzer createAnalyzer(PropertyType type) {
                return new StubPropertyAnalyzer();
            }
        };
        LegalRiskProcessor processor = new LegalRiskProcessor(
                eventStore,
                factory,
                new OwnershipAnalyzer(),
                new RegistrationAnalyzer()
        );

        String ownerId = "owner-42";
        String justification = "High risk owner";
        PersonCheckResult result = processor.requestCheckPersonVerification(ownerId, justification);

        assertEquals(ownerId, result.personId());
        assertEquals("REQUESTED", result.status());
        assertEquals(justification, result.justification());

        List<LegalRiskEvent> events = eventStore.loadByAggregateId("PERSON:" + ownerId);
        assertEquals(1, events.size(), "A single CheckPerson command should be recorded");
        LegalRiskEvent event = events.get(0);
        assertEquals("CheckPersonVerificationRequested", event.getType());
        assertEquals("PERSON:" + ownerId, event.getAggregateId());
        assertNotNull(event.getCorrelationId());
        assertFalse(event.getCorrelationId().isBlank());
        assertTrue(event.getPayloadJson().contains(ownerId));
        assertTrue(event.getPayloadJson().contains(justification));
    }

    private static final class StubPropertyAnalyzer implements PropertyTypeAnalyzer {
        private EGRNData lastEgrn;
        private CommonRiskData lastCommon;

        @Override
        public PropertySpecificRisks analyzePropertySpecificRisks(EGRNData egrnData, CommonRiskData commonRisks) {
            this.lastEgrn = egrnData;
            this.lastCommon = commonRisks;
            StubPropertySpecificRisks risks = new StubPropertySpecificRisks();
            risks.specificRiskScore = 40;
            risks.getClauses().addAll(generatePropertyTypeContractClauses(risks));
            return risks;
        }

        @Override
        public int calculatePropertyTypeScoring(PropertySpecificRisks risks) {
            return 40;
        }

        @Override
        public List<ContractClause> generatePropertyTypeContractClauses(PropertySpecificRisks risks) {
            return List.of(new ContractClause("STUB-CLAUSE", "Test clause"));
        }

        @Override
        public Map<RiskType, Double> getPropertyTypeWeights() {
            return Map.of();
        }

        @Override
        public Map<String, Integer> getPropertySpecificPenalties() {
            return Map.of();
        }
    }

    private static final class StubPropertySpecificRisks extends PropertySpecificRisks {
    }
}
