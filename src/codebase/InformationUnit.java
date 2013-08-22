/*
 * Created on 2006/05/03
 */
package codebase;

/**
 * Utility class for information size unit conversion and formating
 * <p>
 * Defines constants and provides several methods for performing information units like
 * e.g. bytes to kilobytes, among other. TODO: upgrade to a Java formatter for better
 * integration.
 */
public final class InformationUnit {


    /**
     * Byte unit character.
     */
    public static final String BYTE_UNIT = "B";

    /**
     * One kilobyte in bytes.
     */
    public static final int KB = 1024;

    /**
     * Kilobyte unit abbreviation.
     */
    public static final String KB_UNIT = "KB";
   
    /**
     * One megabyte in bytes.
     */
    public static final int MB = 1024 * KB;
    
    /**
     * Megabyte unit abbreviation.
     */
    public static final String MB_UNIT = "MB";

    /**
     * One gigabyte in bytes.
     */
    public static final long GB = 1024 * MB;

    /**
     * Gigabyte unit abbreviation.
     */
    public static final String GB_UNIT = "GB";

    /**
     * Formats a size in bytes adjusting it to (KB, MB, GB).
     * 
     * @param bytes the amount of bytes
     * @param decimals places for the rounding
     * @return human readable string for bytes including the unit
     */
    public static String formatBytes(final long bytes, final int decimals) {
        final String result;

        if (bytes >= GB) {
            result = Double.toString(Math.round(gb(bytes), decimals)) + GB_UNIT;
        } else if (bytes >= MB) {
            result = Double.toString(Math.round(mb(bytes), decimals)) + MB_UNIT;
        } else if (bytes >= KB) {
            result = Double.toString(Math.round(kb(bytes), decimals)) + KB_UNIT;
        } else {
            result = Long.toString(bytes) + BYTE_UNIT;
        }

        return result;
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
     * Converts an amount of kilobytes to bytes.
     * 
     * @param n the amount of kilobytes, must not be negative
     * @return <code>n * 2^10</code>
     */
    public static long kbToBytes(final double n) {
        return (long) java.lang.Math.ceil(n * KB);
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
     * Converts an amount of megabytes to bytes.
     * 
     * @param n the amount of megabytes, must not be negative
     * @return <code>n * 2^20</code>
     */
    public static long mbToBytes(final double n) {
        return (long) java.lang.Math.ceil(n * MB);
    }

    /**
     * Parses a string to a size in bytes.
     * <p>
     * Note that the case of the units is irrelevant
     * 
     * @param s the source string. For example <code>"10.7KB"</code>,
     *            <code>"18.923GB"</code>.
     * @return the size in bytes taken from the string
     */
    public static long parseBytes(final String s) {
        final String u = s.toUpperCase();
        final long bytes;
        if (u.endsWith(GB_UNIT)) {
            bytes = gbToBytes(Double.parseDouble(u.substring(0, u.length() - GB_UNIT.length())));
        } else if (u.endsWith(MB_UNIT)) {
            bytes = mbToBytes(Double.parseDouble(u.substring(0, u.length() - MB_UNIT.length())));
        } else if (u.endsWith(KB_UNIT)) {
            bytes = kbToBytes(Double.parseDouble(u.substring(0, u.length() - KB_UNIT.length())));
        } else if (u.endsWith(BYTE_UNIT)) {
            bytes = (long) java.lang.Math.ceil(Double.parseDouble(u.substring(0, u.length()
                    - BYTE_UNIT.length())));
        } else {
            bytes = Long.parseLong(s);
        }

        return bytes;
    }

    
    /**
     * Prevent instantiation.
     */
    private InformationUnit() {
    }

}
