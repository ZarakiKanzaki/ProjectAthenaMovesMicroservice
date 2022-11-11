package com.projectathena.movesmicroservice.application.service;

import com.projectathena.movesmicroservice.application.port.in.GetCoreMoveQuery;
import com.projectathena.movesmicroservice.application.port.out.CoreMovePort;
import com.projectathena.movesmicroservice.application.port.out.DiceRollCommand;
import com.projectathena.movesmicroservice.core.entities.CharacterPowerBeforeRoll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.projectathena.movesmicroservice.core.CharacterPowerBeforeRollConstants.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

@SpringBootTest
public class CoreMoveServiceTests {

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

    private CharacterPowerBeforeRoll getPowerRollWithIllegalNumberOfThemeBooks() {
        return CharacterPowerBeforeRoll.builder().moveId(SNOWFLAKE_ID).numberOfLogosThemeBooks(ILLEGAL_NUMBER_OF_THEME_BOOK).numberOfMythosThemeBooks(ILLEGAL_NUMBER_OF_THEME_BOOK).build();
    }

    private static CharacterPowerBeforeRoll getRollWithoutMoveId() {
        return CharacterPowerBeforeRoll.builder().moveId(0).build();
    }

}
