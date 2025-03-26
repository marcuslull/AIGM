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

        // communicationPacket is null and so this is probably the start of the application
        if (communicationPacket == null) {
            Optional<CommunicationHandler> handler =
                    handlers
                            .stream()
                            .filter(h -> h.getClass() == PlayerMessageHandler.class)
                            .findFirst();
            handler.orElseThrow().handle(null);
        } else { // all other cases of communicationPacket
            for (CommunicationHandler handler : handlers) {
                if (handler.canHandle(communicationPacket)) handler.handle(communicationPacket);
            }
        }
    }
}
