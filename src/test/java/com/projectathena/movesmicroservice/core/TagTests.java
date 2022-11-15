package com.projectathena.movesmicroservice.core;

import com.projectathena.movesmicroservice.core.entities.Outcome;
import com.projectathena.movesmicroservice.core.entities.Tag;
import com.projectathena.movesmicroservice.core.enums.Condition;
import com.projectathena.movesmicroservice.core.enums.TagType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TagTests {

	private Tag tagForTesting;

	@BeforeEach
	void initializeComponents(){
		tagForTesting = buildTagForTest();
	}

	@Test
	void builder_completeOutcome_shouldBeComplete() {
		assertThat(tagForTesting.getName()).isNotNull();
		assertThat(tagForTesting.getType()).isNotNull();
		assertThat(tagForTesting.getValue()).isGreaterThanOrEqualTo((short) 0);
	}

	private static Tag buildTagForTest() {
		return Tag.builder().type(TagType.POWER).value((short) 0).name("Tag for Test").build();
	}

}
