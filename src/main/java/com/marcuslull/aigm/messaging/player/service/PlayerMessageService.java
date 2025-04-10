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
    private final Scanner scanner;
    private boolean shutdownState = false;

    private CommunicationRouter communicationRouter;

    @Autowired // Lazy setter DI to avoid circular DI on startup
    public void setResponseRouter(@Lazy CommunicationRouter communicationRouter) {
        this.communicationRouter = communicationRouter;
    }

    @Autowired
    public PlayerMessageService(ConfigurableApplicationContext context, CommunicationSender communicationSender) {
        this.context = context;
        this.communicationSender = communicationSender;
        this.scanner = new Scanner(System.in);
    }

    public void conversationLoop(CommunicationPacket communicationPacket) {

        String messageFromPlayer;

        // start the main player loop to keep a responsive connection between AI and player
        if (Thread.currentThread().getName().equals("mainPlayerLoop")) {
            while (true) {
                messageFromPlayer = getInput(scanner); // get player input from console
                if (shutdownState) {
                    break;
                }
                communicationPacket = composeMessage(messageFromPlayer); // create a packet containing player message
                communicationPacket = communicationSender.send(communicationPacket); // send the packet to Oratorix
                displayMessage(communicationPacket); // display the response

                // if the response is more than just a player response send it to the router
                if (communicationPacket.hasPlayerMessageOnly()) {
                    continue;
                }
                communicationPacket.setPlayerMessage(null);
                communicationRouter.route(communicationPacket);
            }

            // out of the main loop - game over :(
            shutDownApp();
        } else {
            displayMessage(communicationPacket);
        }
    }

    private void shutDownApp() {
        System.out.println("Shutting down AI GM...");
        scanner.close();
        context.close();
    }

    private void displayMessage(CommunicationPacket communicationPacket) {
        if (communicationPacket.hasPlayerMessage() && !communicationPacket.getAuthor().equals("PLAYER")) {
            System.out.println("PLAYER MESSAGE - " + communicationPacket.getAuthor() + ": " + communicationPacket.getPlayerMessage().getMessage());
        }
    }

    private String getInput(Scanner scanner) {
        String messageFromPlayer = scanner.nextLine();
        if (messageFromPlayer.equalsIgnoreCase("quit")) {
            this.shutdownState = true;
        }
        return messageFromPlayer;
    }

    private CommunicationPacket composeMessage(String messageFromPlayer) {
        return new CommunicationPacket(
                "PLAYER",
                new PlayerMessage(messageFromPlayer),
                null,
                null,
                null);
    }
}
