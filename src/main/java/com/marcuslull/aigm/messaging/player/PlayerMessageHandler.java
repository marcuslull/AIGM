package com.marcuslull.aigm.messaging.player;

import com.marcuslull.aigm.messaging.player.service.PlayerMessageService;
import com.marcuslull.aigm.router.CommunicationHandler;
import com.marcuslull.aigm.router.model.CommunicationPacket;
import org.springframework.stereotype.Component;

@Component
public class PlayerMessageHandler implements CommunicationHandler {

    private final PlayerMessageService playerMessageService;

    public PlayerMessageHandler(PlayerMessageService playerMessageService) {
        this.playerMessageService = playerMessageService;
    }

    @Override
    public void handle(CommunicationPacket communicationPacket) {
        System.out.println("NEW PLAYER CONVERSATION");
        playerMessageService.startConversation(communicationPacket);
    }

    @Override
    public boolean canHandle(CommunicationPacket communicationPacket) {
        return communicationPacket.hasPlayerMessage();
    }
}
