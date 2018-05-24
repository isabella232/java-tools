/*
 * Copyright 2000-2015 Namics AG. All rights reserved.
 */

package com.namics.oss.java.tools.utils.excel;

import com.namics.oss.java.tools.utils.reflection.BeanUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

/**
 * ExcelBeanWriter.
 *
 * @author aschaefer, Namics AG
 * @since 20.07.15 16:00
 */
public class ExcelBeanWriter {
	private static final Logger LOG = LoggerFactory.getLogger(ExcelBeanWriter.class);

	public static final int DEFAULT_HEADER_ROW = 0;
	protected int headerRow = DEFAULT_HEADER_ROW;

	/**
	 * Write a list of bean to excel file, support for xlsx only.
	 *
	 * @param beans        beans to write to an excel sheet
	 * @param outputStream output stream to write the excel sheet
	 * @param ignores      list of properties to be ignored on writing
	 * @throws ExcelException runtime exception when excel processiing failed
	 */
	public void write(List<?> beans, OutputStream outputStream, String... ignores) throws ExcelException {
		writeInternal(beans, outputStream, null, ignores);
	}

	protected <T> Map<Integer, Method> writeHeaderRow(Sheet sheet, Class<T> clazz, String... ignores) throws Exception {
		LOG.debug("Create header for {}", clazz);

		List<String> ignored = ignores != null ? asList(ignores) : Collections.emptyList();

		Map<Integer, Method> result = new HashMap<>();
		Row row = sheet.createRow(this.headerRow);

		List<PropertyDescriptor> descriptors = BeanUtils.getPropertyDescriptors(clazz);

		int index = 0;
		for (PropertyDescriptor descriptor : descriptors) {
			Method getter = descriptor.getReadMethod();
			if (getter != null && !"class".equals(descriptor.getName()) && !ignored.contains(descriptor.getName())) {
				result.put(index, getter);
				Cell cell = row.createCell(index);
				cell.setCellValue(new XSSFRichTextString(descriptor.getName()));
				CellStyle keyStyle = sheet.getWorkbook().createCellStyle();
				Font f = sheet.getWorkbook().createFont();
				f.setBold(false);
				keyStyle.setFont(f);
				cell.setCellStyle(keyStyle);
				index++;
			}
		}

		return result;
	}

	/**
	 * Write a list of bean to excel file, support for xlsx only.
	 *
	 * In case the {@code mapping} parameter has a predictable iteration order (e.g. {@link java.util.LinkedHashSet}) the columns are written in the
	 * same order.
	 *
	 * @param beans        beans to write to an excel sheet
	 * @param outputStream output stream to write the excel sheet
	 * @param mapping      map for mapping. key=name of property descriptor, value=header value
	 * @throws ExcelException runtime exception when excel processiing failed
	 */
	public void write(List<?> beans, OutputStream outputStream, Map<String, String> mapping) throws ExcelException {
		writeInternal(beans, outputStream, mapping);
	}

	private void writeInternal(List<?> beans, OutputStream outputStream, Map<String, String> mapping, String... ignore) throws ExcelException{
		try {
			if (beans != null && beans.size() > 0) {
				Class<?> clazz = beans.iterator().next().getClass();
				SXSSFWorkbook workbook = new SXSSFWorkbook();
				SXSSFSheet sheet = workbook.createSheet(clazz.getSimpleName());
				sheet.trackAllColumnsForAutoSizing();
				final Map<Integer, Method> getters;
				if(mapping == null || mapping.isEmpty()){
					getters = writeHeaderRow(sheet, clazz, ignore);
				}else {
					getters = writeHeaderRow(sheet, clazz, mapping);
				}
				writeContent(getters, beans, sheet);

				for (Integer index : getters.keySet()) {
					sheet.autoSizeColumn(index);
				}
				workbook.write(outputStream);
			}
		} catch (Exception e) {
			throw new ExcelException("Could not write data to stream.", e);
		}
	}

	protected <T> Map<Integer, Method> writeHeaderRow(Sheet sheet, Class<T> clazz, Map<String, String> mapping) {
		LOG.debug("Create header for {}", clazz);

		Map<Integer, Method> result = new HashMap<>();
		Row row = sheet.createRow(this.headerRow);

		List<PropertyDescriptor> descriptors = BeanUtils.getPropertyDescriptors(clazz);

		int index = 0;
		for (Map.Entry<String, String> entry : mapping.entrySet()) {
			PropertyDescriptor descriptor = descriptors.stream().filter(d -> d.getName().equals(entry.getKey())).findFirst().orElse(null);
			if (descriptor != null) {
				Method getter = descriptor.getReadMethod();
				if (getter != null) {
					result.put(index, getter);
					Cell cell = row.createCell(index);
					cell.setCellValue(new XSSFRichTextString(mapping.get(descriptor.getName())));
					CellStyle keyStyle = sheet.getWorkbook().createCellStyle();
					Font f = sheet.getWorkbook().createFont();
					f.setBold(false);
					keyStyle.setFont(f);
					cell.setCellStyle(keyStyle);
					index++;
				}
			}
		}

		return result;
	}

	protected int writeContent(Map<Integer, Method> getters, List<?> beans, Sheet sheet) throws Exception {
		int rowNum = 0;

		for (Object bean : beans) {
			rowNum++;
			Row row = sheet.createRow(rowNum);

			for (Map.Entry<Integer, Method> entry : getters.entrySet()) {
				Cell cell = row.createCell(entry.getKey());
				Object value = entry.getValue().invoke(bean);
				if (value instanceof String) {
					cell.setCellValue((String) value);
				} else if (value != null) {
					cell.setCellValue(value.toString());
				}
			}
		}
		return rowNum;
	}


	public void setHeaderRow(int headerRow) {
		this.headerRow = headerRow;
	}

	public ExcelBeanWriter headerRow(int headerRow) {
		setHeaderRow(headerRow);
		return this;
	}
}
