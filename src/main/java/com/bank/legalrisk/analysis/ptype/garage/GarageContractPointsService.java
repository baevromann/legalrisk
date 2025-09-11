package com.bank.legalrisk.analysis.ptype.garage;

import com.bank.legalrisk.domain.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Generates contract clauses specific for garages (Task #2, §2.5).
 */
public class GarageContractPointsService {

    public List<ContractClause> generate(GarageSpecificRisks risks) {
        List<ContractClause> clauses = new ArrayList<>();
        if (!risks.landRightRisks.isEmpty()) {
            clauses.add(new ContractClause("GAR-LAND-ESCROW",
                    "Платеж по сделке проводится через эскроу до подтверждения прав на землю."));
        }
        if (!risks.gskMembershipRisks.isEmpty()) {
            clauses.add(new ContractClause("GAR-GSK-LETTER",
                    "Обязательное письмо от правления ГСК об отсутствии задолженностей и споров."));
        }
        if (!risks.unauthorizedConstructionRisks.isEmpty()) {
            clauses.add(new ContractClause("GAR-UNAUTH-PENALTY",
                    "Штрафная оговорка за выявление самовольной постройки после сделки."));
        }
        if (!risks.accessRightRisks.isEmpty()) {
            clauses.add(new ContractClause("GAR-ACCESS-EASEMENT",
                    "Условие о предоставлении сервитута/проезда на территорию ГСК."));
        }
        return clauses;
    }
}
