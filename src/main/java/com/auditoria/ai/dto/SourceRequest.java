package com.auditoria.ai.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;

public record SourceRequest(
        @NotBlank(message = "descricao é obrigatória.")
        String descricao,

        @NotBlank(message = "nome é obrigatório.")
        String nome,

        @NotBlank(message = "url é obrigatória.")
        @URL(message = "url deve ser uma URL válida.")
        @Pattern(regexp = "https?://.+", message = "url deve usar http ou https.")
        String url
) {
}
