package com.marcuslull.aigm_router.legacy;

import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.chat.metadata.DefaultUsage;

import java.util.List;
import java.util.Map;

public class AIClientChatResponse {

    private Metadata metadata;
    private List<Generation> generations;

    public AIClientChatResponse() {
    }

    public AIClientChatResponse(Metadata metadata, List<Generation> generations) {
        this.metadata = metadata;
        this.generations = generations;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public List<Generation> getGenerations() {
        return generations;
    }

    public void setGenerations(List<Generation> generations) {
        this.generations = generations;
    }

    @Override
    public String toString() {
        return "ChatResponsePojo{" +
                "metadata=" + metadata +
                ", generations=" + generations +
                '}';
    }

    public static class Metadata {
        private String id;
        private DefaultUsage usage;
        private String rateLimit;

        public Metadata() {
        }

        public Metadata(String id, DefaultUsage usage, String rateLimit) {
            this.id = id;
            this.usage = usage;
            this.rateLimit = rateLimit;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public DefaultUsage getUsage() {
            return usage;
        }

        public void setUsage(DefaultUsage usage) {
            this.usage = usage;
        }

        public String getRateLimit() {
            return rateLimit;
        }

        public void setRateLimit(String rateLimit) {
            this.rateLimit = rateLimit;
        }

        @Override
        public String toString() {
            return "Metadata{" +
                    "id='" + id + '\'' +
                    ", usage=" + usage +
                    ", rateLimit=" + rateLimit +
                    '}';
        }
    }

    public static class Generation {
        private AssistantMessage assistantMessage;
        private ChatGenerationMetadata chatGenerationMetadata;

        public Generation() {
        }

        public Generation(AssistantMessage assistantMessage, ChatGenerationMetadata chatGenerationMetadata) {
            this.assistantMessage = assistantMessage;
            this.chatGenerationMetadata = chatGenerationMetadata;
        }

        public AssistantMessage getAssistantMessage() {
            return assistantMessage;
        }

        public void setAssistantMessage(AssistantMessage assistantMessage) {
            this.assistantMessage = assistantMessage;
        }

        public ChatGenerationMetadata getChatGenerationMetadata() {
            return chatGenerationMetadata;
        }

        public void setChatGenerationMetadata(ChatGenerationMetadata chatGenerationMetadata) {
            this.chatGenerationMetadata = chatGenerationMetadata;
        }

        @Override
        public String toString() {
            return "Generation{" +
                    "assistantMessage=" + assistantMessage +
                    ", chatGenerationMetadata=" + chatGenerationMetadata +
                    '}';
        }
    }

    public static class AssistantMessage {
        private MessageType messageType;
        private List<String> toolCalls;
        private String textContent;
        private Map<String, Object> metadata;
        private String utteranceType;

        public AssistantMessage() {
        }

        public AssistantMessage(MessageType messageType, List<String> toolCalls, String textContent, Map<String, Object> metadata, String utteranceType) {
            this.messageType = messageType;
            this.toolCalls = toolCalls;
            this.textContent = textContent;
            this.metadata = metadata;
            this.utteranceType = utteranceType;
        }

        public MessageType getMessageType() {
            return messageType;
        }

        public void setMessageType(MessageType messageType) {
            this.messageType = messageType;
        }

        public List<String> getToolCalls() {
            return toolCalls;
        }

        public void setToolCalls(List<String> toolCalls) {
            this.toolCalls = toolCalls;
        }

        public String getTextContent() {
            return textContent;
        }

        public void setTextContent(String textContent) {
            this.textContent = textContent;
        }

        public Map<String, Object> getMetadata() {
            return metadata;
        }

        public void setMetadata(Map<String, Object> metadata) {
            this.metadata = metadata;
        }

        public String getUtteranceType() {
            return utteranceType;
        }

        public void setUtteranceType(String utteranceType) {
            this.utteranceType = utteranceType;
        }

        @Override
        public String toString() {
            return "AssistantMessage{" +
                    "messageType=" + messageType +
                    ", toolCalls=" + toolCalls +
                    ", textContent='" + textContent + '\'' +
                    ", metadata=" + metadata +
                    '}';
        }
    }

    public static class ChatGenerationMetadata {
        private String finishReason;
        private int filters;
        private int metadata;

        public ChatGenerationMetadata() {
        }

        public ChatGenerationMetadata(String finishReason, int filters, int metadata) {
            this.finishReason = finishReason;
            this.filters = filters;
            this.metadata = metadata;
        }

        public String getFinishReason() {
            return finishReason;
        }

        public void setFinishReason(String finishReason) {
            this.finishReason = finishReason;
        }

        public int getFilters() {
            return filters;
        }

        public void setFilters(int filters) {
            this.filters = filters;
        }

        public int getMetadata() {
            return metadata;
        }

        public void setMetadata(int metadata) {
            this.metadata = metadata;
        }

        @Override
        public String toString() {
            return "ChatGenerationMetadata{" +
                    "finishReason='" + finishReason + '\'' +
                    ", filters=" + filters +
                    ", metadata=" + metadata +
                    '}';
        }
    }
}
