package com.projectathena.movesmicroservice.application.service;

import com.projectathena.movesmicroservice.application.port.in.GetCoreMoveQuery;
import com.projectathena.movesmicroservice.application.port.out.CoreMovePort;
import com.projectathena.movesmicroservice.core.entities.*;
import com.projectathena.movesmicroservice.core.enums.Condition;
import com.projectathena.movesmicroservice.core.enums.MoveType;
import com.projectathena.movesmicroservice.core.enums.TagType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static com.projectathena.movesmicroservice.core.CharacterPowerBeforeRollConstants.*;
import static com.projectathena.movesmicroservice.core.MoveRollResultConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class CoreMoveServiceTests {



    private final CoreMovePort coreMovePort = mock(CoreMovePort.class);
    private final DiceRollCommand diceRollCommand = mock(DiceRollCommand.class);
    private final GetCoreMoveQuery getCoreMoveQuery = mock(GetCoreMoveQuery.class);
    private CoreMoveService coreMoveService;
    private CharacterPowerBeforeRoll characterPowerBeforeRollRequest;

    @BeforeEach
    void Setup() {
        coreMoveService = new CoreMoveService(coreMovePort, diceRollCommand, getCoreMoveQuery);
    }

    @Test
    void service_withRequestWithoutMoveId_shouldThrowException() {
        characterPowerBeforeRollRequest = getRollWithoutMoveId();
        assertThatThrownBy(() -> coreMoveService.userCoreMove(characterPowerBeforeRollRequest)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void service_withRequestWithMoreThan4CharacterThemeBooks_ShouldThrowException() {
        characterPowerBeforeRollRequest = getPowerRollWithIllegalNumberOfThemeBooks();
        assertThatThrownBy(() -> coreMoveService.userCoreMove(characterPowerBeforeRollRequest)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void service_withRollLowerThanSeven_ReturnFailingRollResult() {
        characterPowerBeforeRollRequest = buildCharacterBeforeRoll();
        when(diceRollCommand.rollForMove(characterPowerBeforeRollRequest)).thenReturn(FAILED_ROLL);
        when(getCoreMoveQuery.getCoreMove(characterPowerBeforeRollRequest.getMoveId())).thenReturn(buildCoreMove());
        when(coreMoveService.userCoreMove(characterPowerBeforeRollRequest)).thenReturn(buildMoveResultWithFailure());

        var rollResult = coreMoveService.userCoreMove(characterPowerBeforeRollRequest);

        assertThat(diceRollCommand.rollForMove(characterPowerBeforeRollRequest)).isLessThan(PARTIAL_SUCCESS_LOWER_BOUND);
        assertThat(rollResult.getRollResult()).isEqualTo(Condition.FAILURE);
        assertThat(rollResult.getRollOutcomes()).allMatch(x -> x.getConditions().contains(Condition.FAILURE));
    }

    @Test
    void service_withRollLowerThanTenButGreaterThanSeven_ReturnPartialSuccessRollResult() {
        characterPowerBeforeRollRequest = buildCharacterBeforeRoll();
        when(diceRollCommand.rollForMove(characterPowerBeforeRollRequest)).thenReturn(PARTIAL_SUCCESS);
        when(getCoreMoveQuery.getCoreMove(characterPowerBeforeRollRequest.getMoveId())).thenReturn(buildCoreMove());
        when(coreMoveService.userCoreMove(characterPowerBeforeRollRequest)).thenReturn(buildMoveResultWithPartialSuccess());

        var rollResult = coreMoveService.userCoreMove(characterPowerBeforeRollRequest);

        assertThat(diceRollCommand.rollForMove(characterPowerBeforeRollRequest)).isLessThan(SUCCESS_LOWER_BOUND);
        assertThat(diceRollCommand.rollForMove(characterPowerBeforeRollRequest)).isGreaterThanOrEqualTo(PARTIAL_SUCCESS_LOWER_BOUND);
        assertThat(rollResult.getRollResult()).isEqualTo(Condition.PARTIAL_SUCCESS);
        assertThat(rollResult.getRollOutcomes()).allMatch(x -> x.getConditions().contains(Condition.PARTIAL_SUCCESS));
    }

    @Test
    void service_withRollGreaterThanTen_ReturnSuccessRollResult() {
        characterPowerBeforeRollRequest = buildCharacterBeforeRoll();
        when(diceRollCommand.rollForMove(characterPowerBeforeRollRequest)).thenReturn(SUCCESS);
        when(getCoreMoveQuery.getCoreMove(characterPowerBeforeRollRequest.getMoveId())).thenReturn(buildCoreMove());
        when(coreMoveService.userCoreMove(characterPowerBeforeRollRequest)).thenReturn(buildMoveResultWithSuccess());

        var rollResult = coreMoveService.userCoreMove(characterPowerBeforeRollRequest);

        assertThat(diceRollCommand.rollForMove(characterPowerBeforeRollRequest)).isGreaterThanOrEqualTo(SUCCESS_LOWER_BOUND);
        assertThat(rollResult.getRollResult()).isEqualTo(Condition.PARTIAL_SUCCESS);
        assertThat(rollResult.getRollOutcomes()).allMatch(x -> x.getConditions().contains(Condition.PARTIAL_SUCCESS));
    }

    private static MoveRollResult buildMoveResultWithFailure() {
        return MoveRollResult.builder()
                .moveName(TITLE)
                .rollResult(Condition.FAILURE)
                .rollOutcomes(getFailingOutcomes())
                .moveDescription(DESCRIPTION)
                .characterPowerBeforeRoll(buildCharacterBeforeRoll())
                .build();
    }

    private static MoveRollResult buildMoveResultWithSuccess() {
        return MoveRollResult.builder()
                .moveName(TITLE)
                .rollResult(Condition.PARTIAL_SUCCESS)
                .rollOutcomes(buildSuccessOutcomes())
                .moveDescription(DESCRIPTION)
                .characterPowerBeforeRoll(buildCharacterBeforeRoll())
                .build();
    }

    private static MoveRollResult buildMoveResultWithPartialSuccess() {
        return MoveRollResult.builder()
                .moveName(TITLE)
                .rollResult(Condition.PARTIAL_SUCCESS)
                .rollOutcomes(buildPartialSuccessOutcomes())
                .moveDescription(DESCRIPTION)
                .characterPowerBeforeRoll(buildCharacterBeforeRoll())
                .build();
    }

    private static Move buildCoreMove() {
        return Move.builder()
                .id(SNOWFLAKE_ID)
                .type(MoveType.CORE)
                .name(TITLE)
                .description(DESCRIPTION)
                .exampleOfApplication(Arrays.asList("Buff a player", "Create statuses"))
                .outcomes(buildOutcomes())
                .build();
    }

    private static List<Outcome> buildSuccessOutcomes() {
        return buildOutcomes().stream().filter(x -> x.getConditions().stream().allMatch(y -> y.equals(Condition.SUCCESS))).toList();
    }

    private static List<Outcome> buildPartialSuccessOutcomes() {
        return buildOutcomes().stream().filter(x -> x.getConditions().stream().allMatch(y -> y.equals(Condition.PARTIAL_SUCCESS))).toList();
    }

    private static List<Outcome> getFailingOutcomes() {
        return buildOutcomes().stream().filter(x -> x.getConditions().stream().allMatch(y -> y.equals(Condition.FAILURE))).toList();
    }

    private static List<Outcome> buildOutcomes() {
        return Arrays.asList(buildFailureOutcome(),buildPartialSuccessOutcome(), buildSuccessOutcome());
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


    private static CharacterPowerBeforeRoll buildCharacterBeforeRoll() {
        return CharacterPowerBeforeRoll.builder()
                .moveId(SNOWFLAKE_ID)
                .burnedTheTag(false)
                .dynamiteUnlocked(false)
                .highestPowerCharacterStatus(buildHighestPowerStatus())
                .highestWeaknessCharacterStatus(buildHighestWeaknessStatus())
                .numberOfMythosThemeBooks(LEGAL_NUMBER_OF_THEME_BOOK)
                .numberOfLogosThemeBooks(LEGAL_NUMBER_OF_THEME_BOOK)
                .powerTags(Arrays.asList(buildGenericPowerTag(), buildGenericPowerTag()))
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
