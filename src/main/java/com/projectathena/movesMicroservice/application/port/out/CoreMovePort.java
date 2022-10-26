package com.projectathena.movesMicroservice.application.port.out;

import com.projectathena.movesMicroservice.core.entities.Move;
import com.projectathena.movesMicroservice.core.entities.MoveRollResult;

public interface CoreMovePort {
    MoveRollResult getCoreMoveResult(short rollForMove, Move coreMove);
}
