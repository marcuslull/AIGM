package com.marcuslull.aigm_router.service;

import com.marcuslull.aigm_router.model.AIClientGroup;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.Scanner;

import static com.marcuslull.aigm_router.model.AIClientTypes.CONTINUITY;
import static com.marcuslull.aigm_router.model.AIClientTypes.ORATORIX;

@Service
public class Runner implements CommandLineRunner {

    private final Scanner scanner = new Scanner(System.in);
    private final AIClientGroup AIClientGroup;

    public Runner(AIClientGroup AIClientGroup) {
        this.AIClientGroup = AIClientGroup;
    }

    @Override
    public void run(String... args) {

        ChatClient client = AIClientGroup.getModel(ORATORIX);
        ChatClient continuity = AIClientGroup.getModel(CONTINUITY);
        String response;

        System.out.println("\n***AIGM is Ready!***");

        while (true) {
            String line = scanner.nextLine();

            // START DEV MODE
            if (line.equals("continuity")) {
                ChatClient previousChatClient = client;
                client = continuity;
                while (true) {
                    String devLine = scanner.nextLine();
                    if (devLine.equals("break")) break;
                    response = send(client, devLine);
                    System.out.println(response);
                }
                client = previousChatClient; // reset back to pre-dev mode client
                continue;
            }
            // END DEV MODE

            response = send(client, line);
            System.out.println(response);

            send(continuity, "[GM: " + response + "Player: " + line + "] - DO NOT RESPOND TO THIS MESSAGE"); // DEV model
        }
    }

    private String send(ChatClient client, String userPrompt) {
        return client.prompt().user(userPrompt).call().content();
    }
}
