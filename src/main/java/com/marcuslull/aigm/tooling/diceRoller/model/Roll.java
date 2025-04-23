package com.marcuslull.aigm.tooling.diceRoller.model;

import java.util.Arrays;

public class Roll {
    private String name;
    private int modifier;
    private int[] set;
    private boolean advantage;
    private boolean disadvantage;
    private String result;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getModifier() {
        return modifier;
    }

    public void setModifier(int modifier) {
        this.modifier = modifier;
    }

    public int[] getSet() {
        return set;
    }

    public void setSet(int[] set) {
        this.set = set;
    }

    public boolean isAdvantage() {
        return advantage;
    }

    public void setAdvantage(boolean advantage) {
        this.advantage = advantage;
    }

    public boolean isDisadvantage() {
        return disadvantage;
    }

    public void setDisadvantage(boolean disadvantage) {
        this.disadvantage = disadvantage;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Roll{" +
                "name='" + name + '\'' +
                ", modifier=" + modifier +
                ", set=" + Arrays.toString(set) +
                ", advantage=" + advantage +
                ", disadvantage=" + disadvantage +
                ", result='" + result + '\'' +
                '}';
    }
}
