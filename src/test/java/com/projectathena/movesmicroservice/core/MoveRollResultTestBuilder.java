package com.projectathena.movesmicroservice.core;

import com.projectathena.movesmicroservice.core.entities.MoveRollResult;
import com.projectathena.movesmicroservice.core.entities.Outcome;
import com.projectathena.movesmicroservice.core.enums.Condition;

import java.util.Arrays;
import java.util.List;

import static com.projectathena.movesmicroservice.core.CharacterPowerBeforeRollConstants.TITLE;
import static com.projectathena.movesmicroservice.core.CharacterPowerBeforeRollTestBuilder.buildCharacterBeforeRoll;
import static com.projectathena.movesmicroservice.core.MoveTestBuilder.*;
import static com.projectathena.movesmicroservice.core.MoveConstants.DESCRIPTION;

public class MoveRollResultTestBuilder {

    public static MoveRollResult buildMoveResultWithFailure() {
        return buildMoveResultBasedOnCondition(Condition.FAILURE);
    }

    public static MoveRollResult buildMoveResultWithDynamiteSuccess() {
        return buildMoveResultBasedOnCondition(Condition.DYNAMITE_SUCCESS);
    }

    public static MoveRollResult buildMoveResultWithSuccess() {
        return buildMoveResultBasedOnCondition(Condition.SUCCESS);
    }

    public static MoveRollResult buildMoveResultWithPartialSuccess() {
        return buildMoveResultBasedOnCondition(Condition.PARTIAL_SUCCESS);
    }

    private static MoveRollResult buildMoveResultBasedOnCondition(Condition condition) {
        return MoveRollResult.builder()
                .moveName(TITLE)
                .rollResult(condition)
                .rollOutcomes(buildOutcomesBasedOnCondition(condition))
                .moveDescription(DESCRIPTION)
                .characterPowerBeforeRoll(buildCharacterBeforeRoll())
                .build();
    }

    private static List<Outcome> buildOutcomesBasedOnCondition(Condition theCondition) {
        return buildOutcomes().stream().filter(x -> x.getConditions().stream().anyMatch(condition -> condition == theCondition)).toList();
    }

    public static MoveRollResult buildMoveResult() {
        return MoveRollResult.builder()
                .moveName(CharacterPowerBeforeRollConstants.TITLE)
                .rollResult(Condition.PARTIAL_SUCCESS)
                .rollOutcomes(buildOutcomes())
                .moveDescription(DESCRIPTION)
                .characterPowerBeforeRoll(buildCharacterBeforeRoll())
                .build();
    }

    private static List<Outcome> buildOutcomes() {
        return Arrays.asList(getPartialSuccessOutcome(), getSuccessOutcome());
    }
}
