/*
 * Copyright 2000-2016 Namics AG. All rights reserved.
 */

package com.namics.oss.java.tools.utils.test;

import org.junit.Assert;
import org.junit.Test;

import static com.namics.oss.java.tools.utils.test.ThrownMatcher.thrown;
import static org.junit.Assert.assertThat;

/**
 * ThrownMatcherTest.
 *
 * @author aschaefer, Namics AG
 * @since 10.05.16 14:48
 */
public class ThrownMatcherTest {
	@Test
	public void thrownExceptionShouldBeOk() throws Exception {
		assertThat(() -> {
			throw new IllegalArgumentException();
		}, thrown(IllegalArgumentException.class));
	}

	@Test
	public void missingExceptionShouldFail() throws Exception {
		try {

			assertThat(() -> {
			}, thrown(IllegalArgumentException.class));
			Assert.fail("Exception not detected");
		} catch (AssertionError e) {
			// ok
		}
	}

}