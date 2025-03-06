package com.marcuslull.aigm_router.model;

public enum AIClientTypes {
    ORATORIX("oratorix"),
    CHRONOS("chronos"),
    ORBIS("orbis"),
    JUSTIVOR("justivor"),
    CONTINUITY("continuity");

    private final String stringName;

    AIClientTypes(String stringName) {
        this.stringName = stringName;
    }

    public String getStringName() {
        return stringName;
    }

    public static AIClientTypes fromString(String text) {
        for (AIClientTypes model : AIClientTypes.values()) {
            if (model.getStringName().equalsIgnoreCase(text)) {
                return model;
            }
        }
        return null;
    }
}
