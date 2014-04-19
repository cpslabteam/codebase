/*
 * Created on 6/Mai/2005
 */
package codebase.io.converters.display;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import codebase.io.converters.AbstractFixedSizeConverter;

/**
 * A converter for strings with fixed size.
 * <p>
 * Strings handled by this converter can be ASCII or EBCDIC as long as each character is
 * only one byte. represented as an array of ASCII characters.
 * <p>
 * The strings are only delimited by the size specified in the constructor. When reading,
 * only a fixed number of characters are considered. No end of string marker is sought.
 * When writing only a predefined number of characters are written out. No end of line
 * marker is written. If the String is smaller that the fixed sized, then it is right
 * padded with space characters.
 */
public class FixedSizeStringConverter extends
        AbstractFixedSizeConverter {

    /**
     * The buffer used to read in the data.
     */
    private final byte[] readBuffer;

    /**
     * Caches the number of characters to read for optimization purposes.
     */
    private final int length;

    /**
     * The byte to be used to pad the string.
     */
    private final byte padByte;

    /**
     * Builds a new fixed size string converter.
     * 
     * @param size of the string
     * @param pad the byte to pad the string to fill it up to the specified size
     * @throws IllegalArgumentException if size is not positive
     */
    public FixedSizeStringConverter(final int size, final byte pad) {
        super(size);
        if (size < 1) {
            throw new IllegalArgumentException("The size must be positive");
        }
        readBuffer = new byte[size];
        padByte = pad;
        length = size;
    }

    /**
     * Creates a string object from an array of bytes.
     * <p>
     * Descending classes can override this method to provide a different encoding.
     * 
     * @param readBytes the array of bytes
     * @return a string with the default encoding
     */
    protected String createString(final byte[] readBytes) {
        return new String(readBytes);
    }

    /**
     * Returns a new string object from the bytes read.
     * <p>
     * If the input reader is unable to provide the adequate number of ASCII characters,
     * the string object will not be read
     * 
     * @param dataInput the data input to read the data input from
     * @throws IOException if an exception occurs while reading the string
     * @return a string read from the data input
     */
    public synchronized Object read(final DataInput dataInput) throws IOException {
        dataInput.readFully(readBuffer, 0, length);
        return createString(readBuffer);
    }

    /**
     * Writes a string with a fixed sized to the output.
     * <p>
     * The first bytes are the length of the string.
     * 
     * @param dataOutput the data output object to write to
     * @param object the string object to write
     * @throws IOException if an exception occurs while reading the string
     */
    public void write(final DataOutput dataOutput, final Object object) throws IOException {
        assert object instanceof String;
        final String input = (String) object;
        if (input.length() > length) {
            dataOutput.writeBytes(input.substring(0, length));
        } else {
            dataOutput.writeBytes(input);
            final int remainingBytes = length - input.length();
            for (int i = 0; i < remainingBytes; i++) {
                dataOutput.writeByte(padByte);
            }
        }
    }
}
