package com.projectathena.movesmicroservice.application.service;

import com.projectathena.movesmicroservice.core.entities.CharacterPowerBeforeRoll;

public interface DiceRollCommand {

    short rollForMove(CharacterPowerBeforeRoll characterPowerBeforeRoll);
}
