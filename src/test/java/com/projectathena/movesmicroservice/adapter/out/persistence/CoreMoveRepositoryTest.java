package com.projectathena.movesmicroservice.adapter.out.persistence;

import com.faunadb.client.FaunaClient;
import com.projectathena.movesmicroservice.core.enums.Condition;
import com.projectathena.movesmicroservice.core.exceptions.ApplicationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static com.projectathena.movesmicroservice.core.CharacterPowerBeforeRollTestBuilder.buildCharacterBeforeRoll;
import static com.projectathena.movesmicroservice.core.CharacterPowerBeforeRollTestBuilder.buildCharacterBeforeRollDynamite;
import static com.projectathena.movesmicroservice.core.MoveRollResultConstants.*;
import static com.projectathena.movesmicroservice.core.MoveTestBuilder.buildCompletedCoreMove;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;

class CoreMoveRepositoryTest {
    private static final String UNRECOGNIZED_MOVE_NAME = "INVALID_NAME";
    @InjectMocks
    private final FaunaClient client = mock(FaunaClient.class);
    private CoreMoveRepository coreMoveRepository;

    @BeforeEach
    void initializeComponents() {
        coreMoveRepository = new CoreMoveRepository();
        coreMoveRepository.setFaunaClientForTest(client);
    }

    @Test
    void getCoreMove_withNullMoveName_shouldThrowApplicationException() {
        assertThatThrownBy(() -> coreMoveRepository.getCoreMove(null)).isInstanceOf(ApplicationException.class);
    }

    @Test
    void getCoreMove_withEmptyMoveName_shouldThrowApplicationException() {
        assertThatThrownBy(() -> coreMoveRepository.getCoreMove("")).isInstanceOf(ApplicationException.class);
    }

    @Test
    void getCoreMove_withBlankMoveName_shouldThrowApplicationException() {
        assertThatThrownBy(() -> coreMoveRepository.getCoreMove("   ")).isInstanceOf(ApplicationException.class);
    }

    @Test
    void getCoreMove_withUnrecognizedMoveName_shouldThrowApplicationException() {
        assertThatThrownBy(() -> coreMoveRepository.getCoreMove(UNRECOGNIZED_MOVE_NAME)).isInstanceOf(ApplicationException.class);
    }

    @Test
    void getCoreMoveResult_withNullMoveAndRollPower_shouldThrowApplicationException() {
        assertThatThrownBy(() -> coreMoveRepository.getCoreMoveResult(PARTIAL_SUCCESS_ROLL, null, null)).isInstanceOf(ApplicationException.class);
    }

    @Test
    void getCoreMoveResult_withNullMove_shouldThrowApplicationException() {
        assertThatThrownBy(() -> coreMoveRepository.getCoreMoveResult(PARTIAL_SUCCESS_ROLL, null, buildCharacterBeforeRoll())).isInstanceOf(ApplicationException.class);
    }

    @Test
    void getCoreMoveResult_withNullRollPower_shouldThrowApplicationException() {
        assertThatThrownBy(() -> coreMoveRepository.getCoreMoveResult(PARTIAL_SUCCESS_ROLL, buildCompletedCoreMove(), null)).isInstanceOf(ApplicationException.class);
    }

    @Test
    void getCoreMoveResult_withFailure_shouldReturnMoveResultWithPartialSuccess() {
        assertThatCode(() -> {
            var rollResult = coreMoveRepository.getCoreMoveResult(FAILED_ROLL, buildCompletedCoreMove(), buildCharacterBeforeRoll());
            assertThat(rollResult.getRollResult()).isEqualTo(Condition.FAILURE);
            assertThat(rollResult.getRollOutcomes()).allMatch(x -> x.getConditions().contains(Condition.FAILURE));
        }).doesNotThrowAnyException();
    }

    @Test
    void getCoreMoveResult_withPartialSuccess_shouldReturnMoveResultWithPartialSuccess() {
        assertThatCode(() -> {
            var rollResult = coreMoveRepository.getCoreMoveResult(PARTIAL_SUCCESS_ROLL, buildCompletedCoreMove(), buildCharacterBeforeRoll());
            assertThat(rollResult.getRollResult()).isEqualTo(Condition.PARTIAL_SUCCESS);
            assertThat(rollResult.getRollOutcomes()).allMatch(x -> x.getConditions().contains(Condition.PARTIAL_SUCCESS));
        }).doesNotThrowAnyException();
    }

    @Test
    void getCoreMoveResult_withSuccess_shouldReturnMoveResultWithPartialSuccess() {
        assertThatCode(() -> {
            var rollResult = coreMoveRepository.getCoreMoveResult(SUCCESS_ROLL, buildCompletedCoreMove(), buildCharacterBeforeRoll());
            assertThat(rollResult.getRollResult()).isEqualTo(Condition.SUCCESS);
            assertThat(rollResult.getRollOutcomes()).allMatch(x -> x.getConditions().contains(Condition.SUCCESS));
        }).doesNotThrowAnyException();
    }

    @Test
    void getCoreMoveResult_withDynamiteSuccess_shouldReturnMoveResultWithPartialSuccess() {
        assertThatCode(() -> {
            var rollResult = coreMoveRepository.getCoreMoveResult(DYNAMITE_SUCCESS_ROLL, buildCompletedCoreMove(), buildCharacterBeforeRollDynamite());
            assertThat(rollResult.getRollResult()).isEqualTo(Condition.DYNAMITE_SUCCESS);
            assertThat(rollResult.getRollOutcomes()).allMatch(x -> x.getConditions().contains(Condition.DYNAMITE_SUCCESS));
        }).doesNotThrowAnyException();
    }
}
