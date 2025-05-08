package com.marcuslull.aigm.comms.receivers.models;

import com.google.cloud.vertexai.VertexAI;
import com.marcuslull.aigm.comms.Package;
import com.marcuslull.aigm.comms.Payload;
import com.marcuslull.aigm.comms.Router;
import com.marcuslull.aigm.comms.Sender;
import com.marcuslull.aigm.comms.directory.Directory;
import com.marcuslull.aigm.comms.enums.AINameEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VertexAIModelService extends AbstractAIReceiver implements Sender {

    // from super: AINameEnum name, ChatModel chatModel

    private final Logger logger = LoggerFactory.getLogger(VertexAIModelService.class);

    // receiver fields
    private ChatClient chatClient;
    private String generalSystemPrompt;
    private String roleSystemPrompt;
    private String devSystemPrompt;
    private List<Advisor> advisors;

    // sender fields
    private List<Router> routers;

    @Autowired
    public VertexAIModelService(VertexAI vertexAI) {
        super.chatModel = VertexAiGeminiChatModel.builder().vertexAI(vertexAI).build();

        this.advisors = List.of(new MessageChatMemoryAdvisor(new InMemoryChatMemory()));
        this.chatClient = ChatClient.builder(super.chatModel)
                .defaultSystem(this.devSystemPrompt + this.generalSystemPrompt + this.roleSystemPrompt)
                .defaultAdvisors(this.advisors)
                .build();
    }

    @Override
    public boolean canHandle(Payload payload) {
        return AINameEnum.fromString(payload.getReceiver()) != null;
    }

    @Override
    public void handle(Payload payload) {
        logger.info("{} - Handling Payload: {}", this.getClass().getSimpleName(), payload.getUUID());
    }

    @Override
    public void send(Package pkg) {
        // TODO: iterate through routerList
        // TODO: router.route(pkg)
    }

    @Override
    public List<Router> getRouters() {
        return routers;
    }

    @Override
    public void registerWithDirectory() {
        logger.info("{} - Registering with Directory", this.getClass().getSimpleName());
        Directory.addReceiver(this);
        Directory.addSender(this);
    }

    public void setRouters(List<Router> routers) {
        this.routers = routers;
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

    public List<Advisor> getAdvisors() {
        return advisors;
    }

    public void setAdvisors(List<Advisor> advisors) {
        this.advisors = advisors;
    }

}
