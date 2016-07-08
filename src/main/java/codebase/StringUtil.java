package codebase;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.StringTokenizer;

/**
 * String utilities for trimming, padding and parsing strings.
 * <p>
 * These utility functions are of recurrent use and not provided by JDK.
 *
 * @author Paulo Carreira, André Gonçalves
 * @since Created on 7/Out/2004
 */
public final class StringUtil {

    /**
     * String representation of a double-comma.
     */
    public static final char DOUBLE_QUOTE = '"';

    /**
     * The byte that represents the ascii space character.
     */
    public static final byte ASCII_SPACE_BYTE = 0x20;

    /**
     * The platform dependent new line string.
     */
    public static final String NL = System.getProperty("line.separator");

    /**
     * A constant string with the name of the utf-8 encoding.
     */
    public static final String UTF8_NAME = "UTF-8";

    /**
     * The default Charset used in string-to-byte and byte-to-string conversions.
     */
    public static final Charset UTF8 = Charset.forName(UTF8_NAME);

    /**
     * Short name descriptions of ascii invisible characters. This is useful for
     * displaying the contents of a string of bytes.
     */
    private static final String[] SHORT_CHAR_NAMES = { "NUL", "SOH", "STX", "ETX", "EOT", "ENQ",
            "ACK", "BEL", "BS", "TAB", "LF", "VT", "FF", "CR", "SO", "SI", "DLE", "XON", "DC2",
            "XOFF", "DC4", "NAK", "SYN", "ETB", "CAN", "EM ", "SUB" };

    /**
     * Long name descriptions of ascii invisible characters. This is useful for displaying
     * the contents of a string of bytes.
     */
    private static final String[] LONG_CHAR_NAMES = { "Null character", "Start of Header",
            "Start of Text", "End of Text", "End of Transmission", "Enquiry", "Acknowledgment",
            "Bell", "Backspace", "Tab", "Line feed", "Vertical Tab", "Form feed", "Carriage Return",
            "Shift Out", "Shift In", "Data Link Escape", "Device Control 1 (oft. XON)",
            "Device Control 2", "Device Control 3 (oft. XOFF)", "Device Control 4",
            "Negative Acknowledgement", "Synchronous Idle", "End of Trans. Block", "Cancel",
            "End of Medium", "Substitute" };

    /**
     * Returns the complete name of a char.
     * 
     * @param c is the character
     * @return a name like <code>Carriage Return</code> if the character is a special
     *         character. Returns the string representation otherwise.
     */
    public static String charNameLong(final char c) {
        if (isControl(c)) {
            return LONG_CHAR_NAMES[c];
        } else {
            return Character.toString(c);
        }
    }

    /**
     * Retruns the compact name of a char.
     * 
     * @param c is the character
     * @return a name like <code>TAB</code>, <code>EOT</code> or <code>CR</code> if the
     *         character is a special chatarcter. Returns the string representation
     *         otherwise.
     */
    public static String charNameShort(final char c) {
        if (isControl(c)) {
            return SHORT_CHAR_NAMES[c];
        } else {
            return Character.toString(c);
        }
    }

    /**
     * Compacts a string for displaying.
     * <p>
     * This function is meant to be used to avoid the user to be overwhelmed by very large
     * size dumps. To that end it gets the header of the string and the trailer that serve
     * as a summary of the string.
     * <p>
     * If the original string is smaller that the specified size, the original string is
     * returned.
     * 
     * @param str the original string
     * @param size the maximum size for display. The size must be greater than 5. If the
     *            specified size is smaller than 5, it is ignored.
     * @return a string of the form <code>xxxxxx ... xxxxxx</code> with at most
     *         <code>size</code> characters.
     */
    public static String compactFormat(final String str, final int size) {
        final String ellipsis = "...";
        final int lenEllipsis = ellipsis.length();
        final int len = str.length();
        if (size < ellipsis.length() + 2 || len <= size) {
            return str;
        } else {
            final int headerSize = (size - lenEllipsis) / 2;
            final int trailerSize;
            if (2 * (headerSize + 1) + lenEllipsis <= size + 1) {
                trailerSize = headerSize + 1;
            } else {
                trailerSize = headerSize;
            }
            final String result = str.substring(0, headerSize) + ellipsis
                    + str.substring(len - (trailerSize - 1), len);

            assert result.length() == size;
            return result;
        }
    }


