package com.bank.legalrisk.domain;

import java.time.OffsetDateTime;

/**
 * Result of a CheckPerson verification (expensive verification of a person).
 */
public record PersonCheckResult(String personId, String status, String justification, OffsetDateTime at) {
}
