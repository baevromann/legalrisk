package com.bank.legalrisk.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Specific risks for COMMERCIAL_PROPERTY (simplified).
 */
public class CommercialSpecificRisks extends PropertySpecificRisks {
    public final List<RiskItem> rentalRisks = new ArrayList<>();
    public final List<RiskItem> zoningRisks = new ArrayList<>();
    public final List<RiskItem> licenseRisks = new ArrayList<>();
    public final List<RiskItem> taxDisputeRisks = new ArrayList<>();
    public final List<RiskItem> environmentalRisks = new ArrayList<>();
}
