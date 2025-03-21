package com.marcuslull.aigm.messaging.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcuslull.aigm.gm.model.enums.AIName;
import com.marcuslull.aigm.messaging.model.enums.AIMessagePriority;

import java.util.List;
import java.util.UUID;

public record GroupMessage(AIName source, AIName target, AIMessagePriority priority, String message, String context, UUID uuid, List<UUID> relatedUuids, double confidence) {

    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return "Error converting GroupMessage to JSON: " + e.getMessage();
        }
    }
}
