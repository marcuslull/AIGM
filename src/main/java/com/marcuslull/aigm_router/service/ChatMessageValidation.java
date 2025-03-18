package com.marcuslull.aigm_router.service;

import com.marcuslull.aigm_router.model.ChatMessage;
import com.marcuslull.aigm_router.model.ChatMessageValidationWrapper;
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
