package com.marcuslull.aigm.comms.receivers.tools;

import com.marcuslull.aigm.comms.Package;
import com.marcuslull.aigm.comms.Payload;
import com.marcuslull.aigm.comms.Router;
import com.marcuslull.aigm.comms.Sender;
import com.marcuslull.aigm.comms.directory.Directory;
import com.marcuslull.aigm.comms.enums.ToolNameEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiceRollerTool extends AbstractToolReceiver implements Sender {

    // from super: ToolNameEnum name, List<Method> methods;

    private final Logger logger = LoggerFactory.getLogger(DiceRollerTool.class);

    @Override
    public boolean canHandle(Payload payload) {
        return ToolNameEnum.fromString(payload.getReceiver()) == ToolNameEnum.DICE;
    }

    @Override
    public void handle(Payload payload) {
        logger.info("{} - Handling Payload: {}", this.getClass().getSimpleName(), payload.getUUID());
    }

    @Override
    public List<Router> getRouters() {
        return Directory.getRouters();
    }

    @Override
    public void send(Package pkg) {
        // TODO: send via getRouters()
    }

    @Override
    public void registerWithDirectory() {
        logger.info("{} - Registering with Directory", this.getClass().getSimpleName());
        Directory.addReceiver(this);
        Directory.addSender(this);
    }
}
