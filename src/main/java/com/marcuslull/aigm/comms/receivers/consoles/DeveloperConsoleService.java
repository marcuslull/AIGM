package com.marcuslull.aigm.comms.receivers.consoles;

import com.marcuslull.aigm.comms.Package;
import com.marcuslull.aigm.comms.Payload;
import com.marcuslull.aigm.comms.Router;
import com.marcuslull.aigm.comms.Sender;
import com.marcuslull.aigm.comms.directory.Directory;
import com.marcuslull.aigm.comms.enums.ConsoleNameEnum;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeveloperConsoleService extends AbstractConsoleReceiver implements Sender {

    // from super: ConsoleNameEnum name, PrintStream printStream

    @Override
    public boolean canHandle(Payload payload) {
        return ConsoleNameEnum.fromString(payload.getReceiver()) == ConsoleNameEnum.DEV;
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
        // send via getRouters
    }

    @Override
    public void registerWithDirectory() {
        Directory.addReceiver(this);
        Directory.addSender(this);
    }
}
