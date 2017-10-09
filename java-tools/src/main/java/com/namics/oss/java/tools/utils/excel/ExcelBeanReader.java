/*
 * Copyright 2000-2014 Namics AG. All rights reserved.
 */

package com.namics.oss.java.tools.utils.excel;


import com.namics.oss.java.tools.utils.reflection.BeanUtils;
import com.namics.oss.java.tools.utils.reflection.ReflectionUtils;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Reader to read Beans form excel.
 * Therefor the header row is parsed for property names and matched to fields of bean class.
 * Each row is mapped to a specific bean by setting properties.
 * For now this works for String values only.
 *
 * @author aschaefer, Namics AG
 * @since 08.09.14 16:17
 */
public class ExcelBeanReader {
	private static final Logger LOG = LoggerFactory.getLogger(ExcelBeanReader.class);
	public static final int DEFAULT_HEADER_ROW = 0;

	protected int headerRow = DEFAULT_HEADER_ROW;

	/**
	 * Read the excel file and map rows to beans of type T
	 *
	 * @param <T>   Type od beans returned (determined by clazz)
	 * @param clazz class of the Beans to return (T)
	 * @param input input stream of the excel to read
	 * @param mapping map for mapping. key=header value, value=name of property descriptor
	 * @return List of beans read form excel
	 * @throws ExcelException runtime exception when excel processing failed
	 */
	public <T> List<T> read(Class<T> clazz, InputStream input, Map<String, String> mapping) throws ExcelException {
		List<T> entries = new ArrayList<>();

		try (InputStream in = new PushbackInputStream(
				(input instanceof BufferedInputStream) ? (BufferedInputStream) input : new BufferedInputStream(input))) {
			Workbook workbook = WorkbookFactory.create(in);

			int sheetCount = workbook.getNumberOfSheets();
			for (int i = 0; i < sheetCount; i++) {
				String resourceName = Integer.valueOf(i).toString();
				LOG.info("process sheet number [{}]", resourceName);

				// read sheet information
				Sheet sheet = workbook.getSheetAt(i);
				resourceName = workbook.getSheetName(i);
				LOG.info("sheet name is [{}]", resourceName);

				// get headers for properties mapping
				Map<Integer, Method> indexedProperties = parseHeaderRow(sheet, clazz, mapping);

				if (indexedProperties != null) {
					for (Iterator<?> rit = sheet.rowIterator(); rit.hasNext(); ) {
						Row row = (Row) rit.next();
						if (row.getRowNum() > this.headerRow) {
							T entry = parseContentBodyRow(indexedProperties, row, clazz);
							entries.add(entry);
						}
					}
				} else {
					LOG.info("sheet [{}] has no content.", resourceName);
				}
			}
		} catch (Exception e) {
			throw new ExcelException("Could not read data form stream", e);
		}
		return entries;
	}

	/**
	 * Read the excel file and map rows to beans of type T
	 *
	 * @param <T>   Type od beans returned (determined by clazz)
	 * @param clazz class of the Beans to return (T)
	 * @param input input stream of the excel to read
	 * @return List of beans read form excel
	 * @throws ExcelException runtime exception when excel processing failed
	 */
	public <T> List<T> read(Class<T> clazz, InputStream input) throws ExcelException {
		return read(clazz,input, null);
	}

	protected <T> T parseContentBodyRow(Map<Integer, Method> indexedProperties, Row row, Class<T> clazz) throws Exception {

		T bean = clazz.newInstance();
		for (Iterator<?> cit = row.cellIterator(); cit.hasNext(); ) {
			Cell cell = (Cell) cit.next();
			int index = cell.getColumnIndex();
			Method setter = indexedProperties.get(index);
			if (setter != null) {
				try {
					String value = getStringValue(cell);
					setter.invoke(bean, value);
				} catch (Exception e) {
					LOG.warn("Could not set value for row {} column {} : {}", row.getRowNum(), index, e, null);
				}
			}
		}
		LOG.info("Read bean {}", bean);
		return bean;

	}

	private String getStringValue(final Cell cell) {
		switch (cell.getCellType()) {
			case Cell.CELL_TYPE_NUMERIC:
				return String.valueOf(cell.getNumericCellValue());
			default:
				return cell.getRichStringCellValue().getString();
		}
	}


	/**
	 * Method parses the header row of the sheet to extract the properties.
	 *
	 * @param sheet the Excel sheet to process
	 * @return a Map with column number to bean properties field mapping.
	 */
	protected <T> Map<Integer, Method> parseHeaderRow(Sheet sheet, Class<T> clazz, Map<String, String> mapping) throws Exception {
		Map<Integer, Method> result = new HashMap<>();
		if (sheet == null || sheet.getRow(this.headerRow) == null) {
			return null;
		}

		Map<String, Method> setters = new HashMap<>();
		List<PropertyDescriptor> descriptors = BeanUtils.getPropertyDescriptors(clazz);
		for (PropertyDescriptor descriptor : descriptors) {
			Method method = descriptor.getWriteMethod();
			if (method != null) {
				ReflectionUtils.makeAccessible(method);
				setters.put(descriptor.getName(), method);
			}
		}

		for (Iterator<?> cit = sheet.getRow(this.headerRow).cellIterator(); cit.hasNext(); ) {
			Cell cell = (Cell) cit.next();
			int index = cell.getColumnIndex();
			String value = cell.getRichStringCellValue().getString();
			try {
				if (mapping == null || mapping.isEmpty()){
					if (setters.containsKey(value)) {
						result.put(index, setters.get(value));
					}
				}else {
					if(mapping.containsKey(value) && setters.containsKey(mapping.get(value))){
						result.put(index, setters.get(mapping.get(value)));
					}
				}
			} catch (Exception e) {
				LOG.warn("Could not match property {} in column {}: skip ({})", value, index, e, null);
			}
		}
		return result;
	}


	public void setHeaderRow(int headerRow) {
		this.headerRow = headerRow;
	}

	public ExcelBeanReader headerRow(int headerRow) {
		setHeaderRow(headerRow);
		return this;
	}
}
