package com.auditoria.ai.service;

import com.auditoria.ai.dto.AuditRequest;
import com.auditoria.ai.dto.AuditResponse;
import com.auditoria.ai.dto.ContentBlock;
import com.auditoria.ai.dto.SourceRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuditResponseBuilder {

    public AuditResponse build(String generatedText, AuditRequest request) {

        if (generatedText == null || generatedText.isBlank()) {
            throw new IllegalArgumentException("A resposta da IA está vazia.");
        }

        SourceRequest controleInterno = request.controleInterno();
        SourceRequest reuniao = request.reuniao();

        validateSource(controleInterno);
        validateSource(reuniao);

        List<ContentBlock> blocks = new ArrayList<>();

        String controleName = controleInterno.nome();
        String reuniaoName = reuniao.nome();

        int position = 0;

        boolean controleFound = false;
        boolean reuniaoFound = false;

        while (position < generatedText.length()) {

            int controleIndex = generatedText.indexOf(controleName, position);
            int reuniaoIndex = generatedText.indexOf(reuniaoName, position);

            if (controleIndex == -1 && reuniaoIndex == -1) {
                break;
            }

            boolean isControle =
                    controleIndex != -1 &&
                    (reuniaoIndex == -1 || controleIndex <= reuniaoIndex);

            int nextIndex = isControle ? controleIndex : reuniaoIndex;

            addTextBlock(blocks, generatedText.substring(position, nextIndex));

            SourceRequest source = isControle
                    ? controleInterno
                    : reuniao;

            blocks.add(new ContentBlock(
                    "LINK",
                    source.nome(),
                    source.url()
            ));

            if (isControle) {
                controleFound = true;
                position = controleIndex + controleName.length();
            } else {
                reuniaoFound = true;
                position = reuniaoIndex + reuniaoName.length();
            }
        }

        addTextBlock(blocks, generatedText.substring(position));

        if (!controleFound) {
            throw new IllegalArgumentException(
                    "A resposta da IA não incluiu o nome da fonte: " + controleName
            );
        }

        if (!reuniaoFound) {
            throw new IllegalArgumentException(
                    "A resposta da IA não incluiu o nome da fonte: " + reuniaoName
            );
        }

        return new AuditResponse(
                "Entendimento do Controle Interno",
                blocks
        );
    }

    private void addTextBlock(List<ContentBlock> blocks, String text) {
        if (!text.isEmpty()) {
            blocks.add(new ContentBlock("TEXT", text, null));
        }
    }

    private void validateSource(SourceRequest source) {
        if (source == null ||
                source.nome() == null ||
                source.nome().isBlank() ||
                source.url() == null ||
                source.url().isBlank()) {

            throw new IllegalArgumentException(
                    "Os dados da fonte estão incompletos."
            );
        }
    }
}