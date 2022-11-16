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

class OutcomeTests {

	private Outcome outcomeForTesting;

	@BeforeEach
	void initializeComponents(){
        outcomeForTesting = MoveTestBuilder.getPartialSuccessOutcome();
	}

	@Test
	void builder_completeOutcome_shouldBeComplete() {
		assertThat(outcomeForTesting.getDescription()).isNotEmpty();
		assertThat(outcomeForTesting.getConditions()).isNotEmpty();
	}
}
