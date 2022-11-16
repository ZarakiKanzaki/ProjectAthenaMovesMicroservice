package com.projectathena.movesmicroservice.adapter.out.common;

import com.projectathena.movesmicroservice.application.port.out.DiceRollCommand;
import com.projectathena.movesmicroservice.core.entities.CharacterPowerBeforeRoll;
import com.projectathena.movesmicroservice.core.entities.Tag;

import java.security.SecureRandom;

public class DiceRollCommandHandler implements DiceRollCommand {
    private static final short PARTIAL_SUCCESS_LOWER_BOUND = 7;
    private static final short BURNED_TAG_POWER = 3;
    private final SecureRandom firstDice;
    private final SecureRandom secondDice;

    public DiceRollCommandHandler() {
        firstDice = new SecureRandom();
        secondDice = new SecureRandom();
    }

    @Override
    public short rollForMove(CharacterPowerBeforeRoll characterPowerBeforeRoll) {
        if (characterPowerBeforeRoll.isBurnedTheTag()) {
            return (short) (DiceRollCommandHandler.PARTIAL_SUCCESS_LOWER_BOUND +
                    DiceRollCommandHandler.BURNED_TAG_POWER +
                    DiceRollCommandHandler.getPositiveStatus(characterPowerBeforeRoll) -
                    DiceRollCommandHandler.getNegativeStatus(characterPowerBeforeRoll));
        }

        return (short) (rollD6(firstDice) +
                rollD6(secondDice) +
                DiceRollCommandHandler.getPositiveValuesFromCharacterRoll(characterPowerBeforeRoll) -
                getNegativeValuesFromCharacterRoll(characterPowerBeforeRoll));
    }

    private short getNegativeValuesFromCharacterRoll(CharacterPowerBeforeRoll characterPowerBeforeRoll) {
        return (short) (DiceRollCommandHandler.getSumOfWeaknessTags(characterPowerBeforeRoll) + DiceRollCommandHandler.getSumOfWeaknessTags(characterPowerBeforeRoll));
    }

    private static short getSumOfWeaknessTags(CharacterPowerBeforeRoll characterPowerBeforeRoll) {
        return (short) characterPowerBeforeRoll.getWeaknessTags().stream().mapToInt(Tag::getValue).sum();
    }

    private static short getPositiveValuesFromCharacterRoll(CharacterPowerBeforeRoll characterPowerBeforeRoll) {
        return (short) (DiceRollCommandHandler.getSumOfPowerTags(characterPowerBeforeRoll) + DiceRollCommandHandler.getPositiveStatus(characterPowerBeforeRoll));
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

    private short rollD6(SecureRandom dice) {
        return (short) (dice.nextInt(6 - 1) + 1);
    }
}
