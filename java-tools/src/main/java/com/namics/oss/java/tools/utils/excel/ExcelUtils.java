/*
 * Copyright 2000-2015 Namics AG. All rights reserved.
 */

package com.namics.oss.java.tools.utils.excel;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * Utils to supprot reading and writing from / to excel files.
 * If you need more controll over the process, use
 * ExcelBeanReader or ExcelBeanWriter directly
 *
 * @author aschaefer, Namics AG
 * @since 21.07.15 09:28
 */
public abstract class ExcelUtils {
    private ExcelUtils() {
    }

    protected static final ExcelBeanReader excelBeanReader = new ExcelBeanReader();
    protected static final ExcelBeanWriter excelBeanWriter = new ExcelBeanWriter();
    protected static final ExcelMapWriter excelMapWriter = new ExcelMapWriter();
    protected static final ExcelMapReader excelMapReader = new ExcelMapReader();

    /**
     * Read the excel file and map rows to beans of type T
     *
     * @param <T>   Type od beans returned (determined by clazz)
     * @param clazz class of the Beans to return (T)
     * @param input input stream of the excel to read
     * @return List of beans read form excel
     * @throws ExcelException runtime exception when excel processiing failed
     */
    public static <T> List<T> readBeans(Class<T> clazz, InputStream input) {
        return readBeans(clazz, input, null);
    }

    /**
     * @deprecated replaced by {@link #readBeans(Class, InputStream)}
     */
    @Deprecated
    public static <T> List<T> read(Class<T> clazz, InputStream input) {
        return readBeans(clazz, input);
    }

    /**
     * Read the excel file and map rows to beans of type T
     *
     * @param <T>     Type od beans returned (determined by clazz)
     * @param clazz   class of the Beans to return (T)
     * @param input   input stream of the excel to read
     * @param mapping map for mapping. key=header value, value=name of property descriptor
     * @return List of beans read form excel
     * @throws ExcelException runtime exception when excel processiing failed
     */
    public static <T> List<T> readBeans(Class<T> clazz, InputStream input, Map<String, String> mapping) {
        checkAvailability();
        return excelBeanReader.read(clazz, input, mapping);
    }

    /**
     * @deprecated replaced by {@link #readBeans(Class, InputStream, Map)}
     */
    @Deprecated
    public static <T> List<T> read(Class<T> clazz, InputStream input, Map<String, String> mapping) {
        return readBeans(clazz, input, mapping);

    }

    /**
     * Write a list of bean to excel file, support for xlsx only.
     *
     * @param beans        beans to write to an excel sheet
     * @param outputStream output stream to write the excel sheet
     * @param ignores      list of properties to be ignored on writing
     * @throws ExcelException runtime exception when excel processiing failed
     */
    public static void writeBeans(List<?> beans, OutputStream outputStream, String... ignores) {
        checkAvailability();
        excelBeanWriter.write(beans, outputStream, ignores);
    }

    /**
     * @deprecated replaced by {@link #writeBeans(List, OutputStream, String...)}
     */
    @Deprecated
    public static void write(List<?> beans, OutputStream outputStream, String... ignores) {
        writeBeans(beans, outputStream, ignores);
    }

    /**
     * Write a list of bean to excel file, support for xlsx only.
     *
     * @param beans        beans to write to an excel sheet
     * @param outputStream output stream to write the excel sheet
     * @param mapping      map for mapping. key=name of property descriptor, value=header value
     * @throws ExcelException runtime exception when excel processiing failed
     */
    public static void writeBeans(List<?> beans, OutputStream outputStream, Map<String, String> mapping) {
        checkAvailability();
        excelBeanWriter.write(beans, outputStream, mapping);
    }

    /**
     * @deprecated replaced by {@link #writeBeans(List, OutputStream, Map)}
     */
    @Deprecated
    public static void write(List<?> beans, OutputStream outputStream, Map<String, String> mapping) {
        writeBeans(beans, outputStream, mapping);
    }

    /**
     * Write a list of bean to excel file, support for xlsx only.
     *
     * @param maps         maps to write to an excel sheet
     * @param outputStream output stream to write the excel sheet
     * @param ignores      list of properties to be ignored on writing
     * @throws ExcelException runtime exception when excel processiing failed
     */
    public static void writeMaps(List<Map<String, Object>> maps, OutputStream outputStream, String... ignores) {
        checkAvailability();
        excelMapWriter.write(maps, outputStream, ignores);
    }

    /**
     * Write a list of bean to excel file, support for xlsx only.
     *
     * @param maps         maps to write to an excel sheet
     * @param outputStream output stream to write the excel sheet
     * @param mapping      map for mapping. key=name of property descriptor, value=header value
     * @throws ExcelException runtime exception when excel processiing failed
     */
    public static void writeMaps(List<Map<String, Object>> maps, OutputStream outputStream, Map<String, String> mapping) {
        checkAvailability();
        excelMapWriter.write(maps, outputStream, mapping);
    }

    /**
     * Read the excel file and map rows to maps
     *
     * @param input input stream of the excel to read
     * @return List of maps read form excel
     * @throws ExcelException runtime exception when excel processiing failed
     */
    public static List<Map<String, String>> readMaps(InputStream input) {
        return readMaps(input, null);
    }

    /**
     * Read the excel file and map rows to beans of type T
     *
     * @param input   input stream of the excel to read
     * @param mapping map for mapping. key=header value, value=name of property descriptor
     * @return List of maps read form excel
     * @throws ExcelException runtime exception when excel processiing failed
     */
    public static List<Map<String, String>> readMaps(InputStream input, Map<String, String> mapping) {
        checkAvailability();
        return excelMapReader.read(input, mapping);
    }


    public static void checkAvailability() {
        try {
            Class.forName("org.apache.poi.Version", false, ExcelUtils.class.getClassLoader());
        } catch (ClassNotFoundException e) {
            throw new ExcelException("To use Excel functions you must add apache poi to your classpath: \n" + POI_CORE, e);
        }
        try {
            Class.forName("org.apache.poi.xssf.usermodel.XSSFRichTextString", false, ExcelUtils.class.getClassLoader());
        } catch (ClassNotFoundException e) {
            throw new ExcelException("To use Excel functions you must add apache poi to your classpath: \n" + POI_OOXML, e);
        }
    }

    private static final String POI_CORE = "<dependency>\n"
            + "    <groupId>org.apache.poi</groupId>\n"
            + "    <artifactId>poi</artifactId>\n"
            + "    <version>3.12</version>\n"
            + "</dependency>";

    private static final String POI_OOXML = "<dependency>\n"
            + "    <groupId>org.apache.poi</groupId>\n"
            + "    <artifactId>poi-ooxml</artifactId>\n"
            + "    <version>3.12</version>\n"
            + "</dependency>";
}
