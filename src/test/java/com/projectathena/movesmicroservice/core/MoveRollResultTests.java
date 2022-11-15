package com.projectathena.movesmicroservice.core;

import com.projectathena.movesmicroservice.core.entities.*;
import com.projectathena.movesmicroservice.core.enums.Condition;
import com.projectathena.movesmicroservice.core.enums.TagType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static com.projectathena.movesmicroservice.core.CharacterPowerBeforeRollConstants.*;
import static com.projectathena.movesmicroservice.core.MoveRollResultConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

class MoveRollResultTests {

    private MoveRollResult rollResultForTest;

    @BeforeEach
    void initializeComponents() {
        rollResultForTest = MoveRollResultTests.buildMoveResult();
    }


    @Test
    void builder_completeMoveRollResult_shouldBeCompleted() {
        assertThat(rollResultForTest.getMoveName()).isNotNull();
        assertThat(rollResultForTest.getMoveName()).isNotEmpty();
        assertThat(rollResultForTest.getMoveDescription()).isNotNull();
        assertThat(rollResultForTest.getMoveDescription()).isNotEmpty();
        assertThat(rollResultForTest.getRollResult()).isNotNull();
        assertThat(rollResultForTest.getRollOutcomes()).isNotEmpty();
        assertThat(rollResultForTest.getCharacterPowerBeforeRoll()).isNotNull();
    }

    private static MoveRollResult buildMoveResult() {
        return MoveRollResult.builder()
                .moveName(TITLE)
                .rollResult(Condition.PARTIAL_SUCCESS)
                .rollOutcomes(MoveRollResultTests.buildOutcomes())
                .moveDescription(DESCRIPTION)
                .characterPowerBeforeRoll(MoveRollResultTests.buildCharacterBeforeRoll())
                .build();
    }

    private static List<Outcome> buildOutcomes() {
        return Arrays.asList(MoveRollResultTests.buildPartialSuccessOutcome(), MoveRollResultTests.buildSuccessOutcome());
    }

    private static Outcome buildSuccessOutcome() {
        return Outcome.builder()
                .description("Scale up the effect (greater area or more targets)")
                .conditions(List.of(Condition.PARTIAL_SUCCESS, Condition.SUCCESS)).build();
    }

    private static Outcome buildPartialSuccessOutcome() {
        return Outcome.builder().description("Create a story tag").conditions(List.of(Condition.PARTIAL_SUCCESS)).build();
    }

    private static CharacterPowerBeforeRoll buildCharacterBeforeRoll() {
        return CharacterPowerBeforeRoll.builder()
                .moveId(SNOWFLAKE_ID)
                .burnedTheTag(false)
                .dynamiteUnlocked(false)
                .highestPowerCharacterStatus(MoveRollResultTests.buildHighestPowerStatus())
                .highestWeaknessCharacterStatus(MoveRollResultTests.buildHighestWeaknessStatus())
                .numberOfMythosThemeBooks((short) 1)
                .numberOfLogosThemeBooks((short) 3)
                .powerTags(Arrays.asList(MoveRollResultTests.buildGenericPowerTag(), MoveRollResultTests.buildGenericPowerTag()))
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
