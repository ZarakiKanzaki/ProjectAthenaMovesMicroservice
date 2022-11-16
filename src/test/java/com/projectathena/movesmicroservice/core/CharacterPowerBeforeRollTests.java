package com.projectathena.movesmicroservice.core;

import com.projectathena.movesmicroservice.core.entities.CharacterPowerBeforeRoll;
import com.projectathena.movesmicroservice.core.entities.CharacterStatus;
import com.projectathena.movesmicroservice.core.entities.Tag;
import com.projectathena.movesmicroservice.core.enums.TagType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.projectathena.movesmicroservice.core.CharacterPowerBeforeRollConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

class CharacterPowerBeforeRollTests {

    private CharacterPowerBeforeRoll characterPowerForTest;

    @BeforeEach
    void initializeComponents() {
        characterPowerForTest = CharacterPowerBeforeRollTestBuilder.buildCharacterBeforeRoll();
    }

    @Test
    void builder_completeCharacterBeforeRoll_shouldBeCompleted() {
        assertThat(characterPowerForTest.getMoveName()).isNotEmpty();
        assertThat(characterPowerForTest.getMoveName()).isNotNull();
        assertThat(characterPowerForTest.getMoveName()).isNotBlank();
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
        characterPowerForTest = CharacterPowerBeforeRollTestBuilder.buildCharacterBeforeRollWithBurnedTag();
        assertThat(characterPowerForTest.isBurnedTheTag()).isTrue();
        assertThat(characterPowerForTest.getPowerTags()).hasSize(1);
        assertThat(characterPowerForTest.getPowerTags().stream().anyMatch(x -> 3 == x.getValue())).isTrue();
    }

}
