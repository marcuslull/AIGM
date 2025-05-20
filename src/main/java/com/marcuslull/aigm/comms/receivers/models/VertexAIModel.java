package com.marcuslull.aigm.comms.receivers.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.cloud.vertexai.VertexAI;
import com.marcuslull.aigm.comms.Package;
import com.marcuslull.aigm.comms.Payload;
import com.marcuslull.aigm.comms.Router;
import com.marcuslull.aigm.comms.Sender;
import com.marcuslull.aigm.comms.directory.Directory;
import com.marcuslull.aigm.comms.enums.AINameEnum;
import com.marcuslull.aigm.comms.infrastructure.AIGMPackage;
import com.marcuslull.aigm.router.model.CommunicationPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatModel;

import java.util.List;
import java.util.Optional;


public class VertexAIModel extends AbstractAIReceiver implements Sender {

    // from super: AINameEnum name, ChatModel chatModel

    private final Logger logger = LoggerFactory.getLogger(VertexAIModel.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private ChatClient chatClient;
    private List<Router> routers;
    private List<Advisor> advisors;

    public VertexAIModel(VertexAI vertexAI, AINameEnum name, List<String> systemPrompts) {
        super.name = name;
        super.chatModel = VertexAiGeminiChatModel.builder().vertexAI(vertexAI).build();
        this.advisors = List.of(new MessageChatMemoryAdvisor(new InMemoryChatMemory()));
        this.chatClient = ChatClient.builder(super.chatModel)
                .defaultSystem(String.join(" ", systemPrompts))
                .defaultAdvisors(this.advisors)
                .build();

        objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public boolean canHandle(Payload payload) {
        return AINameEnum.fromString(payload.getReceiver()) != null;
    }

    @Override
    public void handle(Payload payload) {
        if (payload != null) { // service boundary null check, drop null argument calls
            logger.info("{} - Handling Payload: {}", this.getClass().getSimpleName(), payload.getUUID());
            Thread thread = new Thread(() -> sendPayloadToModel(payload));
            thread.start();
        }
    }

    private void sendPayloadToModel(Payload payload) {
        logger.info("{} - Sending Payload: {} to Model: {}", this.getClass().getSimpleName(), payload.getUUID(), payload.getReceiver());

        // Spring AI ChatClient expects text and filters on `{` or `}` for their own parsing.
        // The AIs respond in markdown JSON
        try {
            String jsonString = objectMapper.writeValueAsString(payload); // convert to JSON
            Optional<Payload> response = Optional.ofNullable(this.chatClient.prompt().user(escapeJsonBrackets(jsonString)).call().entity(Payload.class));
            response.ifPresent(r -> send(new AIGMPackage(List.of(r))));
        } catch (JsonProcessingException e) {
            //TODO: Handle this
            throw new RuntimeException(e);
        }
    }

    private String escapeJsonBrackets(String message) {
        // The Spring AI ChatClient parser filters on `{` and `}` so we need to escape ours.
        return message.replace("{", "\\{").replace("}", "\\}");
    }

    @Override
    public void send(Package pkg) {
        logger.info("{} - Sending Package: {}", this.getClass().getSimpleName(), pkg.getUUID());
        getRouters().getFirst().route(pkg);
    }

    @Override
    public List<Router> getRouters() {
        return routers;
    }

    @Override
    public void registerWithDirectory() {
        logger.info("{} - {}: Registering with Directory", this.getClass().getSimpleName(), super.name.getStringName());
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

    public List<Advisor> getAdvisors() {
        return advisors;
    }

    public void setAdvisors(List<Advisor> advisors) {
        this.advisors = advisors;
    }
}
