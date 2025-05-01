package com.marcuslull.aigm.comms;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

public interface Payload {
    UUID getUUID();
    Instant getCreated();
    Receiver getReceiver();
    Sender getSender();
    Map<String, String> getData();
}
