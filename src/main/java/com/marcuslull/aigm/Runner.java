package com.marcuslull.aigm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcuslull.aigm.data.DataIngestion;
import com.marcuslull.aigm.data.ledger.LedgerDataHandler;
import com.marcuslull.aigm.data.resonance.ResonanceDataHandler;
import com.marcuslull.aigm.gm.GmGroupCreator;
import com.marcuslull.aigm.gm.model.AIClientGroup;
import com.marcuslull.aigm.gm.model.enums.AIName;
import com.marcuslull.aigm.messaging.group.GroupMessageHandler;
import com.marcuslull.aigm.messaging.player.PlayerMessageHandler;
import com.marcuslull.aigm.messaging.service.AIChatMessaging;
import com.marcuslull.aigm.router.ResponseRouter;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class Runner implements CommandLineRunner {

    private final AIChatMessaging aiChatMessaging;
    private final ConfigurableApplicationContext context;
    private final DataIngestion dataIngestion;
    private final GmGroupCreator creator;
    private final Scanner scanner;
    private final ResponseRouter responseRouter;
    private final ObjectMapper mapper;
    private final PlayerMessageHandler playerMessageHandler;

    private ChatClient client;

    public Runner(AIChatMessaging aiChatMessaging, ConfigurableApplicationContext context, DataIngestion dataIngestion, GmGroupCreator creator, ResponseRouter responseRouter, ObjectMapper mapper, PlayerMessageHandler playerMessageHandler) {
        this.aiChatMessaging = aiChatMessaging;
        this.context = context;
        this.dataIngestion = dataIngestion;
        this.creator = creator;
        this.responseRouter = responseRouter;
        this.mapper = mapper;
        this.playerMessageHandler = playerMessageHandler;

        scanner = new Scanner(System.in);
    }

    @Override
    public void run(String... args) {

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
        System.out.println("\nGMs have been initialized");

        responseRouter.addHandler(context.getBean(PlayerMessageHandler.class));
        responseRouter.addHandler(context.getBean(GroupMessageHandler.class));
        responseRouter.addHandler(context.getBean(ResonanceDataHandler.class));
        responseRouter.addHandler(context.getBean(LedgerDataHandler.class));
        System.out.println("\nResponse handlers registered");

        System.out.println("\nWelcome to AI Game Master!");
        return true;
    }

    private void eventLoop() {

        playerMessageHandler.handle(null);

//        ChatMessage response = new ChatMessage("Welcome to the AI GM!\nType a message below to get started. Type 'quit' to exit.", null);
//        ChatMessage response;
//
//        while (true) {





//            if (response != null) {
//                if (response.hasGroupMessage()) {
//                    if (response.hasPlayerMessage()) aiChatMessaging.displayPlayerMessage(response);
//                    response = aiChatMessaging.processGroupMessage(response);
//                    continue;
//                }
//                aiChatMessaging.displayPlayerMessage(response);
//            }



//            String userMessage = scanner.nextLine();
//            if (userMessage.equalsIgnoreCase("quit")) break;
//            response = aiChatMessaging.send(client, userMessage);
//        }
//
//        System.out.println("Shutting down AI GM...");
//        scanner.close();
//        context.close();
    }
}