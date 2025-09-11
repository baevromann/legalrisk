package com.bank.legalrisk.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Aggregated common risks across all property types.
 */
public class CommonRiskData {
    public final List<RiskItem> ownershipRisks = new ArrayList<>();
    public final List<RiskItem> registrationRisks = new ArrayList<>();
    public final List<RiskItem> creditRisks = new ArrayList<>();
    public final List<RiskItem> courtRisks = new ArrayList<>();
    public final List<PersonCheckResult> checkPersonResults = new ArrayList<>();
}
