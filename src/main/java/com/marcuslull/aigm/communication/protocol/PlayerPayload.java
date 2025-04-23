package com.marcuslull.aigm.communication.protocol;

public class PlayerPayload implements Payload{

    private final Payload payload;

    public PlayerPayload(Payload payload) {
        this.payload = payload;
    }

    @Override
    public boolean isPlayerPayload() {
        return true;
    }
}
