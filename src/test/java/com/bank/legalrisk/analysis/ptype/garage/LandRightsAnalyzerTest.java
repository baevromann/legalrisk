package com.bank.legalrisk.analysis.ptype.garage;

import com.bank.legalrisk.domain.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LandRightsAnalyzerTest {

    @Test
    void returnsCriticalRiskWhenLandRightsAreMissing() {
        LandRightsAnalyzer analyzer = new LandRightsAnalyzer((type, years, region) -> 0);
        EGRNData egrn = new EGRNData(
                "77:01:0004012:1111",
                PropertyType.GARAGE,
                List.of(),
                new PropertyHistory(List.of(), List.of()),
                "",
                null,
                false,
                "",
                null,
                null
        );

        List<RiskItem> risks = analyzer.analyze(egrn, "77");

        assertEquals(1, risks.size());
        RiskItem risk = risks.get(0);
        assertEquals("LAND-NONE", risk.code());
        assertEquals(RiskLevel.CRITICAL, risk.level());
    }

    @Test
    void detectsShortLeaseAndHighMlScore() {
        LandRightsAnalyzer analyzer = new LandRightsAnalyzer((type, years, region) -> 70);
        EGRNData egrn = new EGRNData(
                "77:01:0004012:2222",
                PropertyType.GARAGE,
                List.of(),
                new PropertyHistory(List.of(), List.of()),
                "LEASE",
                3,
                true,
                "ACTIVE",
                10,
                2
        );

        List<RiskItem> risks = analyzer.analyze(egrn, "77");

        assertEquals(2, risks.size());
        assertTrue(risks.stream().anyMatch(r -> r.code().equals("LAND-LEASE-SHORT")));
        assertTrue(risks.stream().anyMatch(r -> r.code().equals("LAND-ML-HIGH")));
        assertTrue(risks.stream().allMatch(r -> r.level() == RiskLevel.HIGH));
    }
}
