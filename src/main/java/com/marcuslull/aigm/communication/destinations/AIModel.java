package com.marcuslull.aigm.communication.destinations;


public interface AIModel extends Destination{

    default boolean isNarrativeAI() {
        return false;
    }

    default boolean isWorldAI() {
        return false;
    }

    default boolean isCombatAI() {
        return false;
    }

    default boolean isChronologicAI() {
        return false;
    }
}
