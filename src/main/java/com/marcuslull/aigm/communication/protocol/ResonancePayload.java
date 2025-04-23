package com.marcuslull.aigm.communication.protocol;

public class ResonancePayload implements Payload{

    private final Payload payload;

    public ResonancePayload(Payload payload) {
        this.payload = payload;
    }

    @Override
    public boolean isResonancePayload() {
        return true;
    }
}
