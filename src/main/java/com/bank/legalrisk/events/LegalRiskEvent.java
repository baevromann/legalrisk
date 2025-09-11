package com.bank.legalrisk.events;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Base event stored in EventStore. Implements the append-only model (Task #1).
 * Only the Command Service (LegalRiskProcessor) creates these events.
 * External "handlers" react to CDC/Kafka and perform real HTTP/SOAP calls.
 */
public class LegalRiskEvent {
    private final String id = UUID.randomUUID().toString();
    private final String type;
    private final String aggregateId;
    private final String correlationId;
    private final String payloadJson;
    private final OffsetDateTime createdAt = OffsetDateTime.now();

    public LegalRiskEvent(String type, String aggregateId, String correlationId, String payloadJson) {
        this.type = type;
        this.aggregateId = aggregateId;
        this.correlationId = correlationId;
        this.payloadJson = payloadJson;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getAggregateId() {
        return aggregateId;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public String getPayloadJson() {
        return payloadJson;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }
}
