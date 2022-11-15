package com.projectathena.movesmicroservice.core;

import com.projectathena.movesmicroservice.core.entities.*;
import com.projectathena.movesmicroservice.core.enums.Condition;
import com.projectathena.movesmicroservice.core.enums.TagType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MoveRollResultTests {
    private static final String CHANGE_THE_GAME = "CHANGE THE GAME";
    private static final String HIGHEST_WEAKNESS_STATUS = "Highest Weakness Status";
    private static final String HIGHEST_POWER_STATUS = "Highest Power Status";
    private static final long SNOWFLAKE_ID = 1427933380;
    private MoveRollResult rollResultForTest;

    @BeforeEach
    void initializeComponents() {
        rollResultForTest = buildMoveResult();
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
                .moveName(CHANGE_THE_GAME)
                .rollResult(Condition.PARTIAL_SUCCESS)
                .rollOutcomes(buildOutcomes())
                .moveDescription("When you use your abilities to give yourself or" +
                        "your allies an advantage, roll+Power. On a hit," +
                        "you get Juice=Power. Spend your Juice to gain the" +
                        "following effects, one-to-one:")
                .characterPowerBeforeRoll(buildCharacterBeforeRoll())
                .build();
    }

    private static List<Outcome> buildOutcomes() {
        return Arrays.asList(buildPartialSuccessOutcome(), buildSuccessOutcome());
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
                .highestPowerCharacterStatus(buildHighestPowerStatus())
                .highestWeaknessCharacterStatus(buildHighestWeaknessStatus())
                .numberOfMythosThemeBooks((short) 1)
                .numberOfLogosThemeBooks((short) 3)
                .powerTags(Arrays.asList(buildGenericPowerTag(), buildGenericPowerTag()))
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
