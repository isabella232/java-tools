/*
 * Copyright 2000-2018 Namics AG. All rights reserved.
 */

package com.namics.oss.java.tools.utils.test;

import org.junit.jupiter.api.Test;

import static com.namics.oss.java.tools.utils.test.StringLengthMatcher.hasLength;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * StringLengthMatcherTest.
 *
 * @author aschaefer, Namics AG
 * @since 30.01.18 08:49
 */
class StringLengthMatcherTest {

	@Test
	void hasLengthOk() {
		assertThat("1234", hasLength(4));
	}

	@Test
	void hasLengthNotOk() {
		try {
			assertThat("12345", hasLength(4));
			fail();
		} catch (AssertionError e) {
			//ok
		}
	}


}
