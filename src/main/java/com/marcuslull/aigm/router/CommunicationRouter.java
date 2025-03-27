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

        // route the packet to anyone that can handle it
        for (CommunicationHandler handler : handlers) {
            // TODO: this must be loop async
                if (handler.canHandle(communicationPacket)) {
                    handler.handle(communicationPacket);
                }
        }
    }

    public void start() {
        // app start - lets notify PlayerMessaging
        Optional<CommunicationHandler> handler = handlers
                        .stream()
                        .filter(h -> h.getClass() == PlayerMessageHandler.class)
                        .findFirst();

        PlayerMessageHandler playerMessageHandler = (PlayerMessageHandler) handler.orElseThrow();
        playerMessageHandler.handle(null);
    }
}
