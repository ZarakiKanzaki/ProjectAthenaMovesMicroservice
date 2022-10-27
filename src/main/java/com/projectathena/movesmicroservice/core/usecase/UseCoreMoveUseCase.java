package com.projectathena.movesmicroservice.core.usecase;

import com.projectathena.movesmicroservice.core.entities.CharacterPowerBeforeRoll;
import com.projectathena.movesmicroservice.core.entities.MoveRollResult;

public interface UseCoreMoveUseCase {
    MoveRollResult userCoreMove(CharacterPowerBeforeRoll characterPowerBeforeRoll);
}
