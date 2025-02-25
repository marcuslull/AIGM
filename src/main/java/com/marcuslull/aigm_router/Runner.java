package com.marcuslull.aigm_router;

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

    private final Map<String, AiModel> aiModelMap = new HashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void run(String... args) throws InterruptedException, JsonProcessingException {

        aiModelMap.put("narrative", AiModelFactory.getAiModel("narrative"));
        aiModelMap.put("social", AiModelFactory.getAiModel("social"));
        aiModelMap.put("history", AiModelFactory.getAiModel("history"));
        aiModelMap.put("combat", AiModelFactory.getAiModel("combat"));

        String response = "";
        ChatClient client = aiModelMap.get("narrative").chatClient();

        while (true) {
            if (isJson(response)) {
                Thread.sleep(1000);
                client = getClient(response);
                response = send(client, response);

                String mdRemoved = response.replaceAll("(?s)^\\s*```json(.*)```\\s*$", "$1");
                JsonNode root = objectMapper.readTree(mdRemoved);
                System.out.println("response = " + root.toPrettyString());
            }
            else {
                System.out.println("Prompt:");
                String line = scanner.nextLine();
                response = send(client, line);

                System.out.println("response = " + response);
            }
        }
    }

    private String send(ChatClient client, String prompt) {
        return client.prompt().user(prompt).call().content();
    }

    private ChatClient getClient(String response) throws JsonProcessingException {
        String mdRemoved = response.replaceAll("(?s)^\\s*```json(.*)```\\s*$", "$1");
        JsonNode root = objectMapper.readTree(mdRemoved);
        String target = root.path("targetAI").asText().toLowerCase();
        System.out.println();
        System.out.println();
        System.out.println("response = " + root.toPrettyString());
        System.out.println("Switching to: " + target);

        return aiModelMap.get(target).chatClient();
    }

    private boolean isJson(String response) {
        // TODO: need to validate JSON
        return response.contains("```json");
    }
}
