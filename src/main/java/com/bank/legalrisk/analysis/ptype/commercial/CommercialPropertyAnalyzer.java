package com.bank.legalrisk.analysis.ptype.commercial;

import com.bank.legalrisk.analysis.ptype.PropertyTypeAnalyzer;
import com.bank.legalrisk.domain.*;

import java.util.List;
import java.util.Map;

/**
 * Simplified commercial analyzer stub.
 */
public class CommercialPropertyAnalyzer implements PropertyTypeAnalyzer {
    @Override
    public PropertySpecificRisks analyzePropertySpecificRisks(EGRNData e, CommonRiskData c) {
        CommercialSpecificRisks r = new CommercialSpecificRisks();
        r.specificRiskScore = 30;
        return r;
    }

    @Override
    public int calculatePropertyTypeScoring(PropertySpecificRisks r) {
        return 30;
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
