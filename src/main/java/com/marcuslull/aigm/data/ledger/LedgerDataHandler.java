package com.marcuslull.aigm.data.ledger;

import com.marcuslull.aigm.data.ledger.service.LedgerRetrieval;
import com.marcuslull.aigm.router.CommunicationHandler;
import com.marcuslull.aigm.router.model.CommunicationPacket;
import org.springframework.stereotype.Component;

@Component
public class LedgerDataHandler implements CommunicationHandler {

    private final LedgerRetrieval ledgerRetrieval;

    public LedgerDataHandler(LedgerRetrieval ledgerRetrieval) {
        this.ledgerRetrieval = ledgerRetrieval;
    }

    @Override
    public void handle(CommunicationPacket communicationPacket) {
        ledgerRetrieval.handle(communicationPacket);
    }

    @Override
    public boolean canHandle(CommunicationPacket communicationPacket) {
        return communicationPacket.hasLedgerSearch();
    }
}
