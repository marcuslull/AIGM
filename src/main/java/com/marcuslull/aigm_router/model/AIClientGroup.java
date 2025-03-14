package com.marcuslull.aigm_router.model;

import org.springframework.ai.chat.client.ChatClient;

import java.util.HashMap;
import java.util.Map;


public class AIClientGroup {

    private final Map<AIName, ChatClient> aiModelMap = new HashMap<>();

    public ChatClient getModel(AIName aiName) {
        return aiModelMap.get(aiName);
    }

    public void addModel(AIName name, ChatClient chatClient) {
        aiModelMap.put(name, chatClient);
    }

    public void removeModel(AIName name) {
        aiModelMap.remove(name);
    }

    public void clearModelGroup() {
        aiModelMap.clear();
    }

    public Map<AIName, ChatClient> getAiModelMap() {
        return aiModelMap;
    }

    public void printModelMap() {
        this.aiModelMap.forEach((k,v) -> System.out.println(k + ": " + v.hashCode()));
    }

    @Override
    public String toString() {
        return "ModelGroup{" +
                "aiModelMap=" + aiModelMap +
                '}';
    }
}
