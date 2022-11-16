package com.projectathena.movesmicroservice.application.port.out;

import com.projectathena.movesmicroservice.core.exceptions.ApplicationException;
import com.projectathena.movesmicroservice.core.entities.Move;

public interface GetCoreMoveQuery {
    Move getCoreMove(String moveName) throws ApplicationException;
}
