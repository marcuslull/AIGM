package com.marcuslull.aigm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.marcuslull.aigm.data.DataIngestion;
import com.marcuslull.aigm.data.service.ResonanceRetrieval;
import com.marcuslull.aigm.gm.GmGroupCreator;
import com.marcuslull.aigm.gm.model.AIClientGroup;
import com.marcuslull.aigm.gm.model.enums.AIName;
import com.marcuslull.aigm.messaging.model.ChatMessage;
import com.marcuslull.aigm.messaging.service.AIChatMessaging;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class Runner implements CommandLineRunner {

    private final AIChatMessaging AIChatMessaging;
    private final ConfigurableApplicationContext context;
    private final DataIngestion dataIngestion;
    private final GmGroupCreator creator;
    private final Scanner scanner;
    private final ResonanceRetrieval resonanceRetrieval;

    private ChatClient client;

    public Runner(AIChatMessaging AIChatMessaging, ConfigurableApplicationContext context, DataIngestion dataIngestion, GmGroupCreator creator, ResonanceRetrieval resonanceRetrieval) {
        this.AIChatMessaging = AIChatMessaging;
        this.context = context;
        this.dataIngestion = dataIngestion;
        this.creator = creator;
        this.resonanceRetrieval = resonanceRetrieval;

        scanner = new Scanner(System.in);
    }

    @Override
    public void run(String... args) throws JsonProcessingException {
//        List<Document> results = resonanceRetrieval.query("{\"metaSearch\":{\"source\":\"SRD_CC_v5.1.pdf\",\"session\":null,\"tag\":null},\"textSearch\":\"initiative\"}");
//        List<Document> results = resonanceRetrieval.query("{\"metaSearch\":{\"source\":null,\"session\":\"0\",\"tag\":null},\"textSearch\":\"initiative\"}");
//        System.out.println(results.size());
//        results.forEach(document -> System.out.println(document.getText()));

//        dataIngestion.ingest();

        try {
            if (initialize()) eventLoop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean initialize() {

        creator.create();
        this.client = AIClientGroup.getModel(AIName.ORATORIX);
        System.out.println("\nAI GM has been initialized");
        return true;
    }

    private void eventLoop() throws JsonProcessingException {
        ChatMessage response = new ChatMessage("Welcome to the AI GM!\nType a message below to get started. Type 'quit' to exit.", null);

        while (true) {
            if (response != null) {
                if (response.hasGroupMessage()) {
                    if (response.hasPlayerMessage()) AIChatMessaging.displayPlayerMessage(response);
                    response = AIChatMessaging.processGroupMessage(response);
                    continue;
                }
                AIChatMessaging.displayPlayerMessage(response);
            }
            String userMessage = scanner.nextLine();
            if (userMessage.equalsIgnoreCase("quit")) break;
            response = AIChatMessaging.send(client, userMessage);
        }

        System.out.println("Shutting down AI GM...");
        scanner.close();
        context.close();
    }
}