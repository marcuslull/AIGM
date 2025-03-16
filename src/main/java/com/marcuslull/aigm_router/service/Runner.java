package com.marcuslull.aigm_router.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.marcuslull.aigm_router.model.AIClientGroup;
import com.marcuslull.aigm_router.model.ChatMessage;
import com.marcuslull.aigm_router.model.enums.AIName;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class Runner implements CommandLineRunner {

    private final Scanner scanner = new Scanner(System.in);
    private final AIClientGroup aiClientGroup;
    private final MessageService messageService;
    private final ConfigurableApplicationContext context;
    private final VectorIngestion vectorIngestion;

    private ChatClient client;

    public Runner(AIClientGroup aiClientGroup, MessageService messageService, ConfigurableApplicationContext context, VectorIngestion vectorIngestion) {
        this.aiClientGroup = aiClientGroup;
        this.messageService = messageService;
        this.context = context;
        this.vectorIngestion = vectorIngestion;
    }

    @Override
    public void run(String... args) {
        try {
            if (initialize()) eventLoop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean initialize() {
//        vectorIngestion.ingestDocsFolder();
        this.client = aiClientGroup.getModel(AIName.ORATORIX);
        System.out.println("\nAI GM has been initialized");
        return true;
    }

    private void eventLoop() throws JsonProcessingException {
        ChatMessage response = new ChatMessage("Welcome to the AI GM!\nType a message below to get started. Type 'quit' to exit.", null);

        while (true) {
            if (response.hasGroupMessage()) {
                if (response.hasPlayerMessage()) messageService.displayPlayerMessage(response);
                response = messageService.processGroupMessage(response);
                continue;
            }
            messageService.displayPlayerMessage(response);
            String userMessage = scanner.nextLine();
            if (userMessage.equalsIgnoreCase("quit")) break;
            response = messageService.send(client, userMessage);
        }

        System.out.println("Shutting down AI GM...");
        scanner.close();
        context.close();
    }
}