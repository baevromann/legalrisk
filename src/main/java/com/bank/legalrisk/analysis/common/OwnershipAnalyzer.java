package com.bank.legalrisk.analysis.common;

import com.bank.legalrisk.domain.*;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * Ownership analyzer (common for all property types).
 * Implements simple rules; extend with ML later.
 */
@Component
public class OwnershipAnalyzer {

    /**
     * Analyze ownership history and produce risks like frequent transfers.
     */
    public List<RiskItem> analyzeOwnershipHistory(PropertyHistory history) {
        int transfers = history.transferDates() == null ? 0 : history.transferDates().size();
        RiskLevel level = transfers >= 5 ? RiskLevel.HIGH : transfers >= 3 ? RiskLevel.MEDIUM : RiskLevel.LOW;
        return List.of(new RiskItem(RiskType.OWNERSHIP, "OWN-TRANSFERS",
                "Количество переходов прав: " + transfers, level, OffsetDateTime.now()));
    }

    /**
     * Placeholder for suspicious transactions analysis.
     */
    public List<RiskItem> identifySuspiciousTransactions(PropertyHistory history) {
        return List.of(); // real rules omitted
    }

    /**
     * Assess complexity (multiple co-owners).
     */
    public RiskItem assessOwnershipComplexity(List<PersonData> owners) {
        RiskLevel level = owners.size() >= 4 ? RiskLevel.MEDIUM : RiskLevel.LOW;
        return new RiskItem(RiskType.OWNERSHIP, "OWN-COMPLEXITY",
                "Количество совладельцев: " + owners.size(), level, OffsetDateTime.now());
    }

    /**
     * Risks specific to co-ownership.
     */
    public List<RiskItem> analyzeCoOwnershipRisks(List<PersonData> owners) {
        return List.of(); // stub
    }
}
