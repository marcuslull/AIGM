package com.marcuslull.aigm_router.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ChatMessage {

    private String playerMessage;
    private GroupMessage groupMessage;

    public String getPlayerMessage() {
        return playerMessage;
    }

    public void setPlayerMessage(String playerMessage) {
        this.playerMessage = playerMessage;
    }

    public GroupMessage getGroupMessage() {
        return groupMessage;
    }

    public void setGroupMessage(GroupMessage groupMessage) {
        this.groupMessage = groupMessage;
    }

    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return "Error converting ChatMessage to JSON: " + e.getMessage();
        }
    }
}
