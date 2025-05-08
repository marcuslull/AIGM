package com.marcuslull.aigm.comms.infrastructure;

import com.marcuslull.aigm.comms.Package;
import com.marcuslull.aigm.comms.Payload;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class AIGMPackage implements Package {

    private final UUID uuid;
    private final Instant created;
    private final List<Payload> payload;

    public AIGMPackage(List<Payload> payload) {
        this.uuid = UUID.randomUUID();
        this.created = Instant.now();
        this.payload = payload;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public Instant getCreated() {
        return created;
    }

    @Override
    public List<Payload> getPayload() {
        return payload;
    }
}
