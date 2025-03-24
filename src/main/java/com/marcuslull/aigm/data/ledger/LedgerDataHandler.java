package com.marcuslull.aigm.data.ledger;

import com.marcuslull.aigm.router.AiResponseHandler;
import com.marcuslull.aigm.router.model.AiResponse;
import org.springframework.stereotype.Component;

@Component
public class LedgerDataHandler implements AiResponseHandler {
    @Override
    public void handle(AiResponse aiResponse) {

    }

    @Override
    public boolean canHandle(AiResponse aiResponse) {
        return aiResponse.hasLedgerSearch();
    }
}
