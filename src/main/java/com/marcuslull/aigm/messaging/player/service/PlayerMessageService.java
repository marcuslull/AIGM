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
        // start the player to AI communication loop
        conversationLoop(communicationPacket);

        // Since the whole app is predicated on player communication the player conversation loop state
        // will determine the whole apps state.

        // exited the main communication loop via player typing 'quit'. Close up the app
        System.out.println("Shutting down AI GM...");
        scanner.close();
        context.close();
    }

    private void conversationLoop(CommunicationPacket communicationPacket) {

        String messageFromPlayer;

        while (true) {

            // display the output of the previous response unless null (probably the start of the app)
            if (communicationPacket != null && communicationPacket.hasPlayerMessage()) {
                System.out.println(communicationPacket.getAuthor() + ": " + communicationPacket.getPlayerMessage().getMessage());
            }

            // get user prompt or quit
            messageFromPlayer = scanner.nextLine();
            if (messageFromPlayer.equalsIgnoreCase("quit")) break;

            // compose prompt into a communication and send to AI
            communicationPacket = new CommunicationPacket(
                    null,
                    new PlayerMessage(messageFromPlayer),
                    null,
                    null,
                    null);
            communicationPacket = communicationSender.send(communicationPacket);

            // if the response has a player message element we can keep handling it here.
            if (communicationPacket.hasPlayerMessageOnly()) continue;

            // and send the rest to be routed and handled as needed
            communicationPacket.setPlayerMessage(null);
            communicationRouter.route(communicationPacket);
        }
    }
}
