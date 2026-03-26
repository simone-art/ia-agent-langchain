package com.example.springboot;

import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AssistantConfig {

    @Value("${gemini.api-key}")
    private String geminiApiKey;

    @Value("${gemini.model}")
    private String geminiModel;

    // metodo que conecta o Spring boot com o LLM do Gemini
    @Bean
    public GoogleAiGeminiChatModel googleAiGeminiChatModel() {
        return GoogleAiGeminiChatModel.builder()
                .apiKey(geminiApiKey)
                .modelName(geminiModel)
                .build();
    }

    @Bean
    public AssistantAIService assistant(GoogleAiGeminiChatModel model, AssistantTools assistantTools) {
        return AiServices.builder(AssistantAIService.class)
                .chatModel(model)
                .tools(assistantTools) // registra a ferramenta no Serviço IA
                .build();
    }
}
