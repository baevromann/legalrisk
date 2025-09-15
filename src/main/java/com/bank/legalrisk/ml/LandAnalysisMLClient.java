package com.bank.legalrisk.ml;

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
