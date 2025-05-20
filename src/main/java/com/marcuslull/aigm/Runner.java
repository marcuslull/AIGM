package com.marcuslull.aigm;

import com.google.cloud.vertexai.VertexAI;
import com.marcuslull.aigm.comms.Package;
import com.marcuslull.aigm.comms.enums.AINameEnum;
import com.marcuslull.aigm.comms.infrastructure.AIGMPackage;
import com.marcuslull.aigm.comms.infrastructure.AIGMPayload;
import com.marcuslull.aigm.comms.infrastructure.AIGMRouterService;
import com.marcuslull.aigm.comms.receivers.consoles.DeveloperConsoleService;
import com.marcuslull.aigm.comms.receivers.data.StructuredDatabaseService;
import com.marcuslull.aigm.comms.receivers.data.VectorDatabaseService;
import com.marcuslull.aigm.comms.receivers.models.VertexAIModel;
import com.marcuslull.aigm.comms.receivers.tools.DiceRollerTool;
import com.marcuslull.aigm.gm.prompts.SystemPrompts;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class Runner implements CommandLineRunner {

    private final AIGMRouterService routerService;
    private final DeveloperConsoleService devConsole;
    private final StructuredDatabaseService sqlDatabase;
    private final VectorDatabaseService vectorDatabase;
    private final DiceRollerTool diceRoller;
    private final VertexAI vertexAI;

    public Runner(AIGMRouterService routerService, DeveloperConsoleService devConsole, StructuredDatabaseService sqlDatabase, VectorDatabaseService vectorDatabase, DiceRollerTool diceRoller, VertexAI vertexAI) {
        this.routerService = routerService;
        this.devConsole = devConsole;
        this.sqlDatabase = sqlDatabase;
        this.vectorDatabase = vectorDatabase;
        this.diceRoller = diceRoller;
        this.vertexAI = vertexAI;
    }

    @Override
    public void run(String... args) {
        routerService.registerWithDirectory();
        devConsole.registerWithDirectory();
        sqlDatabase.registerWithDirectory();
        vectorDatabase.registerWithDirectory();
        diceRoller.registerWithDirectory();

        VertexAIModel oratorix = new VertexAIModel(vertexAI, AINameEnum.ORATORIX,
                List.of(SystemPrompts.devMode, SystemPrompts.generalSystemPrompt, SystemPrompts.oratorixSystemPrompt));
        VertexAIModel orbis = new VertexAIModel(vertexAI, AINameEnum.ORBIS,
                List.of(SystemPrompts.devMode, SystemPrompts.generalSystemPrompt, SystemPrompts.orbisSystemPrompt));

        oratorix.registerWithDirectory();
        orbis.registerWithDirectory();

        Map<String, String> message = new HashMap<>();
        message.put("message", "hello");

        Package pkg = new AIGMPackage(List.of(
                new AIGMPayload("oratorix", "dev", message)));
        routerService.route(pkg);
    }
}