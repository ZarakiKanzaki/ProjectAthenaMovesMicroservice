package com.projectathena.movesmicroservice.application.service;

import com.projectathena.movesmicroservice.adapter.out.common.DiceRollCommandHandler;
import com.projectathena.movesmicroservice.application.port.out.DiceRollCommand;
import com.projectathena.movesmicroservice.core.entities.CharacterPowerBeforeRoll;
import com.projectathena.movesmicroservice.core.entities.CharacterStatus;
import com.projectathena.movesmicroservice.core.entities.Tag;
import com.projectathena.movesmicroservice.core.enums.TagType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.projectathena.movesmicroservice.core.CharacterPowerBeforeRollConstants.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class DiceRollCommandTests {
    private static final short LOWER_DICE_BOUND = 2;
    private static final short UPPER_DICE_BOUND = 12;
    private static final short SUCCESS_ROLL = 10;
    private CharacterPowerBeforeRoll characterPowerBeforeRollRequest;
    private final DiceRollCommand diceRollCommand = new DiceRollCommandHandler();

    @Test
    void command_WithRollWithoutTagsAndStatuses_ShouldBeNaturalRollBetweenTwoAndTwelve() {
        characterPowerBeforeRollRequest = DiceRollCommandTests.buildCharacterBeforeRollWithoutTagsAndStatuses();
        assertThat(diceRollCommand.rollForMove(characterPowerBeforeRollRequest)).isGreaterThanOrEqualTo(DiceRollCommandTests.LOWER_DICE_BOUND).isLessThanOrEqualTo(DiceRollCommandTests.UPPER_DICE_BOUND);
    }

    @Test
    void command_WithRollWithBurnedTag_ShouldBeNaturalRollOfTen() {
        characterPowerBeforeRollRequest = DiceRollCommandTests.buildCharacterBeforeRollWithBurnedTag();
        assertThat(diceRollCommand.rollForMove(characterPowerBeforeRollRequest)).isEqualTo(DiceRollCommandTests.SUCCESS_ROLL);
    }

    private static CharacterPowerBeforeRoll buildCharacterBeforeRollWithBurnedTag() {
        return CharacterPowerBeforeRoll.builder()
                .moveName(TITLE)
                .burnedTheTag(true)
                .dynamiteUnlocked(false)
                .highestPowerCharacterStatus(DiceRollCommandTests.buildHighestPowerStatus())
                .highestWeaknessCharacterStatus(DiceRollCommandTests.buildHighestWeaknessStatus())
                .numberOfMythosThemeBooks(LEGAL_NUMBER_OF_THEME_BOOK)
                .numberOfLogosThemeBooks(LEGAL_NUMBER_OF_THEME_BOOK)
                .powerTags(List.of())
                .weaknessTags(List.of())
                .build();
    }

    private static CharacterPowerBeforeRoll buildCharacterBeforeRollWithoutTagsAndStatuses() {
        return CharacterPowerBeforeRoll.builder()
                .moveName(TITLE)
                .burnedTheTag(false)
                .dynamiteUnlocked(false)
                .highestPowerCharacterStatus(DiceRollCommandTests.buildHighestPowerStatus())
                .highestWeaknessCharacterStatus(DiceRollCommandTests.buildHighestWeaknessStatus())
                .numberOfMythosThemeBooks(LEGAL_NUMBER_OF_THEME_BOOK)
                .numberOfLogosThemeBooks(LEGAL_NUMBER_OF_THEME_BOOK)
                .powerTags(List.of())
                .weaknessTags(List.of())
                .build();
    }

    private static Tag buildGenericPowerTag() {
        return Tag.builder().type(TagType.POWER).value((short) 1).name("Generic Power Tag").build();
    }

    private static CharacterStatus buildHighestWeaknessStatus() {
        return CharacterStatus.builder().name(HIGHEST_WEAKNESS_STATUS).value((short) 0).build();
    }

    private static CharacterStatus buildHighestPowerStatus() {
        return CharacterStatus.builder().name(HIGHEST_POWER_STATUS).value((short) 0).build();
    }


}
