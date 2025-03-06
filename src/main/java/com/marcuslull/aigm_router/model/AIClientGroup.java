package com.marcuslull.aigm_router.model;

import org.springframework.ai.chat.client.ChatClient;

import java.util.HashMap;
import java.util.Map;


public class AIClientGroup {

    private final Map<AIClientTypes, ChatClient> aiModelMap = new HashMap<>();

    public ChatClient getModel(AIClientTypes aiClientTypes) {
        return aiModelMap.get(aiClientTypes);
    }

    public void addModel(AIClientTypes name, ChatClient chatClient) {
        aiModelMap.put(name, chatClient);
    }

    public void removeModel(AIClientTypes name) {
        aiModelMap.remove(name);
    }

    public void clearModelGroup() {
        aiModelMap.clear();
    }

    public Map<AIClientTypes, ChatClient> getAiModelMap() {
        return aiModelMap;
    }

    @Override
    public String toString() {
        return "ModelGroup{" +
                "aiModelMap=" + aiModelMap +
                '}';
    }
}
