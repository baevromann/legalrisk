package com.bank.legalrisk.analysis.ptype;

import com.bank.legalrisk.analysis.ptype.commercial.CommercialPropertyAnalyzer;
import com.bank.legalrisk.analysis.ptype.garage.*;
import com.bank.legalrisk.analysis.ptype.residential.ResidentialPropertyAnalyzer;
import com.bank.legalrisk.domain.PropertyType;
import com.bank.legalrisk.ml.GSKRiskMLClientStub;
import com.bank.legalrisk.ml.LandAnalysisMLClientStub;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Factory that creates specialized analyzers by property type (UML "PropertyTypeAnalysisFactory").
 * For GARAGE the analyzer is fully implemented as per Task #2.
 */
@Configuration
public class PropertyTypeAnalysisFactory {

    @Bean
    public ResidentialPropertyAnalyzer residentialAnalyzer() {
        return new ResidentialPropertyAnalyzer();
    }

    @Bean
    public CommercialPropertyAnalyzer commercialAnalyzer() {
        return new CommercialPropertyAnalyzer();
    }

    @Bean
    public GarageScoringAnalyzer garageScoringAnalyzer() {
        return new GarageScoringAnalyzer();
    }

    @Bean
    public GarageContractPointsService garageContractPointsService() {
        return new GarageContractPointsService();
    }

    @Bean
    public GaragePropertyAnalyzer garageAnalyzer(LandRightsAnalyzer land, GSKAnalyzer gsk,
                                                 GarageScoringAnalyzer scoring, GarageContractPointsService contract) {
        return new GaragePropertyAnalyzer(land, gsk, scoring, contract);
    }

    /**
     * Create analyzer by type. Extend when more types are added.
     */
    public PropertyTypeAnalyzer createAnalyzer(PropertyType type) {
        return switch (type) {
            case GARAGE -> garageAnalyzer(new LandRightsAnalyzer(new LandAnalysisMLClientStub()),
                    new GSKAnalyzer(new GSKRiskMLClientStub()),
                    garageScoringAnalyzer(), garageContractPointsService());
            case COMMERCIAL_PROPERTY -> commercialAnalyzer();
            default -> residentialAnalyzer();
        };
    }
}