    /**
     * The returns the index of the first character of the a string that is different from
     * a specified character.
     * 
     * @param str The string where the characters will be searched
     * @param ch the character that should not appear
     * @return a valid index of <code>-1</code> if all the characters have value of chars
     *         are ch
     */
    public static int firstIndexNotOf(final String str, final char ch) {
        int index = 0;
        final int len = str.length();

        while (index < len) {
            if (str.charAt(index) != ch) {
                return index;
            }
            index += 1;
        }
        return -1;
    }

    /**
     * Determines if an ascii character is a control character.
     * 
     * @param c a character
     * @return <code>true</code> if <code>0 &leq; c and c &leq;
     *         {@link #ASCII_SPACE_BYTE}</code>.
     */
    public static boolean isControl(final char c) {
        return 0 <= c && c < ASCII_SPACE_BYTE;
    }

    /**
     * Converts an array of objects to a delimited string representation.
     * <p>
     * <<<<<<< HEAD The stirng representation of each element is obtained via
     * <code>toString()</code>. If a element of the array is <code>null</code> it is
     * skiped. ======= The string representation of each element is obtained via
     * <code>toString()</code>. If a element of the array is <code>null</code> it is
     * skipped. >>>>>>> [FIX] Corrects an off-by-one bug on StringUtil.join()
     * 
     * @param objs the array of items to join
     * @param delimiter the text to place between each element in the array, cannot be
     *            <code>null</code>. To join without a delimiter, use an empty string.
     * @return the resulting string on <code>null</code> if items is <code>null</code>
     * @throws NullPointerException if the received delimiter is null.
     */
    public static String join(Collection<?> objs, String delimiter) {
        assert delimiter != null : "Delimiter should not be null";

        if (objs == null || objs.size() == 0) {
            return "";
        }

        StringBuffer buffer = new StringBuffer();
        for (Object obj : objs) {
            if (buffer.length() > 0) {
                buffer.append(delimiter);
            }
            buffer.append(obj);
        }
        return buffer.toString();
    }

    /**
     * Converts an array of integer objects to a delimited string representation.
     * <p>
     * 
     * @param ints the array of items to join
     * @param delimiter the text to place between each element in the array; cannot be
     *            <code>null</code>. To join without a delimiter, use an empty string.
     * @return the resulting string or <code>null</code> if items is <code>null</code>
     */
    public static String join(int[] ints, String delimiter) {
        assert delimiter != null : "Delimiter should not be null";

        if (ints == null || ints.length == 0) {
            return "";
        }

        final StringBuffer buffer = new StringBuffer();
        for (int obj : ints) {
            if (buffer.length() > 0) {
                buffer.append(delimiter);
            }
            buffer.append(Integer.toString(obj));
        }
        return buffer.toString();
    }

    /**
     * Converts an array of objects to a delimited string representation.
     * <p>
     * The string representation of each element is obtained via <code>toString()</code>.
     * If a element of the array is <code>null</code> it is skipped.
     * 
     * @param objs the array of items to join
     * @param delimiter the text to place between each element in the array, cannot be
     *            <code>null</code>. To join without a delimiter, use an empty string.
     * @return the resulting string or <code>null</code> if items is <code>null</code>
     */
    public static String join(final Object[] objs, final String delimiter) {
        assert delimiter != null : "Delimiter should not be null";

        if (objs == null) {
            return null;
        }

        final StringBuffer sb = new StringBuffer();

        for (int i = 0; i < objs.length; i++) {
            final Object o = objs[i];
            if (o != null) {
                if (sb.length() > 0) {
                    sb.append(delimiter);
                }

                sb.append(o);
            }
        }

        return sb.toString();
    }


    /**
     * Converts an array of shorts to a delimited string representation.
     * <p>
     * 
     * @param shorts the array of items to join
     * @param delimiter the text to place between each element in the array; cannot be
     *            <code>null</code>. To join without a delimiter, use an empty string.
     * @return the resulting string or <code>null</code> if items is <code>null</code>
     */
    public static String join(short[] shorts, String delimiter) {
        assert delimiter != null : "Delimiter should not be null";

        if (shorts == null || shorts.length == 0) {
            return "";
        }

        StringBuffer buffer = new StringBuffer();
        for (int obj : shorts) {
            if (buffer.length() > 0) {
                buffer.append(delimiter);
            }
            buffer.append(Integer.toString(obj));
        }
        return buffer.toString();
    }

    /**
     * Finds the index of the first character of a string that is different from a
     * specified character, searching from left to right.
     * 
     * @param str The string where the characters will be searched
     * @param ch the character that should not appear
     * @return a valid index of -1 if all chars are ch
     */
    public static int lastIndexNotOf(final String str, final char ch) {
        int index = str.length() - 1;
        while (index >= 0) {
            if (str.charAt(index) != ch) {
                return index;
            }
            index -= 1;
        }
        return -1;
    }

