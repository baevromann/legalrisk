package com.bank.legalrisk.domain;

import java.util.List;

/**
 * Minimal person data used by analyzers.
 */
public record PersonData(String personId, String fullName, List<String> passports, String taxId) {
}
