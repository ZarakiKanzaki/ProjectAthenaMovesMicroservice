package com.projectathena.movesmicroservice.application.service;

import com.projectathena.movesmicroservice.application.port.out.DiceRollCommand;
import com.projectathena.movesmicroservice.application.port.out.GetCoreMoveQuery;
import com.projectathena.movesmicroservice.application.port.out.GetCoreMoveResultQuery;
import com.projectathena.movesmicroservice.core.entities.*;
import com.projectathena.movesmicroservice.core.enums.Condition;
import com.projectathena.movesmicroservice.core.enums.MoveType;
import com.projectathena.movesmicroservice.core.enums.TagType;
import com.projectathena.movesmicroservice.core.exceptions.ApplicationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static com.projectathena.movesmicroservice.core.CharacterPowerBeforeRollConstants.*;
import static com.projectathena.movesmicroservice.core.MoveRollResultConstants.*;
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
        characterPowerBeforeRollRequest = CoreMoveServiceTests.getRollWithoutMoveId();

        assertThatThrownBy(() -> coreMoveService.userCoreMove(characterPowerBeforeRollRequest)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void service_withRequestWithMoreThan4CharacterThemeBooks_ShouldThrowException() {
        characterPowerBeforeRollRequest = getPowerRollWithIllegalNumberOfThemeBooks();

        assertThatThrownBy(() -> coreMoveService.userCoreMove(characterPowerBeforeRollRequest)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void service_WithRollLowerThanSeven_ReturnFailingRollResult() {
        characterPowerBeforeRollRequest = CoreMoveServiceTests.buildCharacterBeforeRoll();

        when(diceRollCommand.rollForMove(characterPowerBeforeRollRequest)).thenReturn(FAILED_ROLL);
        assertThatCode(() -> when(getCoreMoveQuery.getCoreMove(characterPowerBeforeRollRequest.getMoveId())).thenReturn(CoreMoveServiceTests.buildCoreMove())).doesNotThrowAnyException();
        when(coreMoveService.userCoreMove(characterPowerBeforeRollRequest)).thenReturn(CoreMoveServiceTests.buildMoveResultWithFailure());

        final var rollResult = coreMoveService.userCoreMove(characterPowerBeforeRollRequest);

        assertThat(diceRollCommand.rollForMove(characterPowerBeforeRollRequest)).isLessThan(PARTIAL_SUCCESS_LOWER_BOUND);
        assertThat(rollResult.getRollResult()).isEqualTo(Condition.FAILURE);
        assertThat(rollResult.getRollOutcomes()).allMatch(x -> x.getConditions().contains(Condition.FAILURE));
    }

    @Test
    void service_WithRollLowerThanTenButGreaterThanSeven_ReturnPartialSuccessRollResult() {
        characterPowerBeforeRollRequest = CoreMoveServiceTests.buildCharacterBeforeRoll();

        when(diceRollCommand.rollForMove(characterPowerBeforeRollRequest)).thenReturn(PARTIAL_SUCCESS);
        assertThatCode(() -> when(getCoreMoveQuery.getCoreMove(characterPowerBeforeRollRequest.getMoveId())).thenReturn(CoreMoveServiceTests.buildCoreMove())).doesNotThrowAnyException();
        when(coreMoveService.userCoreMove(characterPowerBeforeRollRequest)).thenReturn(CoreMoveServiceTests.buildMoveResultWithPartialSuccess());

        var rollResult = coreMoveService.userCoreMove(characterPowerBeforeRollRequest);

        assertThat(diceRollCommand.rollForMove(characterPowerBeforeRollRequest)).isLessThan(SUCCESS_LOWER_BOUND);
        assertThat(diceRollCommand.rollForMove(characterPowerBeforeRollRequest)).isGreaterThanOrEqualTo(PARTIAL_SUCCESS_LOWER_BOUND);
        assertThat(rollResult.getRollResult()).isEqualTo(Condition.PARTIAL_SUCCESS);
        assertThat(rollResult.getRollOutcomes()).allMatch(x -> x.getConditions().contains(Condition.PARTIAL_SUCCESS));
    }

    @Test
    void service_WithRollGreaterThanTen_ReturnSuccessRollResult() {
        characterPowerBeforeRollRequest = CoreMoveServiceTests.buildCharacterBeforeRoll();

        when(diceRollCommand.rollForMove(characterPowerBeforeRollRequest)).thenReturn(SUCCESS);
        assertThatCode(() -> when(getCoreMoveQuery.getCoreMove(characterPowerBeforeRollRequest.getMoveId())).thenReturn(CoreMoveServiceTests.buildCoreMove())).doesNotThrowAnyException();
        when(coreMoveService.userCoreMove(characterPowerBeforeRollRequest)).thenReturn(CoreMoveServiceTests.buildMoveResultWithSuccess());

        var rollResult = coreMoveService.userCoreMove(characterPowerBeforeRollRequest);

        assertThat(diceRollCommand.rollForMove(characterPowerBeforeRollRequest)).isGreaterThanOrEqualTo(SUCCESS_LOWER_BOUND);
        assertThat(rollResult.getRollResult()).isEqualTo(Condition.SUCCESS);
        assertThat(rollResult.getRollOutcomes()).allMatch(x -> x.getConditions().contains(Condition.SUCCESS));
    }

    @Test
    void service_WithRollGreaterThanTenAndDynamite_ReturnDynamiteSuccessRollResult() {
        characterPowerBeforeRollRequest = CoreMoveServiceTests.buildCharacterBeforeRollDynamite();

        when(diceRollCommand.rollForMove(characterPowerBeforeRollRequest)).thenReturn(DYNAMITE_SUCCESS);
        assertThatCode(() -> when(getCoreMoveQuery.getCoreMove(characterPowerBeforeRollRequest.getMoveId())).thenReturn(CoreMoveServiceTests.buildCoreMove())).doesNotThrowAnyException();
        when(coreMoveService.userCoreMove(characterPowerBeforeRollRequest)).thenReturn(CoreMoveServiceTests.buildMoveResultWithDynamiteSuccess());

        var rollResult = coreMoveService.userCoreMove(characterPowerBeforeRollRequest);

        assertThat(diceRollCommand.rollForMove(characterPowerBeforeRollRequest)).isGreaterThanOrEqualTo(SUCCESS_UPPER_BOUND);
        assertThat(rollResult.getRollResult()).isEqualTo(Condition.DYNAMITE_SUCCESS);
        assertThat(rollResult.getRollOutcomes()).allMatch(x -> x.getConditions().contains(Condition.DYNAMITE_SUCCESS));
    }

    @Test
    void service_WithCompliantRollButTimeoutRequest_ShouldThrowException() {
        characterPowerBeforeRollRequest = CoreMoveServiceTests.buildCharacterBeforeRoll();

        when(diceRollCommand.rollForMove(characterPowerBeforeRollRequest)).thenReturn(SUCCESS);
        try {
            when(getCoreMoveQuery.getCoreMove(characterPowerBeforeRollRequest.getMoveId())).thenThrow(new ApplicationException());
        } catch (final ApplicationException e) {
            assertThatThrownBy(() -> coreMoveService.userCoreMove(characterPowerBeforeRollRequest)).isInstanceOf(RuntimeException.class);
        }
    }

    private static MoveRollResult buildMoveResultWithFailure() {
        return buildMoveResultBasedOnCondition(Condition.FAILURE);
    }

    private static MoveRollResult buildMoveResultWithDynamiteSuccess() {
        return buildMoveResultBasedOnCondition(Condition.DYNAMITE_SUCCESS);
    }

    private static MoveRollResult buildMoveResultWithSuccess() {
        return buildMoveResultBasedOnCondition(Condition.SUCCESS);
    }

    private static MoveRollResult buildMoveResultWithPartialSuccess() {
        return buildMoveResultBasedOnCondition(Condition.PARTIAL_SUCCESS);
    }

    private static MoveRollResult buildMoveResultBasedOnCondition(Condition condition) {
        return MoveRollResult.builder()
                .moveName(TITLE)
                .rollResult(condition)
                .rollOutcomes(CoreMoveServiceTests.buildSuccessOutcomes())
                .moveDescription(DESCRIPTION)
                .characterPowerBeforeRoll(CoreMoveServiceTests.buildCharacterBeforeRoll())
                .build();
    }

    private static Move buildCoreMove() {
        return Move.builder()
                .id(SNOWFLAKE_ID)
                .type(MoveType.CORE)
                .name(TITLE)
                .description(DESCRIPTION)
                .exampleOfApplication(Arrays.asList("Buff a player", "Create statuses"))
                .outcomes(CoreMoveServiceTests.buildOutcomes())
                .build();
    }

    private static List<Outcome> buildSuccessOutcomes() {
        return CoreMoveServiceTests.buildOutcomes().stream().filter(x -> x.getConditions().stream().allMatch(y -> y == Condition.SUCCESS)).toList();
    }

    private static List<Outcome> buildPartialSuccessOutcomes() {
        return CoreMoveServiceTests.buildOutcomes().stream().filter(x -> x.getConditions().stream().allMatch(y -> y == Condition.PARTIAL_SUCCESS)).toList();
    }

    private static List<Outcome> getFailingOutcomes() {
        return CoreMoveServiceTests.buildOutcomes().stream().filter(x -> x.getConditions().stream().allMatch(y -> y == Condition.FAILURE)).toList();
    }

    private static List<Outcome> buildOutcomes() {
        return Arrays.asList(CoreMoveServiceTests.buildFailureOutcome(), CoreMoveServiceTests.buildPartialSuccessOutcome(), CoreMoveServiceTests.buildSuccessOutcome());
    }

    private static Outcome buildFailureOutcome() {
        return Outcome.builder()
                .description("You Failed the test, the MC can now make his own Hard Move against you!")
                .conditions(List.of(Condition.FAILURE)).build();
    }

    private static Outcome buildSuccessOutcome() {
        return Outcome.builder()
                .description("Scale up the effect (greater area or more targets)")
                .conditions(List.of(Condition.PARTIAL_SUCCESS, Condition.SUCCESS)).build();
    }

    private static Outcome buildPartialSuccessOutcome() {
        return Outcome.builder().description("Create a story tag").conditions(List.of(Condition.PARTIAL_SUCCESS)).build();
    }

    private static CharacterPowerBeforeRoll buildCharacterBeforeRollDynamite() {
        return CharacterPowerBeforeRoll.builder()
                .moveId(SNOWFLAKE_ID)
                .burnedTheTag(false)
                .dynamiteUnlocked(true)
                .highestPowerCharacterStatus(CoreMoveServiceTests.buildHighestPowerStatus())
                .highestWeaknessCharacterStatus(CoreMoveServiceTests.buildHighestWeaknessStatus())
                .numberOfMythosThemeBooks(LEGAL_NUMBER_OF_THEME_BOOK)
                .numberOfLogosThemeBooks(LEGAL_NUMBER_OF_THEME_BOOK)
                .powerTags(Arrays.asList(CoreMoveServiceTests.buildGenericPowerTag(), CoreMoveServiceTests.buildGenericPowerTag()))
                .weaknessTags(List.of())
                .build();
    }

    private static CharacterPowerBeforeRoll buildCharacterBeforeRoll() {
        return CharacterPowerBeforeRoll.builder()
                .moveId(SNOWFLAKE_ID)
                .burnedTheTag(false)
                .dynamiteUnlocked(false)
                .highestPowerCharacterStatus(CoreMoveServiceTests.buildHighestPowerStatus())
                .highestWeaknessCharacterStatus(CoreMoveServiceTests.buildHighestWeaknessStatus())
                .numberOfMythosThemeBooks(LEGAL_NUMBER_OF_THEME_BOOK)
                .numberOfLogosThemeBooks(LEGAL_NUMBER_OF_THEME_BOOK)
                .powerTags(Arrays.asList(CoreMoveServiceTests.buildGenericPowerTag(), CoreMoveServiceTests.buildGenericPowerTag()))
                .weaknessTags(List.of())
                .build();
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

    private CharacterPowerBeforeRoll getPowerRollWithIllegalNumberOfThemeBooks() {
        return CharacterPowerBeforeRoll.builder().moveId(SNOWFLAKE_ID).numberOfLogosThemeBooks(ILLEGAL_NUMBER_OF_THEME_BOOK).numberOfMythosThemeBooks(ILLEGAL_NUMBER_OF_THEME_BOOK).build();
    }

    private static CharacterPowerBeforeRoll getRollWithoutMoveId() {
        return CharacterPowerBeforeRoll.builder().moveId(0).build();
    }

}
