package com.marcuslull.aigm_router.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Component
public class Runner implements CommandLineRunner {

    private final Map<String, ChatClient> aiModelMap = new HashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void run(String... args) throws InterruptedException, JsonProcessingException {

        aiModelMap.put("narrative", AiModelFactory.createAiModel("narrative"));
        aiModelMap.put("social", AiModelFactory.createAiModel("social"));
        aiModelMap.put("history", AiModelFactory.createAiModel("history"));
        aiModelMap.put("combat", AiModelFactory.createAiModel("combat"));
        aiModelMap.put("handoff", AiModelFactory.createAiModel("handoff"));
        aiModelMap.put("continuity", AiModelFactory.createAiModel("continuity"));

        String response = "***USER MODE***";
        ChatClient client = aiModelMap.get("narrative");
        ChatClient handoff = aiModelMap.get("handoff");
        ChatClient continuity = aiModelMap.get("continuity");
        String handoffContent = "";
        String continuityContent = "";

        while (true) {

            System.out.println("\nNew loop ---------------------------------------");
            System.out.println("current client = " + client);
            System.out.println("continuityContent = " + continuityContent + " from: " + continuity);
            System.out.println("handoffContent = " + handoffContent + " from: " + handoff);
            System.out.println("response = " + response);

            if (isJson(response)) {
                handoffContent = send(handoff, response); // DEV model
                continuityContent = send(continuity, response); // DEV model
                Thread.sleep(1000);
                client = getClient(response);
                response = send(client, response);
            }
            else {
                continuityContent = send(continuity, "GM: " + response); // DEV model
                handoffContent = "";  // DEV model
                String line = scanner.nextLine();

                // START DEV MODE
                if (line.equals("continuity") || line.equals("handoff")) {
                    ChatClient previousChatClient = client;
                    client = aiModelMap.get(line);
                    response = "***DEV MODE***";
                    while(true) {
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
                // END DEV MODE

                continuityContent = send(continuity, "Player: " + line); // DEV model
                handoffContent = "";  // DEV model
                response = send(client, line);
            }
        }
    }

    private String send(ChatClient client, String prompt) {
        return client.prompt().user(prompt).call().content();
    }

    private ChatClient getClient(String response) throws JsonProcessingException {
        String mdRemoved = response.replaceAll("(?s)^\\s*```json(.*)```\\s*$", "$1");
        JsonNode root = objectMapper.readTree(mdRemoved.strip());
        String target = root.path("targetAI").asText().toLowerCase();
        return aiModelMap.get(target);
    }

    private boolean isJson(String response) {
        // TODO: need to validate JSON
        return response.contains("```json");
    }
}
