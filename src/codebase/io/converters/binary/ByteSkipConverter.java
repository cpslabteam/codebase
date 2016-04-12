/*
 * Created on 6/Mai/2005
 */
package codebase.io.converters.binary;


import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import codebase.ArrayUtil;
import codebase.StringUtil;
import codebase.io.converters.AbstractFixedSizeConverter;

/**
 * A converter for that skips a predefined number of bytes when reading.
 * <p>
 * This converter is useful for reading an writing records with fixed size. It returns
 * <code>null</code> when reading.
 * <p>
 * The write operation will write out a predefined byte array specified in the
 * constructor.
 */
public class ByteSkipConverter extends
        AbstractFixedSizeConverter {

    /**
     * Caches the number of ASCII characters to read for optimization purposes. Thus is
     * avoids to call <code>getSerializedSize</code> repeatedly.
     */
    private final int length;

    /**
     * The array of bytes to be written.
     */
    private final byte[] writeBytes;

    /**
     * Creates a new skip converter of a predefined size.
     * 
     * @param size the number of bytes to skip when reading
     * @param bytes the bytes to write out
     * @throws IllegalArgumentException if the size is not positive
     * @throws IllegalArgumentException if the bytes array is not assigned
     * @throws IllegalArgumentException if the length of the bytes buffer does not match
     *             the size
     */
    public ByteSkipConverter(final int size, final byte[] bytes) {
        super(size);
        if (size < 1) {
            throw new IllegalArgumentException("The size must be positive");
        }
        length = size;

        if (bytes == null) {
            throw new IllegalArgumentException(
                    "The array of bytes must be assigned");
        }

        if (size != bytes.length) {
            throw new IllegalArgumentException(
                    "The size must match the number of bytes");
        }

        writeBytes = bytes.clone();
    }

    /**
     * Builds a new fixed size skiper converter that writes out spaces.
     * 
     * @param size number of bytes to skip
     * @throws IllegalArgumentException if size is not positive
     */
    public ByteSkipConverter(final int size) {
        this(size, ArrayUtil.replicate(StringUtil.ASCII_SPACE_BYTE, size));
    }

    /**
     * Skips the specified number of bytes.
     * <p>
     * 
     * @param dataInput the stream to skype the <tt>byte</tt> value from.
     * @return <code>null</code>
     * @throws IOException if an exception occurs while skipping the bytes form the data
     *             input
     */
    public final Object read(final DataInput dataInput) throws IOException {
        if (dataInput.skipBytes(length) > 0) {
            // ignore
            return null;
        }
        
        return null;
    }

    /**
     * Writes out the byte array.
     * 
     * @param dataOutput where to rite the data
     * @param object ignored
     * @throws IOException if an exception occurs while skipping the bytes form the data
     *             input
     */
    public final void write(final DataOutput dataOutput, final Object object) throws IOException {
        dataOutput.write(writeBytes);
    }
}
