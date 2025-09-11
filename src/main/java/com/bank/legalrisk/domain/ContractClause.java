package com.bank.legalrisk.domain;

import jakarta.validation.constraints.NotBlank;

/**
 * Contract clause to be added into the pledge/loan agreement to mitigate risks.
 */
public record ContractClause(@NotBlank String code, @NotBlank String text) {
}
