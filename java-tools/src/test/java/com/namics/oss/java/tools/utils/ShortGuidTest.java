package com.namics.oss.java.tools.utils;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import static com.namics.oss.java.tools.utils.ShortGuid.*;
import static org.junit.Assert.*;

/**
 * ShortGuidTest.
 *
 * @author aschaefer, Namics AG
 * @since 16.07.18 11:23
 */
public class ShortGuidTest {
    private static final Logger LOG = LoggerFactory.getLogger(ShortGuidTest.class);

    @Test
    public void generateShortGuid() {
        LOG.info("{}", shortGuid());
    }

    @Test
    public void shortGuid_has22Digits() {
        assertEquals(22, shortGuid().length());
    }

    @Test
    public void shortGuid_nullsave() {
        assertNull(shortGuid(null));
    }

    @Test
    public void parseShortGuid_ok() {
        UUID uuid = UUID.randomUUID();
        byte[] array = toByteArray(uuid);
        String encoded = ENCODER.encodeToString(array);
        UUID parsed = parseShortGuid(encoded);
        assertEquals(uuid, parsed);
    }

    @Test
    public void parseShortGuid_nullable() {
        assertNull(parseShortGuid(null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseShortGuid_invalidStringThrowsIllegalArgumentException() {
        parseShortGuid("Hello");
    }

    @Test
    public void fromByteArray_nullable() {
        assertNull(fromByteArray(null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void fromByteArray_invalidArrayThrowsIllegalArgumentException() {
        fromByteArray("Hello".getBytes());
    }

    @Test
    public void toByteArray_fromByteArray_equal() {
        UUID uuid = UUID.randomUUID();
        byte[] bytes = toByteArray(uuid);
        UUID andBack = fromByteArray(bytes);
        assertEquals(uuid, andBack);
    }
}

