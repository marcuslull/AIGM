package com.marcuslull.aigm.messaging.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcuslull.aigm.gm.model.AIClientGroup;
import com.marcuslull.aigm.gm.model.enums.AIName;
import com.marcuslull.aigm.messaging.Messaging;
import com.marcuslull.aigm.messaging.model.ChatMessage;
import com.marcuslull.aigm.router.CommunicationRouter;
import com.marcuslull.aigm.router.model.CommunicationPacket;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class AIChatMessaging implements Messaging {

    private final ChatMessageMarshalling chatMessageMarshalling;
    private final ObjectMapper mapper;

    @Autowired
    public AIChatMessaging(ChatMessageMarshalling chatMessageMarshalling, ObjectMapper mapper) {
        this.chatMessageMarshalling = chatMessageMarshalling;
        this.mapper = mapper;
    }

    private CommunicationRouter communicationRouter;

    @Autowired // Lazy setter DI to avoid circular DI on startup
    public void setResponseRouter(@Lazy CommunicationRouter communicationRouter) {
        this.communicationRouter = communicationRouter;
    }

    @Override
    public void handle(ChatMessage chatMessage) throws JsonProcessingException {

        CommunicationPacket response;

        if (chatMessage.hasPlayerMessage() && !chatMessage.hasGroupMessage()) {
            displayPlayerMessage(chatMessage);
            return;
        }

        if (chatMessage.hasGroupMessage()) {
            displayPlayerMessage(chatMessage);
            chatMessage = processGroupMessage(chatMessage);
//            response = new AiResponse(chatMessage, null, null);
//            String responseAsString = mapper.writeValueAsString(response);
//            responseRouter.route(responseAsString);
        }
    }

    @Override
    public ChatMessage send(ChatClient client, String message) {
        System.out.println("\n" + AIClientGroup.getModelNameByHash(client.hashCode()) + " - ");
        String content = client.prompt().user(message).call().content();
        return chatMessageMarshalling.markdownToChatMessage(content, client);
    }

    @Override
    public ChatMessage processGroupMessage(ChatMessage chatMessage) {
        AIName target = chatMessage.groupMessage().target();
        ChatClient targetClient = AIClientGroup.getModel(target);
        System.out.println("GROUP MESSAGE: " + chatMessage.groupMessage());
        return send(targetClient, chatMessage);
    }

    @Override
    public void displayPlayerMessage(ChatMessage chatMessage) {
        System.out.println("PLAYER MESSAGE: " + chatMessage.playerMessage());
    }

    private ChatMessage send(ChatClient client, ChatMessage message) {
        System.out.println("\n" + AIClientGroup.getModelNameByHash(client.hashCode()) + " - ");
        String content = client.prompt().user(escapeJsonBrackets(message)).call().content();
        return chatMessageMarshalling.markdownToChatMessage(content, client);
    }

    private String escapeJsonBrackets(ChatMessage message) {
        // This Spring parser filters on {} so we need to escape ours.
        return message.toString().replace("{", "\\{").replace("}", "\\}");
    }
}
