package com.marcuslull.aigm.communication.router;

import com.marcuslull.aigm.communication.destinations.Destination;
import com.marcuslull.aigm.communication.protocol.Packet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HandlerRouter implements Router{

    private static final Logger logger = LoggerFactory.getLogger(HandlerRouter.class);

    // Context class for strategy design pattern - handler interface = CommunicationHandler
    public final static List<Destination> handlers = new ArrayList<>();

    @Override
    public void addDestination(Destination destination) {
        if (destination != null) {
            handlers.add(destination);
            logger.debug("NEW HANDLER REGISTERED: {}", destination.getClass().getSimpleName());
        }
    }

    @Override
    public void removeDestination(Destination destination) {
        if (destination != null) {
            handlers.remove(destination);
            logger.debug("HANDLER DE-REGISTERED: {}", destination.getClass().getSimpleName());
        }
    }

    @Override
    public Packet route(Packet packet) {
        if (handlers.isEmpty()) throw new RuntimeException("No response handlers available");
        if(!packet.hasPayloadCollection()) throw new RuntimeException("No payload found in packet");

        // match payloads to handlers and send
        packet.getPayloadCollection().forEach(payload ->
                handlers.stream().filter(handler ->
                        handler.canHandle(payload)).findAny().orElseThrow().handleAsync(payload));

        return packet;
    }
}
