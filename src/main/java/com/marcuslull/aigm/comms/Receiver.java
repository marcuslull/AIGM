package com.marcuslull.aigm.comms;

public interface Receiver extends DirectoryNode {
    boolean canHandle(Payload payload);
    void handle(Payload payload);
}
