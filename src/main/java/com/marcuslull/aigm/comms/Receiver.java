package com.marcuslull.aigm.comms;

import com.marcuslull.aigm.comms.directory.DirectoryNode;

public interface Receiver extends DirectoryNode {
    boolean canHandle(Payload payload);
    void handle(Payload payload);
}
