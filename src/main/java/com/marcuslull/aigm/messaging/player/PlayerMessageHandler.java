package com.marcuslull.aigm.messaging.player;

import com.marcuslull.aigm.messaging.player.service.PlayerMessageService;
import com.marcuslull.aigm.router.AiResponseHandler;
import com.marcuslull.aigm.router.model.AiResponse;
import org.springframework.stereotype.Component;

@Component
public class PlayerMessageHandler implements AiResponseHandler {

    private final PlayerMessageService playerMessageService;

    public PlayerMessageHandler(PlayerMessageService playerMessageService) {
        this.playerMessageService = playerMessageService;
    }

    @Override
    public void handle(AiResponse aiResponse) {
        System.out.println("NEW PLAYER CONVERSATION");
        playerMessageService.startConversation(aiResponse);
    }

    @Override
    public boolean canHandle(AiResponse aiResponse) {
        return aiResponse.hasPlayerMessage();
    }
}
