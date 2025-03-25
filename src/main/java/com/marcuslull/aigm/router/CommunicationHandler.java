package com.marcuslull.aigm.router;

import com.marcuslull.aigm.router.model.CommunicationPacket;

public interface CommunicationHandler {
    void handle(CommunicationPacket communicationPacket);
    boolean canHandle(CommunicationPacket communicationPacket);
}
