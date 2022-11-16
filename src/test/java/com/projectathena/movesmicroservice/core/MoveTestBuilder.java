package com.projectathena.movesmicroservice.core;

import com.projectathena.movesmicroservice.core.entities.Move;
import com.projectathena.movesmicroservice.core.entities.Outcome;
import com.projectathena.movesmicroservice.core.enums.Condition;
import com.projectathena.movesmicroservice.core.enums.MoveType;

import java.util.Arrays;
import java.util.List;

import static com.projectathena.movesmicroservice.core.MoveConstants.*;

public class MoveTestBuilder {
    public static Move buildCompletedCoreMove() {
        return Move.builder()
                .type(MoveType.CORE)
                .name(TITLE)
                .description(DESCRIPTION)
                .exampleOfApplication(EXAMPLE_OF_APPLICATIONS)
                .outcomes(buildOutcomes())
                .build();
    }

    private static List<Outcome> buildOutcomes() {
        return Arrays.asList(getPartialSuccessOutcome(), getSuccessOutcome());
    }

    public static Outcome getSuccessOutcome() {
        return Outcome.builder()
                .description("Scale up the effect (greater area or more targets)")
                .conditions(List.of(Condition.PARTIAL_SUCCESS, Condition.SUCCESS)).build();
    }

    public static Outcome getPartialSuccessOutcome() {
        return Outcome.builder().description("Create a story tag").conditions(List.of(Condition.PARTIAL_SUCCESS)).build();
    }

    public static Outcome buildFailureOutcome() {
        return Outcome.builder()
                .description("You Failed the test, the MC can now make his own Hard Move against you!")
                .conditions(List.of(Condition.FAILURE)).build();
    }
}
