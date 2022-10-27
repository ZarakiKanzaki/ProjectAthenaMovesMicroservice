package com.projectathena.movesmicroservice.application.port.out;

import com.projectathena.movesmicroservice.core.entities.CharacterPowerBeforeRoll;

public interface DiceRollCommand {

    short rollForMove(CharacterPowerBeforeRoll characterPowerBeforeRoll);
}
