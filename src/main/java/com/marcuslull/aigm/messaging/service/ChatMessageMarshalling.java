package com.marcuslull.aigm.messaging.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcuslull.aigm.gm.model.AIClientGroup;
import com.marcuslull.aigm.gm.model.enums.AIName;
import com.marcuslull.aigm.messaging.model.ChatMessage;
import com.marcuslull.aigm.messaging.model.ChatMessageValidationWrapper;
import com.marcuslull.aigm.messaging.model.GroupMessage;
import com.marcuslull.aigm.messaging.model.enums.AIMessagePriority;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ChatMessageMarshalling {

    private final ChatMessageValidation chatMessageValidation;
    private final ObjectMapper objectMapper;

    public ChatMessageMarshalling(ChatMessageValidation chatMessageValidation, ObjectMapper objectMapper) {
        this.chatMessageValidation = chatMessageValidation;
        this.objectMapper = objectMapper;
    }

    public ChatMessage markdownToChatMessage(String markdown, ChatClient sender) {

        AIName senderName = AIClientGroup.getModelNameByHash(sender.hashCode());
        String json = "";
        ChatMessage chatMessage;
        ChatMessageValidationWrapper chatMessageValidationWrapper;

        // extracting and mapping
        try {
            json = extractJsonFromMarkdown(markdown);
            chatMessage = objectMapper.readValue(json, ChatMessage.class);
        } catch (IllegalArgumentException e) {
            return bounce(markdown, senderName, "IllegalArgumentException - Try again! " + e.getMessage());
        } catch (JsonProcessingException e) {
            return bounce(json, senderName, "JsonProcessingException - Try again! " + e.getMessage());
        }

        // validating
        chatMessageValidationWrapper = chatMessageValidation.validate(chatMessage);
        if (chatMessageValidationWrapper.isValid()) {
            chatMessage = chatMessageValidationWrapper.getChatMessage();
        } else {
            return bounce(json, senderName, "Validation Failed - Try again! " + chatMessageValidationWrapper.getValidationProblems());
        }

        return chatMessage;
    }

    private ChatMessage bounce(String badChatMessageMdOrJson, AIName senderName, String errorMessage) {
        GroupMessage groupMessage = new GroupMessage(
                AIName.PLAYER,
                senderName,
                AIMessagePriority.HIGH,
                errorMessage,
                badChatMessageMdOrJson,
                UUID.randomUUID(),
                null,
                1.0);
        return new ChatMessage(null, groupMessage);
    }

    private String extractJsonFromMarkdown(String markdownString) {
        String regex = "```json\\s*([\\s\\S]*?)\\s*```";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(markdownString);

        if (matcher.find()) {
            return matcher.group(1).trim(); // Extract the captured JSON and trim whitespace.
        }
        throw new IllegalArgumentException("Attempting to extract JSON from markdown but no JSON found");
    }
}
