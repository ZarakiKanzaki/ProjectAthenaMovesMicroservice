package com.projectathena.movesmicroservice;

import com.projectathena.movesmicroservice.core.entities.Move;
import com.projectathena.movesmicroservice.core.enums.MoveType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MovesMicroserviceApplicationTests {

	private Move moveForTesting;
	private static final String CHANGE_THE_GAME = "CHANGE THE GAME";
	@BeforeEach
	void initializeComponents(){
		moveForTesting = buildCompletedCoreMove();
	}

	@Test
	void contextLoads() {
		assertThat(moveForTesting.getName()).isEqualTo(CHANGE_THE_GAME);
	}

	
	private static Move buildCompletedCoreMove() {
		return Move.builder()
				.type(MoveType.CORE)
				.name(CHANGE_THE_GAME)
				.description("When you use your abilities to give yourself or" +
						"your allies an advantage, roll+Power. On a hit," +
						"you get Juice=Power. Spend your Juice to gain the" +
						"following effects, one-to-one:")
				.exampleOfApplication(Arrays.asList("Buff a player", "Create statuses"))
				.build();
	}

}
