package com.marcuslull.aigm;

import com.marcuslull.aigm.data.ledger.LedgerDataHandler;
import com.marcuslull.aigm.data.resonance.ResonanceDataHandler;
import com.marcuslull.aigm.data.resonance.ingestion.DataIngestion;
import com.marcuslull.aigm.gm.GmGroupCreator;
import com.marcuslull.aigm.messaging.group.GroupMessageHandler;
import com.marcuslull.aigm.messaging.player.PlayerMessageHandler;
import com.marcuslull.aigm.router.CommunicationRouter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;


@Service
public class Runner implements CommandLineRunner {

    private final ConfigurableApplicationContext context;
    private final GmGroupCreator creator;
    private final CommunicationRouter communicationRouter;
    private final DataIngestion dataIngestion;

    @Autowired
    public Runner(ConfigurableApplicationContext context,
                  DataIngestion dataIngestion,
                  GmGroupCreator creator,
                  CommunicationRouter communicationRouter) {
        this.context = context;
        this.dataIngestion = dataIngestion;
        this.creator = creator;
        this.communicationRouter = communicationRouter;
    }

    @Override
    public void run(String... args) {

        // Vector DB data ingestion
//        dataIngestion.ingest();

        try {
            if (initialize()) eventLoop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean initialize() {

        // Create our AI clients and add them to our AI Group
        creator.create();
        System.out.println("\nGMs have been initialized...");

        // Register the handlers for the different types of communication the AI group utilizes
        communicationRouter.addHandler(context.getBean(PlayerMessageHandler.class));
        communicationRouter.addHandler(context.getBean(GroupMessageHandler.class));
        communicationRouter.addHandler(context.getBean(ResonanceDataHandler.class));
        communicationRouter.addHandler(context.getBean(LedgerDataHandler.class));
        System.out.println("Response handlers registered...");

        System.out.println("\nWelcome to AI Game Master!");
        return true;
    }

    private void eventLoop() {
        // kick off the app with an empty communication
        communicationRouter.route(null);
    }
}