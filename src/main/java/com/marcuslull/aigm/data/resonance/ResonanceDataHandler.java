package com.marcuslull.aigm.data.resonance;

import com.marcuslull.aigm.router.CommunicationHandler;
import com.marcuslull.aigm.router.model.CommunicationPacket;
import org.springframework.stereotype.Component;

@Component
public class ResonanceDataHandler implements CommunicationHandler {
    @Override
    public void handle(CommunicationPacket communicationPacket) {

    }

    @Override
    public boolean canHandle(CommunicationPacket communicationPacket) {
        return communicationPacket.hasResonanceSearch();
    }
}
