package com.marcuslull.aigm.messaging.player.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcuslull.aigm.gm.model.AIClientGroup;
import com.marcuslull.aigm.gm.model.enums.AIName;
import com.marcuslull.aigm.messaging.player.model.PlayerMessage;
import com.marcuslull.aigm.router.CommunicationRouter;
import com.marcuslull.aigm.router.model.CommunicationPacket;
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

    private CommunicationRouter communicationRouter;

    @Autowired // Lazy setter DI to avoid circular DI on startup
    public void setResponseRouter(@Lazy CommunicationRouter communicationRouter) {
        this.communicationRouter = communicationRouter;
    }

    @Autowired
    public PlayerMessageService(ConfigurableApplicationContext context, ObjectMapper objectMapper) {
        this.context = context;
        this.objectMapper = objectMapper;
    }

    public void startConversation(CommunicationPacket communicationPacket) {
        // start the player to AI communication loop
        conversationLoop(communicationPacket);

        // Since the whole app is predicated on player communication the player conversation loop state
        // will determine the whole apps state.

        // exited the main communication loop via player typing 'quit'. Close up the app
        System.out.println("Shutting down AI GM...");
        scanner.close();
        context.close();
    }

    private void conversationLoop(CommunicationPacket communicationPacket) {

        String messageFromPlayer;

        while (true) {

            // display the output of the previous response unless null (probably the start of the app)
            if (communicationPacket != null) {
                System.out.println(communicationPacket.author() + ": " + communicationPacket.playerMessage().message());
            }

            // get user prompt or quit
            messageFromPlayer = scanner.nextLine();
            if (messageFromPlayer.equalsIgnoreCase("quit")) break;

            // compose prompt into a communication and send to AI
            communicationPacket = new CommunicationPacket(
                    null,
                    new PlayerMessage(messageFromPlayer),
                    null,
                    null,
                    null);
            communicationPacket = send(communicationPacket);

            // if the response has a player message element we can keep handling it with this loop.
            if (communicationPacket.hasPlayerMessageOnly()) continue;

            // and send the rest to be routed as needed
            communicationRouter.route(new CommunicationPacket(
                    communicationPacket.author(),
                    null,
                    communicationPacket.groupMessage(),
                    communicationPacket.resonanceSearch(),
                    communicationPacket.ledgerSearch()));
        }
    }

    private CommunicationPacket send(CommunicationPacket response) {
        ChatClient client = AIClientGroup.getModel(AIName.ORATORIX); // player facing AI is always ORATORIX

        // Spring AI ChatClient expects text and filters on `{` or `}` for their own parsing.
        // The AIs respond in markdown JSON
        try {
            String jsonString = objectMapper.writeValueAsString(response); // convert to JSON
            String markdown = client.prompt().user(escapeJsonBrackets(jsonString)).call().content(); // escape {} and send
            String json = extractJsonFromMarkdown(markdown); // remove the Markdown code ticks (```json ... ```)
            response = objectMapper.readValue(json, CommunicationPacket.class); // convert to Object
        } catch (JsonProcessingException e) {
            //TODO: Handle this
            throw new RuntimeException(e);
        }
        return response;
    }

    private String escapeJsonBrackets(String message) {
        // The Spring AI ChatClient parser filters on `{` and `}` so we need to escape ours.
        return message.replace("{", "\\{").replace("}", "\\}");
    }

    private String extractJsonFromMarkdown(String markdownString) {
        // The AIs respond in Markdown codeblock formatted JSON. We need to strip the Markdown
        String regex = "```json\\s*([\\s\\S]*?)\\s*```";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(markdownString);

        if (matcher.find()) {
            return matcher.group(1).trim(); // Extract the captured JSON and trim whitespace.
        }
        throw new IllegalArgumentException("Attempting to extract JSON from markdown but no JSON found");
    }
}
