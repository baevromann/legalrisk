package com.bank.legalrisk.events;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Simple in-memory append-only store.
 * <p><b>Important:</b> there is no update/delete operation by design (append-only).</p>
 * In production this would be backed by Cassandra with a table like:
 * <pre>
 *   CREATE TABLE legal_risk_events (
 *       aggregate_id text,
 *       created_at timestamp,
 *       id text,
 *       type text,
 *       correlation_id text,
 *       payload_json text,
 *       PRIMARY KEY ((aggregate_id), created_at, id)
 *   ) WITH CLUSTERING ORDER BY (created_at ASC, id ASC);
 * </pre>
 * Debezium CDC would publish INSERTs to Kafka topics per integration.
 */
@Repository
public class InMemoryEventStore implements EventStore {

    private final Map<String, List<LegalRiskEvent>> storage = new ConcurrentHashMap<>();

    @Override
    public void append(LegalRiskEvent event) {
        storage.computeIfAbsent(event.getAggregateId(), k -> new ArrayList<>()).add(event);
    }

    @Override
    public List<LegalRiskEvent> loadByAggregateId(String aggregateId) {
        return storage.getOrDefault(aggregateId, List.of());
    }
}
