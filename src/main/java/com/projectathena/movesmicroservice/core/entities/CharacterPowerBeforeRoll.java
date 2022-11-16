package com.projectathena.movesmicroservice.core.entities;

import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class CharacterPowerBeforeRoll {
    private String moveName;
    private boolean burnedTheTag;
    private boolean dynamiteUnlocked;
    private List<Tag> powerTags;
    private List<Tag> weaknessTags;
    private CharacterStatus highestPowerCharacterStatus;
    private CharacterStatus highestWeaknessCharacterStatus;
    private short numberOfMythosThemeBooks;
    private short numberOfLogosThemeBooks;

    public int getSumOfAllThemeBooks() {
        return numberOfMythosThemeBooks + numberOfLogosThemeBooks;
    }
}
