package com.marcuslull.aigm_router.service;

import com.google.cloud.vertexai.VertexAI;
import com.marcuslull.aigm_router.model.enums.AIName;
import com.marcuslull.aigm_router.prompts.SystemPrompts;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatModel;
import org.springframework.stereotype.Service;


@Service
public class AIClientFactory {

    private final VectorStore vectorStore;
    private final VertexAI vertexAI;

    public AIClientFactory(VectorStore vectorStore, VertexAI vertexAI) {
        this.vectorStore = vectorStore;
        this.vertexAI = vertexAI;
    }

    public ChatClient createAiModel(AIName type) {

        return switch (type) {
            case ORATORIX -> ChatClient
                    .builder(VertexAiGeminiChatModel
                            .builder()
                            .vertexAI(this.vertexAI)
                            .build())
                    .defaultSystem(SystemPrompts.devMode + SystemPrompts.generalSystemPrompt + SystemPrompts.oratorixSystemPrompt)
                    .defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory()), new QuestionAnswerAdvisor(vectorStore))
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
        };
    }
}
