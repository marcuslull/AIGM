package com.marcuslull.aigm.messaging.service;

import com.marcuslull.aigm.messaging.model.ChatMessage;
import com.marcuslull.aigm.messaging.model.ChatMessageValidationWrapper;
import org.springframework.stereotype.Service;

@Service
public class ChatMessageValidation {
    public ChatMessageValidationWrapper validate(ChatMessage chatMessage) {

        ChatMessageValidationWrapper chatMessageValidationWrapper = new ChatMessageValidationWrapper(chatMessage);

        // TODO: Validation
        chatMessageValidationWrapper.setValid(true);
        return chatMessageValidationWrapper;
    }
}