    /**
     * Prefixes the string input with enough copies of pad that until a specified length.
     * <p>
     * Usually, pad should be a single character.
     * 
     * @param input the input string
     * @param length the maximum length of the string
     * @param pad the pad string to be used
     * @return the padded string
     */
    public static String pad(final String input, final int length, final String pad) {
        final StringBuffer sb = new StringBuffer();
        final int padLength = length - input.length();
        while (sb.length() < padLength) {
            sb.append(pad);
        }
        sb.append(input);
        return sb.toString();
    }

    /**
     * Create a string with with a char repeated a number of times.
     * 
     * @param chr the char to be repeted
     * @param n the number of times that the string shound be repeated
     * @return a new string with filled with the specified char n times.
     */
    public static String repeat(final char chr, final int n) {
        if (n > 0) {
            final char[] buffer = new char[n];
            for (int i = 0; i < n; i++) {
                buffer[i] = chr;
            }

            return new String(buffer);
        } else {
            return "";
        }
    }

    /**
     * Create a string with a string repeated a number of times.
     * 
     * @param s the string to be repeated
     * @param n the times that the string should be repeated
     * @return a new string with the str string repeated a number of times
     */
    public static String repeat(final String s, final int n) {
        if (n > 0) {
            final StringBuffer strBuffer = new StringBuffer();
            int strCounter = 0;
            do {
                strBuffer.append(s);
                strCounter += 1;
            } while (strCounter < n);
            return strBuffer.toString();
        } else {
            return "";
        }
    }


    /**
     * Pads a string to the right with a filler character.
     *
     * @param s the string to be padded
     * @param filler the filler character
     * @param length the desired size of the string
     * @return the string padded with the specified character until the specified length
     */
    public static String rpad(String s, char filler, int length) {
        StringBuffer result = new StringBuffer(s);
        while (result.length() < length) {
            result.append(filler);
        }
        return result.toString();
    }

    /**
     * Tests two strings for equality when one or both of them can be <code>null</code>.
     * 
     * @param left the left hand string
     * @param right the right hand string
     * @return <code>true</code> if both left and right are <code>null</code>, returns
     *         <code>false</code> if one of them is <code>null</code> and the other is
     *         not, returns <code>left.equals(right)</code> is none of them is
     *         <code>null</code>
     */
    public static boolean safeEquals(final String left, final String right) {
        if (left == right) {
            return true;
        } else if (left != null && right == null || left == null) {
            return false;
        } else {
            return left.equals(right);
        }
    }

    /**
     * <<<<<<< HEAD <<<<<<< HEAD ======= Obtains the safe value of a atring when the
     * string is <code>null</code>. ======= Obtains the safe value of a string when the
     * string is <code>null</code>. >>>>>>> [ENH] Several documentation enhancements
     * introduced.
     * 
     * @param s the string to be converted that can be <code>null</code>
     * @param nullReplacement a default string value to replace when s is
     *            <code>null</code>. Cannot be <code>null</code>.
     * @return s or the value of nullReplacement if s is <code>null</code>
     */
    public static String safeString(final String s, final String nullReplacement) {
        assert nullReplacement != null : "Null replacement value cannot be null";

        return s == null ? nullReplacement : s;
    }

    /**
     * >>>>>>> [FIX] StringUtil.join() throws an NPE when delimiter is null. Altered
     * corresponding docs. Converts a string to an array of strings separated by a token.
     * <p>
     * Can be used to convert comma-separated tokens into an array. Used as a replacement
     * for <code>String.split(String)</code> which has a bug with DBCS.
     * 
     * @param string the initial comma-separated string
     * @param separator the separator characters
     * @return the array of string tokens
     */
    public static String[] split(final String string, final String separator) {
        if (string == null || string.trim().isEmpty()) {
            return new String[0];
        }

        final ArrayList<String> list = new ArrayList<String>();
        final StringTokenizer tokens = new StringTokenizer(string, separator);
        while (tokens.hasMoreTokens()) {
            String token = tokens.nextToken().trim();
            if (!token.isEmpty()) {
                list.add(token);
            }
        }

        if (list.isEmpty()) {
            return new String[0];
        } else {
            return list.toArray(new String[list.size()]);
        }
    }

    /**
     * Puts double quotes before and after the string.
     * <p>
     * Existing double quotes are escaped.
     * 
     * @param str the string to stringify
     * @return a new string with " before and after
     */
    public static String stringify(final String str) {
        final StringBuilder buffer = new StringBuilder();
        buffer.append(DOUBLE_QUOTE);
        for (char c : str.toCharArray()) {
            if (c == DOUBLE_QUOTE) {
                buffer.append('\\');
                buffer.append('"');
            } else {
                buffer.append(c);
            }
        }
        buffer.append(DOUBLE_QUOTE);
        return buffer.toString();
    }

