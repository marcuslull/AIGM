package com.marcuslull.aigm.communication.protocol;

public class RollPayload implements Payload{

    private final Payload payload;

    public RollPayload(Payload payload) {
        this.payload = payload;
    }

    @Override
    public boolean isRollPayload() {
        return true;
    }
}
