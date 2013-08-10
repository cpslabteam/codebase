/*
 * Created on 2006/05/03
 */
package codebase;

/**
 * Formats information units. 
 * 
 * TODO: upgrade to a Java formatter for better integration.
 */
public class Format {

    /**
     * Converts a string to a visible ascii string.
     * <p>
     * This routine is means to be used in to dump strings that may have binary data.
     * 
     * @param string the string to be converted
     * @param invisible the character to replace the invisible control characters
     * @return a string where invisible characters are replaced by a predefined character.
     */
    public static String visibleAsciiString(final String string, final char invisible) {
        final byte[] target = string.getBytes();
        for (int i = 0; i < target.length; i++) {
            if (Strings.isControl((char) target[i])) {
                target[i] = (byte) invisible;
            }
        }
        return new String(target);
    }

    /**
     * Parses a string to a size in bytes
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
        if (u.endsWith(Conversions.GB_UNIT)) {
            bytes = Conversions.gbToBytes(Double.parseDouble(u.substring(0, u.length()
                    - Conversions.GB_UNIT.length())));
        } else if (u.endsWith(Conversions.MB_UNIT)) {
            bytes = Conversions.mbToBytes(Double.parseDouble(u.substring(0, u.length()
                    - Conversions.MB_UNIT.length())));
        } else if (u.endsWith(Conversions.KB_UNIT)) {
            bytes = Conversions.kbToBytes(Double.parseDouble(u.substring(0, u.length()
                    - Conversions.KB_UNIT.length())));
        } else if (u.endsWith(Conversions.BYTE_UNIT)) {
            bytes = (long) java.lang.Math.ceil(Double.parseDouble(u.substring(0, u.length()
                    - Conversions.BYTE_UNIT.length())));
        } else {
            bytes = Long.parseLong(s);
        }

        return bytes;
    }

    /**
     * Formats a size in bytes adjusting it to (KB, MB, GB)
     * 
     * @param bytes the ammount of bytes
     * @param decimals places for the rounding
     * @return human readable string for bytes including the unit
     */
    public static String formatBytes(final long bytes, final int decimals) {
        final String result;

        if (bytes >= Conversions.GB) {
            result = Double.toString(Math.round(Conversions.gb(bytes), decimals))
                    + Conversions.GB_UNIT;
        } else if (bytes >= Conversions.MB) {
            result = Double.toString(Math.round(Conversions.mb(bytes), decimals))
                    + Conversions.MB_UNIT;
        } else if (bytes >= Conversions.KB) {
            result = Double.toString(Math.round(Conversions.kb(bytes), decimals))
                    + Conversions.KB_UNIT;
        } else {
            result = Long.toString(bytes) + Conversions.BYTE_UNIT;
        }

        return result;
    }

}
