package com.marcuslull.aigm.comms.enums;

public enum ToolNameEnum {
    DICE("dice");

    private final String stringName;

    ToolNameEnum(String stringName) {
        this.stringName = stringName;
    }

    public String getStringName() {
        return stringName;
    }

    public static ToolNameEnum fromString(String text) {
        for (ToolNameEnum tool : ToolNameEnum.values()) {
            if (tool.getStringName().equalsIgnoreCase(text)) {
                return tool;
            }
        }
        return null;
    }
}
