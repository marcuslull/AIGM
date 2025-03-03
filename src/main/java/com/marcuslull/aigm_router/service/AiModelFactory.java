package com.marcuslull.aigm_router.service;

import com.google.cloud.vertexai.VertexAI;
import com.marcuslull.aigm_router.config.Prompts;
import com.marcuslull.aigm_router.model.AIModelType;
import com.marcuslull.aigm_router.tooling.CommunicationTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatModel;
import org.springframework.stereotype.Component;


@Component
public class AiModelFactory {

    private final CommunicationTool communicationTool;

    public AiModelFactory(CommunicationTool communicationTool) {
        this.communicationTool = communicationTool;
    }

    public ChatClient createAiModel(AIModelType type) {

        return switch (type) {
            case ORATORIX -> ChatClient
                    .builder(VertexAiGeminiChatModel
                            .builder()
                            .vertexAI(new VertexAI())
                            .build())
                    .defaultSystem(Prompts.generalSystemPrompt + Prompts.oratorixSystemPrompt)
                    .defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory()))
                    .defaultTools(communicationTool)
                    .build();

            case ORBIS -> ChatClient
                    .builder(VertexAiGeminiChatModel
                            .builder()
                            .vertexAI(new VertexAI())
                            .build())
                    .defaultSystem(Prompts.generalSystemPrompt + Prompts.orbisSystemPrompt)
                    .defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory()))
                    .defaultTools(communicationTool)
                    .build();

            case CHRONOS -> ChatClient
                    .builder(VertexAiGeminiChatModel
                            .builder()
                            .vertexAI(new VertexAI())
                            .build())
                    .defaultSystem(Prompts.generalSystemPrompt + Prompts.chronosSystemPrompt)
                    .defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory()))
                    .defaultTools(communicationTool)
                    .build();

            case JUSTIVOR -> ChatClient
                    .builder(VertexAiGeminiChatModel
                            .builder()
                            .vertexAI(new VertexAI())
                            .build())
                    .defaultSystem(Prompts.generalSystemPrompt + Prompts.justivorSystemPrompt)
                    .defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory()))
                    .defaultTools(communicationTool)
                    .build();

            case HANDOFF -> ChatClient
                    .builder(VertexAiGeminiChatModel
                            .builder()
                            .vertexAI(new VertexAI())
                            .build())
                    .defaultSystem(Prompts.handoffSystemPrompt)
                    .defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory()))
                    .build();

            case CONTINUITY -> ChatClient
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
