package codebase;

/**
 * Utility class that encapsulates many common for binary operations.
 * <p>
 * This class is not part of the <tt>codebase.binary</tt> package because it is a general
 * purpose library class meant to be used by any code that must deal with binary
 * operations.
 */
public final class Binary {

    /**
     * Number of bits on a byte.
     */
    public static final int BIT_SIZE_OF_BYTE = 8;

    /**
     * Number of bits on a nibble.
     */
    public static final int BITS_SIZE_OF_NIBBLE = 4;

    /**
     * Number of bits in two bytes.
     */
    public static final int BIT_SIZE_OF_TWO_BYTES = 2 * BIT_SIZE_OF_BYTE;

    /**
     * Number of bits in a short.
     */
    public static final int BIT_SIZE_OF_SHORT = BIT_SIZE_OF_TWO_BYTES;

    /**
     * Number of bits in three bytes.
     */
    public static final int BIT_SIZE_OF_THREE_BYTES = 3 * BIT_SIZE_OF_BYTE;

    /**
     * Number of bits in four bytes.
     */
    public static final int BIT_SIZE_OF_INTEGER = 4 * BIT_SIZE_OF_BYTE;

    /**
     * The mask of the highest byte of an integer.
     */
    public static final int INT_HIGH_BYTE_MASK = 0xFF000000;

    /**
     * The mask of a byte from an integer. Used to simulate a fast coercion of integer to
     * byte.
     */
    public static final int INT_LOW_BYTE_MASK = 0x000000FF;

    /**
     * The mask for the low nibble of a byte.
     */
    public static final int INT_BYTE_LOW_NIBBLE_MASK = 0x0000000F;

    /**
     * The mask for the low high nibble of a byte.
     */
    public static final int INT_BYTE_HIGH_NIBBLE_MASK = 0x000000F0;

    /**
     * A mask for the least significant byte of a long.
     */
    public static final long LONG_LOW_BYTE_MASK = 0x00000000000000FFL;

    /**
     * A mask for the most significant byte of a long.
     */
    public static final long LONG_HIGH_BYTE_MASK = 0xFF00000000000000L;

    /**
     * The size of an integer in bytes.
     */
    public static final int SIZE_OF_INT = 4;

    /**
     * The size of a char in bytes.
     */
    public static final int SIZE_OF_CHAR = 4;

    /**
     * The size of a long in bytes.
     */
    public static final int SIZE_OF_LONG = 8;

    /**
     * Table of Hex digits.
     */
    private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f' };

    /**
     * A table that converts ASCII codes of characters to the corresponding hex values.
     * Only codes corresponding to characters in the range '0'-'9', 'A'-'F', 'A'-'F' have
     * translation all the remaining are mapped to zero.
     */
    private static final int[] HEX_VALUES;


    // CHECKSTYLE:OFF

    /* 
     * Checkstyle will complain about the constants below.
     */
    static {
        HEX_VALUES = new int[128];
        HEX_VALUES['0'] = 0;
        HEX_VALUES['1'] = 1;
        HEX_VALUES['2'] = 2;
        HEX_VALUES['3'] = 3;
        HEX_VALUES['4'] = 4;
        HEX_VALUES['5'] = 5;
        HEX_VALUES['6'] = 6;
        HEX_VALUES['7'] = 7;
        HEX_VALUES['8'] = 8;
        HEX_VALUES['9'] = 9;
        HEX_VALUES['A'] = 10;
        HEX_VALUES['B'] = 11;
        HEX_VALUES['C'] = 12;
        HEX_VALUES['D'] = 13;
        HEX_VALUES['E'] = 14;
        HEX_VALUES['F'] = 15;
        HEX_VALUES['a'] = 10;
        HEX_VALUES['b'] = 11;
        HEX_VALUES['c'] = 12;
        HEX_VALUES['d'] = 13;
        HEX_VALUES['e'] = 14;
        HEX_VALUES['f'] = 15;
    }
    // CHECKSTYLE:ON

