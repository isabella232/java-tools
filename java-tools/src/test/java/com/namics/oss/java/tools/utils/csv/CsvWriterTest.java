package com.namics.oss.java.tools.utils.csv;

import com.namics.oss.java.tools.utils.bean.TestBean;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

import static org.hamcrest.text.IsEqualIgnoringWhiteSpace.equalToIgnoringWhiteSpace;
import static org.junit.Assert.assertThat;

/**
 * CsvWriterTest.
 *
 * @author lboesch, Namics AG
 * @since 14.08.2015
 */
public class CsvWriterTest {

	TestBean[] testBeans = new TestBean[] {
			new TestBean().username("hmuster").firstname("Hans").lastname("Muster").id("1").pleaseIgnore("ignoreProp"),
			new TestBean().username("emuster").firstname("Erika").lastname("Muster").id("2").pleaseIgnore("ignoreProp"),
	};

	CsvWriter service = new CsvWriter();

	@Test
	public void testWrite() throws IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		service.write(Arrays.asList(testBeans), outputStream, null, "pleaseIgnore");
		assertThat(outputStream.toString(), equalToIgnoringWhiteSpace("firstname,id,lastname,username "
		                                                              + "Hans,1,Muster,hmuster "
		                                                              + "Erika,2,Muster,emuster"));

	}

	@Test
	public void testWriteWithHeaders() throws IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		service.writeWithHeaders(Arrays.asList(testBeans), outputStream, null, "username", "lastname");
		assertThat(outputStream.toString(), equalToIgnoringWhiteSpace("username,lastname "
		                                                              + "hmuster,Muster "
		                                                              + "emuster,Muster"));

	}

	@Test
	public void testWriteWithHeadersSeparator() throws IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		int delimiter = ';';
		service.writeWithHeaders(Arrays.asList(testBeans), outputStream, delimiter, "username", "lastname");
		assertThat(outputStream.toString(), equalToIgnoringWhiteSpace("username;lastname "
		                                                              + "hmuster;Muster "
		                                                              + "emuster;Muster"));

	}

}