package com.bank.legalrisk.domain;

import java.util.List;

/**
 * Minimal subset of EGRN data required for type detection and analysis.
 */
public record EGRNData(String cadastralNumber, PropertyType propertyType,
                       List<PersonData> owners, PropertyHistory history,
                       String landRightType, Integer leaseYearsRemaining,
                       boolean hasGsk, String gskStatus, Integer gskMembers, Integer gskDebtors) {
}
