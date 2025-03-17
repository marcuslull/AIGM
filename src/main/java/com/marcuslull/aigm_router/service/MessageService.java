package com.marcuslull.aigm_router.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcuslull.aigm_router.model.AIClientGroup;
import com.marcuslull.aigm_router.model.ChatMessage;
import com.marcuslull.aigm_router.model.enums.AIName;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MessageService {

    private final AIClientGroup aiClientGroup;
    private final ObjectMapper objectMapper;

    public MessageService(AIClientGroup aiClientGroup, ObjectMapper objectMapper) {
        this.aiClientGroup = aiClientGroup;
        this.objectMapper = objectMapper;
    }

    private ChatMessage send(ChatClient client, ChatMessage message) throws JsonProcessingException {
        System.out.println("\n" + aiClientGroup.getModelNameByHash(client.hashCode()) + " - ");
        String content = client.prompt().user(message.toString()).call().content();
        return objectMapper.readValue(extractJson(content), ChatMessage.class);
    }

    ChatMessage send(ChatClient client, String message) throws JsonProcessingException {
        System.out.println("\n" + aiClientGroup.getModelNameByHash(client.hashCode()) + " - ");
        String content = client.prompt().user(message).call().content();
        return objectMapper.readValue(extractJson(content), ChatMessage.class);
    }

    ChatMessage processGroupMessage(ChatMessage chatMessage) throws JsonProcessingException {
        AIName target = chatMessage.groupMessage().target();
        // TODO: Implement validation for ChatMessage
        ChatClient targetClient;
        if (target != null) {
            targetClient = aiClientGroup.getModel(target);
            System.out.println("GROUP MESSAGE: " + chatMessage.groupMessage());
            return send(targetClient, chatMessage);
        }
        System.out.println("Model is trying to send groupMessages with no target...");
        return null;
    }

    void displayPlayerMessage(ChatMessage chatMessage) {
        System.out.println("PLAYER MESSAGE: " + chatMessage.playerMessage());
    }

    private String extractJson(String markdownString) {
        String regex = "```json\\s*([\\s\\S]*?)\\s*```";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(markdownString);

        if (matcher.find()) {
            return matcher.group(1).trim(); // Extract the captured JSON and trim whitespace.
        } else {
            return null; // No JSON found.
        }
    }
}
