package com.projectathena.movesmicroservice.application.service;

import com.projectathena.movesmicroservice.application.port.out.DiceRollCommand;
import com.projectathena.movesmicroservice.application.port.out.GetCoreMoveQuery;
import com.projectathena.movesmicroservice.application.port.out.GetCoreMoveResultQuery;
import com.projectathena.movesmicroservice.core.entities.*;
import com.projectathena.movesmicroservice.core.enums.Condition;
import com.projectathena.movesmicroservice.core.exceptions.ApplicationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.projectathena.movesmicroservice.core.CharacterPowerBeforeRollTestBuilder.*;
import static com.projectathena.movesmicroservice.core.MoveRollResultConstants.*;
import static com.projectathena.movesmicroservice.core.MoveRollResultTestBuilder.*;
import static com.projectathena.movesmicroservice.core.MoveTestBuilder.buildCompletedCoreMove;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CoreMoveServiceTests {
    private final GetCoreMoveResultQuery coreMovePort = mock(GetCoreMoveResultQuery.class);
    private final DiceRollCommand diceRollCommand = mock(DiceRollCommand.class);
    private final GetCoreMoveQuery getCoreMoveQuery = mock(GetCoreMoveQuery.class);
    private CoreMoveService coreMoveService;
    private CharacterPowerBeforeRoll characterPowerBeforeRollRequest;

    @BeforeEach
    void Setup() {
        coreMoveService = new CoreMoveService(coreMovePort, diceRollCommand, getCoreMoveQuery);
    }

    @Test
    void service_WithRequestWithoutMoveId_shouldThrowException() {
        characterPowerBeforeRollRequest = getRollWithoutMoveId();

        assertThatThrownBy(() -> coreMoveService.userCoreMove(characterPowerBeforeRollRequest)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void service_withRequestWithMoreThan4CharacterThemeBooks_ShouldThrowException() {
        characterPowerBeforeRollRequest = getPowerRollWithIllegalNumberOfThemeBooks();

        assertThatThrownBy(() -> coreMoveService.userCoreMove(characterPowerBeforeRollRequest)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void service_WithRollLowerThanSeven_ReturnFailingRollResult() {
        characterPowerBeforeRollRequest = buildCharacterBeforeRollWithoutTags();

        when(diceRollCommand.rollForMove(characterPowerBeforeRollRequest)).thenReturn(FAILED_ROLL);
        assertThatCode(() -> when(getCoreMoveQuery.getCoreMove(characterPowerBeforeRollRequest.getMoveName())).thenReturn(buildCompletedCoreMove())).doesNotThrowAnyException();
        when(coreMoveService.userCoreMove(characterPowerBeforeRollRequest)).thenReturn(buildMoveResultWithFailure());

        var rollResult = coreMoveService.userCoreMove(characterPowerBeforeRollRequest);

        assertThat(diceRollCommand.rollForMove(characterPowerBeforeRollRequest)).isLessThan(PARTIAL_SUCCESS_LOWER_BOUND);
        assertThat(rollResult.getRollResult()).isEqualTo(Condition.FAILURE);
        assertThat(rollResult.getRollOutcomes()).allMatch(x -> x.getConditions().contains(Condition.FAILURE));
    }

    @Test
    void service_WithRollLowerThanTenButGreaterThanSeven_ReturnPartialSuccessRollResult() {
        characterPowerBeforeRollRequest = buildCharacterBeforeRoll();

        when(diceRollCommand.rollForMove(characterPowerBeforeRollRequest)).thenReturn(PARTIAL_SUCCESS_ROLL);
        assertThatCode(() -> when(getCoreMoveQuery.getCoreMove(characterPowerBeforeRollRequest.getMoveName())).thenReturn(buildCompletedCoreMove())).doesNotThrowAnyException();
        when(coreMoveService.userCoreMove(characterPowerBeforeRollRequest)).thenReturn(buildMoveResultWithPartialSuccess());

        var rollResult = coreMoveService.userCoreMove(characterPowerBeforeRollRequest);

        assertThat(diceRollCommand.rollForMove(characterPowerBeforeRollRequest)).isLessThan(SUCCESS_LOWER_BOUND);
        assertThat(diceRollCommand.rollForMove(characterPowerBeforeRollRequest)).isGreaterThanOrEqualTo(PARTIAL_SUCCESS_LOWER_BOUND);
        assertThat(rollResult.getRollResult()).isEqualTo(Condition.PARTIAL_SUCCESS);
        assertThat(rollResult.getRollOutcomes()).allMatch(x -> x.getConditions().contains(Condition.PARTIAL_SUCCESS));
    }

    @Test
    void service_WithRollGreaterThanTen_ReturnSuccessRollResult() {
        characterPowerBeforeRollRequest = buildCharacterBeforeRoll();

        when(diceRollCommand.rollForMove(characterPowerBeforeRollRequest)).thenReturn(SUCCESS_ROLL);
        assertThatCode(() -> when(getCoreMoveQuery.getCoreMove(characterPowerBeforeRollRequest.getMoveName())).thenReturn(buildCompletedCoreMove())).doesNotThrowAnyException();
        when(coreMoveService.userCoreMove(characterPowerBeforeRollRequest)).thenReturn(buildMoveResultWithSuccess());

        var rollResult = coreMoveService.userCoreMove(characterPowerBeforeRollRequest);

        assertThat(diceRollCommand.rollForMove(characterPowerBeforeRollRequest)).isGreaterThanOrEqualTo(SUCCESS_LOWER_BOUND);
        assertThat(rollResult.getRollResult()).isEqualTo(Condition.SUCCESS);
        assertThat(rollResult.getRollOutcomes()).allMatch(x -> x.getConditions().contains(Condition.SUCCESS));
    }

    @Test
    void service_WithRollGreaterThanTenAndDynamite_ReturnDynamiteSuccessRollResult() {
        characterPowerBeforeRollRequest = buildCharacterBeforeRollDynamite();

        when(diceRollCommand.rollForMove(characterPowerBeforeRollRequest)).thenReturn(DYNAMITE_SUCCESS_ROLL);
        assertThatCode(() -> when(getCoreMoveQuery.getCoreMove(characterPowerBeforeRollRequest.getMoveName())).thenReturn(buildCompletedCoreMove())).doesNotThrowAnyException();
        when(coreMoveService.userCoreMove(characterPowerBeforeRollRequest)).thenReturn(buildMoveResultWithDynamiteSuccess());

        var rollResult = coreMoveService.userCoreMove(characterPowerBeforeRollRequest);

        assertThat(diceRollCommand.rollForMove(characterPowerBeforeRollRequest)).isGreaterThanOrEqualTo(SUCCESS_UPPER_BOUND);
        assertThat(rollResult.getRollResult()).isEqualTo(Condition.DYNAMITE_SUCCESS);
        assertThat(rollResult.getRollOutcomes()).allMatch(x -> x.getConditions().contains(Condition.DYNAMITE_SUCCESS));
    }

    @Test
    void service_WithCompliantRollButTimeoutRequest_ShouldThrowException() {
        characterPowerBeforeRollRequest = buildCharacterBeforeRoll();

        when(diceRollCommand.rollForMove(characterPowerBeforeRollRequest)).thenReturn(SUCCESS_ROLL);
        try {
            when(getCoreMoveQuery.getCoreMove(characterPowerBeforeRollRequest.getMoveName())).thenThrow(new ApplicationException());
        } catch (final ApplicationException e) {
            assertThatThrownBy(() -> coreMoveService.userCoreMove(characterPowerBeforeRollRequest)).isInstanceOf(RuntimeException.class);
        }
    }

}
