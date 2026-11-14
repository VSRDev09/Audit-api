package com.auditoria.ai.service;

import com.auditoria.ai.dto.AuditRequest;
import com.auditoria.ai.dto.AuditResponse;
import org.springframework.ai.google.genai.GoogleGenAiChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class AuditService {

    private final SourceContextService sourceContextService;
    private final PromptService promptService;
    private final AuditResponseBuilder responseBuilder;

    private final GoogleGenAiChatModel hydrogenChatModel;
    private final GoogleGenAiChatModel flashChatModel;
    private final GoogleGenAiChatModel flashLiteChatModel;

    public AuditService(
            SourceContextService sourceContextService,
            PromptService promptService,
            AuditResponseBuilder responseBuilder,

            @Qualifier("hydrogenChatModel")
            GoogleGenAiChatModel hydrogenChatModel,

            @Qualifier("flashChatModel")
            GoogleGenAiChatModel flashChatModel,

            @Qualifier("flashLiteChatModel")
            GoogleGenAiChatModel flashLiteChatModel
    ) {
        this.sourceContextService = sourceContextService;
        this.promptService = promptService;
        this.responseBuilder = responseBuilder;
        this.hydrogenChatModel = hydrogenChatModel;
        this.flashChatModel = flashChatModel;
        this.flashLiteChatModel = flashLiteChatModel;
    }

    public AuditResponse generateWithHydrogen(AuditRequest request) {
        return generate(request, hydrogenChatModel);
    }

    public AuditResponse generateWithFlash(AuditRequest request) {
        return generate(request, flashChatModel);
    }

    public AuditResponse generateWithFlashLite(AuditRequest request) {
        return generate(request, flashLiteChatModel);
    }

    private AuditResponse generate(
            AuditRequest request,
            GoogleGenAiChatModel chatModel
    ) {
        String context = sourceContextService.buildContext(request);
        String prompt = promptService.buildPrompt(context);
        String generatedText = chatModel.call(prompt);

        return responseBuilder.build(generatedText, request);
    }
}