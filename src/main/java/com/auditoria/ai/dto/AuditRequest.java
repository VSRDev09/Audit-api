package com.auditoria.ai.dto;

public record AuditRequest(
        SourceRequest controleInterno,
        SourceRequest reuniao
) {
}