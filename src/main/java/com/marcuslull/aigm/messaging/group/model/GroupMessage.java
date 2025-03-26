package com.marcuslull.aigm.messaging.group.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcuslull.aigm.gm.model.enums.AIName;

import java.util.List;
import java.util.UUID;

public class GroupMessage {

    private AIName source;
    private AIName target;
    private AIMessagePriority priority;
    private String message;
    private String context;
    private UUID uuid;
    private List<UUID> relatedUuids;
    private double confidence;

    public GroupMessage() {
    }

    public GroupMessage(AIName source, AIName target, AIMessagePriority priority, String message, String context, UUID uuid, List<UUID> relatedUuids, double confidence) {
        this.source = source;
        this.target = target;
        this.priority = priority;
        this.message = message;
        this.context = context;
        this.uuid = uuid;
        this.relatedUuids = relatedUuids;
        this.confidence = confidence;
    }

    public AIName getSource() {
        return source;
    }

    public void setSource(AIName source) {
        this.source = source;
    }

    public AIName getTarget() {
        return target;
    }

    public void setTarget(AIName target) {
        this.target = target;
    }

    public AIMessagePriority getPriority() {
        return priority;
    }

    public void setPriority(AIMessagePriority priority) {
        this.priority = priority;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public List<UUID> getRelatedUuids() {
        return relatedUuids;
    }

    public void setRelatedUuids(List<UUID> relatedUuids) {
        this.relatedUuids = relatedUuids;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

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
