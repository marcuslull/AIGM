package com.marcuslull.aigm_router.model;

public enum AIModelType {
    ORATORIX("oratorix"),
    CHRONOS("chronos"),
    ORBIS("orbis"),
    JUSTIVOR("justivor"),
    CONTINUITY("continuity");

    private final String stringName;

    AIModelType(String stringName) {
        this.stringName = stringName;
    }

    public String getStringName() {
        return stringName;
    }

    public static AIModelType fromString(String text) {
        for (AIModelType model : AIModelType.values()) {
            if (model.getStringName().equalsIgnoreCase(text)) {
                return model;
            }
        }
        return null;
    }
}
