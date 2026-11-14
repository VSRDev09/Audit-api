package com.auditoria.ai.controller;

import com.auditoria.ai.dto.AuditRequest;
import com.auditoria.ai.dto.AuditResponse;
import com.auditoria.ai.service.AuditService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/audit")
public class AuditController {

    private final AuditService auditService;

    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }

    @PostMapping("/hydrogen")
    public AuditResponse generateWithHydrogen(
            @Valid @RequestBody AuditRequest request
    ) {
        return auditService.generateWithHydrogen(request);
    }

    @PostMapping("/flash")
    public AuditResponse generateWithFlash(
            @Valid @RequestBody AuditRequest request
    ) {
        return auditService.generateWithFlash(request);
    }

    @PostMapping("/flash-lite")
    public AuditResponse generateWithFlashLite(
            @Valid @RequestBody AuditRequest request
    ) {
        return auditService.generateWithFlashLite(request);
    }
}
