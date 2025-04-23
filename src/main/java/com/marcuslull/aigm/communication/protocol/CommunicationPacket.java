package com.marcuslull.aigm.communication.protocol;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class CommunicationPacket implements Packet {
    private String sender = "";
    private final Instant created = Instant.now();
    private Instant updated = Instant.now();
    private final List<Payload> payloadCollection = new ArrayList<>();

    public CommunicationPacket(String sender) {
        this.sender = sender;
    }

    @Override
    public String getSender() {
        return this.sender;
    }

    @Override
    public Instant getCreated() {
        return this.created;
    }

    @Override
    public Instant getUpdated() {
        return this.updated;
    }

    @Override
    public Boolean hasPayloadCollection() {
        return !payloadCollection.isEmpty();
    }

    @Override
    public List<Payload> getPayloadCollection() {
        return this.payloadCollection;
    }

    @Override
    public void addPayload(Payload payloadToAdd) {
        this.updated = Instant.now();
        this.payloadCollection.add(payloadToAdd);
    }

    @Override
    public void removePayload(Payload payloadToRemove) {
        this.updated = Instant.now();
        this.payloadCollection.remove(payloadToRemove);
    }
}
