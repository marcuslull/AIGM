package com.marcuslull.aigm.comms;

public interface Receiver {
    boolean canHandle(Payload payload);
    void handle(Payload payload);
}
