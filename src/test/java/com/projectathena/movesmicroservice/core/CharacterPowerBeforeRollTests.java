package com.projectathena.movesmicroservice.core;

import com.projectathena.movesmicroservice.core.entities.CharacterPowerBeforeRoll;
import com.projectathena.movesmicroservice.core.entities.CharacterStatus;
import com.projectathena.movesmicroservice.core.entities.Tag;
import com.projectathena.movesmicroservice.core.enums.TagType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.projectathena.movesmicroservice.core.CharacterPowerBeforeRollConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CharacterPowerBeforeRollTests {

    private CharacterPowerBeforeRoll characterPowerForTest;

    @BeforeEach
    void initializeComponents() {
        characterPowerForTest = buildCharacterBeforeRoll();
    }

    @Test
    void builder_completeCharacterBeforeRoll_shouldBeCompleted() {
        assertThat(characterPowerForTest.getMoveId()).isPositive();
        assertThat(characterPowerForTest.getPowerTags()).isNotNull();
        assertThat(characterPowerForTest.getWeaknessTags()).isNotNull();
        assertThat(characterPowerForTest.getHighestPowerCharacterStatus()).isNotNull();
        assertThat(characterPowerForTest.getHighestWeaknessCharacterStatus()).isNotNull();
        assertThat(characterPowerForTest.getNumberOfLogosThemeBooks()).isGreaterThanOrEqualTo((short) 0);
        assertThat(characterPowerForTest.getNumberOfMythosThemeBooks()).isGreaterThanOrEqualTo((short) 0);

        assertThat(characterPowerForTest.getSumOfAllThemeBooks()).isEqualTo(4);
    }

    @Test
    void builder_withBurnedTag_shouldContainOnePowerTagWith3Power(){
        characterPowerForTest = buildCharacterBeforeRollWithBurnedTag();
        assertThat(characterPowerForTest.isBurnedTheTag()).isTrue();
        assertThat(characterPowerForTest.getPowerTags()).hasSize(1);
        assertThat(characterPowerForTest.getPowerTags().stream().anyMatch(x -> x.getValue() == 3)).isTrue();
    }

    private static CharacterPowerBeforeRoll buildCharacterBeforeRollWithBurnedTag() {
        return CharacterPowerBeforeRoll.builder()
                .moveId(SNOWFLAKE_ID)
                .burnedTheTag(true)
                .dynamiteUnlocked(false)
                .highestPowerCharacterStatus(buildHighestPowerStatus())
                .highestWeaknessCharacterStatus(buildHighestWeaknessStatus())
                .numberOfMythosThemeBooks((short) 1)
                .numberOfLogosThemeBooks((short) 3)
                .powerTags(Collections.singletonList(buildBurnedPowerTag()))
                .weaknessTags(List.of())
                .build();
    }

    private static CharacterPowerBeforeRoll buildCharacterBeforeRoll() {
        return CharacterPowerBeforeRoll.builder()
                .moveId(SNOWFLAKE_ID)
                .burnedTheTag(false)
                .dynamiteUnlocked(false)
                .highestPowerCharacterStatus(buildHighestPowerStatus())
                .highestWeaknessCharacterStatus(buildHighestWeaknessStatus())
                .numberOfMythosThemeBooks(LEGAL_NUMBER_OF_THEME_BOOK)
                .numberOfLogosThemeBooks(LEGAL_NUMBER_OF_THEME_BOOK)
                .powerTags(Arrays.asList(buildGenericPowerTag(), buildGenericPowerTag()))
                .weaknessTags(List.of())
                .build();
    }

    private static Tag buildBurnedPowerTag() {
        return Tag.builder().type(TagType.POWER).value((short) 3).name("Generic Power Tag").build();
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
