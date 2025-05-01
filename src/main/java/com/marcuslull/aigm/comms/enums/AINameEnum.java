package com.marcuslull.aigm.comms.enums;

public enum AINameEnum {
    ORATORIX("oratorix"),
    CHRONOS("chronos"),
    ORBIS("orbis"),
    JUSTIVOR("justivor"),
    CONTINUITY("continuity"),
    PLAYER("player");

    private final String stringName;

    AINameEnum(String stringName) {
        this.stringName = stringName;
    }

    public String getStringName() {
        return stringName;
    }

    public static AINameEnum fromString(String text) {
        for (AINameEnum model : AINameEnum.values()) {
            if (model.getStringName().equalsIgnoreCase(text)) {
                return model;
            }
        }
        return null;
    }
}
