package com.marcuslull.aigm.tooling.diceRoller.service;

import com.marcuslull.aigm.tooling.diceRoller.model.Die;
import com.marcuslull.aigm.tooling.diceRoller.model.Roll;

import java.util.ArrayList;
import java.util.List;

public class Roller {

    public static List<Integer> roll(Roll roll) {
        String name = roll.getName();
        int modifiers = roll.getModifier();
        int[] set = roll.getSet();


        List<Integer> result;
        if (roll.isAdvantage() && roll.isDisadvantage()) { // a regular old d20 roll
            result = rollD20(name, modifiers);
        }
        else if (roll.isAdvantage()) { // 2d20 advantage
            result = rollD20WithAdvantage(name, modifiers);
        }
        else if (roll.isDisadvantage()) { // 2d20 disadvantage
            result = rollD20WithDisadvantage(name, modifiers);
        }
        else { // everything else is a custom roll, create the custom roll then roll
            if (set == null) return null;
            result = rollCustom(name, modifiers, instantiateDice(set));
        }
        return result;
    }

    private static List<Die> instantiateDice(int[] diceArray) {
        int[] diceSides = {4, 6, 8, 10, 12, 20, 100};
        List<Die> setOfDice = new ArrayList<>();

        // diceSides is the key (sides), diceArray is the value (count)
        for (int i = 0; i < diceArray.length; i++) {
            for (int j = 0; j < diceArray[i]; j++) {
                // build setOfDice with <diceArray[i]> d <diceSides[i]>
                setOfDice.add(new Die(diceSides[i]));
            }
        }
        return setOfDice;
    }

    private static List<Integer> rollD20(String name, int modifiers) {
        List<Integer> result = getRoll(List.of(new Die(20)));

        result.removeLast();
        result.add(modifiers);
        result.add(result.getFirst() + modifiers);

        return result;
    }

    private static List<Integer> rollD20WithAdvantage(String name, int modifiers) {
        List<Integer> result = getRoll(List.of(new Die(20), new Die(20)));
        int first = result.get(0);
        int second = result.get(1);
        int value = Math.max(first, second);

        result.removeLast();
        result.add(modifiers);
        result.add(modifiers + value);

        return result;
    }

    private static List<Integer> rollD20WithDisadvantage(String name, int modifiers) {
        List<Integer> result = getRoll(List.of(new Die(20), new Die(20)));
        int first = result.get(0);
        int second = result.get(1);
        int value = Math.min(first, second);

        result.removeLast();
        result.add(modifiers);
        result.add(modifiers + value);

        return result;
    }

    private static List<Integer> rollCustom(String name, int modifiers, List<Die> dice) {
        List<Integer> result = getRoll(dice);
        int subtotal = result.removeLast();

        result.add(modifiers);
        result.add(subtotal + modifiers);

        return result;
    }

    private static List<Integer> getRoll(List<Die> dice) {
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
