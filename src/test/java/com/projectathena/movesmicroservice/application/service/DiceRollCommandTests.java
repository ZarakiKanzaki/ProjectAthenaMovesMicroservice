package com.projectathena.movesmicroservice.application.service;

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
        characterPowerBeforeRollRequest = buildCharacterBeforeRollWithoutTagsAndStatuses();
        assertThat(diceRollCommand.rollForMove(characterPowerBeforeRollRequest)).isGreaterThanOrEqualTo(LOWER_DICE_BOUND).isLessThanOrEqualTo(UPPER_DICE_BOUND);
    }

    @Test
    void command_WithRollWithBurnedTag_ShouldBeNaturalRollOfTen() {
        characterPowerBeforeRollRequest = buildCharacterBeforeRollWithBurnedTag();
        assertThat(diceRollCommand.rollForMove(characterPowerBeforeRollRequest)).isEqualTo(SUCCESS_ROLL);
    }

    private static CharacterPowerBeforeRoll buildCharacterBeforeRollWithBurnedTag() {
        return CharacterPowerBeforeRoll.builder()
                .moveId(SNOWFLAKE_ID)
                .burnedTheTag(true)
                .dynamiteUnlocked(false)
                .highestPowerCharacterStatus(buildHighestPowerStatus())
                .highestWeaknessCharacterStatus(buildHighestWeaknessStatus())
                .numberOfMythosThemeBooks(LEGAL_NUMBER_OF_THEME_BOOK)
                .numberOfLogosThemeBooks(LEGAL_NUMBER_OF_THEME_BOOK)
                .powerTags(List.of())
                .weaknessTags(List.of())
                .build();
    }

    private static CharacterPowerBeforeRoll buildCharacterBeforeRollWithoutTagsAndStatuses() {
        return CharacterPowerBeforeRoll.builder()
                .moveId(SNOWFLAKE_ID)
                .burnedTheTag(false)
                .dynamiteUnlocked(false)
                .highestPowerCharacterStatus(buildHighestPowerStatus())
                .highestWeaknessCharacterStatus(buildHighestWeaknessStatus())
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

    private static CharacterPowerBeforeRoll getRollWithoutMoveId() {
        return CharacterPowerBeforeRoll.builder().moveId(0).build();
    }

}
