package com.projectathena.movesmicroservice.application.port.out;

import com.projectathena.movesmicroservice.core.entities.Move;
import com.projectathena.movesmicroservice.core.entities.MoveRollResult;

public interface CoreMovePort {
    MoveRollResult getCoreMoveResult(short rollForMove, Move coreMove);
}
