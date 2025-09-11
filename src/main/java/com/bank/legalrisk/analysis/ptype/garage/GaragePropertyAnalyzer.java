package com.bank.legalrisk.analysis.ptype.garage;

import com.bank.legalrisk.analysis.ptype.PropertyTypeAnalyzer;
import com.bank.legalrisk.domain.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Concrete Strategy for GARAGE properties. Aggregates specialized analyzers.
 */
public class GaragePropertyAnalyzer implements PropertyTypeAnalyzer {

    private final LandRightsAnalyzer landRightsAnalyzer;
    private final GSKAnalyzer gskAnalyzer;
    private final GarageScoringAnalyzer scoringAnalyzer;
    private final GarageContractPointsService contractService;

    public GaragePropertyAnalyzer(LandRightsAnalyzer landRightsAnalyzer, GSKAnalyzer gskAnalyzer,
                                  GarageScoringAnalyzer scoringAnalyzer, GarageContractPointsService contractService) {
        this.landRightsAnalyzer = landRightsAnalyzer;
        this.gskAnalyzer = gskAnalyzer;
        this.scoringAnalyzer = scoringAnalyzer;
        this.contractService = contractService;
    }

    @Override
    public PropertySpecificRisks analyzePropertySpecificRisks(EGRNData egrnData, CommonRiskData commonRisks) {
        GarageSpecificRisks risks = new GarageSpecificRisks();
        String region = "77"; // stub region code
        risks.landRightRisks.addAll(landRightsAnalyzer.analyze(egrnData, region));
        risks.gskMembershipRisks.addAll(gskAnalyzer.analyze(egrnData, region));
        risks.specificRiskScore = calculatePropertyTypeScoring(risks);
        risks.getClauses().addAll(generatePropertyTypeContractClauses(risks));
        return risks;
    }

    @Override
    public int calculatePropertyTypeScoring(PropertySpecificRisks r) {
        return scoringAnalyzer.calculateGarageScoring((GarageSpecificRisks) r);
    }

    @Override
    public List<ContractClause> generatePropertyTypeContractClauses(PropertySpecificRisks r) {
        return contractService.generate((GarageSpecificRisks) r);
    }

    @Override
    public Map<RiskType, Double> getPropertyTypeWeights() {
        return scoringAnalyzer.getWeights();
    }

    @Override
    public Map<String, Integer> getPropertySpecificPenalties() {
        Map<String, Integer> p = new HashMap<>();
        p.put("UNAUTHORIZED_CONSTRUCTION", scoringAnalyzer.getUnauthorizedConstructionPenalty());
        return p;
    }
}
