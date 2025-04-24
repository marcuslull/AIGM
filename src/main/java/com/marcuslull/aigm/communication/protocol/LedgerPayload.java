package com.marcuslull.aigm.communication.protocol;

public class LedgerPayload implements Payload{

    private final Payload payload;

    public LedgerPayload(Payload payload) {
        this.payload = payload;
    }

    @Override
    public boolean isLedgerPayload() {
        return true;
    }
}
