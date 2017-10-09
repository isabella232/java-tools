/*
 * Copyright 2000-2015 namics ag. All rights reserved.
 */

package com.namics.oss.java.tools.utils.csv;

import com.namics.oss.java.tools.utils.reflection.BeanUtils;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.beans.PropertyDescriptor;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * CsvWriter.
 *
 * @author lboesch, Namics AG
 * @since 14.08.2015
 */
public class CsvWriter {

	public void writeWithHeaders(List<?> beans, OutputStream outputStream, Integer delimiterChar, String... headers) throws IOException {
		try (BufferedWriter csvWriter = new BufferedWriter(new OutputStreamWriter(outputStream))) {
			CsvPreference csvPreference = CsvPreference.STANDARD_PREFERENCE;
			if (delimiterChar != null) {
				csvPreference = new CsvPreference.Builder('"', delimiterChar, "\n").build();
			}
			try (CsvBeanWriter csvBeanWriter = new CsvBeanWriter(csvWriter, csvPreference)) {
				csvBeanWriter.writeHeader(headers);

				for (Object bean : beans) {
					csvBeanWriter.write(bean, headers);
				}
			}
		}
	}

	public void write(List<?> beans, OutputStream outputStream, Integer delimiterChar, String... ignores) throws IOException {
		List<String> ignored = ignores != null ? asList(ignores) : Collections.<String>emptyList();
		if (beans != null && beans.size() > 0) {
			String[] headers = getHeaders(beans.get(0).getClass(), ignored);
			writeWithHeaders(beans, outputStream, delimiterChar, headers);
		}
	}

	protected String[] getHeaders(Class<?> _class, List<String> ignored) {
		List<String> headers = new LinkedList<>();
		List<PropertyDescriptor> descriptors = BeanUtils.getPropertyDescriptors(_class);
		for (PropertyDescriptor descriptor : descriptors) {
			Method getter = descriptor.getReadMethod();
			String name = descriptor.getName();
			if (getter != null && !"class".equals(name) && !ignored.contains(name)) {
				headers.add(name);
			}
		}
		String[] headerArr = new String[headers.size()];
		return headers.toArray(headerArr);
	}
}
