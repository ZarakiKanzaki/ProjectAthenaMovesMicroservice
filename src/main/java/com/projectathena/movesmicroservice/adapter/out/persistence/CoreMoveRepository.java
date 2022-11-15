package com.projectathena.movesmicroservice.adapter.out.persistence;

import com.projectathena.movesmicroservice.core.entities.CharacterPowerBeforeRoll;
import com.projectathena.movesmicroservice.core.entities.Outcome;
import com.projectathena.movesmicroservice.core.enums.Condition;
import com.projectathena.movesmicroservice.core.exceptions.ApplicationException;
import com.projectathena.movesmicroservice.application.port.in.GetCoreMoveQuery;
import com.projectathena.movesmicroservice.application.port.out.CoreMovePort;
import com.projectathena.movesmicroservice.core.entities.Move;
import com.projectathena.movesmicroservice.core.entities.MoveRollResult;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.faunadb.client.query.Language.*;

@Repository
public class CoreMoveRepository extends FaunaRepository<Move> implements CoreMovePort, GetCoreMoveQuery {

    private static final short SUCCESS_LOWER_BOUND = 10;
    private static final short PARTIAL_SUCCESS_LOWER_BOUND = 7;
    private static final String MOVES = "moves";

    public CoreMoveRepository() {
        super(Move.class, CoreMoveRepository.MOVES);
    }

    @Override
    public Move getCoreMove(final long moveId) throws ApplicationException {
        try {
            return client.query(
                            Select(
                                    Value("data"),
                                    Get(Ref(Value(moveId), className))
                            )
                    )
                    .thenApply(this::toEntity)
                    .get();
        } catch (final InterruptedException | ExecutionException e) {
            throw new ApplicationException();
        }
    }

    @Override
    public MoveRollResult getCoreMoveResult(short rollForMove, final Move coreMove, final CharacterPowerBeforeRoll rollPower) {
        var rollResult = buildRollResult(coreMove, rollPower);

        decideTheOutcomeOfTheRoll(rollForMove, coreMove, rollPower, rollResult);

        return rollResult;
    }

    private static MoveRollResult buildRollResult(Move coreMove, CharacterPowerBeforeRoll rollPower) {
        return MoveRollResult.builder()
                .characterPowerBeforeRoll(rollPower)
                .moveName(coreMove.getName())
                .moveDescription(coreMove.getDescription())
                .build();
    }

    private static void decideTheOutcomeOfTheRoll(short rollForMove, Move coreMove, CharacterPowerBeforeRoll rollPower, MoveRollResult rollResult) {
        if (isDynamiteSuccess(rollForMove, rollPower)) {
            setDynamiteSuccess(coreMove, rollResult);
        } else if (isSuccess(rollForMove)) {
            setSuccess(coreMove, rollResult);
        } else if (isFailure(rollForMove)) {
            setFailure(rollResult);
        } else {
            setPartialSuccess(coreMove, rollResult);
        }
    }

    private static void setPartialSuccess(Move coreMove, MoveRollResult rollResult) {
        rollResult.setRollOutcomes(getPartialSuccessOutcomes(coreMove));
        rollResult.setRollResult(Condition.PARTIAL_SUCCESS);
    }

    private static void setFailure(MoveRollResult rollResult) {
        rollResult.setRollOutcomes(List.of(getFailureCondition()));
        rollResult.setRollResult(Condition.FAILURE);
    }
    
    private static void setSuccess(Move coreMove, MoveRollResult rollResult) {
        rollResult.setRollOutcomes(getSuccessOutcomes(coreMove));
        rollResult.setRollResult(Condition.SUCCESS);
    }

    private static void setDynamiteSuccess(Move coreMove, MoveRollResult rollResult) {
        rollResult.setRollOutcomes(getDynamiteOutcomes(coreMove));
        rollResult.setRollResult(Condition.DYNAMITE_SUCCESS);
    }

    private static Outcome getFailureCondition() {
        return new Outcome("You Failed the test, the MC can now make his own Hard Move against you!", List.of(Condition.FAILURE));
    }

    private static boolean isFailure(short rollForMove) {
        return rollForMove < PARTIAL_SUCCESS_LOWER_BOUND;
    }

    private static boolean isSuccess(short rollForMove) {
        return rollForMove >= SUCCESS_LOWER_BOUND;
    }

    private static boolean isDynamiteSuccess(short rollForMove, CharacterPowerBeforeRoll rollPower) {
        return isSuccess(rollForMove) && rollPower.isDynamiteUnlocked();
    }

    private static List<Outcome> getPartialSuccessOutcomes(Move coreMove) {
        return coreMove.getOutcomes().stream()
                .filter(outcome -> outcome.getConditions().stream()
                        .anyMatch(condition -> condition == Condition.PARTIAL_SUCCESS))
                .toList();
    }

    private static List<Outcome> getSuccessOutcomes(Move coreMove) {
        return coreMove.getOutcomes().stream()
                .filter(outcome -> outcome.getConditions().stream()
                        .anyMatch(condition -> condition == Condition.SUCCESS))
                .toList();
    }

    private static List<Outcome> getDynamiteOutcomes(Move coreMove) {
        return coreMove.getOutcomes().stream()
                .filter(outcome -> outcome.getConditions().stream()
                        .anyMatch(condition -> condition == Condition.DYNAMITE_SUCCESS))
                .toList();
    }
}
