package com.projectathena.movesMicroservice.core.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CharacterPowerBeforeRoll {
    private long moveId;
    private boolean burnedTheTag;
    private boolean dynamiteUnlocked;
    private List<Tag> powerTags;
    private List<Tag> weaknessTags;
    private CharacterStatus highestPowerCharacterStatus;
    private CharacterStatus highestWeaknessCharacterStatus;
    private short numberOfMythosThemeBooks;
    private short numberOfLogosThemeBooks;
}
