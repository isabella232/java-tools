package com.namics.oss.java.tools.utils.excel;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * UnitTest for ExcelMapWriter.
 *
 * @author tzehnder, Namics AG
 * @since 05.10.2016 10:57
 */
class ExcelMapWriterTest {
	private static final Logger LOG = LoggerFactory.getLogger(ExcelMapWriterTest.class);
	private final static String COLUMN_INT_KEY = "columnInt";
	private final static Integer COLUMN_INT_VALUE = 1;

	private final static String COLUMN_STRING_KEY = "columnString";
	private final static String COLUMN_STRING_VALUE = "StringValue";

	private final static String COLUMN_BOOL0_KEY = "columnBool0";
	private final static boolean COLUMN_BOOL0_VALUE = false;

	private final static String COLUMN_BOOL1_KEY = "columnBool1";
	private final static boolean COLUMN_BOOL1_VALUE = false;


	@Test
	void testWrite() throws Exception {
		final List<Map<String, Object>> testMaps = getTestMaps();
		String absolute = getClass().getResource("/").getFile() + "excel/mapwriter-test.xlsx";
		OutputStream out = new FileOutputStream(absolute);
		new ExcelMapWriter().write(testMaps, out);
		checkIfFileContainsMaps(absolute, testMaps);
	}

	private List<Map<String, Object>> getTestMaps() {
		List<Map<String, Object>> maps = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		map.put(COLUMN_INT_KEY, COLUMN_INT_VALUE);
		map.put(COLUMN_STRING_KEY, COLUMN_STRING_VALUE);
		map.put(COLUMN_BOOL0_KEY, COLUMN_BOOL0_VALUE);
		map.put(COLUMN_BOOL1_KEY, COLUMN_BOOL1_VALUE);
		maps.add(map);
		return maps;
	}

	private void checkIfFileContainsMaps(final String absolute, final List<Map<String, Object>> bulk) throws IOException {
		try (InputStream input = new FileInputStream(absolute)) {
			final List<Map<String, String>> verify = new ExcelMapReader().read(input);
			verify.forEach(e -> LOG.info("{}", e));
			assertEquals(verify.size(), bulk.size());
			Map<String, String> verifyEntry = verify.get(0);
			Map<String, Object> bulkEntry = bulk.get(0);

			assertEquals(verifyEntry.get(COLUMN_INT_KEY), bulkEntry.get(COLUMN_INT_KEY).toString());
			assertEquals(verifyEntry.get(COLUMN_STRING_KEY), bulkEntry.get(COLUMN_STRING_KEY).toString());
			assertEquals(verifyEntry.get(COLUMN_BOOL0_KEY), bulkEntry.get(COLUMN_BOOL0_KEY).toString());
			assertEquals(verifyEntry.get(COLUMN_BOOL1_KEY), bulkEntry.get(COLUMN_BOOL1_KEY).toString());
		}
	}
}
