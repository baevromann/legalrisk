package com.bank.legalrisk.domain;

import java.time.OffsetDateTime;

/**
 * Simple risk DTO with a type, description and a machine-friendly code.
 */
public record RiskItem(RiskType type, String code, String description, RiskLevel level, OffsetDateTime detectedAt) {
}
