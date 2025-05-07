package com.marcuslull.aigm.comms.receivers;

import com.marcuslull.aigm.comms.*;
import com.marcuslull.aigm.comms.Package;
import com.marcuslull.aigm.comms.enums.ConsoleNameEnum;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeveloperConsole extends AbstractConsoleReceiver implements Sender {

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
