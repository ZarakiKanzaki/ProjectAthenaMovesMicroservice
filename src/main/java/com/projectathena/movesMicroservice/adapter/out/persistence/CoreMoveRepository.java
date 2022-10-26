package com.projectathena.movesMicroservice.adapter.out.persistence;

import com.projectathena.movesMicroservice.application.port.in.GetCoreMoveQuery;
import com.projectathena.movesMicroservice.application.port.out.CoreMovePort;
import com.projectathena.movesMicroservice.core.entities.Move;
import com.projectathena.movesMicroservice.core.entities.MoveRollResult;

public class CoreMoveRepository implements CoreMovePort, GetCoreMoveQuery {
    @Override
    public Move getCoreMove(long moveId) {
        return null;
    }

    @Override
    public MoveRollResult getCoreMoveResult(short rollForMove, Move coreMove) {
        return null;
    }
}
