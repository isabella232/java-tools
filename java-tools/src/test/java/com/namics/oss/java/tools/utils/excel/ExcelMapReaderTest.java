package com.namics.oss.java.tools.utils.excel;

import org.junit.Test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * UnitTest for ExcelMapReader.
 *
 * @author tzehnder, Namics AG
 * @since 05.10.2016 13:25
 */
public class ExcelMapReaderTest {

    private static final String HEADER_1 = "email";
    private static final String HEADER_2 = "username";
    private static final String HEADER_3 = "gender";
    private static final String HEADER_4 = "firstname";
    private static final String HEADER_5 = "lastname";
    private static final String HEADER_6 = "password";
    private static final String HEADER_7 = "id";

    @Test
    public void testReadExcel() throws Exception {
        try (InputStream input = getClass().getResourceAsStream("/excel/testMap.xlsx")) {
            List<Map<String, String>> maps = new ExcelMapReader().read(input);
            assertNotNull(maps);
            assertEquals(488, maps.size());
            for (Map<String, String> map : maps) {
                assertEquals(7, map.size());
                assertNotNull(map.get(HEADER_1));
                assertNotNull(map.get(HEADER_2));
                assertNotNull(map.get(HEADER_3));
                assertNotNull(map.get(HEADER_4));
                assertNotNull(map.get(HEADER_5));
                assertNotNull(map.get(HEADER_6));
                assertNotNull(map.get(HEADER_7));
            }
        }
    }

    @Test
    public void testReadExcelWithMapping() throws Exception {
        String mapping1 = "mapping1";
        String mapping2 = "mapping2";
        List<String> passwords = new ArrayList<>();
        passwords.add("customer1");
        passwords.add("customer2");

        try (InputStream input = getClass().getResourceAsStream("/excel/testMap.xlsx")) {
            Map<String, String> mapping = new HashMap<>();
            mapping.put("password", mapping1);
            mapping.put("id", mapping2);
            List<Map<String, String>> maps = new ExcelMapReader().read(input, mapping);
            assertNotNull(maps);
            assertEquals(488, maps.size());
            for (Map<String, String> map : maps) {
                assertEquals(2, map.size());
                assertThat(passwords, hasItem(map.get(mapping1)));
                assertThat(Double.valueOf(map.get(mapping2)).intValue(), allOf(greaterThan(0), lessThan(489)));
            }
        }
    }

}