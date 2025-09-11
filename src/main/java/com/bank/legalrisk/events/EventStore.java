package com.bank.legalrisk.events;

import java.util.List;

/**
 * Append-only event store (abstraction).
 * For the demo we use an in-memory implementation. In production it maps to Cassandra + Debezium CDC → Kafka.
 */
public interface EventStore {
    /**
     * Append (only) a new event to the store.
     */
    void append(LegalRiskEvent event);

    /**
     * Read all events for an aggregate (e.g., cadastral number) in insertion order.
     */
    List<LegalRiskEvent> loadByAggregateId(String aggregateId);
}
