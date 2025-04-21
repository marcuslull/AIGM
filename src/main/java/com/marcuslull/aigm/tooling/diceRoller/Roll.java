package com.marcuslull.aigm.tooling.diceRoller;

import com.marcuslull.aigm.tooling.diceRoller.model.Die;

import java.util.ArrayList;
import java.util.List;

public class Roll {

    public static List<Integer> rollD20(String name, int modifiers) {
        List<Integer> result = roll(List.of(new Die(20)));

        result.removeLast();
        result.add(modifiers);
        result.add(result.getFirst() + modifiers);

        return result;
    }

    public static List<Integer> rollD20WithAdvantage(String name, int modifiers) {
        List<Integer> result = roll(List.of(new Die(20), new Die(20)));
        int first = result.get(0);
        int second = result.get(1);
        int value = Math.max(first, second);

        result.removeLast();
        result.add(modifiers);
        result.add(modifiers + value);

        return result;
    }

    public static List<Integer> rollD20WithDisadvantage(String name, int modifiers) {
        List<Integer> result = roll(List.of(new Die(20), new Die(20)));
        int first = result.get(0);
        int second = result.get(1);
        int value = Math.min(first, second);

        result.removeLast();
        result.add(modifiers);
        result.add(modifiers + value);

        return result;
    }

    public static List<Integer> rollCustom(String name, int modifiers, List<Die> dice) {
        List<Integer> result = roll(dice);
        int subtotal = result.removeLast();

        result.add(modifiers);
        result.add(subtotal + modifiers);

        return result;
    }

    private static List<Integer> roll(List<Die> dice) {
        List<Integer> result = new ArrayList<>();
        int subTotal = 0;

        for (Die die : dice) {
            int value = die.roll();
            result.add(value);
            subTotal += value;
        }

        result.add(subTotal);

        return result;
    }
}
