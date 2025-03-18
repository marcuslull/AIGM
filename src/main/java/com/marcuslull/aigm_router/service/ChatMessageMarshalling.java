package com.marcuslull.aigm_router.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcuslull.aigm_router.model.AIClientGroup;
import com.marcuslull.aigm_router.model.ChatMessage;
import com.marcuslull.aigm_router.model.ChatMessageValidationWrapper;
import com.marcuslull.aigm_router.model.GroupMessage;
import com.marcuslull.aigm_router.model.enums.AIMessagePriority;
import com.marcuslull.aigm_router.model.enums.AIName;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ChatMessageMarshalling {

    private final ChatMessageValidation chatMessageValidation;
    private final ObjectMapper objectMapper;
    private final AIClientGroup aiClientGroup;

    public ChatMessageMarshalling(ChatMessageValidation chatMessageValidation, ObjectMapper objectMapper, AIClientGroup aiClientGroup) {
        this.chatMessageValidation = chatMessageValidation;
        this.objectMapper = objectMapper;
        this.aiClientGroup = aiClientGroup;
    }

    public ChatMessage markdownToChatMessage(String markdown, ChatClient sender) {

        AIName senderName = aiClientGroup.getModelNameByHash(sender.hashCode());
        String json;
        ChatMessage chatMessage;

        // Extracting JSON from MD
        try {
            json = extractJson(markdown);
        } catch (IllegalArgumentException e) {

            // TODO: Extract
            // send back to model
            GroupMessage groupMessage = new GroupMessage(
                    AIName.MESSAGE_VALIDATION,
                    senderName,
                    AIMessagePriority.HIGH,
                    "IllegalArgumentException - Try again! " + e.getMessage(),
                    markdown,
                    UUID.randomUUID(),
                    null,
                    1.0);
            chatMessage = new ChatMessage(null, groupMessage);
            System.out.println("VALIDATION: = " + chatMessage);
            return chatMessage;
        }

        // Mapping JSON to ChatMessage
        try {
            chatMessage = objectMapper.readValue(json, ChatMessage.class);
        } catch (JsonProcessingException e) {

            // TODO: Extract
            // send error back to model
            GroupMessage groupMessage = new GroupMessage(
                    AIName.MESSAGE_VALIDATION,
                    senderName,
                    AIMessagePriority.HIGH,
                    "JsonProcessingException - Try again! " + e.getMessage(),
                    json,
                    UUID.randomUUID(),
                    null,
                    1.0);
            chatMessage = new ChatMessage(null, groupMessage);
            System.out.println("VALIDATION: = " + chatMessage);
            return chatMessage;
        }

        // Validating ChatMessage
        ChatMessageValidationWrapper chatMessageValidationWrapper = null;
        if (chatMessage != null) {
            chatMessageValidationWrapper = chatMessageValidation.validate(new ChatMessageValidationWrapper(chatMessage));
        }

        if (chatMessageValidationWrapper != null && chatMessageValidationWrapper.isValid()) {
            chatMessage = chatMessageValidationWrapper.getChatMessage();
        } else {

            // TODO: Extract
            // send back to model
            GroupMessage groupMessage = new GroupMessage(
                    AIName.MESSAGE_VALIDATION,
                    senderName,
                    AIMessagePriority.HIGH,
                    "Validation Failed - Try again! " + chatMessageValidationWrapper.getValidationProblems(),
                    json,
                    UUID.randomUUID(),
                    null,
                    1.0);
            chatMessage = new ChatMessage(null, groupMessage);
            System.out.println("VALIDATION: = " + chatMessage);
            return chatMessage;
        }

        return chatMessage;
    }

    private String extractJson(String markdownString) {
        String regex = "```json\\s*([\\s\\S]*?)\\s*```";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(markdownString);

        if (matcher.find()) {
            return matcher.group(1).trim(); // Extract the captured JSON and trim whitespace.
        }
        throw new IllegalArgumentException("Attempting to extract JSON from markdown but no JSON found");
    }
}
