package com.bank.legalrisk.analysis.ptype.garage;

import com.bank.legalrisk.domain.*;
import com.bank.legalrisk.ml.GSKRiskMLClient;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * GSK (garage cooperative) analyzer:
 * - Validates status, membership, debts (base rules)
 * - Calls ML client to predict instability (stubbed)
 */
@Component
public class GSKAnalyzer {

    private final GSKRiskMLClient ml;

    public GSKAnalyzer(GSKRiskMLClient ml) {
        this.ml = ml;
    }

    public List<RiskItem> analyze(EGRNData egrn, String regionCode) {
        List<RiskItem> res = new ArrayList<>();
        if (!egrn.hasGsk()) return res;

        if ("LIQUIDATED".equalsIgnoreCase(egrn.gskStatus())) {
            res.add(new RiskItem(RiskType.GSK, "GSK-LIQ",
                    "ГСК находится в ликвидации.", RiskLevel.CRITICAL, OffsetDateTime.now()));
        }
        if (egrn.gskMembers() != null && egrn.gskDebtors() != null && egrn.gskMembers() > 0) {
            int share = egrn.gskDebtors() * 100 / egrn.gskMembers();
            if (share >= 20) {
                res.add(new RiskItem(RiskType.GSK, "GSK-DEBT-HIGH",
                        "Высокая доля должников в ГСК: " + share + "%", RiskLevel.HIGH, OffsetDateTime.now()));
            }
        }
        int ml = ml.predictGskInstabilityScore(egrn.gskStatus(), egrn.gskMembers(), egrn.gskDebtors(), regionCode);
        if (ml >= 60) {
            res.add(new RiskItem(RiskType.GSK, "GSK-ML-HIGH",
                    "ML прогноз нестабильности ГСК: " + ml, RiskLevel.MEDIUM, OffsetDateTime.now()));
        }
        return res;
    }
}
