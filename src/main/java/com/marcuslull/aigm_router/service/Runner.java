package com.marcuslull.aigm_router.service;

import com.marcuslull.aigm_router.model.AIModelType;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

import static com.marcuslull.aigm_router.model.AIModelType.*;

@Component
public class Runner implements CommandLineRunner {

    private final Scanner scanner = new Scanner(System.in);
    private final AiModelFactory aiModelFactory;
    private final ModelGroup modelGroup;

    public Runner(AiModelFactory aiModelFactory, ModelGroup modelGroup) {
        this.aiModelFactory = aiModelFactory;
        this.modelGroup = modelGroup;
    }

    @Override
    public void run(String... args) throws InterruptedException {

        modelGroup.addModel(ORATORIX, aiModelFactory.createAiModel(ORATORIX));
        modelGroup.addModel(ORBIS, aiModelFactory.createAiModel(ORBIS));
        modelGroup.addModel(CHRONOS, aiModelFactory.createAiModel(CHRONOS));
        modelGroup.addModel(JUSTIVOR, aiModelFactory.createAiModel(JUSTIVOR));
        modelGroup.addModel(HANDOFF, aiModelFactory.createAiModel(HANDOFF));
        modelGroup.addModel(CONTINUITY, aiModelFactory.createAiModel(CONTINUITY));

        String response = "***USER MODE***";
        ChatClient client = modelGroup.getModel(ORATORIX);
        ChatClient handoff = modelGroup.getModel(HANDOFF);
        ChatClient continuity = modelGroup.getModel(CONTINUITY);
        String handoffContent = "";
        String continuityContent = "";

        while (true) {

            System.out.println("\nNew loop ---------------------------------------");
//            System.out.println("current client = " + client);
//            System.out.println("continuityContent = " + continuityContent + " from: " + continuity);
//            System.out.println("handoffContent = " + handoffContent + " from: " + handoff);
            System.out.println("response = " + response);

            continuityContent = send(continuity, "GM: " + response); // DEV model
            handoffContent = "";  // DEV model
            String line = scanner.nextLine();

            // START DEV MODE
            if (line.equals("continuity") || line.equals("handoff")) {
                ChatClient previousChatClient = client;
                client = modelGroup.getModel(AIModelType.fromString(line));
                response = "***DEV MODE***";
                while (true) {
                    System.out.println("\nNew DEV loop ---------------------------------------");
                    System.out.println("client = " + client);
                    System.out.println("response = " + response);
                    String devLine = scanner.nextLine();
                    if (devLine.equals("break")) break;
                    response = send(client, devLine);
                }
                client = previousChatClient; // reset back to pre-dev mode client
                response = "***USER MODE***";
                continue;
            }

            continuityContent = send(continuity, "Player: " + line); // DEV model
            handoffContent = "";  // DEV model
            response = send(client, line);
        }
    }

    private String send(ChatClient client, String prompt) {
        return client.prompt().user(prompt).call().content();
    }
}
