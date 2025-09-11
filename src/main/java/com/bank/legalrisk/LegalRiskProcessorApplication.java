package com.bank.legalrisk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the LegalRiskProcessor Spring Boot application.
 * <p>
 * This module implements two core tasks described in легенда6.docx:
 * <ol>
 *   <li><b>Task #1</b> — Event Sourcing architecture for external integrations.
 *       The processor only <i>creates command events</i> and appends them to an append-only event store.
 *       Actual HTTP/SOAP interactions are performed by separate integration services (event handlers).</li>
 *   <li><b>Task #2</b> — Specialized <i>Garage</i> analysis with dedicated analyzers, scoring and contract clauses.
 *       The factory produces a Garage analyzer; base rules are implemented and gRPC ML clients are stubbed as interfaces.</li>
 * </ol>
 * The code is production-oriented, yet self-contained to compile without external infra.
 */
@SpringBootApplication
public class LegalRiskProcessorApplication {
    public static void main(String[] args) {
        SpringApplication.run(LegalRiskProcessorApplication.class, args);
    }
}
