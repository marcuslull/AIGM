package com.marcuslull.aigm.comms.receivers.consoles;

import com.marcuslull.aigm.comms.Package;
import com.marcuslull.aigm.comms.Payload;
import com.marcuslull.aigm.comms.Router;
import com.marcuslull.aigm.comms.Sender;
import com.marcuslull.aigm.comms.directory.Directory;
import com.marcuslull.aigm.comms.enums.ConsoleNameEnum;
import com.marcuslull.aigm.comms.infrastructure.AIGMPackage;
import com.marcuslull.aigm.comms.infrastructure.AIGMPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Service
public class DeveloperConsoleService extends AbstractConsoleReceiver implements Sender {

    // from super: ConsoleNameEnum name, PrintStream printStream, Scanner scanner

    private final Logger logger = LoggerFactory.getLogger(DeveloperConsoleService.class);


    public DeveloperConsoleService() {
        printStream = new PrintStream(System.out);
    }

    @Override
    public boolean canHandle(Payload payload) {
        return ConsoleNameEnum.fromString(payload.getReceiver()) == ConsoleNameEnum.DEV;
    }

    @Override
    public void handle(Payload payload) {
        if (payload != null) { // service boundary null check, drop null argument calls
            logger.info("{} - Handling Payload: {}", this.getClass().getSimpleName(), payload.getUUID());
            Thread thread = new Thread(() -> startConversation(payload));
            thread.start();
        }
    }

    private void startConversation(Payload payload) {

        // temp delay during dev to let the console clean up
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        display(payload); // display the payload
        String messageFromDev = getInput(scanner); // get dev input from console

        payload = new AIGMPayload("oratorix", "dev", Map.of("message", messageFromDev)); // create a payload containing dev message
        Package pkg = new AIGMPackage(List.of(payload)); // package it up
        send(pkg); // send it
    }

    private void display(Payload payload) {
        printStream.println("DEV - Message from " + payload.getSender() + ": " + payload.getData());
    }

    private String getInput(Scanner scanner) {
        printStream.print("DEV - Message to Oratorix > ");
        return scanner.nextLine();
    }

    @Override
    public List<Router> getRouters() {
        return Directory.getRouters();
    }

    @Override
    public void send(Package pkg) {
        logger.info("{} - Sending Package: {}", this.getClass().getSimpleName(), pkg.getUUID());
        getRouters().getFirst().route(pkg);
    }

    @Override
    public void registerWithDirectory() {
        logger.info("{} - Registering with Directory", this.getClass().getSimpleName());
        Directory.addReceiver(this);
        Directory.addSender(this);
    }
}
