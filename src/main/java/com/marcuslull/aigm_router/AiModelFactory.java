package com.marcuslull.aigm_router;

import com.google.cloud.vertexai.VertexAI;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatModel;
import org.springframework.stereotype.Component;

@Component
public class AiModelFactory {

    private static final VertexAI vertexAI = new VertexAI();

    public static AiModel getAiModel(String type) {

        return switch (type) {
            case ("narrative") -> new AiModel("narrative",
                    ChatClient
                            .builder(VertexAiGeminiChatModel
                                    .builder()
                                    .vertexAI(vertexAI)
                                    .build())
                            .defaultSystem(Prompts.generalSystemPrompt + Prompts.narrativeSystemPrompt)
                            .build());

            case ("social") -> new AiModel("social",
                    ChatClient
                            .builder(VertexAiGeminiChatModel
                                    .builder()
                                    .vertexAI(vertexAI)
                                    .build())
                            .defaultSystem(Prompts.generalSystemPrompt + Prompts.socialSystemPrompt)
                            .build());

            case ("history") -> new AiModel("history",
                    ChatClient
                            .builder(VertexAiGeminiChatModel
                                    .builder()
                                    .vertexAI(vertexAI)
                                    .build())
                            .defaultSystem(Prompts.generalSystemPrompt + Prompts.historySystemPrompt)
                            .build());

            case ("combat") -> new AiModel("combat",
                    ChatClient
                            .builder(VertexAiGeminiChatModel
                                    .builder()
                                    .vertexAI(vertexAI)
                                    .build())
                            .defaultSystem(Prompts.generalSystemPrompt + Prompts.combatSystemPrompt)
                            .build());

            case ("handoff") -> new AiModel("handoff",
                    ChatClient
                            .builder(VertexAiGeminiChatModel
                                    .builder()
                                    .vertexAI(vertexAI)
                                    .build())
                            .defaultSystem(Prompts.handoffSystemPrompt)
                            .build());

            case ("continuity") -> new AiModel("continuity",
                    ChatClient
                            .builder(VertexAiGeminiChatModel
                                    .builder()
                                    .vertexAI(vertexAI)
                                    .build())
                            .defaultSystem(Prompts.continuitySystemPrompt)
                            .build());

            default -> throw new RuntimeException(":(");
        };
    }
}
