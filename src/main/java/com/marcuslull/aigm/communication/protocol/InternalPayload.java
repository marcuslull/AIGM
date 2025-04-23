package com.marcuslull.aigm.communication.protocol;

public class InternalPayload implements Payload{
    private final Payload payload;

    public InternalPayload(Payload payload) {
        this.payload = payload;
    }

    @Override
    public boolean isInternalPayload() {
        return true;
    }
}
