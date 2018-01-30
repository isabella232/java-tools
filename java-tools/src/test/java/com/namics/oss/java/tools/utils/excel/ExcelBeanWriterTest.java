/*
 * Copyright 2000-2015 Namics AG. All rights reserved.
 */

package com.namics.oss.java.tools.utils.excel;

import com.namics.commons.random.RandomData;
import com.namics.oss.java.tools.utils.bean.TestBean;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

/**
 * ExcelBeanWriterTest.
 *
 * @author aschaefer, Namics AG
 * @since 21.07.15 09:04
 */
public class ExcelBeanWriterTest {
	private static final Logger LOG = LoggerFactory.getLogger(ExcelBeanWriterTest.class);

	TestBean[] testBeans = new TestBean[] {
			new TestBean().username("hmuster").firstname("Hans").lastname("Muster"),
			new TestBean().username("emuster").firstname("Erika").lastname("Muster"),
	};


	@Test
	public void testWrite() throws Exception {
		String absolute = getClass().getResource("/").getFile() + "excel/writer-test.xlsx";
		LOG.info("{}", absolute);
		try (OutputStream out = new FileOutputStream(absolute)) {
			new ExcelBeanWriter().write(Arrays.asList(testBeans), out);
		}

		checkIfFileContainsBeans(absolute, testBeans);
	}

	@Test
	public void testWriteLargeAmount() throws Exception {
		String absolute = getClass().getResource("/").getFile() + "excel/writer-bulk-test.xlsx";
		LOG.info("{}", absolute);
		TestBean[] bulk = new TestBean[1000];
		for (int i = 0; i < 1000; i++) {
			bulk[i] = RandomData.random(TestBean.class);
		}

		try (OutputStream out = new FileOutputStream(absolute)) {
			new ExcelBeanWriter().write(Arrays.asList(bulk), out);
		}

		checkIfFileContainsBeans(absolute, bulk);
	}

	@Test
	public void testMapping() throws IOException {
		TestBean[] testBeansMapped = new TestBean[] {
				new TestBean().username("Hans").firstname("hmuster").lastname("Muster"),
				new TestBean().username("Erika").firstname("emuster").lastname("Muster"),
		};
		Map<String, String> mapping = new HashMap<>();
		mapping.put("username", "firstname");
		mapping.put("firstname", "username");
		mapping.put("lastname", "lastname");
		String absolute = getClass().getResource("/").getFile() + "excel/writer-test.xlsx";
		LOG.info("{}", absolute);
		try (OutputStream out = new FileOutputStream(absolute)) {
			new ExcelBeanWriter().write(Arrays.asList(testBeans), out, mapping);
		}

		checkIfFileContainsBeans(absolute, testBeansMapped);
	}

	private void checkIfFileContainsBeans(final String absolute, final TestBean[] bulk) throws IOException {
		try (InputStream input = new FileInputStream(absolute)) {
			List<TestBean> verify = new ExcelBeanReader().read(TestBean.class, input);
			for (TestBean testBean : verify) {
				LOG.info("{}", testBean);
			}
			assertThat(verify, containsInAnyOrder(bulk));
		}
	}
}