package com.bank.legalrisk.events;

/**
 * Command to request expensive CheckPerson verification.
 */
public final class CheckPersonVerificationRequestedEvent extends LegalRiskEvent {
    public CheckPersonVerificationRequestedEvent(String aggregateId, String correlationId, String payloadJson) {
        super("CheckPersonVerificationRequested", aggregateId, correlationId, payloadJson);
    }
}
