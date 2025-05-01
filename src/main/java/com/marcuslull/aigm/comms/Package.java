package com.marcuslull.aigm.comms;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface Package {
    UUID getUUID();
    Instant getCreated();
    List<Payload> getPayload();
}
