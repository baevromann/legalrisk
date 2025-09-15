package com.bank.legalrisk.ml;

import org.springframework.stereotype.Component;

@Component
public class LandAnalysisMLClientStub implements LandAnalysisMLClient {
    @Override
    public int predictLandRiskScore(String landRightType, Integer leaseYearsRemaining, String regionCode) {
        int base = "LEASE".equalsIgnoreCase(landRightType) ? 60 : "OWNERSHIP".equalsIgnoreCase(landRightType) ? 10 : 30;
        int leasePenalty = (leaseYearsRemaining != null && leaseYearsRemaining < 5) ? 25 : 0;
        return Math.min(100, base + leasePenalty);
    }
}
