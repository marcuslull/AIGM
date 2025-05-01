package com.marcuslull.aigm.comms.receivers;

import com.marcuslull.aigm.comms.Receiver;
import com.marcuslull.aigm.comms.enums.AINameEnum;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;

public abstract class AbstractAIReceiver implements Receiver {
    private AINameEnum name;
    private ChatModel chatModel;
    private ChatClient chatClient;
    private String generalSystemPrompt;
    private String roleSystemPrompt;
    private String devSystemPrompt;

    public AbstractAIReceiver(AINameEnum name, ChatModel chatModel) {
        this.name = name;
        this.chatModel = chatModel;
    }

    public AINameEnum getName() {
        return name;
    }

    public ChatModel getChatModel() {
        return chatModel;
    }

    public ChatClient getChatClient() {
        return chatClient;
    }

    public void setChatClient(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public String getGeneralSystemPrompt() {
        return generalSystemPrompt;
    }

    public void setGeneralSystemPrompt(String generalSystemPrompt) {
        this.generalSystemPrompt = generalSystemPrompt;
    }

    public String getRoleSystemPrompt() {
        return roleSystemPrompt;
    }

    public void setRoleSystemPrompt(String roleSystemPrompt) {
        this.roleSystemPrompt = roleSystemPrompt;
    }

    public String getDevSystemPrompt() {
        return devSystemPrompt;
    }

    public void setDevSystemPrompt(String devSystemPrompt) {
        this.devSystemPrompt = devSystemPrompt;
    }
}
