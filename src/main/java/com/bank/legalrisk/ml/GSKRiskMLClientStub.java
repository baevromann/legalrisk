package com.bank.legalrisk.ml;

import org.springframework.stereotype.Component;

@Component
public class GSKRiskMLClientStub implements GSKRiskMLClient {
    @Override
    public int predictGskInstabilityScore(String status, Integer members, Integer debtors, String regionCode) {
        int base = "LIQUIDATED".equalsIgnoreCase(status) ? 90 : "ACTIVE".equalsIgnoreCase(status) ? 20 : 50;
        int debtPressure = (debtors != null && members != null && members > 0) ? (debtors * 100 / members) : 0;
        return Math.min(100, base + debtPressure / 2);
    }
}
