package com.marcuslull.aigm.communication.destinations;


public interface Handler extends Destination{

    default boolean isPlayerHandler() {
        return false;
    }

    default boolean isInternalHandler() {
        return false;
    }

    default boolean isResonanceHandler() {
        return false;
    }

    default boolean isLedgerHandler() {
        return false;
    }

    default boolean isRollHandler() {
        return false;
    }
}
