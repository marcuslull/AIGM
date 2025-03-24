package com.marcuslull.aigm.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.marcuslull.aigm.messaging.model.ChatMessage;
import org.springframework.ai.chat.client.ChatClient;

public interface Messaging {
    ChatMessage send(ChatClient client, String message);
    ChatMessage processGroupMessage(ChatMessage chatMessage);
    void displayPlayerMessage(ChatMessage chatMessage);
    void handle(ChatMessage chatMessage) throws JsonProcessingException;
}
