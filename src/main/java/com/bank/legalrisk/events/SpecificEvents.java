package com.bank.legalrisk.events;

/**
 * Command to request EGRN (Росреестр) data via IntegrationService.
 */
public final class RosreestrDataRequestedEvent extends LegalRiskEvent {
    public RosreestrDataRequestedEvent(String aggregateId, String correlationId, String payloadJson) {
        super("RosreestrDataRequested", aggregateId, correlationId, payloadJson);
    }
}

/**
 * Command to request NBKI credit history via IntegrationService.
 */
final class NBKIDataRequestedEvent extends LegalRiskEvent {
    public NBKIDataRequestedEvent(String aggregateId, String correlationId, String payloadJson) {
        super("NBKIDataRequested", aggregateId, correlationId, payloadJson);
    }
}

/**
 * Command to request expensive CheckPerson verification.
 */
final class CheckPersonVerificationRequestedEvent extends LegalRiskEvent {
    public CheckPersonVerificationRequestedEvent(String aggregateId, String correlationId, String payloadJson) {
        super("CheckPersonVerificationRequested", aggregateId, correlationId, payloadJson);
    }
}
