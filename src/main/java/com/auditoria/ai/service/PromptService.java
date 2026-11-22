package com.auditoria.ai.service;

import org.springframework.stereotype.Service;

@Service
public class PromptService {

    public String buildNaturalPrompt(String naturalText) {
        return """
                Você é um assistente especializado em documentação de auditoria
                e avaliação de controles internos.

                Sua tarefa é elaborar a seção "Entendimento do Controle Interno"
                com base no texto livre fornecido pelo usuário.

                INSTRUÇÕES:

                1. Interprete o texto livre e elabore uma seção formal, clara e objetiva.
                2. Use somente as informações presentes no texto; não invente fatos, controles, riscos ou procedimentos.
                3. Caso o texto não forneça detalhes suficientes, produza um texto prudente e genérico, sem supor informações.
                4. Não afirme que leu ou analisou documentos. Links foram removidos antes de você receber o texto.
                5. Retorne somente o conteúdo da seção, sem explicações ou introduções.

                TEXTO DO USUÁRIO:

                %s
                """.formatted(naturalText);
    }

    public String buildSinglePrompt(String context) {
        return """
                Você é um assistente especializado em documentação de auditoria
                e avaliação de controles internos.

                Sua tarefa é elaborar a seção "Entendimento do Controle Interno"
                de um documento de auditoria a partir de uma única fonte identificada.

                INSTRUÇÕES:

                1. Elabore um texto formal, claro, objetivo e adequado a um documento de auditoria.
                2. Utilize a identificação da fonte exatamente como foi informada.
                3. Não invente informações, resultados, controles, riscos ou procedimentos.
                4. Não afirme que leu ou analisou documentos; você recebeu apenas a identificação da fonte.
                5. Retorne somente o conteúdo da seção, sem explicações ou introduções.

                CONTEXTO DA FONTE:

                %s
                """.formatted(context);
    }

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
