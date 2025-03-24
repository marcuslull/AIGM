package com.marcuslull.aigm.messaging.group;

import com.marcuslull.aigm.gm.model.enums.AIName;
import com.marcuslull.aigm.messaging.group.service.GroupMessageService;
import com.marcuslull.aigm.router.AiResponseHandler;
import com.marcuslull.aigm.router.model.AiResponse;
import org.springframework.stereotype.Component;

@Component
public class GroupMessageHandler implements AiResponseHandler {

    private final GroupMessageService groupMessageService;

    public GroupMessageHandler(GroupMessageService groupMessageService) {
        this.groupMessageService = groupMessageService;
    }

    @Override
    public void handle(AiResponse aiResponse) {
        System.out.println("NEW AI GROUP CONVERSATION");
        AIName aiName = AIName.valueOf(aiResponse.author());
        groupMessageService.startConversation(aiName, aiResponse.groupMessage());
    }

    @Override
    public boolean canHandle(AiResponse aiResponse) {
        return aiResponse.hasGroupMessage();
    }
}
