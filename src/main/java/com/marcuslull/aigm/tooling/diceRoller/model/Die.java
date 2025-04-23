package com.marcuslull.aigm.tooling.diceRoller.model;

import java.util.Random;

public class Die {

    private final int sides;
    private final Random random;

    public Die(int sides) {
        this.sides = sides;
        this.random = new Random();
    }

    public int roll() {
        return random.nextInt(1,sides + 1);
    }

    public int getSides() {
        return sides;
    }

    @Override
    public String toString() {
        return "Die{" +
                "sides=" + sides +
                '}';
    }
}
