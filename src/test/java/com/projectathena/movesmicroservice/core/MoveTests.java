package com.projectathena.movesmicroservice.core;

import com.projectathena.movesmicroservice.core.entities.Move;
import com.projectathena.movesmicroservice.core.entities.Outcome;
import com.projectathena.movesmicroservice.core.enums.Condition;
import com.projectathena.movesmicroservice.core.enums.MoveType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static com.projectathena.movesmicroservice.core.MoveConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

class MoveTests {

	private Move moveForTesting;
	@BeforeEach
	void initializeComponents(){
		moveForTesting = MoveTestBuilder.buildCompletedCoreMove();
	}

	@Test
	void builder_completeCoreMove_shouldBeComplete() {
		assertThat(moveForTesting.getName()).isNotEmpty();
		assertThat(moveForTesting.getDescription()).isNotEmpty();
		assertThat(moveForTesting.getExampleOfApplication()).isNotEmpty();
		assertThat(moveForTesting.getType()).isNotNull();
		assertThat(moveForTesting.getOutcomes()).isNotEmpty();
		assertThat(moveForTesting.getOutcomes().stream().anyMatch(x -> x.getConditions().stream().anyMatch(y -> Condition.SUCCESS == y))).isTrue();
		assertThat(moveForTesting.getOutcomes().stream().anyMatch(x -> x.getConditions().stream().anyMatch(y -> Condition.PARTIAL_SUCCESS == y))).isTrue();
	}
}
