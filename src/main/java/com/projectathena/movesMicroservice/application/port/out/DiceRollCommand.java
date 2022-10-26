package com.projectathena.movesMicroservice.application.port.out;

import com.projectathena.movesMicroservice.core.entities.CharacterPowerBeforeRoll;

public interface DiceRollCommand {

    short rollForMove(CharacterPowerBeforeRoll characterPowerBeforeRoll);
}
