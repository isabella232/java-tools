/*
 * Copyright 2000-2015 Namics AG. All rights reserved.
 */

package com.namics.oss.java.tools.utils.excel;

import com.namics.oss.java.tools.utils.bean.TestBean;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * ExcelUtilsTest.
 *
 * @author aschaefer, Namics AG
 * @since 21.07.15 10:05
 */
class ExcelUtilsTest {
	private static final Logger LOG = LoggerFactory.getLogger(ExcelUtilsTest.class);

	TestBean[] testBeans = new TestBean[] {
			new TestBean().username("hmuster").firstname("Hans").lastname("Muster").id("1").pleaseIgnore("ignoreProp"),
			new TestBean().username("emuster").firstname("Erika").lastname("Muster").id("2").pleaseIgnore("ignoreProp"),
	};


	@Test
	void testWriteRead() throws Exception {
		String absolute = getClass().getResource("/").getFile() + "excel/util-test.xlsx";
		LOG.info("{}", absolute);
		try (OutputStream out = new FileOutputStream(absolute)) {
			ExcelUtils.write(Arrays.asList(testBeans), out, "pleaseIgnore");
		}

		try (InputStream input = new FileInputStream(absolute)) {
			List<TestBean> verify = ExcelUtils.read(TestBean.class, input);
			for (TestBean testBean : verify) {
				LOG.info("{}", testBean);
			}
			assertThat(verify, contains(sameBeanAs(testBeans[0]).ignoring("pleaseIgnore").with("pleaseIgnore", isEmptyOrNullString()),
			                            sameBeanAs(testBeans[1]).ignoring("pleaseIgnore").with("pleaseIgnore", isEmptyOrNullString())));
		}
	}
}
