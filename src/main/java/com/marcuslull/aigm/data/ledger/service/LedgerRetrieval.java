package com.marcuslull.aigm.data.ledger.service;

import com.marcuslull.aigm.data.ledger.repositories.TestRepository;
import com.marcuslull.aigm.router.model.CommunicationPacket;
import org.springframework.stereotype.Service;

@Service
public class LedgerRetrieval {

    private final TestRepository testRepository;

    public LedgerRetrieval(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    public void handle(CommunicationPacket communicationPacket) {
        // TODO: implementation
    }
}
