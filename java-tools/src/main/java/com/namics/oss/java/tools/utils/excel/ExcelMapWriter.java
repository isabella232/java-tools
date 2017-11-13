/*
 * Copyright 2000-2016 Namics AG. All rights reserved.
 */
package com.namics.oss.java.tools.utils.excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.OutputStream;
import java.util.*;

import static java.util.Arrays.asList;

/**
 * ExcelMapWriter.
 *
 * @author tzehnder, Namics AG
 * @since 05.10.2016 09:55
 */
public class ExcelMapWriter {
    private static final Logger LOG = LoggerFactory.getLogger(ExcelMapWriter.class);

    public static final String SHEET_NAME = "sheet1";

    public static final int DEFAULT_HEADER_ROW = 0;
    protected int headerRow = DEFAULT_HEADER_ROW;

    /**
     * Write a list of bean to excel file, support for xlsx only.
     *
     * @param maps         maps to write to an excel sheet
     * @param outputStream output stream to write the excel sheet
     * @param ignores      list of properties to be ignored on writing
     * @throws ExcelException runtime exception when excel processiing failed
     */
    public void write(List<Map<String, Object>> maps, OutputStream outputStream, String... ignores) throws ExcelException {
        writeInternal(maps, outputStream, null, ignores);
    }

    private void writeInternal(List<Map<String, Object>> maps, OutputStream outputStream, Map<String, String> mapping, String... ignore) throws ExcelException {
        try {
            if (maps != null && !maps.isEmpty()) {
                SXSSFWorkbook workbook = new SXSSFWorkbook();
                SXSSFSheet sheet = workbook.createSheet(SHEET_NAME);
                sheet.trackAllColumnsForAutoSizing();
                final Map<Integer, String> keys;

                if (mapping == null || mapping.isEmpty()) {
                    keys = writeHeaderRow(sheet, maps.get(0), ignore);
                } else {
                    keys = writeHeaderRow(sheet, maps.get(0), mapping);
                }
                writeContent(keys, maps, sheet);

                keys.keySet().forEach(sheet::autoSizeColumn);
                workbook.write(outputStream);
            }
        } catch (Exception e) {
            throw new ExcelException("Could not write data to stream.", e);
        }
    }

    protected int writeContent(final Map<Integer, String> header, final List<Map<String, Object>> maps, final Sheet sheet) {
        int rowNum = 0;

        for (Map<String, Object> map : maps) {
            rowNum++;
            Row row = sheet.createRow(rowNum);

            for (Map.Entry<Integer, String> entry : header.entrySet()) {
                Cell cell = row.createCell(entry.getKey());
                if (map.containsKey(entry.getValue())) {
                    Object value = map.get(entry.getValue());
                    if (value != null) {
                        cell.setCellValue(value.toString());
                    }
                }
            }
        }
        return rowNum;
    }

    protected Map<Integer, String> writeHeaderRow(final Sheet sheet, final Map<String, Object> map, final Map<String, String> mapping) {
        LOG.debug("Create header for {}", map);

        Map<Integer, String> result = new HashMap<>();
        Row row = sheet.createRow(this.headerRow);
        Set<String> keys = map.keySet();

        int index = 0;
        for (String key : keys) {
            if (mapping.containsKey(key)) {
                result.put(index, key);
                Cell cell = row.createCell(index);
                cell.setCellValue(new XSSFRichTextString(mapping.get(key)));
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

    protected Map<Integer, String> writeHeaderRow(final Sheet sheet, final Map<String, Object> map, final String... ignores) {
        LOG.debug("Create header for {}", map);

        List<String> ignored = ignores != null ? asList(ignores) : Collections.emptyList();
        Map<Integer, String> result = new HashMap<>();
        Row row = sheet.createRow(this.headerRow);
        Set<String> keys = map.keySet();

        int index = 0;
        for (String key : keys) {
            if (!ignored.contains(key)) {
                result.put(index, key);
                Cell cell = row.createCell(index);
                cell.setCellValue(new XSSFRichTextString(key));
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
     * @param maps         maps to write to an excel sheet
     * @param outputStream output stream to write the excel sheet
     * @param mapping      map for mapping. key=name of property descriptor, value=header value
     * @throws ExcelException runtime exception when excel processiing failed
     */
    public void write(final List<Map<String, Object>> maps, final OutputStream outputStream, final Map<String, String> mapping) {
        writeInternal(maps, outputStream, mapping);
    }
}
