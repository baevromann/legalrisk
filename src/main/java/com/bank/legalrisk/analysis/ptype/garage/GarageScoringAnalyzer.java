package com.bank.legalrisk.analysis.ptype.garage;

import com.bank.legalrisk.domain.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Specialized scoring for garages (Task #2, §2.4).
 * Weigh land risks at 0.5 and apply interaction coefficient with GSK risks (+30%).
 */
public class GarageScoringAnalyzer {

    private final Map<RiskType, Double> landRightWeights = Map.of(RiskType.LAND_RIGHT, 0.5);
    private final Map<RiskType, Double> gskRiskWeights = Map.of(RiskType.GSK, 0.3);
    private final int unauthorizedConstructionPenalty = 20;

    /**
     * Calculate a 0..100 garage score using weighted risk counts and penalties.
     */
    public int calculateGarageScoring(GarageSpecificRisks risks) {
        int land = risks.landRightRisks.size() * (int) (landRightWeights.get(RiskType.LAND_RIGHT) * 100);
        int gsk = risks.gskMembershipRisks.size() * (int) (gskRiskWeights.get(RiskType.GSK) * 100);
        int unauthorized = risks.unauthorizedConstructionRisks.isEmpty() ? 0 : unauthorizedConstructionPenalty;
        int base = Math.min(100, land + gsk + unauthorized);
        // Interaction: land + GSK add +30%
        return Math.min(100, (int) Math.round(base * 1.3));
    }

    public Map<RiskType, Double> getWeights() {
        Map<RiskType, Double> w = new HashMap<>();
        w.putAll(landRightWeights);
        w.putAll(gskRiskWeights);
        return w;
    }

    public int getUnauthorizedConstructionPenalty() {
        return unauthorizedConstructionPenalty;
    }
}
