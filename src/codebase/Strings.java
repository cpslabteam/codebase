package codebase;

import java.util.ArrayList;
import java.util.StringTokenizer;

/*
 * Created on 7/Out/2004 Needs review
 */

/**
 * String utilities.
 * <p>
 * The underlying idea is to put here string functions that are commonly used in
 * the code to avoid that each project/person implements its own version. These
 * functions are also throughly tested.
 * 
 * @author Paulo Carreira; André Gonçalves
 */
public final class Strings {

    /**
     * Predefined <code>SPACE</code> character constant.
     */
    public static final char SPACE = ' ';

    /**
     * Predefined <code>SPACE</code> string constant.
     */
    public static final String SPACE_STRING = " ";

    /**
     * Predefined empty string constant.
     */
    public static final String EMPTY_STRING = "";

    /**
     * Predefined Carriage Return constant.
     */
    public static final char CR = '\n';

    /**
     * Predefined Line Feed constant.
     */
    public static final char LF = '\r';

    /**
     * Predefined Tab constant.
     */
    public static final char TAB = '\t';

    /**
     * String representation of a double-comma.
     */
    public static final String DOUBLE_QUOTE = "\"";

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
    public static final String UTF8_ENCODING = "UTF-8";

    /**
     * Short name descriptions of ascii invisible characters.
     */
    private static final String[] SHORT_CHAR_NAMES = { "NUL", "SOH", "STX", "ETX", "EOT",
            "ENQ", "ACK", "BEL", "BS", "TAB", "LF", "VT", "FF", "CR", "SO", "SI", "DLE",
            "XON", "DC2", "XOFF", "DC4", "NAK", "SYN", "ETB", "CAN", "EM ", "SUB" };

    /**
     * Long name descriptions of ascii invisible characters.
     */
    private static final String[] LONG_CHAR_NAMES = { "Null character",
            "Start of Header", "Start of Text", "End of Text", "End of Transmission",
            "Enquiry", "Acknowledgment", "Bell", "Backspace", "Tab", "Line feed",
            "Vertical Tab", "Form feed", "Carriage Return", "Shift Out", "Shift In",
            "Data Link Escape", "Device Control 1 (oft. XON)", "Device Control 2",
            "Device Control 3 (oft. XOFF)", "Device Control 4",
            "Negative Acknowledgement", "Synchronous Idle", "End of Trans. Block",
            "Cancel", "End of Medium", "Substitute" };

    /**
     * Avoid this class form being instantiated.
     */
    private Strings() {
    }

    /**
     * The returns the index of the first character of the a string that is
     * different from a specified character.
     * 
     * @param str The string where the characters will be searched
     * @param ch the character that should not appear
     * @return a valid index of <code>-1</code> if all the characters have
     *         value of chars are ch
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
     * Converts an array of objects to a delimited representation.
     * <p>
     * The stirng representation of each element is obtained via
     * <code>toString()</code>. If a element of the array is
     * <code>null</code> it is skiped.
     * 
     * @param items the array of items to join
     * @param delimiter the text to place between each element in the array,
     *            cannot be <code>null</code>
     * @return the resulting string on <code>null</code> if items is
     *         <code>null</code>
     */
    public static String join(final Object[] items, final String delimiter) {
        if (items == null) {
            return null;
        }

        final int length = items.length;
        String result = EMPTY_STRING;

        if (length > 0) {
            final StringBuffer sb = new StringBuffer();

            for (int i = 0; i < length - 1; i++) {
                final Object o = items[i];
                if (o != null) {
                    if (sb.length() > 0) {
                        sb.append(delimiter);
                    }

                    sb.append(o);
                }
            }

            result = sb.toString();
        }

        return result;
    }

    /**
     * Determines if an ascii character is a control character.
     * 
     * @param c a character
     * @return <code>true</code> if
     *         <code>0&leq;c and c<{@link #ASCII_SPACE_BYTE}</code>.
     */
    public static boolean isControl(final char c) {
        return (0 <= c) && (c < ASCII_SPACE_BYTE);
    }