    /**
     * Skips the first occurrence of token from a string.
     * 
     * @param str the string where the token is to be found.
     * @param token is the token to be skipped
     * @return a pointer to the character immediately after the token or the void. Returns
     *         the original string if the token is not found.
     */
    public static String stripPrefix(final String str, final java.lang.String token) {
        if (token.length() > 0 && str.startsWith(token)) {
            return str.substring(token.length());
        } else {
            return str;
        }
    }

    /**
     * Trims white spaces right and left from the string.
     * 
     * @param str the string to skip white spaces from
     * @return the pointer to a buffer with the string trimmed from spaces on both sizes.
     *         if there are no spaces to trim it returns the string itself.
     */
    public static String trim(final String str) {
        return str.trim();
    }

    /**
     * Trims a portion of a string based on a begin and end index.
     * 
     * @param s the string
     * @param start the start index of the trim operation
     * @param end the end of the trim operation
     * @return the trimmed string
     */
    public static String trim(final String s, final int start, final int end) {
        final String trimedFirst = s.substring(0, start);
        final String trimedSecond;
        if (end < s.length()) {
            trimedSecond = s.substring(end, s.length() - 1);
        } else {
            trimedSecond = "";
        }
        return trimedFirst + trimedSecond;
    }

    /**
     * Trims characters right and left from the string.
     * 
     * @param str the string to skip white spaces from
     * @param ch is the char to be trimmed
     * @return the the string trimmed from all occurrences of the ch character.
     */
    public static String trimChar(final String str, final char ch) {
        return trimCharRight(trimCharLeft(str, ch), ch);
    }

    /**
     * Skips all occurrences of ch from a string.
     * 
     * @param str the string to skip white spaces from
     * @param ch is the char to be trimmed
     * @return the first non-SPACE character or the original string if the character ch is
     *         not found
     */
    public static String trimCharLeft(final java.lang.String str, final char ch) {
        int noCharPosIdx = firstIndexNotOf(str, ch);
        if (noCharPosIdx > -1) {
            return str.substring(noCharPosIdx);
        } else {
            return str;
        }
    }

    /**
     * Trims characters right from the string.
     * 
     * @param str the string to skip white spaces from
     * @param ch is the character to be trimmed
     * @return the pointer to a buffer with the string trimmed from all occurrences of ch
     *         on the right. If ch is not found returns the original string.
     */
    public static String trimCharRight(final java.lang.String str, final char ch) {
        int noCharPosIdx = lastIndexNotOf(str, ch);
        if (noCharPosIdx > -1) {
            return str.substring(0, noCharPosIdx + 1);
        } else {
            return str;
        }
    }

    /**
     * Skips white spaces from the left of a string.
     * 
     * @param str the string to skip white spaces from
     * @return the first non-SPACE character of the string or the original string if the
     *         SPACE character is not found
     */
    public static String trimLeft(final String str) {
        return trimCharLeft(str, ' ');
    }

    /**
     * Trims white white spaces right from the string.
     * 
     * @param str the string to skip white spaces from
     * @return the pointer to a buffer with the string trimmed from spaces on the right.
     *         If there are no spaces to trim it returns the string itself.
     */
    public static String trimRight(final java.lang.String str) {
        return trimCharRight(str, ' ');
    }

    /**
     * Strips a string from double quotes.
     * <p>
     * Escaped doubles quotes in the middle of the string are converted appropriately.
     * 
     * @param str a string that may have leading and trailing double quotes
     * @return a string without leading and trailing double quotes.
     */
    public static String unstringify(final String str) {
        final StringBuilder buffer = new StringBuilder();
        final char[] chars = str.toCharArray();

        int i = 0;
        while (i < chars.length) {
            if (chars[i] == DOUBLE_QUOTE) {
                i++;
                continue;
            }

            final boolean isEscaped = chars[i] == '\\' && i + 1 < chars.length;

            if (isEscaped) {
                i++;
            }

            buffer.append(chars[i]);
            i++;
        }
        return buffer.toString();
    }

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
        final byte[] target = string.getBytes(UTF8);
        for (int i = 0; i < target.length; i++) {
            if (StringUtil.isControl((char) target[i])) {
                target[i] = (byte) invisible;
            }
        }
        return new String(target, UTF8);
    }

    /**
     * Prevents this class form being instantiated.
     */
    private StringUtil() {
    }

}