    /**
     * Number of set bits per byte '1' bits per byte.
     */
    private static final byte[] SET_BITS_PER_BYTE = { 0, 1, 1, 2, 1, 2, 2, 3, 1, 2, 2, 3, 2, 3, 3,
            4, 1, 2, 2, 3, 2, 3, 3, 4, 2, 3, 3, 4, 3, 4, 4, 5, 1, 2, 2, 3, 2, 3, 3, 4, 2, 3, 3, 4,
            3, 4, 4, 5, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 1, 2, 2, 3, 2, 3, 3, 4, 2,
            3, 3, 4, 3, 4, 4, 5, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 2, 3, 3, 4, 3, 4,
            4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 5, 6, 6, 7, 1, 2, 2,
            3, 2, 3, 3, 4, 2, 3, 3, 4, 3, 4, 4, 5, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6,
            2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 5,
            6, 6, 7, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 3, 4, 4, 5, 4, 5, 5, 6, 4, 5,
            5, 6, 5, 6, 6, 7, 3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 5, 6, 6, 7, 4, 5, 5, 6, 5, 6, 6,
            7, 5, 6, 6, 7, 6, 7, 7, 8 };

    /**
     * Decodes an array of integers into an array of bytes.
     * 
     * @param input an array of integers to convert
     * @param length the number of bytes to decode
     * @return an array of bytes with the decoding
     */
    public static byte[] decodeIntegersToBytes(final int[] input, final int length) {
        final int remByteCount = length % SIZE_OF_INT;
        final int correctedLength;
        if (remByteCount != 0) {
            correctedLength = length + (SIZE_OF_INT - remByteCount);
        } else {
            correctedLength = length;
        }
        final byte[] output = new byte[correctedLength];

        int i = 0;
        for (int j = 0; j < correctedLength; i++, j += SIZE_OF_INT) {
            toByteArray(input[i], output, j);
        }

        return output;
    }

    /**
     * Converts a byte array into an array of integers.
     * <p>
     * Assumes that the input length has the at least offset + length*4 bytes
     * 
     * @param input the input buffer
     * @param offset where to start in the input buffer
     * @param length the number integers to encode
     * @return a byte array containg the inbteger encoding of the given bytes
     */
    public static int[] encodeBytesToIntegers(final byte[] input, final int offset, final int length) {
        // Byte displacement constants 
        final int firstByteOffset = 0;
        final int secondByteOffset = 1;
        final int thirdByteOffset = 2;
        final int fourthByteOffset = 3;

        final int remByteCount = length % SIZE_OF_INT;
        final int correctedLength;
        if (remByteCount != 0) {
            correctedLength = length + (SIZE_OF_INT - remByteCount);
        } else {
            correctedLength = length;
        }
        final int numIntegers = (correctedLength / SIZE_OF_INT);
        final int[] output = new int[numIntegers];

        byte b0 = 0;
        byte b1 = 0;
        byte b2 = 0;
        byte b3 = 0;

        // Treats all but the last integer
        for (int i = 0, j = 0; j < length; i++, j += SIZE_OF_INT) {
            b0 = input[offset + j + firstByteOffset];
            b1 = input[offset + j + secondByteOffset];
            b2 = input[offset + j + thirdByteOffset];
            b3 = input[offset + j + fourthByteOffset];
            output[i] = toInteger(b0, b1, b2, b3);
        }

        if (remByteCount > 0) {
            // INV: remByteCount < 4
            final int lastOffset = length - remByteCount;
            b0 = 0;
            b1 = 0;
            b2 = 0;
            b3 = 0;

            if (remByteCount > firstByteOffset) {
                b0 = input[offset + lastOffset + firstByteOffset];
            }

            if (remByteCount > secondByteOffset) {
                b1 = input[offset + lastOffset + secondByteOffset];
            }

            if (remByteCount > thirdByteOffset) {
                b2 = input[offset + lastOffset + thirdByteOffset];
            }

            output[numIntegers - 1] = toInteger(b0, b1, b2, b3);
        }
        return output;
    }

    /**
     * Computes the byte represented by two ASCII characters taken from a String.
     * 
     * @param left the most significant character (high nibble), must be in the range
     *            '0'-'9' 'A'-'F'
     * @param right the least significant character (low nibble), must be in the range
     *            '0'-'9' 'A'-'F'
     * @return the corresponding byte
     */
    public static byte fromHex(final char left, final char right) {
        return toByte(HEX_VALUES[left], HEX_VALUES[right]);
    }

    /**
     * Extracts the higher byte of a two-byte integer.
     * 
     * @param value the integer with two bytes
     * @return (v >> 8) & 0xFF
     */
    public static int getHighByte(final int value) {
        return (value >> BIT_SIZE_OF_BYTE) & INT_LOW_BYTE_MASK;
    }

