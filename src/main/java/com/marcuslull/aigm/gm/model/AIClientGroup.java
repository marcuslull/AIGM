package com.marcuslull.aigm.gm.model;

import com.marcuslull.aigm.gm.model.enums.AIName;
import org.springframework.ai.chat.client.ChatClient;

import java.util.HashMap;
import java.util.Map;

public class AIClientGroup {

    private static final Map<AIName, ChatClient> aiModelMap = new HashMap<>();

    public static ChatClient getModel(AIName aiName) {
        return aiModelMap.get(aiName);
    }

    public static void addModel(AIName name, ChatClient chatClient) {
        aiModelMap.put(name, chatClient);
    }

    public static AIName getModelNameByHash(int hashcode) {
        return aiModelMap
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().hashCode() == hashcode)
                .findFirst()
                .orElseThrow()
                .getKey();
    }

    @Override
    public String toString() {
        return "ModelGroup{" +
                "aiModelMap=" + aiModelMap +
                '}';
    }
}
