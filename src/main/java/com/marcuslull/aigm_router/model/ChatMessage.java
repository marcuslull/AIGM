package com.marcuslull.aigm_router.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public record ChatMessage(String playerMessage, GroupMessage groupMessage) {

    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return "Error converting ChatMessage to JSON: " + e.getMessage();
        }
    }

    public boolean hasGroupMessage() {
        return this.groupMessage != null;
    }

    public boolean hasPlayerMessage() {
        return this.playerMessage != null;
    }
}
