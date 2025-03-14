package com.marcuslull.aigm_router.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcuslull.aigm_router.model.AIClientGroup;
import com.marcuslull.aigm_router.model.AIName;
import com.marcuslull.aigm_router.model.ChatMessage;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.marcuslull.aigm_router.model.AIName.CONTINUITY;
import static com.marcuslull.aigm_router.model.AIName.ORATORIX;

@Service
public class Runner implements CommandLineRunner {

    private final Scanner scanner = new Scanner(System.in);
    private final AIClientGroup aiClientGroup;
    private final ObjectMapper objectMapper;

    public Runner(AIClientGroup aiClientGroup, ObjectMapper objectMapper) {
        this.aiClientGroup = aiClientGroup;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws JsonProcessingException {
        // TODO: accept args for Player vs Dev mode

        aiClientGroup.printModelMap();

        ChatClient client = aiClientGroup.getModel(ORATORIX);
        ChatClient continuity = aiClientGroup.getModel(CONTINUITY);
        ChatMessage response;

        System.out.println("\n***AIGM is Ready!***");

        while (true) {
            String line = scanner.nextLine();

            // TODO: Remove once I implement arg input
            // START DEV MODE
            if (line.equals("continuity")) {
                ChatClient previousChatClient = client;
                client = continuity;
                while (true) {
                    String devLine = scanner.nextLine();
                    if (devLine.equals("break")) break;
                    response = send(client, devLine);
                    displayPlayerMessageIfPresent(response);
                }
                client = previousChatClient; // reset back to pre-dev mode client
                continue;
            }
            // END DEV MODE

            response = send(client, line);
            response = exchangeMessage(client, response);
            displayPlayerMessageIfPresent(response);

//            send(continuity, "[GM: " + response + "Player: " + line + "] - DO NOT RESPOND TO THIS MESSAGE"); // DEV model
        }
    }

    private ChatMessage send(ChatClient client, ChatMessage message) throws JsonProcessingException {
        String content = client.prompt().user(message.toString()).call().content();
        ChatMessage chatMessage = objectMapper.readValue(extractJson(content), ChatMessage.class);
        return chatMessage;
    }

    private ChatMessage send(ChatClient client, String message) throws JsonProcessingException {
        String content = client.prompt().user(message).call().content();
        ChatMessage chatMessage = objectMapper.readValue(extractJson(content), ChatMessage.class);
        return chatMessage;
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

    private void displayPlayerMessageIfPresent(ChatMessage chatMessage) {
        if (!chatMessage.getPlayerMessage().isEmpty()) {
            System.out.println("\n RESPONSE: " + chatMessage);
        }
    }

    private ChatMessage exchangeMessage(ChatClient source, ChatMessage chatMessage) throws JsonProcessingException {
        AIName target;
        ChatClient targetClient;
        ChatMessage response;

        while (chatMessage.getGroupMessage() != null) {

            displayPlayerMessageIfPresent(chatMessage);

            target = chatMessage.getGroupMessage().getTarget();
            targetClient = aiClientGroup.getModel(target);

            System.out.println("\n GROUP MESSAGE REQUEST: " + chatMessage);
            response = send(targetClient, chatMessage);

            System.out.println("\n GROUP MESSAGE RESPONSE " + response);
            chatMessage = send(source, response);
        }
        return chatMessage;
    }
}