    /**
     * Extracts the higher nibble of a byte.
     * 
     * @param value the byte value as an int
     * @return the high nibble of the least significant byte of the integer.
     */
    public static int getHighNibble(final int value) {
        return (value >> BITS_SIZE_OF_NIBBLE) & INT_BYTE_LOW_NIBBLE_MASK;
    }

    /**
     * Extracts the lower byte of a two-byte integer.
     * 
     * @param value the integer with two bytes
     * @return v & 0xFF
     */
    public static int getLowerByte(final int value) {
        return value & INT_LOW_BYTE_MASK;
    }

    /**
     * Extracts the lower nibble of a byte.
     * 
     * @param value the a byte value as an int
     * @return the lower nibble of the least significant byte of the integer
     */
    public static int getLowNibble(final int value) {
        return value & INT_BYTE_LOW_NIBBLE_MASK;
    }

    /**
     * Returns the number of set bits on a byte.
     * 
     * @param b the byte, must be <code>0 <= b <= 0xFF</code>
     * @return the number of <code>1</code> bits
     */
    public static int getSetBits(final int b) {
        return SET_BITS_PER_BYTE[b];
    }

    /**
     * Sets the higher nibble of the least significant byte.
     * 
     * @param value the a byte value as an int
     * @param nibble the value to be set. Only the leaset significant 4 bits are used,
     *            hence <code> 0x00 <= nibble <= 0x0F </code>.
     * @return the high nibble of the least significant byte of the integer
     */
    public static int setHighNibble(final int value, final int nibble) {
        return (value & ~INT_BYTE_HIGH_NIBBLE_MASK)
                | ((nibble & INT_BYTE_LOW_NIBBLE_MASK) << BITS_SIZE_OF_NIBBLE);
    }

    /**
     * Sets the lower nibble of the least significant byte.
     * 
     * @param value the a byte value as an int
     * @param nibble the a byte value as an int param nibble the value to be set. Only the
     *            leaset significant 4 bits are used, hence
     *            <code> 0x00 <= nibble <= 0x0F </code>.
     * @return the lower nibble of the least significant byte of the integer
     */
    public static int setLowNibble(final int value, final int nibble) {
        return (value & ~INT_BYTE_LOW_NIBBLE_MASK) | (nibble & INT_BYTE_LOW_NIBBLE_MASK);
    }

    /**
     * Converts a "double" value between endian systems.
     * 
     * @param value value to convert
     * @return the converted value
     */
    public static double swapDouble(final double value) {
        return Double.longBitsToDouble(swapLong(Double.doubleToLongBits(value)));
    }

    /**
     * Converts a "float" value between endian systems.
     * 
     * @param value value to convert
     * @return the converted value
     */
    public static float swapFloat(final float value) {
        return Float.intBitsToFloat(swapInteger(Float.floatToIntBits(value)));
    }

    /**
     * Converts a "int" value between endian systems.
     * 
     * @param value value to convert
     * @return the converted value
     */
    public static int swapInteger(final int value) {
        return (((value >> 0) & INT_LOW_BYTE_MASK) << BIT_SIZE_OF_THREE_BYTES)
                + (((value >> BIT_SIZE_OF_BYTE) & INT_LOW_BYTE_MASK) << BIT_SIZE_OF_SHORT)
                + (((value >> BIT_SIZE_OF_SHORT) & INT_LOW_BYTE_MASK) << BIT_SIZE_OF_BYTE)
                + (((value >> BIT_SIZE_OF_THREE_BYTES) & INT_LOW_BYTE_MASK) << 0);
    }

    /**
     * Converts a "long" value between endian systems.
     * 
     * @param value value to convert
     * @return the converted value
     */
    public static long swapLong(final long value) {
        // CHECKSTYLE:OFF

        /*
         *  The values below are the bit positions of each byte on a long.
         */
        return (((value >> 0) & INT_LOW_BYTE_MASK) << 56)
                + (((value >> BIT_SIZE_OF_BYTE) & INT_LOW_BYTE_MASK) << 48)
                + (((value >> BIT_SIZE_OF_SHORT) & INT_LOW_BYTE_MASK) << 40)
                + (((value >> BIT_SIZE_OF_THREE_BYTES) & INT_LOW_BYTE_MASK) << BIT_SIZE_OF_INTEGER)
                + (((value >> BIT_SIZE_OF_INTEGER) & INT_LOW_BYTE_MASK) << BIT_SIZE_OF_THREE_BYTES)
                + (((value >> 40) & INT_LOW_BYTE_MASK) << BIT_SIZE_OF_SHORT)
                + (((value >> 48) & INT_LOW_BYTE_MASK) << BIT_SIZE_OF_BYTE)
                + (((value >> 56) & INT_LOW_BYTE_MASK) << 0);
        // CHECKSTYLE:ON
    }

