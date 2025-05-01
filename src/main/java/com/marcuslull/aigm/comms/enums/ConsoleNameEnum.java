package com.marcuslull.aigm.comms.enums;

public enum ConsoleNameEnum {
    DEV("dev"),
    CHAT("chat"),
    VTT("vtt");

    private final String stringName;

    ConsoleNameEnum(String stringName) {
        this.stringName = stringName;
    }

    public String getStringName() {
        return stringName;
    }

    public static ConsoleNameEnum fromString(String stringName) {
        for (ConsoleNameEnum console : ConsoleNameEnum.values()) {
            if (console.getStringName().equalsIgnoreCase(stringName)) {
                return console;
            }
        }
        return null;
    }
}
