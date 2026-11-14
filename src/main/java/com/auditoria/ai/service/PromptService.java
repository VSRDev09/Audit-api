package com.auditoria.ai.service;

import org.springframework.stereotype.Service;

@Service
public class PromptService {

    public String buildPrompt(String context) {

        return """
                Você é um assistente especializado em documentação de auditoria
                e avaliação de controles internos.

                Sua tarefa é elaborar a seção "Entendimento do Controle Interno"
                de um documento de auditoria.

                ESTRUTURA DE REFERÊNCIA:

                "A identificação dos controles internos está inserida no papel
                de trabalho 'Questionário de Avaliação de Controle Interno',
                e a avaliação dos riscos de controle consta da
                'Matriz de Riscos e Controles - MRC'.

                As informações coletadas sobre o processo de trabalho constam
                das 'Atas de Reunião Preliminar', utilizadas para o mapeamento
                do processo."

                INSTRUÇÕES:

                1. Utilize as informações fornecidas para elaborar um texto
                   formal, claro, objetivo e adequado a um documento de auditoria.

                2. Preserve o sentido e a finalidade da estrutura de referência.

                3. Utilize os nomes dos documentos exatamente como foram
                   informados nas fontes.

                4. Não invente informações, resultados, controles, riscos
                   ou procedimentos que não estejam descritos no contexto.

                5. Não afirme que leu ou analisou o conteúdo dos documentos.
                   Você recebeu apenas suas descrições e identificações.

                6. A Matriz de Riscos e Controles - MRC é uma referência fixa
                   da estrutura de auditoria. Não solicite uma fonte adicional
                   para ela.

                7. Retorne somente o conteúdo da seção, sem explicações,
                   comentários ou introduções sobre sua resposta.

                CONTEXTO DAS FONTES:

                %s
                """.formatted(context);
    }
}