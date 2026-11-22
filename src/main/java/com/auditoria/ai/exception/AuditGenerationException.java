package com.auditoria.ai.exception;

public class AuditGenerationException extends RuntimeException {

    public AuditGenerationException(String message) {
        super(message);
    }

    public AuditGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
}
