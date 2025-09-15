package com.bank.legalrisk.events;

/**
 * Command to request EGRN (Росреестр) data via IntegrationService.
 */
public final class RosreestrDataRequestedEvent extends LegalRiskEvent {
    public RosreestrDataRequestedEvent(String aggregateId, String correlationId, String payloadJson) {
        super("RosreestrDataRequested", aggregateId, correlationId, payloadJson);
    }
}
