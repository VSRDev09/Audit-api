package com.auditoria.ai.dto;

import java.util.List;

public record AuditResponse(
        String titulo,
        List<ContentBlock> blocos
) {
}