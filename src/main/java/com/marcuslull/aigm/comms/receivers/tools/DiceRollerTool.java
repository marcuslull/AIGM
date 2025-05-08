package com.marcuslull.aigm.comms.receivers.tools;

import com.marcuslull.aigm.comms.Package;
import com.marcuslull.aigm.comms.Payload;
import com.marcuslull.aigm.comms.Router;
import com.marcuslull.aigm.comms.Sender;
import com.marcuslull.aigm.comms.directory.Directory;
import com.marcuslull.aigm.comms.enums.ToolNameEnum;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiceRollerTool extends AbstractToolReceiver implements Sender {

    // from super: ToolNameEnum name, List<Method> methods;

    @Override
    public boolean canHandle(Payload payload) {
        return ToolNameEnum.fromString(payload.getReceiver()) == ToolNameEnum.DICE;
    }

    @Override
    public void handle(Payload payload) {
        // TODO:
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
        Directory.addReceiver(this);
        Directory.addSender(this);
    }
}