    /**
     * Checks if a string is a prefix of another string.
     * 
     * @param prefix to be found.
     * @param str the string where the prefix to be found.
     * @return <code>true</code> if the string prefix is a prefix of string.
     *         If prefix is empty it also return <code>true</code>
     */
    public static boolean startsWith(final String prefix, final String str) {
        return str.startsWith(prefix);
    }

    /**
     * Finds the index of the first character of a string that is different from
     * a specified character, searching from left to right.
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
     * Prefixes the string input with enough copies of pad that it has length
     * equal to length.
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
     * Create a string with a string repeated a number of times.
     * 
     * @param str the string to be repeted
     * @param n the times that the string shound be repeated
     * @return a new string with the str string repeated a number of times
     */
    public static String repeat(final String str, final int n) {
        if (n > 0) {
            final StringBuffer strBuffer = new StringBuffer(EMPTY_STRING);
            int strCounter = 0;
            do {
                strBuffer.append(str);
                strCounter += 1;
            } while (strCounter < n);
            return strBuffer.toString();
        }
        else {
            return EMPTY_STRING;
        }
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
        }
        else {
            return EMPTY_STRING;
        }
    }

    /**
     * Trims white white spaces right from the string.
     * 
     * @param str the string to skip white spaces from
     * @return the pointer to a buffer with the string trimmed from spaces on
     *         the right. If there are no spaces to trim it returns the string
     *         itself.
     */
    public static String trimRight(final java.lang.String str) {
        return trimCharRight(str, SPACE);
    }

    /**
     * Trims characters right from the string.
     * 
     * @param str the string to skip white spaces from
     * @param ch is the character to be trimed
     * @return the pointer to a buffer with the string trimmed from all
     *         occourences of ch on the right. If ch is not found returns the
     *         original string.
     */
    public static String trimCharRight(final java.lang.String str, final char ch) {
        int noCharPosIdx = lastIndexNotOf(str, ch);
        if (noCharPosIdx > -1) {
            return str.substring(0, noCharPosIdx + 1);
        }
        else {
            return str;
        }
    }

    /**
     * Skips white spaces from the left of a string.
     * 
     * @param str the string to skip white spaces from
     * @return the first non-SPACE character of the string or the original
     *         string if the SPACE character is not found
     */
    public static String trimLeft(final String str) {
        return trimCharLeft(str, SPACE);
    }

    /**
     * Skips all occourences of ch from a string.
     * 
     * @param str the string to skip white spaces from
     * @param ch is the char to be trimmed
     * @return the first non-SPACE character or the original string if the
     *         character ch is not found
     */
    public static String trimCharLeft(final java.lang.String str, final char ch) {
        int noCharPosIdx = firstIndexNotOf(str, ch);
        if (noCharPosIdx > -1) {
            return str.substring(noCharPosIdx);
        }
        else {
            return str;
        }
    }

    /**
     * Skips the first occourence of token from a string.
     * 
     * @param str the string where the toke is to be found.
     * @param token is the token to be skipped
     * @return a pointer to the character immediatly after the token or the
     *         void. Returns the original string if the token is not found.
     */
    public static String stripPrefix(final String str, final java.lang.String token) {
        if (token.length() > 0 && str.startsWith(token)) {
            return str.substring(token.length());
        }
        else {
            return str;
        }
    }

    /**
     * Puts " before and after the string.
     * 
     * @param str the string to stringify
     * @return a new string with " before and after
     */
    public static String stringify(final String str) {
        return DOUBLE_QUOTE + str + DOUBLE_QUOTE;
    }

    /**
     * Trims white spaces right and left from the string.
     * 
     * @param str the string to skip white spaces from
     * @return the pointer to a buffer with the string trimmed from spaces on
     *         both sizes. if there are no spaces to trim it returns the string
     *         itself.
     */
    public static String trim(final String str) {
        return str.trim();
    }

    /**
     * Trims characters right and left from the string.
     * 
     * @param str the string to skip white spaces from
     * @param ch is the char to be trimmed
     * @return the the string trimmed from all occourences of the ch character.
     */
    public static String trimChar(final String str, final char ch) {
        return trimCharRight(trimCharLeft(str, ch), ch);
    }

    /**
     * Converts a string to an array of strings sepeared by a token.
     * <p>
     * Can be used to convert comma-separated tokens into an array. Used as a
     * replacement for <code>String.split(String)</code> which has a bug with
     * DBCS.
     * 
     * @param string the initial comma-separated string
     * @param separator the separator characters
     * @return the array of string tokens
     */
    public static String[] split(final String string, final String separator) {
        if (string == null || string.trim().equals("")) {
            return new String[0];
        }

        ArrayList list = new ArrayList();
        StringTokenizer tokens = new StringTokenizer(string, separator);
        while (tokens.hasMoreTokens()) {
            String token = tokens.nextToken().trim();
            if (!token.equals("")) {
                list.add(token);
            }
        }

        if (list.isEmpty()) {
            return new String[0];
        }
        else {
            return (String[]) list.toArray(new String[list.size()]);
        }
    }

    /**
     * Compacts a string for displaying.
     * <p>
     * This function is meant to be used to avoid the user to be overwelmed by
     * very large size dumps. To that end it gets the header of the string and
     * the trailer that serve as a summary of the string.
     * <p>
     * If the original string is smaller that the specified size, the original
     * string is returned.
     * 
     * @param str the original string
     * @param size the maximum size for display. The size must be greater than
     *            5. If the specified size is smaller than 5, it is ignored.
     * @return a string of the form <code>xxxxxx ... xxxxxx</code> with atmost
     *         <code>size</code> characters.
     */
    public static String compactFormat(final String str, final int size) {
        final String ellipsis = new String("...");
        final int lenEllipsis = ellipsis.length();
        final int len = str.length();
        if ((size < ellipsis.length() + 2) || (len <= size)) {
            return str;
        }
        else {
            final int headerSize = (size - lenEllipsis) / 2;
            final int trailerSize;
            if ((2 * (headerSize + 1) + lenEllipsis) <= size + 1) {
                trailerSize = headerSize + 1;
            }
            else {
                trailerSize = headerSize;
            }
            final String result = str.substring(0, headerSize) + ellipsis
                    + str.substring(len - (trailerSize - 1), len);
            // ASSERT: result.length = size
            return result;
        }
    }

    /**
     * Retruns the compact name of a char.
     * 
     * @param c is the character
     * @return a name like <code>TAB</code>, <code>EOT</code> or
     *         <code>CR</code> if the character is a special chatarcter.
     *         Returns the string representation otherwise.
     */
    public static String charNameShort(final char c) {
        if (isControl(c)) {
            return SHORT_CHAR_NAMES[c];
        }
        else {
            return Character.toString(c);
        }
    }

    /**
     * Returns the complete name of a char.
     * 
     * @param c is the character
     * @return a name like <code>Carriage Return</code> if the character is a
     *         special charcter. Returns the string representation otherwise.
     */
    public static String charNameLong(final char c) {
        if (isControl(c)) {
            return LONG_CHAR_NAMES[c];
        }
        else {
            return Character.toString(c);
        }
    }

    /**
     * Tests two strings for equality when one or both of them can be
     * <code>null</code>
     * 
     * @param left the left hand string
     * @param right the right hand string
     * @return <code>true</code> if both left and right are <code>null</code>,
     *         returns <code>false</code> if one of them is <code>null</code>
     *         and the other is not, returns <code>left.equals(right)</code> is none of them
     *         is <code>null</code>
     */
    public static boolean safeStrEquals(final String left, final String right) {
        if (left == right) {
            return true;
        }
        else if ((left != null && right == null) || (left == null && right != null)) {
            return false;
        }
        else {
            return left.equals(right);
        }
    }
}
