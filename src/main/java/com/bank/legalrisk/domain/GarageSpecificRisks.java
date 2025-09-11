package com.bank.legalrisk.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Specific risks for GARAGE property type.
 */
public class GarageSpecificRisks extends PropertySpecificRisks {
    public final List<RiskItem> landRightRisks = new ArrayList<>();
    public final List<RiskItem> gskMembershipRisks = new ArrayList<>();
    public final List<RiskItem> unauthorizedConstructionRisks = new ArrayList<>();
    public final List<RiskItem> accessRightRisks = new ArrayList<>();
}
