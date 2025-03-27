package com.marcuslull.aigm.messaging.player.service;

import com.marcuslull.aigm.messaging.player.model.PlayerMessage;
import com.marcuslull.aigm.router.CommunicationRouter;
import com.marcuslull.aigm.router.CommunicationSender;
import com.marcuslull.aigm.router.model.CommunicationPacket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class PlayerMessageService {

    private final ConfigurableApplicationContext context;
    private final CommunicationSender communicationSender;
    private final Scanner scanner = new Scanner(System.in);

    private CommunicationRouter communicationRouter;

    @Autowired // Lazy setter DI to avoid circular DI on startup
    public void setResponseRouter(@Lazy CommunicationRouter communicationRouter) {
        this.communicationRouter = communicationRouter;
    }

    @Autowired
    public PlayerMessageService(ConfigurableApplicationContext context, CommunicationSender communicationSender) {
        this.context = context;
        this.communicationSender = communicationSender;
    }

    public void startConversation(CommunicationPacket communicationPacket) {

        // probably app startup we just need a placeholder packet to pass along
        if (communicationPacket == null) {
            communicationPacket = new CommunicationPacket(
                    "PLAYER",null,null,null,null);
        }
        conversationLoop(communicationPacket);
    }

    private void conversationLoop(CommunicationPacket communicationPacket) {

        String messageFromPlayer;

        while (true) {

            if (!communicationPacket.getAuthor().equals("PLAYER")) {
                System.out.println(communicationPacket.getAuthor() + ": " + communicationPacket.getPlayerMessage().getMessage());
            }

            // get user prompt or quit
            messageFromPlayer = scanner.nextLine();
            if (messageFromPlayer.equalsIgnoreCase("quit")) {
                shutDownApp();
                break;
            }

            // TODO: WHY create a new packet on first startup if we already have one we could just add player message to.
            // compose prompt into a communication and send to AI
            communicationPacket = new CommunicationPacket(
                    "PLAYER",
                    new PlayerMessage(messageFromPlayer),
                    null,
                    null,
                    null);
            communicationPacket = communicationSender.send(communicationPacket);

            // or send it back to the router for complete handling
            communicationRouter.route(communicationPacket);
        }
    }

    private void shutDownApp() {
        System.out.println("Shutting down AI GM...");
        scanner.close();
        context.close();
    }
}
