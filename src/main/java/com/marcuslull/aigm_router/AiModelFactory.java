package com.marcuslull.aigm_router;

import com.google.cloud.vertexai.VertexAI;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatModel;
import org.springframework.stereotype.Component;

@Component
public class AiModelFactory {

    public static ChatClient createAiModel(String type) {

        return switch (type) {
            case ("narrative") -> ChatClient
                    .builder(VertexAiGeminiChatModel
                            .builder()
                            .vertexAI(new VertexAI())
                            .build())
                    .defaultSystem(Prompts.generalSystemPrompt + Prompts.narrativeSystemPrompt)
                    .defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory()))
                    .build();

            case ("social") -> ChatClient
                    .builder(VertexAiGeminiChatModel
                            .builder()
                            .vertexAI(new VertexAI())
                            .build())
                    .defaultSystem(Prompts.generalSystemPrompt + Prompts.socialSystemPrompt)
                    .defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory()))
                    .build();

            case ("history") -> ChatClient
                    .builder(VertexAiGeminiChatModel
                            .builder()
                            .vertexAI(new VertexAI())
                            .build())
                    .defaultSystem(Prompts.generalSystemPrompt + Prompts.historySystemPrompt)
                    .defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory()))
                    .build();

            case ("combat") -> ChatClient
                    .builder(VertexAiGeminiChatModel
                            .builder()
                            .vertexAI(new VertexAI())
                            .build())
                    .defaultSystem(Prompts.generalSystemPrompt + Prompts.combatSystemPrompt)
                    .defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory()))
                    .build();

            case ("handoff") -> ChatClient
                    .builder(VertexAiGeminiChatModel
                            .builder()
                            .vertexAI(new VertexAI())
                            .build())
                    .defaultSystem(Prompts.handoffSystemPrompt)
                    .defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory()))
                    .build();

            case ("continuity") -> ChatClient
                    .builder(VertexAiGeminiChatModel
                            .builder()
                            .vertexAI(new VertexAI())
                            .build())
                    .defaultSystem(Prompts.continuitySystemPrompt)
                    .defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory()))
                    .build();

            default -> throw new RuntimeException(":(");
        };
    }
}
