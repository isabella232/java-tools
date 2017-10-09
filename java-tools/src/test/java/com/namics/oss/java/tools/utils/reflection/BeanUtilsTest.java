/*
 * Copyright 2000-2015 Namics AG. All rights reserved.
 */

package com.namics.oss.java.tools.utils.reflection;

import com.namics.oss.java.tools.utils.bean.TestBean;
import org.junit.Test;

import java.util.Map;

import static com.namics.commons.random.RandomData.random;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * BeanUtilsTest.
 *
 * @author aschaefer, Namics AG
 * @since 28.08.15 14:24
 */
public class BeanUtilsTest {

	@Test
	public void testGetPropertiesMap() throws Exception {
		TestBean source = random(TestBean.class);
		Map<String, Object> target = BeanUtils.getPropertiesMap(source);

		assertNotNull(target);
		assertThat(target.keySet(), containsInAnyOrder("username",
		                                               "firstname",
		                                               "lastname",
				                                       "id",
		                                               "pleaseIgnore"
		                                              ));
	}

	@Test
	public void testGetPropertiesMapIgnore() throws Exception {
		TestBean source = random(TestBean.class);
		Map<String, Object> target = BeanUtils.getPropertiesMap(source,"pleaseIgnore");

		assertNotNull(target);
		assertThat(target.keySet(), containsInAnyOrder("username",
		                                               "firstname",
		                                               "lastname",
														"id"
		                                              ));
	}

	@Test
	public void testGetPropertiesMapNull() throws Exception {
		Map<String, Object> target = BeanUtils.getPropertiesMap(null);
		assertNotNull(target);
		assertThat(target.keySet(), hasSize(0));
	}


}