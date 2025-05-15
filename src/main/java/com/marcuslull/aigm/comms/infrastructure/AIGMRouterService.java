package com.marcuslull.aigm.comms.infrastructure;

import com.marcuslull.aigm.comms.Package;
import com.marcuslull.aigm.comms.Receiver;
import com.marcuslull.aigm.comms.Router;
import com.marcuslull.aigm.comms.directory.Directory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AIGMRouterService implements Router {

    private final Logger logger = LoggerFactory.getLogger(AIGMRouterService.class);

    @Override
    public List<Receiver> getReceiverList() {
        return Directory.getReceivers();
    }

    @Override
    public void route(Package pkg) {
        logger.info("{} - Processing Package: {}", this.getClass().getSimpleName(), pkg.getUUID());

        // match payloads to handlers
        Optional.of(pkg) // take nothing for granted
                .ifPresent(pack -> pack.getPayload()
                        .forEach(payload -> getReceiverList()
                                .stream()
                                .filter(r -> r.canHandle(payload))
                                .findFirst()
                                .ifPresent(m -> m.handle(payload))));
    }

    @Override
    public void registerWithDirectory() {
        logger.info("{} - Registering with Directory", this.getClass().getSimpleName());
        Directory.addRouter(this);
    }
}
