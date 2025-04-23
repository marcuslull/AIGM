package com.marcuslull.aigm.communication.protocol;


public interface Payload {

    default boolean isPlayerPayload() {
        return false;
    }

    default boolean isInternalPayload() {
        return false;
    }

    default boolean isResonancePayload() {
        return false;
    }

    default boolean isLedgerPayload() {
        return false;
    }

    default boolean isRollPayload() {
        return false;
    }
}
