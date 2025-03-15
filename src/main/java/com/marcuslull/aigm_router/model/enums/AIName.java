package com.marcuslull.aigm_router.model.enums;

public enum AIName {
    ORATORIX("oratorix"),
    CHRONOS("chronos"),
    ORBIS("orbis"),
    JUSTIVOR("justivor"),
    CONTINUITY("continuity");

    private final String stringName;

    AIName(String stringName) {
        this.stringName = stringName;
    }

    public String getStringName() {
        return stringName;
    }

    public static AIName fromString(String text) {
        for (AIName model : AIName.values()) {
            if (model.getStringName().equalsIgnoreCase(text)) {
                return model;
            }
        }
        return null;
    }
}
