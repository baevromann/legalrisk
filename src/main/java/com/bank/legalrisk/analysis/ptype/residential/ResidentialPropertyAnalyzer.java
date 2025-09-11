package com.bank.legalrisk.analysis.ptype.residential;

import com.bank.legalrisk.analysis.ptype.PropertyTypeAnalyzer;
import com.bank.legalrisk.domain.*;

import java.util.List;
import java.util.Map;

/**
 * Simplified residential analyzer stub.
 */
public class ResidentialPropertyAnalyzer implements PropertyTypeAnalyzer {
    @Override
    public PropertySpecificRisks analyzePropertySpecificRisks(EGRNData e, CommonRiskData c) {
        ResidentialSpecificRisks r = new ResidentialSpecificRisks();
        r.specificRiskScore = 20;
        return r;
    }

    @Override
    public int calculatePropertyTypeScoring(PropertySpecificRisks r) {
        return 20;
    }

    @Override
    public List<ContractClause> generatePropertyTypeContractClauses(PropertySpecificRisks r) {
        return List.of();
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
