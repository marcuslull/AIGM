package com.marcuslull.aigm.router;

import com.marcuslull.aigm.messaging.player.PlayerMessageHandler;
import com.marcuslull.aigm.router.model.CommunicationPacket;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommunicationRouter {

    // Context class for strategy design pattern - handler interface = CommunicationHandler
    public final static List<CommunicationHandler> handlers = new ArrayList<>();

    public void addHandler(CommunicationHandler handler) {
        handlers.add(handler);
    }

    public void removeHandler(CommunicationHandler handler) {
        handlers.remove(handler);
    }

    public void route(CommunicationPacket communicationPacket) {
        if (handlers.isEmpty()) throw new RuntimeException("No response handlers available");

        System.out.println("ROUTER_LOG: " + communicationPacket);

        // drop all empty packets
        if (communicationPacket.isEmpty()) return;

        // there could be more than one channel per communication so let's isolate them
        explode(communicationPacket);
    }

    public void explode(CommunicationPacket communicationPacket) {
        if (communicationPacket.hasPlayerMessage()) egress(new CommunicationPacket(communicationPacket.getAuthor(), communicationPacket.getPlayerMessage(), null, null, null, null));
        if (communicationPacket.hasGroupMessage()) egress(new CommunicationPacket(communicationPacket.getAuthor(), null, communicationPacket.getGroupMessage(), null, null, null));
        if (communicationPacket.hasResonanceSearch()) egress(new CommunicationPacket(communicationPacket.getAuthor(), null, null, communicationPacket.getResonanceSearch(), null, null));
        if (communicationPacket.hasLedgerSearch()) egress(new CommunicationPacket(communicationPacket.getAuthor(), null, null, null, communicationPacket.getLedgerSearch(), null));
        if (communicationPacket.hasTooling()) egress(new CommunicationPacket(communicationPacket.getAuthor(), null, null, null, null, communicationPacket.getTooling()));
    }

    private void egress(CommunicationPacket communicationPacket) {
        // route the packet to anyone that can handle it
        for (CommunicationHandler handler : handlers) {
            if (handler.canHandle(communicationPacket)) {
                Thread thread = new Thread(() -> handler.handle(communicationPacket));
                thread.start();
            }
        }
    }

    public void start() {

        Optional<CommunicationHandler> handler = handlers.stream()
                        .filter(h -> h.getClass() == PlayerMessageHandler.class)
                        .findFirst();

        PlayerMessageHandler playerMessageHandler = (PlayerMessageHandler) handler.orElseThrow();
        Thread thread = new Thread(() -> playerMessageHandler.handle(null));
        thread.setName("mainPlayerLoop");
        thread.start();
    }
}
