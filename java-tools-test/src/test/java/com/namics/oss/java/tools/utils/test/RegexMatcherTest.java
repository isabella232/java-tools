/*
* Copyright 2000-2015 Namics AG. All rights reserved.
*/

package com.namics.oss.java.tools.utils.test;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;

import static com.namics.oss.java.tools.utils.test.RegexMatcher.matchesRegex;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.fail;

class RegexMatcherTest {
	private static final Logger LOG = LoggerFactory.getLogger(RegexMatcherTest.class);
	@Test
	void testMatchesRegexString() throws Exception {
		assertThat(matchesRegex("test").matchesSafely("test"), is(true));
	}

	@Test
	void testMatchesRegexPattern() throws Exception {
		assertThat(matchesRegex(Pattern.compile("test")).matchesSafely("test"), is(true));
	}

	@Test
	void testMatchesRegexFalse() throws Exception {
		assertThat(matchesRegex(Pattern.compile("test1")).matchesSafely("test"), is(false));
	}

	@Test
	void testUsageOk() throws Exception {
		assertThat("expression", matchesRegex("expression"));
		assertThat("expression", matchesRegex(Pattern.compile("expression")));
	}

	@Test
	void testUsageStringNok() throws Exception {
		try {
			assertThat("different", matchesRegex("expression"));
			fail();
		} catch (AssertionError e) {
			LOG.info("{}",e,e);
		}
	}
	@Test
	void testUsagePatternNok() throws Exception {
		try {
			assertThat("different", matchesRegex(Pattern.compile("expression",Pattern.CASE_INSENSITIVE)));
			fail();
		} catch (AssertionError e) {
			LOG.info("{}",e,e);
		}
	}


}
