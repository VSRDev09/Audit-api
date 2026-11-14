package com.auditoria.ai.service;

import org.springframework.stereotype.Service;

import com.auditoria.ai.dto.AuditRequest;
import com.auditoria.ai.dto.SourceRequest;

@Service
public class SourceContextService {

    public String buildContext(AuditRequest request) {

        SourceRequest controleInterno = request.controleInterno();
        SourceRequest reuniao = request.reuniao();

        return """
                FONTES DE INFORMAÇÃO

                1. CONTROLE INTERNO
                Descrição: %s
                Nome do documento: %s

                2. REUNIÃO PRELIMINAR
                Descrição: %s
                Nome do documento: %s
                """.formatted(
                controleInterno.descricao(),
                controleInterno.nome(),
                reuniao.descricao(),
                reuniao.nome()
        );
    }
}