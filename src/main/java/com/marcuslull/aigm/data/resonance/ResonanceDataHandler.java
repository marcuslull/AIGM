package com.marcuslull.aigm.data.resonance;

import com.marcuslull.aigm.data.resonance.service.ResonanceRetrieval;
import com.marcuslull.aigm.router.CommunicationHandler;
import com.marcuslull.aigm.router.model.CommunicationPacket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResonanceDataHandler implements CommunicationHandler {

    private final ResonanceRetrieval resonanceRetrieval;

    @Autowired
    public ResonanceDataHandler(ResonanceRetrieval resonanceRetrieval) {
        this.resonanceRetrieval = resonanceRetrieval;
    }

    @Override
    public void handle(CommunicationPacket communicationPacket) {
        resonanceRetrieval.process(communicationPacket);
    }

    @Override
    public boolean canHandle(CommunicationPacket communicationPacket) {
        return communicationPacket.hasResonanceSearch();
    }
}
