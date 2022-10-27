package com.projectathena.movesmicroservice.adapter.out.persistence;

import com.projectathena.movesmicroservice.application.port.in.GetCoreMoveQuery;
import com.projectathena.movesmicroservice.application.port.out.CoreMovePort;
import com.projectathena.movesmicroservice.core.entities.Move;
import com.projectathena.movesmicroservice.core.entities.MoveRollResult;

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
