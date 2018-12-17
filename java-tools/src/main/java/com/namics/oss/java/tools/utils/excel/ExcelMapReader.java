/*
 * Copyright 2000-2016 Namics AG. All rights reserved.
 */
package com.namics.oss.java.tools.utils.excel;

import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.*;

/**
 * ExcelMapReader.
 *
 * @author tzehnder, Namics AG
 * @since 05.10.2016 12:53
 */
public class ExcelMapReader {
    private static final Logger LOG = LoggerFactory.getLogger(ExcelMapReader.class);

    public static final int DEFAULT_HEADER_ROW = 0;

    protected int headerRow = DEFAULT_HEADER_ROW;

    /**
     * Read the excel file and map rows to maps
     *
     * @param input input stream of the excel to read
     * @return List of maps read form excel
     * @throws ExcelException runtime exception when excel processing failed
     */
    public List<Map<String, String>> read(final InputStream input) {
        return read(input, null);
    }

    /**
     * Read the excel file and map rows to maps
     *
     * @param input   input stream of the excel to read
     * @param mapping map for mapping. key=header value, value=name of property descriptor
     * @return List of maps read form excel
     * @throws ExcelException runtime exception when excel processing failed
     */
    public List<Map<String, String>> read(final InputStream input, final Map<String, String> mapping) {
        List<Map<String, String>> maps = new ArrayList<>();

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
                Map<Integer, String> headerMapping = parseHeaderRow(sheet, mapping);

                if (headerMapping != null) {
                    for (Iterator<?> rit = sheet.rowIterator(); rit.hasNext(); ) {
                        Row row = (Row) rit.next();
                        if (row.getRowNum() > this.headerRow) {
                            Map<String, String> entry = parseContentBodyRow(headerMapping, row);
                            maps.add(entry);
                        }
                    }
                } else {
                    LOG.info("sheet [{}] has no content.", resourceName);
                }
            }
        } catch (Exception e) {
            throw new ExcelException("Could not read data form stream", e);
        }


        return maps;
    }

    protected Map<String, String> parseContentBodyRow(Map<Integer, String> headerMapping, Row row) throws Exception {
        Map<String, String> map = new HashMap<>();
        for (Iterator<?> cit = row.cellIterator(); cit.hasNext(); ) {
            Cell cell = (Cell) cit.next();
            int index = cell.getColumnIndex();
            String key = headerMapping.get(index);
            if (key != null) {
                try {
                    String value = getStringValue(cell);
                    map.put(key, value);
                } catch (Exception e) {
                    LOG.warn("Could not set value for row {} column {} : {}", row.getRowNum(), index, e, null);
                }
            }
        }
        LOG.info("Read map {}", map);
        return map;

    }

    private String getStringValue(final Cell cell) {
        switch (cell.getCellType()) {
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            default:
                return cell.getRichStringCellValue().getString();
        }
    }


    protected Map<Integer, String> parseHeaderRow(Sheet sheet, Map<String, String> mapping) throws Exception {
        Map<Integer, String> result = new HashMap<>();
        if (sheet == null || sheet.getRow(this.headerRow) == null) {
            return null;
        }

        for (Iterator<?> cit = sheet.getRow(this.headerRow).cellIterator(); cit.hasNext(); ) {
            Cell cell = (Cell) cit.next();
            int index = cell.getColumnIndex();
            String value = cell.getRichStringCellValue().getString();
            try {
                if (mapping == null || mapping.isEmpty()) {
                    result.put(index, value);
                } else {
                    if (mapping.containsKey(value)) {
                        result.put(index, mapping.get(value));
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

    public ExcelMapReader headerRow(int headerRow) {
        setHeaderRow(headerRow);
        return this;
    }
}
