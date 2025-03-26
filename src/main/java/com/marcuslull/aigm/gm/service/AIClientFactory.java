package com.marcuslull.aigm.gm.service;

import com.google.cloud.vertexai.VertexAI;
import com.marcuslull.aigm.gm.model.enums.AIName;
import com.marcuslull.aigm.gm.prompts.SystemPrompts;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatModel;


public class AIClientFactory {
    private final VertexAI vertexAI;

    AIClientFactory(VertexAI vertexAI) {
        this.vertexAI = vertexAI;
    }

    ChatClient createAiModel(AIName type) {

        return switch (type) {
            case ORATORIX -> ChatClient
                    .builder(VertexAiGeminiChatModel
                            .builder()
                            .vertexAI(this.vertexAI)
                            .build())
                    .defaultSystem(SystemPrompts.devMode + SystemPrompts.generalSystemPrompt + SystemPrompts.oratorixSystemPrompt)
                    .defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory()))
                    .build();

            case ORBIS -> ChatClient
                    .builder(VertexAiGeminiChatModel
                            .builder()
                            .vertexAI(this.vertexAI)
                            .build())
                    .defaultSystem(SystemPrompts.devMode + SystemPrompts.generalSystemPrompt + SystemPrompts.orbisSystemPrompt)
                    .defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory()))
                    .build();

            case CHRONOS -> ChatClient
                    .builder(VertexAiGeminiChatModel
                            .builder()
                            .vertexAI(this.vertexAI)
                            .build())
                    .defaultSystem(SystemPrompts.devMode + SystemPrompts.generalSystemPrompt + SystemPrompts.chronosSystemPrompt)
                    .defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory()))
                    .build();

            case JUSTIVOR -> ChatClient
                    .builder(VertexAiGeminiChatModel
                            .builder()
                            .vertexAI(this.vertexAI)
                            .build())
                    .defaultSystem(SystemPrompts.devMode + SystemPrompts.generalSystemPrompt + SystemPrompts.justivorSystemPrompt)
                    .defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory()))
                    .build();

            case CONTINUITY -> ChatClient
                    .builder(VertexAiGeminiChatModel
                            .builder()
                            .vertexAI(this.vertexAI)
                            .build())
                    .defaultSystem(SystemPrompts.continuitySystemPrompt)
                    .defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory()))
                    .build();

            case PLAYER -> null;
        };
    }
}
