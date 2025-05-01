package com.marcuslull.aigm.communication.models;

import com.google.cloud.vertexai.VertexAI;
import com.marcuslull.aigm.communication.destinations.Model;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatModel;

public abstract class VertexModel implements Model {

    private final String name;
    private final ChatClient chatClient;
    private String generalSystemPrompt;
    private String systemPrompt;
    private final MessageChatMemoryAdvisor messageChatMemoryAdvisor;
    private final VertexAI vertexAI;

    public VertexModel(String name, VertexAI vertexAI) {
        this.name = name;
        this.vertexAI = vertexAI;
        this.messageChatMemoryAdvisor = new MessageChatMemoryAdvisor(new InMemoryChatMemory());
        this.chatClient = ChatClient.builder(
                VertexAiGeminiChatModel.builder().vertexAI(this.vertexAI).build())
                        .defaultSystem(this.generalSystemPrompt + this.systemPrompt)
                        .defaultAdvisors(messageChatMemoryAdvisor)
                        .build();
    }

    public String getName() {
        return this.name;
    }

    public ChatClient getChatClient() {
        return this.chatClient;
    }

    public String getGeneralSystemPrompt() {
        return this.generalSystemPrompt;
    }

    public void setGeneralSystemPrompt(String generalSystemPrompt) {
        this.generalSystemPrompt = generalSystemPrompt;
    }

    public String getSystemPrompt() {
        return this.systemPrompt;
    }

    public void setSystemPrompt(String systemPrompt) {
        this.systemPrompt = systemPrompt;
    }
}
