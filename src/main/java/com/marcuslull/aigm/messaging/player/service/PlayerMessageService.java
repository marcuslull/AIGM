package com.marcuslull.aigm.messaging.player.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcuslull.aigm.gm.model.AIClientGroup;
import com.marcuslull.aigm.gm.model.enums.AIName;
import com.marcuslull.aigm.messaging.player.model.PlayerMessage;
import com.marcuslull.aigm.router.ResponseRouter;
import com.marcuslull.aigm.router.model.AiResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PlayerMessageService {

    private final ConfigurableApplicationContext context;
    private final ObjectMapper objectMapper;
    private final Scanner scanner = new Scanner(System.in);

    private ResponseRouter responseRouter;

    @Autowired // Lazy setter DI to avoid circular DI on startup
    public void setResponseRouter(@Lazy ResponseRouter responseRouter) {
        this.responseRouter = responseRouter;
    }

    @Autowired
    public PlayerMessageService(ConfigurableApplicationContext context, ObjectMapper objectMapper) {
        this.context = context;
        this.objectMapper = objectMapper;
    }

    public void startConversation(AiResponse aiResponse) {
        conversationLoop(aiResponse);
        System.out.println("Shutting down AI GM...");
        scanner.close();
        context.close();
    }

    private void conversationLoop(AiResponse aiResponse) {

        String messageFromPlayer;

        boolean appIsAlive = true;
        while (appIsAlive) {

            if (aiResponse != null) System.out.println(aiResponse.author() + ": " + aiResponse.playerMessage().message());

            messageFromPlayer = scanner.nextLine();
            if (messageFromPlayer.equalsIgnoreCase("quit")) appIsAlive = false;

            aiResponse = new AiResponse(null, new PlayerMessage(messageFromPlayer), null, null, null);
            aiResponse = send(aiResponse);

            // if the response is only a player message we can keep handling it with this loop.
            if (aiResponse.hasPlayerMessageOnly()) continue;

            // otherwise send the rest to be routed
            responseRouter.route(new AiResponse(aiResponse.author(), null, aiResponse.groupMessage(), aiResponse.resonanceSearch(), aiResponse.ledgerSearch()));
        }
    }

    private AiResponse send(AiResponse response) {
        ChatClient client = AIClientGroup.getModel(AIName.ORATORIX);

        try {
            String jsonString = objectMapper.writeValueAsString(response);
            String markdown = client.prompt().user(escapeJsonBrackets(jsonString)).call().content();
            String json = extractJsonFromMarkdown(markdown);
            response = objectMapper.readValue(json, AiResponse.class);
        } catch (JsonProcessingException e) {
            //TODO: Handle this
            throw new RuntimeException(e);
        }
        return response;
    }

    private String escapeJsonBrackets(String message) {
        // This Spring parser filters on {} so we need to escape ours.
        return message.toString().replace("{", "\\{").replace("}", "\\}");
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
