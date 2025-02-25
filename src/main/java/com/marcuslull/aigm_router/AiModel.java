package com.marcuslull.aigm_router;

import org.springframework.ai.chat.client.ChatClient;

public record AiModel(String name, ChatClient chatClient) {}
