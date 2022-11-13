package com.projectathena.movesmicroservice.application.service;

import com.projectathena.movesmicroservice.core.entities.CharacterPowerBeforeRoll;
import com.projectathena.movesmicroservice.core.entities.Tag;

import java.util.Random;

public class DiceRollCommandHandler implements DiceRollCommand {
    private static final short PARTIAL_SUCCESS_LOWER_BOUND = 7;
    private static final short BURNED_TAG_POWER = 3;
    private Random firstDice;
    private Random secondDice;

    public DiceRollCommandHandler() {
        firstDice = new Random();
        secondDice = new Random();
    }

    @Override
    public short rollForMove(CharacterPowerBeforeRoll characterPowerBeforeRoll) {
        if (characterPowerBeforeRoll.isBurnedTheTag()) {
            return (short) (PARTIAL_SUCCESS_LOWER_BOUND +
                    BURNED_TAG_POWER +
                    getPositiveStatus(characterPowerBeforeRoll) -
                    getNegativeStatus(characterPowerBeforeRoll));
        }

        return (short) (rollD6(firstDice) +
                rollD6(secondDice) +
                getPositiveValuesFromCharacterRoll(characterPowerBeforeRoll) -
                getNegativeValuesFromCharacterRoll(characterPowerBeforeRoll));
    }

    private short getNegativeValuesFromCharacterRoll(CharacterPowerBeforeRoll characterPowerBeforeRoll) {
        return (short) (getSumOfWeaknessTags(characterPowerBeforeRoll) + getSumOfWeaknessTags(characterPowerBeforeRoll));
    }

    private static short getSumOfWeaknessTags(CharacterPowerBeforeRoll characterPowerBeforeRoll) {
        return (short) characterPowerBeforeRoll.getWeaknessTags().stream().mapToInt(Tag::getValue).sum();
    }

    private static short getPositiveValuesFromCharacterRoll(CharacterPowerBeforeRoll characterPowerBeforeRoll) {
        return (short) (getSumOfPowerTags(characterPowerBeforeRoll) + getPositiveStatus(characterPowerBeforeRoll));
    }

    private static short getNegativeStatus(CharacterPowerBeforeRoll characterPowerBeforeRoll) {
        return characterPowerBeforeRoll.getHighestWeaknessCharacterStatus().getValue();
    }

    private static short getPositiveStatus(CharacterPowerBeforeRoll characterPowerBeforeRoll) {
        return characterPowerBeforeRoll.getHighestPowerCharacterStatus().getValue();
    }

    private static short getSumOfPowerTags(CharacterPowerBeforeRoll characterPowerBeforeRoll) {
        return (short) characterPowerBeforeRoll.getPowerTags().stream().mapToInt(Tag::getValue).sum();
    }

    private short rollD6(Random dice) {
        return (short) (dice.nextInt(6 - 1) + 1);
    }
}
