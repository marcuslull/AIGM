package com.marcuslull.aigm.messaging.service;

import com.marcuslull.aigm.gm.model.AIClientGroup;
import com.marcuslull.aigm.gm.model.enums.AIName;
import com.marcuslull.aigm.messaging.Messaging;
import com.marcuslull.aigm.messaging.model.ChatMessage;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class AIChatMessaging implements Messaging {

    private final ChatMessageMarshalling chatMessageMarshalling;

    public AIChatMessaging(ChatMessageMarshalling chatMessageMarshalling) {
        this.chatMessageMarshalling = chatMessageMarshalling;
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
