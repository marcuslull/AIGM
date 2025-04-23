package com.marcuslull.aigm.communication.protocol;

import java.time.Instant;
import java.util.List;

public interface Packet {
    String getSender();
    Instant getCreated();
    Instant getUpdated();
    Boolean hasPayloadCollection();
    List<Payload> getPayloadCollection();
    void addPayload(Payload payloadToAdd);
    void removePayload(Payload payloadToRemove);
}
