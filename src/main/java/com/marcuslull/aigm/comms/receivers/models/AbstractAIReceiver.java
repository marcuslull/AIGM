package com.marcuslull.aigm.comms.receivers.models;

import com.marcuslull.aigm.comms.Receiver;
import com.marcuslull.aigm.comms.enums.AINameEnum;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Service;

@Service
public abstract class AbstractAIReceiver implements Receiver {
    protected AINameEnum name;
    protected ChatModel chatModel;


    public AINameEnum getName() {
        return name;
    }

    public void setName(AINameEnum name) {
        this.name = name;
    }

    public ChatModel getChatModel() {
        return chatModel;
    }

    public void setChatModel(ChatModel chatModel) {
        this.chatModel = chatModel;
    }
}
