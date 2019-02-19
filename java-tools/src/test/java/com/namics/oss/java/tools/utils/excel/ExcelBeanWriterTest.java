/*
 * Copyright 2000-2015 Namics AG. All rights reserved.
 */

package com.namics.oss.java.tools.utils.excel;

import com.namics.commons.random.RandomData;
import com.namics.oss.java.tools.utils.bean.TestBean;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.collection.IsMapContaining.hasEntry;

/**
 * ExcelBeanWriterTest.
 *
 * @author aschaefer, Namics AG
 * @since 21.07.15 09:04
 */
class ExcelBeanWriterTest {
	private static final Logger LOG = LoggerFactory.getLogger(ExcelBeanWriterTest.class);

	TestBean[] testBeans = new TestBean[] {
			new TestBean().username("hmuster").firstname("Hans").lastname("Muster"),
			new TestBean().username("emuster").firstname("Erika").lastname("Muster"),
	};


	@Test
	void testWrite() throws Exception {
		String absolute = getClass().getResource("/").getFile() + "excel/writer-test.xlsx";
		LOG.info("{}", absolute);
		try (OutputStream out = new FileOutputStream(absolute)) {
			new ExcelBeanWriter().write(Arrays.asList(testBeans), out);
		}

		checkIfFileContainsBeans(absolute, testBeans);
	}

	@Test
	void testWriteLargeAmount() throws Exception {
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
	void testMapping() throws IOException {
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

	@Test
	void testWriteHeaderRow() throws NoSuchMethodException {
		// Given
		Class<TestBean> clazz = TestBean.class;

		SXSSFWorkbook workbook = new SXSSFWorkbook();
		SXSSFSheet sheet = workbook.createSheet(clazz.getSimpleName());

		Map<String, String> mapping = new LinkedHashMap<>();
		mapping.put("username", "User Name");
		mapping.put("firstname", "First Name");
		mapping.put("lastname", "Last Name");
		mapping.put("id", "ID");

		// When
		Map<Integer, Method> getters = new ExcelBeanWriter().writeHeaderRow(sheet, clazz, mapping);

		// Then
		assertThat(getters, hasEntry(equalTo(0), equalTo(clazz.getMethod("getUsername"))));
		assertThat(getters, hasEntry(equalTo(1), equalTo(clazz.getMethod("getFirstname"))));
		assertThat(getters, hasEntry(equalTo(2), equalTo(clazz.getMethod("getLastname"))));
		assertThat(getters, hasEntry(equalTo(3), equalTo(clazz.getMethod("getId"))));
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
