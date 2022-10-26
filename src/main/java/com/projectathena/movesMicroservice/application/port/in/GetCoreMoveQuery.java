package com.projectathena.movesMicroservice.application.port.in;

import com.projectathena.movesMicroservice.core.entities.Move;

public interface GetCoreMoveQuery {
    Move getCoreMove(long moveId);
}
