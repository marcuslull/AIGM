package com.marcuslull.aigm.messaging.group.service;

import com.marcuslull.aigm.router.CommunicationRouter;
import com.marcuslull.aigm.router.CommunicationSender;
import com.marcuslull.aigm.router.model.CommunicationPacket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class GroupMessageService {

    private CommunicationRouter communicationRouter;
    private final CommunicationSender communicationSender;

    @Autowired // Lazy setter DI to avoid circular DI on startup
    public void setResponseRouter(@Lazy CommunicationRouter communicationRouter) {
        this.communicationRouter = communicationRouter;
    }

    @Autowired
    public GroupMessageService(CommunicationSender communicationSender) {
        this.communicationSender = communicationSender;
    }

    public void startConversation(CommunicationPacket communicationPacket) {
        // start the AI to AI communication loop
        conversationLoop(communicationPacket);
    }

    private void conversationLoop(CommunicationPacket communicationPacket) {

        // display
        System.out.println("GROUP MESSAGE - " + communicationPacket.getAuthor() + ": " + communicationPacket.getGroupMessage());

        // get response
        communicationPacket = communicationSender.send(communicationPacket);

        // send it back to the router
        communicationRouter.route(communicationPacket);

        // virtual thread dies naturally when runnable completes
    }
}