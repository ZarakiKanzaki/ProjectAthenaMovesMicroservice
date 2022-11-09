package com.projectathena.movesmicroservice.core.entities;

import com.projectathena.movesmicroservice.core.enums.Condition;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class MoveRollResult {
    private Condition rollResult;
    private String moveName;
    private String moveDescription;
    private List<Outcome> rollOutcomes;
    private CharacterPowerBeforeRoll characterPowerBeforeRoll;
}
