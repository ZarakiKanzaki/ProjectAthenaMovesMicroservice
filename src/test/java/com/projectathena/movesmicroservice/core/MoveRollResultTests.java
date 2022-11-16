package com.projectathena.movesmicroservice.core;

import com.projectathena.movesmicroservice.core.entities.MoveRollResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.projectathena.movesmicroservice.core.MoveRollResultTestBuilder.buildMoveResult;
import static org.assertj.core.api.Assertions.assertThat;

class MoveRollResultTests {

    private MoveRollResult rollResultForTest;

    @BeforeEach
    void initializeComponents() {
        rollResultForTest = buildMoveResult();
    }

    @Test
    void builder_completeMoveRollResult_shouldBeCompleted() {
        assertThat(rollResultForTest.getMoveName()).isNotNull();
        assertThat(rollResultForTest.getMoveName()).isNotEmpty();
        assertThat(rollResultForTest.getMoveDescription()).isNotNull();
        assertThat(rollResultForTest.getMoveDescription()).isNotEmpty();
        assertThat(rollResultForTest.getRollResult()).isNotNull();
        assertThat(rollResultForTest.getRollOutcomes()).isNotEmpty();
        assertThat(rollResultForTest.getCharacterPowerBeforeRoll()).isNotNull();
    }

}
