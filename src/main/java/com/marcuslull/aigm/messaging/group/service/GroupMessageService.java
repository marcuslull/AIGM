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

        while (true) {
            // display the output for logging purposes
            System.out.println(communicationPacket.getAuthor() + ": " + communicationPacket.getGroupMessage());
            communicationPacket = communicationSender.send(communicationPacket);

            // if the response has a group message element we can keep handling it here.
            if (communicationPacket.hasGroupMessageOnly()) continue;

            // and send the rest to be routed and handled as needed
            communicationPacket.setGroupMessage(null);
            communicationRouter.route(communicationPacket);
        }
    }
}