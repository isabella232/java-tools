/*
 * Copyright 2000-2016 Namics AG. All rights reserved.
 */

package com.namics.oss.java.tools.utils;

import org.junit.jupiter.api.Test;

import java.util.function.Predicate;

import static com.namics.oss.java.tools.utils.ArrayUtils.stream;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

/**
 * PredicateUtilsTest.
 *
 * @author aschaefer, Namics AG
 * @since 05.02.16 08:23
 */
class PredicateUtilsTest {

	@Test
	void testNot() throws Exception {
		assertThat(stream("test", null, "test2")
				           .filter(PredicateUtils.not(item -> item == null))
				           .collect(toList()),
		           contains("test", "test2"));
	}

	@Test
	void testNotNull() throws Exception {
		assertThat(stream("test", null, "test2")
				           .filter(PredicateUtils.notNull())
				           .collect(toList()),
		           contains("test", "test2"));
	}

	@Test
	void testAsPredicate() throws Exception {
		Predicate<?> predicate = PredicateUtils.as(item -> item == null).negate();
	}


}
