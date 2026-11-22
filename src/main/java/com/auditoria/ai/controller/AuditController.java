package com.auditoria.ai.controller;

import com.auditoria.ai.dto.NaturalAuditRequest;
import com.auditoria.ai.service.AuditService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/audit")
public class AuditController {

    private final AuditService auditService;

    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }

    @PostMapping(value = "/natural-text/hydrogen", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> generateNaturalTextWithHydrogen(
            @Valid @RequestBody NaturalAuditRequest request
    ) {
        return ResponseEntity.ok(auditService.generateNaturalTextWithHydrogen(request));
    }

    @PostMapping(value = "/natural-text/flash", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> generateNaturalTextWithFlash(
            @Valid @RequestBody NaturalAuditRequest request
    ) {
        return ResponseEntity.ok(auditService.generateNaturalTextWithFlash(request));
    }

    @PostMapping(value = "/natural-text/flash-lite", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> generateNaturalTextWithFlashLite(
            @Valid @RequestBody NaturalAuditRequest request
    ) {
        return ResponseEntity.ok(auditService.generateNaturalTextWithFlashLite(request));
    }
}
