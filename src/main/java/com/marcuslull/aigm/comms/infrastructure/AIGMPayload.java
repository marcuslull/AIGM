package com.marcuslull.aigm.comms.infrastructure;

import com.marcuslull.aigm.comms.Payload;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

public class AIGMPayload implements Payload {

    private final UUID uuid;
    private final Instant created;
    private final String receiver;
    private final String sender;
    private final Map<String, String> data;

    public AIGMPayload(String receiver, String sender, Map<String, String> data) {
        this.uuid = UUID.randomUUID();
        this.created = Instant.now();
        this.receiver = receiver;
        this.sender = sender;
        this.data = data;
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
    public String getReceiver() {
        return receiver;
    }

    @Override
    public String getSender() {
        return sender;
    }

    @Override
    public Map<String, String> getData() {
        return data;
    }
}
