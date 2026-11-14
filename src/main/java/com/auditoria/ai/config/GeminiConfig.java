package com.auditoria.ai.config;

import com.google.genai.Client;
import org.springframework.ai.google.genai.GoogleGenAiChatModel;
import org.springframework.ai.google.genai.GoogleGenAiChatOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeminiConfig {

    @Value("${spring.ai.google.genai.api-key}")
    private String apiKey;

    @Bean
    public Client genAiClient() {
        return Client.builder()
                .apiKey(apiKey)
                .build();
    }

    @Bean
    public GoogleGenAiChatModel hydrogenChatModel(Client client) {
        return createModel(client, "gemini-4-hydrogen");
    }

    @Bean
    public GoogleGenAiChatModel flashChatModel(Client client) {
        return createModel(client, "gemini-3.8-flash");
    }

    @Bean
    public GoogleGenAiChatModel flashLiteChatModel(Client client) {
        return createModel(client, "gemini-3.5-flash-lite");
    }

    private GoogleGenAiChatModel createModel(Client client, String modelName) {

        GoogleGenAiChatOptions options = GoogleGenAiChatOptions.builder()
                .model(modelName)
                .temperature(0.2)
                .build();

        return GoogleGenAiChatModel.builder()
                .genAiClient(client)
                .options(options)
                .build();
    }
}