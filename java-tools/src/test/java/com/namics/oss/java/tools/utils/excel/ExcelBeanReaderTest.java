/*
 * Copyright 2000-2014 Namics AG. All rights reserved.
 */

package com.namics.oss.java.tools.utils.excel;

import com.namics.oss.java.tools.utils.bean.TestBean;
import org.junit.Test;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * ExcelProfileReaderTest.
 *
 * @author aschaefer, Namics AG
 * @since 08.09.14 16:39
 */
public class ExcelBeanReaderTest {

    @Test
    public void testReadExcel() throws Exception {
        try (InputStream input = getClass().getResourceAsStream("/excel/testBean.xlsx")) {
            List<TestBean> profiles = new ExcelBeanReader().read(TestBean.class, input);
            assertNotNull(profiles);
            assertEquals(488, profiles.size());
            for (TestBean profile : profiles) {
                assertNotNull(profile.toString(), profile.getUsername());
                assertNotNull(profile.toString(), profile.getFirstname());
                assertNotNull(profile.toString(), profile.getLastname());
            }
        }
    }

    @Test
    public void testReadExcelWithMapping() throws Exception {
        try (InputStream input = getClass().getResourceAsStream("/excel/testBean.xlsx")) {
            Map<String, String> mapping = new HashMap<>();
            mapping.put("password", "username");
            mapping.put("gender", "firstname");
            mapping.put("email", "lastname");
            List<TestBean> profiles = new ExcelBeanReader().read(TestBean.class, input, mapping);
            assertNotNull(profiles);
            assertEquals(488, profiles.size());
            for (TestBean profile : profiles) {
                assertNotNull(profile.toString(), profile.getUsername());
                assertNotNull(profile.toString(), profile.getFirstname());
                assertNotNull(profile.toString(), profile.getLastname());
            }
        }
    }

    @Test
    public void testReadExcelWithNumerics() throws Exception {
        try (InputStream input = getClass().getResourceAsStream("/excel/testBean.xlsx")) {
            Map<String, String> mapping = new HashMap<>();
            mapping.put("password", "username");
            mapping.put("gender", "firstname");
            mapping.put("id", "lastname");
            List<TestBean> profiles = new ExcelBeanReader().read(TestBean.class, input, mapping);
            assertNotNull(profiles);
            assertEquals(488, profiles.size());
            for (TestBean profile : profiles) {
                assertNotNull(profile.toString(), profile.getUsername());
                assertNotNull(profile.toString(), profile.getFirstname());
                assertNotNull(profile.toString(), profile.getLastname());
            }
        }
    }

}