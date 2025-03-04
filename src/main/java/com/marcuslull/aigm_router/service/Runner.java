package com.marcuslull.aigm_router.service;

import com.marcuslull.aigm_router.tooling.CommunicationTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.tool.ToolCallingChatOptions;
import org.springframework.ai.model.tool.ToolExecutionResult;
import org.springframework.ai.vertexai.gemini.schema.VertexToolCallingManager;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

import static com.marcuslull.aigm_router.model.AIModelType.*;

@Component
public class Runner implements CommandLineRunner {

    private final VertexToolCallingManager toolCallingManager;
    private final Scanner scanner = new Scanner(System.in);
    private final AiModelFactory aiModelFactory;
    private final ModelGroup modelGroup;

    public Runner(VertexToolCallingManager toolCallingManager, AiModelFactory aiModelFactory, ModelGroup modelGroup) {
        this.toolCallingManager = toolCallingManager;
        this.aiModelFactory = aiModelFactory;
        this.modelGroup = modelGroup;
    }

    @Override
    public void run(String... args) {



        modelGroup.addModel(ORATORIX, aiModelFactory.createAiModel(ORATORIX));
        modelGroup.addModel(ORBIS, aiModelFactory.createAiModel(ORBIS));
        modelGroup.addModel(CHRONOS, aiModelFactory.createAiModel(CHRONOS));
        modelGroup.addModel(JUSTIVOR, aiModelFactory.createAiModel(JUSTIVOR));
        modelGroup.addModel(CONTINUITY, aiModelFactory.createAiModel(CONTINUITY));

        ChatClient client = modelGroup.getModel(ORATORIX);
        ChatClient continuity = modelGroup.getModel(CONTINUITY);
        String response;

        System.out.println("***Ready!***");

        while (true) {
            System.out.println("\nNew loop ---------------------------------------");
            String line = scanner.nextLine();

            // START DEV MODE
            if (line.equals("continuity")) {
                ChatClient previousChatClient = client;
                client = continuity;
                while (true) {

                    String devLine = scanner.nextLine();

                    if (devLine.equals("break")) break;

                    System.out.println("\nNew DEV loop ---------------------------------------");
                    response = send(client, devLine);
                    System.out.println("response = " + response);
                }

                client = previousChatClient; // reset back to pre-dev mode client
                continue;
            }

            response = send(client, line);
            System.out.println("response = " + response);

            send(continuity, "[GM: " + response + "Player: " + line + "] - REMEMBER, DO NOT RESPOND TO THIS MESSAGE"); // DEV model
        }
    }

    private String send(ChatClient client, String userPrompt) {

        ChatOptions chatOptions = ToolCallingChatOptions.builder().internalToolExecutionEnabled(false).build();
        Prompt prompt = new Prompt(userPrompt, chatOptions);
        ChatResponse chatResponse = client.prompt(prompt).call().chatResponse();

        if (chatResponse.hasToolCalls()) {
            ToolExecutionResult toolExecutionResult = toolCallingManager.executeToolCalls(prompt, chatResponse);
            chatResponse = client.prompt().user(toolExecutionResult.conversationHistory().toString()).call().chatResponse();
        }

        return chatResponse.getResult().getOutput().getText();
    }
}
