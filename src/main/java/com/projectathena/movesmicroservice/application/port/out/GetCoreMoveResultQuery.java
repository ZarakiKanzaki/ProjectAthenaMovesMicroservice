package com.projectathena.movesmicroservice.application.port.out;

import com.projectathena.movesmicroservice.core.entities.CharacterPowerBeforeRoll;
import com.projectathena.movesmicroservice.core.entities.Move;
import com.projectathena.movesmicroservice.core.entities.MoveRollResult;
import com.projectathena.movesmicroservice.core.exceptions.ApplicationException;

public interface GetCoreMoveResultQuery {
    MoveRollResult getCoreMoveResult(short rollForMove, Move coreMove, CharacterPowerBeforeRoll rollPower) throws ApplicationException;
}
