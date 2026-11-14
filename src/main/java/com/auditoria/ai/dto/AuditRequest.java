package com.auditoria.ai.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record AuditRequest(
        @NotNull(message = "controleInterno é obrigatório.")
        @Valid
        SourceRequest controleInterno,

        @NotNull(message = "reuniao é obrigatório.")
        @Valid
        SourceRequest reuniao
) {
}
