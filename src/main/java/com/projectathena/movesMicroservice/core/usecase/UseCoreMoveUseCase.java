package com.projectathena.movesMicroservice.core.usecase;

import com.projectathena.movesMicroservice.core.entities.CharacterPowerBeforeRoll;
import com.projectathena.movesMicroservice.core.entities.MoveRollResult;

public interface UseCoreMoveUseCase {
    MoveRollResult userCoreMove(CharacterPowerBeforeRoll characterPowerBeforeRoll);
}
