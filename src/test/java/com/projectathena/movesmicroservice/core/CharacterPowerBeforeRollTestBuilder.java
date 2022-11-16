package com.projectathena.movesmicroservice.core;

import com.projectathena.movesmicroservice.core.entities.CharacterPowerBeforeRoll;
import com.projectathena.movesmicroservice.core.entities.CharacterStatus;
import com.projectathena.movesmicroservice.core.entities.Tag;
import com.projectathena.movesmicroservice.core.enums.TagType;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.projectathena.movesmicroservice.core.CharacterPowerBeforeRollConstants.*;
import static com.projectathena.movesmicroservice.core.CharacterPowerBeforeRollConstants.HIGHEST_POWER_STATUS;

public class CharacterPowerBeforeRollTestBuilder {


    public static CharacterPowerBeforeRoll getRollWithoutMoveId() {
        return CharacterPowerBeforeRoll.builder().moveName(null).build();
    }

    public static CharacterPowerBeforeRoll getPowerRollWithIllegalNumberOfThemeBooks() {
        return CharacterPowerBeforeRoll.builder().moveName(TITLE).numberOfLogosThemeBooks(ILLEGAL_NUMBER_OF_THEME_BOOK).numberOfMythosThemeBooks(ILLEGAL_NUMBER_OF_THEME_BOOK).build();
    }

    public static CharacterPowerBeforeRoll buildCharacterBeforeRollDynamite() {
        return CharacterPowerBeforeRoll.builder()
                .moveName(TITLE)
                .burnedTheTag(false)
                .dynamiteUnlocked(true)
                .highestPowerCharacterStatus(buildHighestPowerStatus())
                .highestWeaknessCharacterStatus(buildHighestWeaknessStatus())
                .numberOfMythosThemeBooks(LEGAL_NUMBER_OF_THEME_BOOK)
                .numberOfLogosThemeBooks(LEGAL_NUMBER_OF_THEME_BOOK)
                .powerTags(Arrays.asList(buildGenericPowerTag(), buildGenericPowerTag()))
                .weaknessTags(List.of())
                .build();
    }

    public static CharacterPowerBeforeRoll buildCharacterBeforeRollWithBurnedTag() {
        return CharacterPowerBeforeRoll.builder()
                .moveName(TITLE)
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

    public static CharacterPowerBeforeRoll buildCharacterBeforeRoll() {
        return CharacterPowerBeforeRoll.builder()
                .moveName(TITLE)
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

    public static CharacterPowerBeforeRoll buildCharacterBeforeRollWithoutTags() {
        return CharacterPowerBeforeRoll.builder()
                .moveName(TITLE)
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
