package com.projectathena.movesmicroservice.core.entities;

import com.projectathena.movesmicroservice.core.enums.Condition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MoveRollResult {
    private Condition rollResult;
    private String moveName;
    private String moveDescription;
    private List<Outcome> rollOutcomes;
    private CharacterPowerBeforeRoll characterPowerBeforeRoll;
}