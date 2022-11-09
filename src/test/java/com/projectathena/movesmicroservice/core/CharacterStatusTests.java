package com.projectathena.movesmicroservice.core;

import com.projectathena.movesmicroservice.core.entities.CharacterStatus;
import com.projectathena.movesmicroservice.core.entities.Outcome;
import com.projectathena.movesmicroservice.core.enums.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CharacterStatusTests {

	private CharacterStatus characterStatusForTesting;

	@BeforeEach
	void initializeComponents(){
		characterStatusForTesting = buildStatusForTest();
	}

	@Test
	void builder_completeCharacterStatus_shouldBeComplete() {
		assertThat(characterStatusForTesting.getName()).isNotNull();
		assertThat(characterStatusForTesting.getValue()).isPositive();
	}

	private static CharacterStatus buildStatusForTest() {
		return CharacterStatus.builder().name("Status For Test").value((short) 2).build();
	}

}
