package com.projectathena.movesmicroservice.application.port.in;

import com.projectathena.movesmicroservice.core.entities.Move;

public interface GetCoreMoveQuery {
    Move getCoreMove(long moveId);
}
