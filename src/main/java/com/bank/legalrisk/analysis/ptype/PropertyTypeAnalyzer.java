package com.bank.legalrisk.analysis.ptype;

import com.bank.legalrisk.domain.*;

import java.util.List;
import java.util.Map;

/**
 * Strategy interface for property-type-specific analysis.
 */
public interface PropertyTypeAnalyzer {
    PropertySpecificRisks analyzePropertySpecificRisks(EGRNData egrnData, CommonRiskData commonRisks);

    int calculatePropertyTypeScoring(PropertySpecificRisks risks);

    List<ContractClause> generatePropertyTypeContractClauses(PropertySpecificRisks risks);

    Map<RiskType, Double> getPropertyTypeWeights();

    Map<String, Integer> getPropertySpecificPenalties();
}
