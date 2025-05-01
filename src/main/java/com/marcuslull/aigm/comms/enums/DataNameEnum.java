package com.marcuslull.aigm.comms.enums;

public enum DataNameEnum {
    LEDGER("ledger"),
    RESONANCE("resonance");

    private final String stringName;

    DataNameEnum(String stringName) {
        this.stringName = stringName;
    }

    public String getStringName() {
        return stringName;
    }

    public static DataNameEnum fromString(String text) {
        for (DataNameEnum data : DataNameEnum.values()) {
            if (data.getStringName().equalsIgnoreCase(text)) {
                return data;
            }
        }
        return null;
    }
}
