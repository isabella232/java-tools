/*
 * Copyright 2000-2018 Namics AG. All rights reserved.
 */

package com.namics.oss.java.tools.utils.test;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static com.namics.oss.java.tools.utils.test.TimeoutHelper.sleep;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * TimeoutHelperTest.
 *
 * @author aschaefer, Namics AG
 * @since 30.01.18 08:51
 */
class TimeoutHelperTest {
	@Test
	void timeout() {
		long before = System.currentTimeMillis();
		sleep(10);
		long after = System.currentTimeMillis();
		assertThat(after, Matchers.greaterThanOrEqualTo(before + 10L));
	}
}
