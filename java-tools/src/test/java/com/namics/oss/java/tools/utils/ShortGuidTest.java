package com.namics.oss.java.tools.utils;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import static com.namics.oss.java.tools.utils.ShortGuid.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * ShortGuidTest.
 *
 * @author aschaefer, Namics AG
 * @since 16.07.18 11:23
 */
class ShortGuidTest {
	private static final Logger LOG = LoggerFactory.getLogger(ShortGuidTest.class);

	@Test
	void generateShortGuid() {
		LOG.info("{}", shortGuid());
	}

	@Test
	void shortGuid_has22Digits() {
		assertEquals(22, shortGuid().length());
	}

	@Test
	void shortGuid_nullsave() {
		assertNull(shortGuid(null));
	}

	@Test
	void parseShortGuid_ok() {
		UUID uuid = UUID.randomUUID();
		byte[] array = toByteArray(uuid);
		String encoded = ENCODER.encodeToString(array);
		UUID parsed = parseShortGuid(encoded);
		assertEquals(uuid, parsed);
	}

	@Test
	void parseShortGuid_nullable() {
		assertNull(parseShortGuid(null));
	}

	@Test
	void parseShortGuid_invalidStringThrowsIllegalArgumentException() {
		assertThrows(IllegalArgumentException.class, () -> parseShortGuid("Hello"));
	}

	@Test
	void fromByteArray_nullable() {
		assertNull(fromByteArray(null));
	}

	@Test
	void fromByteArray_invalidArrayThrowsIllegalArgumentException() {
		assertThrows(IllegalArgumentException.class, () -> fromByteArray("Hello".getBytes()));
	}

	@Test
	void toByteArray_fromByteArray_equal() {
		UUID uuid = UUID.randomUUID();
		byte[] bytes = toByteArray(uuid);
		UUID andBack = fromByteArray(bytes);
		assertEquals(uuid, andBack);
	}
}

