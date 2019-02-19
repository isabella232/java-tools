/*
 * Copyright 2000-2016 Namics AG. All rights reserved.
 */

package com.namics.oss.java.tools.utils.test;

import org.junit.jupiter.api.Test;

import static com.namics.oss.java.tools.utils.test.ThrownMatcher.thrown;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * ThrownMatcherTest.
 *
 * @author aschaefer, Namics AG
 * @since 10.05.16 14:48
 */
class ThrownMatcherTest {
	@Test
	void thrownExceptionShouldBeOk() throws Exception {
		assertThat(() -> {
			throw new IllegalArgumentException();
		}, thrown(IllegalArgumentException.class));
	}

	@Test
	void missingExceptionShouldFail() throws Exception {
		try {

			assertThat(() -> {
			}, thrown(IllegalArgumentException.class));
			fail("Exception not detected");
		} catch (AssertionError e) {
			// ok
		}
	}

}
