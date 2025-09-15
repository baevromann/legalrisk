package com.bank.legalrisk.ml;

/**
 * Stubs for ML clients invoked via gRPC in production.
 * In this demo we return deterministic values to keep the project self-contained.
 */
public interface GSKRiskMLClient {
    /**
     * Returns a risk score 0..100 for garage cooperative stability.
     */
    int predictGskInstabilityScore(String gskStatus, Integer members, Integer debtors, String regionCode);
}
