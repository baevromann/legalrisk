package com.bank.legalrisk.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Specific risks for RESIDENTIAL_APARTMENT (simplified).
 */
public class ResidentialSpecificRisks extends PropertySpecificRisks {
    public final List<RiskItem> familyRisks = new ArrayList<>();
    public final List<RiskItem> privatizationRisks = new ArrayList<>();
    public final List<RiskItem> renovationRisks = new ArrayList<>();
    public final List<RiskItem> utilityDebtRisks = new ArrayList<>();
    public final List<RiskItem> minorOwnershipRisks = new ArrayList<>();
}
