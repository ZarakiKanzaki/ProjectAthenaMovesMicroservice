package com.projectathena.movesmicroservice.core;

import com.projectathena.movesmicroservice.core.entities.CharacterPowerBeforeRoll;
import com.projectathena.movesmicroservice.core.entities.CharacterStatus;
import com.projectathena.movesmicroservice.core.entities.Tag;
import com.projectathena.movesmicroservice.core.enums.TagType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CharacterPowerBeforeRollTests {
    private static final String CHANGE_THE_GAME = "CHANGE THE GAME";
    private static final String HIGHEST_WEAKNESS_STATUS = "Highest Weakness Status";
    private static final String HIGHEST_POWER_STATUS = "Highest Power Status";
    private static final long SNOWFLAKE_ID = 1427933380;
    private CharacterPowerBeforeRoll characterPowerForTest;

    @BeforeEach
    void initializeComponents() {
        characterPowerForTest = buildCharacterBeforeRoll();
    }

    @Test
    void builder_completeCharacterBeforeRoll_shouldBeCompleted() {
        assertThat(characterPowerForTest.getMoveId()).isNotNull();
        assertThat(characterPowerForTest.getMoveId()).isGreaterThan(0);
        assertThat(characterPowerForTest.isBurnedTheTag()).isNotNull();
        assertThat(characterPowerForTest.isDynamiteUnlocked()).isNotNull();
        assertThat(characterPowerForTest.getPowerTags()).isNotNull();
        assertThat(characterPowerForTest.getWeaknessTags()).isNotNull();
        assertThat(characterPowerForTest.getHighestPowerCharacterStatus()).isNotNull();
        assertThat(characterPowerForTest.getHighestWeaknessCharacterStatus()).isNotNull();
        assertThat(characterPowerForTest.getNumberOfLogosThemeBooks()).isNotNull();
        assertThat(characterPowerForTest.getNumberOfMythosThemeBooks()).isNotNull();

        assertThat(getSumOfAllThemeBooks()).isEqualTo(4);


    }

    private int getSumOfAllThemeBooks() {
        return characterPowerForTest.getNumberOfMythosThemeBooks() + characterPowerForTest.getNumberOfLogosThemeBooks();
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
