/*
 * Created on 9/Jun/2005
 */
package codebase.util;

/**
 * Utility class for information size unit conversions.
 * <p>
 * Defines constants and provides several methods for performing information
 * units like e.g. bytes to kilobytes, among other.
 */
public final class Conversions {

    /**
     * One kilobyte in bytes.
     */
    public static final int    KB        = 1024;

    /**
     * One megabyte in bytes.
     */
    public static final int    MB        = 1024 * KB;

    /**
     * One gigabyte in bytes.
     */
    public static final long   GB        = 1024 * MB;

    /**
     * Byte unit character.
     */
    public static final String BYTE_UNIT = "B";

    /**
     * Kilobyte unit abbreviation.
     */
    public static final String KB_UNIT   = "KB";

    /**
     * Megabsyte unit abbreviation.
     */
    public static final String MB_UNIT   = "MB";

    /**
     * Gigabyte unit abbreviation.
     */
    public static final String GB_UNIT   = "GB";

    /**
     * Prevent instantiation.
     */
    private Conversions() {
    }

    /**
     * Converts an amount of kilobytes to bytes.
     * 
     * @param n the amount of kilobytes, must not be negative
     * @return <code>n * 2^10</code>
     */
    public static long kbToBytes(final double n) {
        return (long) java.lang.Math.ceil(n * KB);
    }

    /**
     * Converts an amount of megabytes to bytes.
     * 
     * @param n the amount of megabytes, must not be negative
     * @return <code>n * 2^20</code>
     */
    public static long mbToBytes(final double n) {
        return (long) java.lang.Math.ceil(n * MB);
    }

    /**
     * Converts an amount of gigabytes to byte.
     * 
     * @param n the amount of gigabytes, must not be negative
     * @return <code>n * 2^30</code>
     */
    public static long gbToBytes(final double n) {
        return (long) java.lang.Math.ceil(n * GB);
    }

    /**
     * Converts an amount of bytes to kilobytes.
     * 
     * @param bytes the number of bytes
     * @return <code>bytes/2^10</code>
     */
    public static double kb(final long bytes) {
        return bytes / (double) KB;
    }

    /**
     * Converts an amount of bytes to megabytes.
     * 
     * @param bytes the number of bytes
     * @return <code>bytes/2^10</code>
     */
    public static double mb(final long bytes) {
        return bytes / (double) MB;
    }

    /**
     * Converts an amount of bytes to megabytes.
     * 
     * @param bytes the number of bytes
     * @return <code>bytes/2^10</code>
     */
    public static double gb(final long bytes) {
        return bytes / (double) GB;
    }
}
