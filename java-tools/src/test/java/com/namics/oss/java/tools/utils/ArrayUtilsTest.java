/*
 * Copyright 2000-2016 Namics AG. All rights reserved.
 */

package com.namics.oss.java.tools.utils;

import org.junit.Test;

import static com.namics.oss.java.tools.utils.ArrayUtils.array;
import static com.namics.oss.java.tools.utils.ArrayUtils.join;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;

/**
 * ArrayUtilsTest.
 *
 * @author aschaefer, Namics AG
 * @since 05.02.16 08:39
 */
public class ArrayUtilsTest {

	@Test
	public void testArray() throws Exception {
		assertThat(array("Test"),instanceOf(String[].class));
		assertThat(array("Test","123"),instanceOf(String[].class));
		assertThat(array(),instanceOf(Object[].class));
	}

	@Test
	public void testJoin() throws Exception {
		assertThat(asList(join(new String[]{"1","2","3"},null)), contains("1","2","3"));
		assertThat(asList(join(null,new String[]{"1","2","3"})), contains("1","2","3"));
		assertThat(asList(join(new String[]{"1","2","3"},new String[]{"4","5","6"})), contains("1","2","3","4","5","6"));
	}
}