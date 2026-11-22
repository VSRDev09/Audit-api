package com.auditoria.ai.dto;

import jakarta.validation.constraints.NotBlank;

public record NaturalAuditRequest(
        @NotBlank(message = "entrada é obrigatória.")
        String entrada
) {
}
