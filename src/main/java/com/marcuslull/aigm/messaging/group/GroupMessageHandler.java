package com.marcuslull.aigm.messaging.group;

import com.marcuslull.aigm.messaging.group.service.GroupMessageService;
import com.marcuslull.aigm.router.CommunicationHandler;
import com.marcuslull.aigm.router.model.CommunicationPacket;
import org.springframework.stereotype.Component;

@Component
public class GroupMessageHandler implements CommunicationHandler {

    private final GroupMessageService groupMessageService;

    public GroupMessageHandler(GroupMessageService groupMessageService) {
        this.groupMessageService = groupMessageService;
    }

    @Override
    public void handle(CommunicationPacket communicationPacket) {
        groupMessageService.startConversation(communicationPacket);
    }

    @Override
    public boolean canHandle(CommunicationPacket communicationPacket) {
        return communicationPacket.hasGroupMessage();
    }
}