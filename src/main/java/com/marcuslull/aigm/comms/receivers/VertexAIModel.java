package com.marcuslull.aigm.comms.receivers;

import com.google.cloud.vertexai.VertexAI;
import com.marcuslull.aigm.comms.Package;
import com.marcuslull.aigm.comms.Payload;
import com.marcuslull.aigm.comms.Router;
import com.marcuslull.aigm.comms.Sender;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VertexAIModel extends AbstractAIReceiver implements Sender {

    // from super: AINameEnum name, ChatModel chatModel

    // receiver fields
    private ChatClient chatClient;
    private String generalSystemPrompt;
    private String roleSystemPrompt;
    private String devSystemPrompt;
    private List<Advisor> advisors;

    // sender fields
    private List<Router> routers;

    @Autowired
    public VertexAIModel(VertexAI vertexAI) {
        super.chatModel = VertexAiGeminiChatModel.builder().vertexAI(vertexAI).build();

        this.advisors = List.of(new MessageChatMemoryAdvisor(new InMemoryChatMemory()));
        this.chatClient = ChatClient.builder(super.chatModel)
                .defaultSystem(this.devSystemPrompt + this.generalSystemPrompt + this.roleSystemPrompt)
                .defaultAdvisors(this.advisors)
                .build();
    }

    @Override
    public boolean canHandle(Payload payload) {
        return payload.getReceiver() instanceof VertexAIModel &&
                ((VertexAIModel) payload.getReceiver()).getName() != null &&
                ((VertexAIModel) payload.getReceiver()).getName() == super.name;
    }

    @Override
    public void handle(Payload payload) {
        // convert to JSON
        // escape JSON brackets
        // deliver it to the model via chatClient with .entity(<?> implements Package)
        // send(package)
    }

    @Override
    public void send(Package pkg) {
        // iterate through routerList
        // router.route(pkg)
    }

    @Override
    public List<Router> getRouters() {
        return routers;
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
