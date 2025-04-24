package com.marcuslull.aigm.communication.destinations;

import com.marcuslull.aigm.communication.protocol.Payload;

public interface Destination {
    boolean canHandle(Payload payload);
    void handleAsync(Payload payload);
}
