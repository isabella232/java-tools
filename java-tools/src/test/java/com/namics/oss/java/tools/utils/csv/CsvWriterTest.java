package com.namics.oss.java.tools.utils.csv;

import com.namics.oss.java.tools.utils.bean.TestBean;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToCompressingWhiteSpace;
import static org.hamcrest.text.IsEqualCompressingWhiteSpace.equalToIgnoringWhiteSpace;

/**
 * CsvWriterTest.
 *
 * @author lboesch, Namics AG
 * @since 14.08.2015
 */
class CsvWriterTest {

	TestBean[] testBeans = new TestBean[] {
			new TestBean().username("hmuster").firstname("Hans").lastname("Muster").id("1").pleaseIgnore("ignoreProp"),
			new TestBean().username("emuster").firstname("Erika").lastname("Muster").id("2").pleaseIgnore("ignoreProp"),
	};

	CsvWriter service = new CsvWriter();

	@Test
	void testWrite() throws IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		service.write(Arrays.asList(testBeans), outputStream, null, "pleaseIgnore");
		assertThat(outputStream.toString(), equalToCompressingWhiteSpace("firstname,id,lastname,username "
		                                                                 + "Hans,1,Muster,hmuster "
		                                                                 + "Erika,2,Muster,emuster"));

	}

	@Test
	void testWriteWithHeaders() throws IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		service.writeWithHeaders(Arrays.asList(testBeans), outputStream, null, "username", "lastname");
		assertThat(outputStream.toString(), equalToCompressingWhiteSpace("username,lastname "
		                                                                 + "hmuster,Muster "
		                                                                 + "emuster,Muster"));

	}

	@Test
	void testWriteWithHeadersSeparator() throws IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		int delimiter = ';';
		service.writeWithHeaders(Arrays.asList(testBeans), outputStream, delimiter, "username", "lastname");
		assertThat(outputStream.toString(), equalToIgnoringWhiteSpace("username;lastname "
		                                                              + "hmuster;Muster "
		                                                              + "emuster;Muster"));

	}

}
