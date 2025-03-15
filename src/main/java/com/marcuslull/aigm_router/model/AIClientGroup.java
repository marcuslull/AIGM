package com.marcuslull.aigm_router.model;

import com.marcuslull.aigm_router.model.enums.AIName;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AIClientGroup {

    private final Map<AIName, ChatClient> aiModelMap = new HashMap<>();

    public ChatClient getModel(AIName aiName) {
        return aiModelMap.get(aiName);
    }

    public void addModel(AIName name, ChatClient chatClient) {
        aiModelMap.put(name, chatClient);
    }

    @Override
    public String toString() {
        return "ModelGroup{" +
                "aiModelMap=" + aiModelMap +
                '}';
    }
}
