package com.bank.legalrisk.analysis.common;

import com.bank.legalrisk.domain.*;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * Registration analyzer: checks passport/document consistency and registration anomalies.
 */
@Component
public class RegistrationAnalyzer {

    public List<RiskItem> analyzeRegistrationData(List<PersonData> owners) {
        // Simple heuristic: if any owner has no passports recorded -> risk
        boolean anyMissing = owners.stream().anyMatch(p -> p.passports() == null || p.passports().isEmpty());
        if (anyMissing) {
            return List.of(new RiskItem(RiskType.REGISTRATION, "REG-MISSING-DOCS",
                    "У одного или нескольких собственников отсутствуют данные документов.", RiskLevel.MEDIUM, OffsetDateTime.now()));
        }
        return List.of();
    }

    public List<RiskItem> validateDocumentConsistency(List<PersonData> owners) {
        return List.of();
    }

    public RiskItem checkAddressHistory(PersonData person) {
        return new RiskItem(RiskType.REGISTRATION, "REG-ADDR", "Адресная история не проанализирована (заглушка).",
                RiskLevel.LOW, OffsetDateTime.now());
    }
}
