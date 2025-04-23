package com.marcuslull.aigm.tooling.model;

import com.marcuslull.aigm.tooling.diceRoller.model.Roll;

public class Tools {
    private Roll roll;

    public Roll getRoll() {
        return roll;
    }

    public void setRoll(Roll roll) {
        this.roll = roll;
    }

    public boolean hasRoll() {
        return (this.roll != null);
    }

    @Override
    public String toString() {
        return "Tools{" +
                "roll=" + roll +
                '}';
    }
}
