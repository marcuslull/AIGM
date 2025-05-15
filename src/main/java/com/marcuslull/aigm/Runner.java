package com.marcuslull.aigm;

import com.marcuslull.aigm.comms.Package;
import com.marcuslull.aigm.comms.infrastructure.AIGMPackage;
import com.marcuslull.aigm.comms.infrastructure.AIGMPayload;
import com.marcuslull.aigm.comms.infrastructure.AIGMRouterService;
import com.marcuslull.aigm.comms.receivers.consoles.DeveloperConsoleService;
import com.marcuslull.aigm.comms.receivers.data.StructuredDatabaseService;
import com.marcuslull.aigm.comms.receivers.data.VectorDatabaseService;
import com.marcuslull.aigm.comms.receivers.models.VertexAIModelService;
import com.marcuslull.aigm.comms.receivers.tools.DiceRollerTool;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;


@Service
public class Runner implements CommandLineRunner {

    private final AIGMRouterService routerService;
    private final DeveloperConsoleService devConsole;
    private final StructuredDatabaseService sqlDatabase;
    private final VectorDatabaseService vectorDatabase;
    private final VertexAIModelService vertexAIModel;
    private final DiceRollerTool diceRoller;

    public Runner(AIGMRouterService routerService, DeveloperConsoleService devConsole, StructuredDatabaseService sqlDatabase, VectorDatabaseService vectorDatabase, VertexAIModelService vertexAIModel, DiceRollerTool diceRoller) {
        this.routerService = routerService;
        this.devConsole = devConsole;
        this.sqlDatabase = sqlDatabase;
        this.vectorDatabase = vectorDatabase;
        this.vertexAIModel = vertexAIModel;
        this.diceRoller = diceRoller;
    }

    @Override
    public void run(String... args) {
        routerService.registerWithDirectory();
        devConsole.registerWithDirectory();
        sqlDatabase.registerWithDirectory();
        vectorDatabase.registerWithDirectory();
        vertexAIModel.registerWithDirectory();
        diceRoller.registerWithDirectory();

        Package pkg = new AIGMPackage(List.of(
                new AIGMPayload("dev", "orbis", new HashMap<>()),
                new AIGMPayload("oratorix", "justivor", new HashMap<>()),
                new AIGMPayload("ledger", "orbis", new HashMap<>()),
                new AIGMPayload("dice", "orbis", new HashMap<>())));
        routerService.route(pkg);
    }
}