    /**
     * Swaps the two nibbles of the least significant byte.
     * 
     * @param value the value to swap nibbles
     * @return a byte with the nibbles swapped
     */
    public static int swapNibbles(final int value) {
        return ((value >> BITS_SIZE_OF_NIBBLE) & INT_BYTE_LOW_NIBBLE_MASK)
                | ((value << BITS_SIZE_OF_NIBBLE) & INT_BYTE_HIGH_NIBBLE_MASK);
    }

    /**
     * Converts a "short" value between endian systems.
     * 
     * @param value value to convert
     * @return the converted value
     */
    public static short swapShort(final short value) {
        return (short) ((((value >> 0) & INT_LOW_BYTE_MASK) << BIT_SIZE_OF_BYTE) 
                + (((value >> BIT_SIZE_OF_BYTE) & INT_LOW_BYTE_MASK) << 0));        
    }

    /**
     * Composes a byte from two nibbles.
     * 
     * @param highNibble the higher nibble
     * @param lowNibble the lower nibble
     * @return a byte composed of the specified nibbles
     */
    public static byte toByte(final int highNibble, final int lowNibble) {
        final int result = ((highNibble & INT_BYTE_LOW_NIBBLE_MASK) << BITS_SIZE_OF_NIBBLE)
                | (lowNibble & INT_BYTE_LOW_NIBBLE_MASK);

        return (byte) result;
    }

    /**
     * Converts an integer to a byte array.
     * <p>
     * Assumes that output.length > offset + 4
     * 
     * @param i the integer to be converted
     * @param output the output buffer
     * @param offset the offset of the first byte to be written
     */
    public static void toByteArray(final int i, final byte[] output, final int offset) {
        final int firstByteOffset = 0;
        final int secondByteOffset = 1;
        final int thirdByteOffset = 2;
        final int fourthByteOffset = 3;
        output[offset + firstByteOffset] = (byte) (i & INT_LOW_BYTE_MASK);
        output[offset + secondByteOffset] = (byte) ((i >> secondByteOffset * BIT_SIZE_OF_BYTE) & INT_LOW_BYTE_MASK);
        output[offset + thirdByteOffset] = (byte) ((i >> thirdByteOffset * BIT_SIZE_OF_BYTE) & INT_LOW_BYTE_MASK);
        output[offset + fourthByteOffset] = (byte) ((i >> fourthByteOffset * BIT_SIZE_OF_BYTE) & INT_LOW_BYTE_MASK);
    }

    /**
     * Encodes two bytes into a char.
     * 
     * @param b0 the least significant byte
     * @param b1 the most significant byte
     * @return a char where b0 is the least significant byte and b1 is the most
     *         significant byte.
     */
    public static char toChar(final byte b0, final byte b1) {
        final char result = (char) (((int) (b0 & Binary.INT_LOW_BYTE_MASK))
                | (((int) (b1 & Binary.INT_LOW_BYTE_MASK)) << BIT_SIZE_OF_BYTE));
        return result;
    }

    /**
     * Computes the hex representation of a byte.
     * 
     * @param b the byte to be treated
     * @return a String with size 2, containing the hex representation of the byte.
     */
    public static String toHex(final byte b) {
        final int unsignedByte = b & INT_LOW_BYTE_MASK;
        final char[] byteChars = new char[] {
                HEX_DIGITS[(unsignedByte & INT_BYTE_HIGH_NIBBLE_MASK) >> BITS_SIZE_OF_NIBBLE],
                HEX_DIGITS[unsignedByte & INT_BYTE_LOW_NIBBLE_MASK] };
        return new String(byteChars);

    }

