package com.bank.legalrisk.domain;

import java.time.LocalDate;
import java.util.List;

/**
 * Simplified property history used by ownership analysis.
 */
public record PropertyHistory(List<String> owners, List<LocalDate> transferDates) {
}
