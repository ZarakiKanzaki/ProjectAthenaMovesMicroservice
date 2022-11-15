package com.projectathena.movesmicroservice.application.service;

import com.projectathena.movesmicroservice.core.exceptions.ApplicationException;
import com.projectathena.movesmicroservice.application.port.in.GetCoreMoveQuery;
import com.projectathena.movesmicroservice.application.port.out.CoreMovePort;
import com.projectathena.movesmicroservice.core.entities.CharacterPowerBeforeRoll;
import com.projectathena.movesmicroservice.core.entities.MoveRollResult;
import com.projectathena.movesmicroservice.core.usecase.UseCoreMoveUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CoreMoveService implements UseCoreMoveUseCase {
    private static final int MAXIMUM_ALLOWED_THEME_BOOKS = 4;
    private final CoreMovePort coreMovePort;
    private final DiceRollCommand diceRollCommand;
    private final GetCoreMoveQuery getCoreMoveQuery;

    @Override
    public MoveRollResult userCoreMove(CharacterPowerBeforeRoll characterPowerBeforeRoll){
        validateRoll(characterPowerBeforeRoll);
        try {
            return coreMovePort.getCoreMoveResult(diceRollCommand.rollForMove(characterPowerBeforeRoll),
                    getCoreMoveQuery.getCoreMove(characterPowerBeforeRoll.getMoveId()),
                    characterPowerBeforeRoll);
        } catch (ApplicationException e) {
            throw new RuntimeException(e);
        }
    }

    private void validateRoll(final CharacterPowerBeforeRoll characterPowerBeforeRoll) {
        if (characterPowerBeforeRoll.getMoveId() <= 0) {
            throw new IllegalArgumentException(String.format("Core move Id should contain a valid Id, current Id: %d", characterPowerBeforeRoll.getMoveId()));
        }

        if (characterPowerBeforeRoll.getSumOfAllThemeBooks() > 4) {
            throw new IllegalArgumentException(String.format("Illegal number of theme books, maximum allowed is %d", CoreMoveService.MAXIMUM_ALLOWED_THEME_BOOKS));
        }
    }
}
