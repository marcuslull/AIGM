package com.marcuslull.aigm_router.service;

import com.marcuslull.aigm_router.model.AIClientGroup;
import com.marcuslull.aigm_router.model.ChatMessage;
import com.marcuslull.aigm_router.model.enums.AIName;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private final AIClientGroup aiClientGroup;
    private final ChatMessageMarshalling chatMessageMarshalling;

    public MessageService(AIClientGroup aiClientGroup, ChatMessageMarshalling chatMessageMarshalling) {
        this.aiClientGroup = aiClientGroup;
        this.chatMessageMarshalling = chatMessageMarshalling;
    }

    private ChatMessage send(ChatClient client, ChatMessage message) {
        System.out.println("\n" + aiClientGroup.getModelNameByHash(client.hashCode()) + " - ");

        // TODO: Extract
        String content = client.prompt().user(message.toString().replace("{", "\\{").replace("}", "\\}")).call().content();
        return chatMessageMarshalling.markdownToChatMessage(content, client);
    }

    ChatMessage send(ChatClient client, String message) {
        System.out.println("\n" + aiClientGroup.getModelNameByHash(client.hashCode()) + " - ");
        String content = client.prompt().user(message).call().content();
        return chatMessageMarshalling.markdownToChatMessage(content, client);
    }

    ChatMessage processGroupMessage(ChatMessage chatMessage) {
        AIName target = chatMessage.groupMessage().target();
        ChatClient targetClient = aiClientGroup.getModel(target);
        System.out.println("GROUP MESSAGE: " + chatMessage.groupMessage());
        return send(targetClient, chatMessage);
    }

    void displayPlayerMessage(ChatMessage chatMessage) {
        System.out.println("PLAYER MESSAGE: " + chatMessage.playerMessage());
    }
}
