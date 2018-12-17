package com.namics.oss.java.tools.utils;


import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.UUID;

/**
 * Utility to process short guid.
 * Short guid is a 22 digit base64 representation of a Type 4 UUID (128 bit)
 *
 * @author aschaefer, Namics AG
 * @since 09.04.18 16:12
 */
public abstract class ShortGuid {

    public static final Base64.Encoder ENCODER = Base64.getEncoder().withoutPadding();
    public static final Base64.Decoder DECODER = Base64.getDecoder();

    public static final int SHORT_GUID_LENGTH = 22;
    public static final String SHORT_GUID_PATTERN = ".{" + SHORT_GUID_LENGTH + "," + SHORT_GUID_LENGTH + "}";

    private ShortGuid() {
        //hide util const
    }

    /**
     * Generate a short UUID - 22 digit base64 representation of a UUID.
     *
     * @return 22 digit base64 representation of a UUID
     */
    public static String shortGuid() {
        UUID uuid = UUID.randomUUID();
        return shortGuid(uuid);
    }

    /**
     * Format a UUID to short UUID - 22 digit base64 representation of a UUID.
     *
     * @param uuid UUID to be formatted
     * @return 22 digit base64 representation of a UUID, null for null
     */
    public static String shortGuid(UUID uuid) {
        if (uuid == null) {
            return null;
        }
        byte[] array = toByteArray(uuid);
        return ENCODER.encodeToString(array);
    }

    /**
     * parse 22 digit base64 UUID representation to actual UUID.
     *
     * @param candidate string that represents UUID in short GUID format
     * @return UUID represented by string, null for null
     * @throws IllegalArgumentException if candidate is not a 22 digit base64 UUID representation
     */
    public static UUID parseShortGuid(String candidate) {
        if (candidate == null) {
            return null;
        }
        if (candidate.length() == SHORT_GUID_LENGTH) {
            return fromByteArray(DECODER.decode(candidate));
        }
        throw new IllegalArgumentException(candidate + " is not a short guid");
    }

    /**
     * Convert UUID to 16 byte array.
     *
     * @param uuid uuid to get byte representation
     * @return 16 byte array, null for null
     */
    public static byte[] toByteArray(UUID uuid) {
        if (uuid == null) {
            return null;
        }
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return bb.array();
    }


    /**
     * Read UUID from 16 byte array.
     *
     * @param data 16 bytes representing a UUID
     * @return UUID represented by byte array, null for null
     * @throws IllegalArgumentException if candidate is not a 16 byte array
     */
    public static UUID fromByteArray(byte[] data) {
        if (data == null) {
            return null;
        }
        if (data.length != 16) {
            throw new IllegalArgumentException("data must be 16 byte");
        }
        long msb = 0;
        long lsb = 0;
        for (int i = 0; i < 8; i++) {
            msb = (msb << 8) | (data[i] & 0xff);
        }
        for (int i = 8; i < 16; i++) {
            lsb = (lsb << 8) | (data[i] & 0xff);
        }
        return new UUID(msb, lsb);
    }


}