    /**
     * Computes the hex representation of a char.
     * <p>
     * If the character value is between 0 and 255 (0xFF) it converts it as a byte else it
     * converts it as a short.
     * 
     * @param c is the char to be treated
     * @return a String with size 2 or 4, containing the hex representation of the char
     *         value.
     */
    public static String toHex(final char c) {
        if (c >= 0 && c <= INT_LOW_BYTE_MASK) {
            return toHex((byte) c);
        } else {
            final char[] charChars = new char[] {
                    HEX_DIGITS[(c >> BIT_SIZE_OF_SHORT - BITS_SIZE_OF_NIBBLE)
                            & INT_BYTE_LOW_NIBBLE_MASK],
                    HEX_DIGITS[(c >> BIT_SIZE_OF_BYTE) & INT_BYTE_LOW_NIBBLE_MASK],
                    HEX_DIGITS[(c >> BITS_SIZE_OF_NIBBLE) & INT_BYTE_LOW_NIBBLE_MASK],
                    HEX_DIGITS[c & INT_BYTE_LOW_NIBBLE_MASK] };

            return new String(charChars);
        }
    }

    /**
     * Computes the hex representation of an integer.
     * 
     * @param i the integer to be treated
     * @return a String with size 8, containing the hex representation of the integer
     *         value.
     */
    public static String toHex(final int i) {
        final char[] intChars = new char[] {
                HEX_DIGITS[(i >> BIT_SIZE_OF_INTEGER - BITS_SIZE_OF_NIBBLE)
                        & INT_BYTE_LOW_NIBBLE_MASK],
                HEX_DIGITS[(i >> BIT_SIZE_OF_THREE_BYTES) & INT_BYTE_LOW_NIBBLE_MASK],
                HEX_DIGITS[(i >> BIT_SIZE_OF_THREE_BYTES - BITS_SIZE_OF_NIBBLE)
                        & INT_BYTE_LOW_NIBBLE_MASK],
                HEX_DIGITS[(i >> BIT_SIZE_OF_SHORT) & INT_BYTE_LOW_NIBBLE_MASK],
                HEX_DIGITS[(i >> BIT_SIZE_OF_SHORT - BITS_SIZE_OF_NIBBLE)
                        & INT_BYTE_LOW_NIBBLE_MASK],
                HEX_DIGITS[(i >> BIT_SIZE_OF_BYTE) & INT_BYTE_LOW_NIBBLE_MASK],
                HEX_DIGITS[(i >> BITS_SIZE_OF_NIBBLE) & INT_BYTE_LOW_NIBBLE_MASK],
                HEX_DIGITS[i & INT_BYTE_LOW_NIBBLE_MASK] };

        return new String(intChars);
    }

    /**
     * Converts an array of bytes into an hex string.
     * 
     * @param buffer the byte buffer
     * @return a string with pairs of hex digits corresponding to the byte buffer
     */
    public static String toHexString(final byte[] buffer) {
        final char[] charBuffer = new char[buffer.length * 2];

        for (int i = 0; i < buffer.length; ++i) {
            charBuffer[i * 2] = HEX_DIGITS[(buffer[i] >> BITS_SIZE_OF_NIBBLE)
                    & INT_BYTE_LOW_NIBBLE_MASK];
            charBuffer[i * 2 + 1] = HEX_DIGITS[buffer[i] & INT_BYTE_LOW_NIBBLE_MASK];
        }

        return new String(charBuffer);
    }

    /**
     * Encodes four bytes to integer.
     * <p>
     * Usage example:
     * 
     * <pre>
     * <code>
     * 
     * 0x78563412 == Binary.toInteger((byte) 0x12, (byte) 0x34, (byte) 0x56,
     *     (byte) 0x78)
     * 
     * </code>
     * </pre>
     * 
     * @param b0 the first byte
     * @param b1 the second byte
     * @param b2 the third byte
     * @param b3 the fourth byte
     * @return an integer where b0 is the least significant byte and b3 is the most
     *         significant byte
     */
    public static int toInteger(final byte b0, final byte b1, final byte b2, final byte b3) {
        final int result = ((int) (b0 & Binary.INT_LOW_BYTE_MASK))
                | (((int) (b1 & Binary.INT_LOW_BYTE_MASK)) << BIT_SIZE_OF_BYTE)
                | (((int) (b2 & Binary.INT_LOW_BYTE_MASK)) << BIT_SIZE_OF_SHORT)
                | (((int) (b3 & Binary.INT_LOW_BYTE_MASK)) << BIT_SIZE_OF_THREE_BYTES);
        return result;
    }

    /**
     * Prevent instantiation.
     */
    private Binary() {
    }
}
