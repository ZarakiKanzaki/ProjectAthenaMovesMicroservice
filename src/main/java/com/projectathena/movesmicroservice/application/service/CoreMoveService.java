package com.projectathena.movesmicroservice.application.service;

import com.projectathena.movesmicroservice.application.port.in.GetCoreMoveQuery;
import com.projectathena.movesmicroservice.application.port.out.CoreMovePort;
import com.projectathena.movesmicroservice.application.port.out.DiceRollCommand;
import com.projectathena.movesmicroservice.core.entities.CharacterPowerBeforeRoll;
import com.projectathena.movesmicroservice.core.entities.MoveRollResult;
import com.projectathena.movesmicroservice.core.usecase.UseCoreMoveUseCase;
import common.UseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CoreMoveService implements UseCoreMoveUseCase {
    private final CoreMovePort coreMovePort;
    private final DiceRollCommand diceRollCommand;
    private final GetCoreMoveQuery getCoreMoveQuery;
    @Override
    public MoveRollResult userCoreMove(CharacterPowerBeforeRoll characterPowerBeforeRoll) {
        return coreMovePort.getCoreMoveResult(diceRollCommand.rollForMove(characterPowerBeforeRoll),
                                              getCoreMoveQuery.getCoreMove(characterPowerBeforeRoll.getMoveId()));
    }
}
