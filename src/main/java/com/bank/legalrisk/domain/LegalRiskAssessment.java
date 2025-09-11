package com.bank.legalrisk.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Final legal risk assessment object produced by the processor.
 */
public class LegalRiskAssessment {
    private PropertyType propertyType;
    private CommonRiskData commonRisks;
    private PropertySpecificRisks propertySpecificRisks;
    private int integratedScore;
    private RiskLevel riskLevel;
    private final List<ContractClause> allContractClauses = new ArrayList<>();
    private final List<String> recommendations = new ArrayList<>();

    public PropertyType getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(PropertyType propertyType) {
        this.propertyType = propertyType;
    }

    public CommonRiskData getCommonRisks() {
        return commonRisks;
    }

    public void setCommonRisks(CommonRiskData commonRisks) {
        this.commonRisks = commonRisks;
    }

    public PropertySpecificRisks getPropertySpecificRisks() {
        return propertySpecificRisks;
    }

    public void setPropertySpecificRisks(PropertySpecificRisks propertySpecificRisks) {
        this.propertySpecificRisks = propertySpecificRisks;
    }

    public int getIntegratedScore() {
        return integratedScore;
    }

    public void setIntegratedScore(int integratedScore) {
        this.integratedScore = integratedScore;
    }

    public RiskLevel getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(RiskLevel riskLevel) {
        this.riskLevel = riskLevel;
    }

    public List<ContractClause> getAllContractClauses() {
        return allContractClauses;
    }

    public List<String> getRecommendations() {
        return recommendations;
    }
}
