package com.projectathena.movesMicroservice.application.service;

import com.projectathena.movesMicroservice.application.port.in.GetCoreMoveQuery;
import com.projectathena.movesMicroservice.application.port.out.CoreMovePort;
import com.projectathena.movesMicroservice.application.port.out.DiceRollCommand;
import com.projectathena.movesMicroservice.core.entities.CharacterPowerBeforeRoll;
import com.projectathena.movesMicroservice.core.entities.MoveRollResult;
import com.projectathena.movesMicroservice.core.usecase.UseCoreMoveUseCase;
import common.UseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@UseCase
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
