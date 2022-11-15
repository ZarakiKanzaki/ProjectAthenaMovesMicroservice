package com.projectathena.movesmicroservice.core;

import com.projectathena.movesmicroservice.core.entities.Move;
import com.projectathena.movesmicroservice.core.entities.Outcome;
import com.projectathena.movesmicroservice.core.enums.Condition;
import com.projectathena.movesmicroservice.core.enums.MoveType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MoveTests {

	private Move moveForTesting;
	private static final String CHANGE_THE_GAME = "CHANGE THE GAME";
	private static final long SNOWFLAKE_ID = 1427933380;

	@BeforeEach
	void initializeComponents(){
		moveForTesting = buildCompletedCoreMove();
	}

	@Test
	void builder_completeCoreMove_shouldBeComplete() {
		assertThat(moveForTesting.getId()).isPositive();
		assertThat(moveForTesting.getName()).isNotEmpty();
		assertThat(moveForTesting.getDescription()).isNotEmpty();
		assertThat(moveForTesting.getExampleOfApplication()).isNotEmpty();
		assertThat(moveForTesting.getType()).isNotNull();
		assertThat(moveForTesting.getOutcomes()).isNotEmpty();
		assertThat(moveForTesting.getOutcomes().stream().anyMatch(x -> x.getConditions().stream().anyMatch(y -> y == Condition.SUCCESS))).isTrue();
		assertThat(moveForTesting.getOutcomes().stream().anyMatch(x -> x.getConditions().stream().anyMatch(y -> y == Condition.PARTIAL_SUCCESS))).isTrue();
	}

	private static Move buildCompletedCoreMove() {
		return Move.builder()
				.id(SNOWFLAKE_ID)
				.type(MoveType.CORE)
				.name(CHANGE_THE_GAME)
				.description("When you use your abilities to give yourself or" +
						"your allies an advantage, roll+Power. On a hit," +
						"you get Juice=Power. Spend your Juice to gain the" +
						"following effects, one-to-one:")
				.exampleOfApplication(Arrays.asList("Buff a player", "Create statuses"))
				.outcomes(buildOutcomes())
				.build();
	}

	private static List<Outcome> buildOutcomes() {
		return Arrays.asList(getPartialSuccessOutcome(), getSuccessOutcome());
	}

	private static Outcome getSuccessOutcome() {
		return Outcome.builder()
				.description("Scale up the effect (greater area or more targets)")
				.conditions(List.of(Condition.PARTIAL_SUCCESS, Condition.SUCCESS)).build();
	}

	private static Outcome getPartialSuccessOutcome() {
		return Outcome.builder().description("Create a story tag").conditions(List.of(Condition.PARTIAL_SUCCESS)).build();
	}

}
