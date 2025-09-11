package com.bank.legalrisk.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for property-type-specific risks.
 * Holds a specific risk score and the list of recommended contract clauses.
 */
public abstract class PropertySpecificRisks {
    public int specificRiskScore;
    protected final List<ContractClause> clauses = new ArrayList<>();

    public int getSpecificRiskScore() {
        return specificRiskScore;
    }

    public List<ContractClause> getClauses() {
        return clauses;
    }
}
