package com.bank.legalrisk.ml;

import org.springframework.stereotype.Component;

/**
 * Stubs for ML clients invoked via gRPC in production.
 * In this demo we return deterministic values to keep the project self-contained.
 */
public interface LandAnalysisMLClient {
    /**
     * Returns a risk score 0..100 for land-rights related risks predicted by ML.
     */
    int predictLandRiskScore(String landRightType, Integer leaseYearsRemaining, String regionCode);
}

@Component
class LandAnalysisMLClientStub implements LandAnalysisMLClient {
    @Override
    public int predictLandRiskScore(String landRightType, Integer leaseYearsRemaining, String regionCode) {
        int base = "LEASE".equalsIgnoreCase(landRightType) ? 60 : "OWNERSHIP".equalsIgnoreCase(landRightType) ? 10 : 30;
        int leasePenalty = (leaseYearsRemaining != null && leaseYearsRemaining < 5) ? 25 : 0;
        return Math.min(100, base + leasePenalty);
    }
}

public interface GSKRiskMLClient {
    /**
     * Returns a risk score 0..100 for garage cooperative stability.
     */
    int predictGskInstabilityScore(String gskStatus, Integer members, Integer debtors, String regionCode);
}

@Component
class GSKRiskMLClientStub implements GSKRiskMLClient {
    @Override
    public int predictGskInstabilityScore(String status, Integer members, Integer debtors, String regionCode) {
        int base = "LIQUIDATED".equalsIgnoreCase(status) ? 90 : "ACTIVE".equalsIgnoreCase(status) ? 20 : 50;
        int debtPressure = (debtors != null && members != null && members > 0) ? (debtors * 100 / members) : 0;
        return Math.min(100, base + debtPressure / 2);
    }
}
