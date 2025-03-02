package com.marcuslull.aigm_router.service;

import com.marcuslull.aigm_router.model.AIModelType;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ModelGroup {
    private final Map<AIModelType, ChatClient> aiModelMap = new HashMap<>();

    public ChatClient getModel(AIModelType aiModelType) {
        return aiModelMap.get(aiModelType);
    }

    public void addModel(AIModelType name, ChatClient chatClient) {
        aiModelMap.put(name, chatClient);
    }

    public void removeModel(AIModelType name) {
        aiModelMap.remove(name);
    }

    public void clearModelGroup() {
        aiModelMap.clear();
    }

    public Map<AIModelType, ChatClient> getAiModelMap() {
        return aiModelMap;
    }

    @Override
    public String toString() {
        return "ModelGroup{" +
                "aiModelMap=" + aiModelMap +
                '}';
    }
}
