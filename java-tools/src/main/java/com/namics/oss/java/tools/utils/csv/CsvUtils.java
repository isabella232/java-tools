/*
 * Copyright 2000-2015 Namics AG. All rights reserved.
 */

package com.namics.oss.java.tools.utils.csv;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Utils to support writing to csv files.
 *
 * @author lboesch, Namics AG
 * @since 14.08.15 09:28
 */
public abstract class CsvUtils {
	protected static final CsvWriter writer = new CsvWriter();
	private static final String SUPER_CSV = "<dependency>\n"
	                                        + "    <groupId>net.sf.supercsv</groupId>\n"
	                                        + "    <artifactId>super-csv</artifactId>\n"
	                                        + "    <version>2.3.1</version>\n"
	                                        + "</dependency>";


	private CsvUtils() {
	}

	/**
	 * Write a list of bean to csv file, support for csv only.
	 *
	 * @param beans        beans to write to an excel sheet
	 * @param outputStream output stream to write the excel sheet
	 * @param ignores      list of properties to be ignored on writing
	 */
	public static void write(List<?> beans, OutputStream outputStream, String... ignores) throws IOException {
		checkAvailability();
		writer.write(beans, outputStream, null, ignores);
	}

	/**
	 * Write a list of bean to csv file, support for csv only.
	 *
	 * @param beans        beans to write to an excel sheet
	 * @param outputStream output stream to write the excel sheet
	 * @param ignores      list of properties to be ignored on writing
	 */
	public static void write(List<?> beans, OutputStream outputStream, int delimiterChar, String... ignores) throws IOException {
		checkAvailability();
		writer.write(beans, outputStream, delimiterChar, ignores);
	}

	/**
	 * Write a list of bean to csv file, support for csv only.
	 * only write bean properties, which are in <code>headers</code> array.
	 *
	 * @param beans        beans to write to an excel sheet
	 * @param outputStream output stream to write the excel sheet
	 * @param headers      list of properties as header, also useful for ordered list
	 */
	public static void writeWithHeaders(List<?> beans, OutputStream outputStream, String... headers) throws IOException {
		checkAvailability();
		writer.writeWithHeaders(beans, outputStream, null, headers);
	}

	/**
	 * Write a list of bean to csv file, support for csv only.
	 * only write bean properties, which are in <code>headers</code> array.
	 *
	 * @param beans        beans to write to an excel sheet
	 * @param outputStream output stream to write the excel sheet
	 * @param headers      list of properties as header, also useful for ordered list
	 */
	public static void writeWithHeaders(List<?> beans, OutputStream outputStream, int delimiterChar, String... headers) throws IOException {
		checkAvailability();
		writer.writeWithHeaders(beans, outputStream, delimiterChar, headers);
	}

	public static void checkAvailability() {
		try {
			Class.forName("org.supercsv.util.CsvContext", false, CsvUtils.class.getClassLoader());
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException("To use CSV functions you must add super csv to your classpath: \n" + SUPER_CSV, e);
		}

	}

}
