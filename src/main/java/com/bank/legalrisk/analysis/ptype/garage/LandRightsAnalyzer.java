package com.bank.legalrisk.analysis.ptype.garage;

import com.bank.legalrisk.domain.*;
import com.bank.legalrisk.ml.LandAnalysisMLClient;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Land rights analyzer:
 * - Validates presence of land rights and lease term (base rules)
 * - Calls ML client to predict land-dispute risk (stubbed)
 */
@Component
public class LandRightsAnalyzer {

    private final LandAnalysisMLClient ml;

    public LandRightsAnalyzer(LandAnalysisMLClient ml) {
        this.ml = ml;
    }

    public List<RiskItem> analyze(EGRNData egrn, String regionCode) {
        List<RiskItem> out = new ArrayList<>();
        if (egrn.landRightType() == null || egrn.landRightType().isBlank()) {
            out.add(new RiskItem(RiskType.LAND_RIGHT, "LAND-NONE",
                    "Отсутствуют права на землю под гаражом.", RiskLevel.CRITICAL, OffsetDateTime.now()));
            return out;
        }
        if ("LEASE".equalsIgnoreCase(egrn.landRightType())) {
            Integer years = egrn.leaseYearsRemaining();
            if (years != null && years < 5) {
                out.add(new RiskItem(RiskType.LAND_RIGHT, "LAND-LEASE-SHORT",
                        "Осталось менее 5 лет аренды земельного участка.", RiskLevel.HIGH, OffsetDateTime.now()));
            }
        }
        int mlScore = ml.predictLandRiskScore(egrn.landRightType(), egrn.leaseYearsRemaining(), regionCode);
        if (mlScore >= 60) {
            out.add(new RiskItem(RiskType.LAND_RIGHT, "LAND-ML-HIGH",
                    "ML прогноз высокого риска земельных споров: " + mlScore, RiskLevel.HIGH, OffsetDateTime.now()));
        }
        return out;
    }
}
