package com.bank.legalrisk.api;

import com.bank.legalrisk.core.LegalRiskProcessor;
import com.bank.legalrisk.domain.LegalRiskAssessment;
import com.bank.legalrisk.domain.PersonCheckResult;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.*;

/**
 * Minimal REST layer to drive the processor for demo/manual testing.
 */
@RestController
@RequestMapping("/legal-risk")
public class LegalRiskController {

    private final LegalRiskProcessor processor;

    public LegalRiskController(LegalRiskProcessor processor) {
        this.processor = processor;
    }

    /**
     * Analyze legal risks by cadastral number (creates ES commands + runs analyzers).
     */
    @PostMapping("/analyze")
    public LegalRiskAssessment analyze(@RequestParam @NotBlank String cadastralNumber) {
        return processor.analyzeLegalRisks(cadastralNumber);
    }

    /**
     * Request a CheckPerson verification.
     */
    @PostMapping("/check-person")
    public PersonCheckResult checkPerson(@RequestParam @NotBlank String ownerId,
                                         @RequestParam @NotBlank String justification) {
        return processor.requestCheckPersonVerification(ownerId, justification);
    }
}
