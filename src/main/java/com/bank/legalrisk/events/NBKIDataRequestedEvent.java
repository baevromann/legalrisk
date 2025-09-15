package com.bank.legalrisk.events;

/**
 * Command to request NBKI credit history via IntegrationService.
 */
public final class NBKIDataRequestedEvent extends LegalRiskEvent {
    public NBKIDataRequestedEvent(String aggregateId, String correlationId, String payloadJson) {
        super("NBKIDataRequested", aggregateId, correlationId, payloadJson);
    }
}
