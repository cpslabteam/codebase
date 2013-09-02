package codebase.streams;

import java.io.IOException;
import java.io.InputStream;

import codebase.Binary;


/**
 * An input stream that returns bytes from a fixed buffer.
 * <p>
 * The buffer is given as an initializer to the constructor and stored internally. When
 * {@link #peekNextByte()} is called, a byte is taken from the buffer and returned. An
 * internal pointer is incremented and then wrapped when it gets to the end of the buffer.
 */
public class ConstantInputStream extends
        InputStream {

    private static final String DEFAULT_STRING_ENCODING = "UTF-8";

    /**
     * The constant byte buffer.
     */
    private final byte[] buffer;

    /**
     * Pointer to the current byte in the array.
     */
    private int ptr = 0;

    /**
     * Constructs a constant input from an array of bytes.
     * 
     * @param initializer the array of bytes that will be cycled
     * @throws IllegalArgumentException if the initializer is <code>null</code>
     * @throws IllegalArgumentException if the initializer does not have at least one byte
     */
    public ConstantInputStream(final byte[] initializer) {
        if (initializer == null) {
            throw new IllegalArgumentException("Initializer must be assigned");
        }
        if (initializer.length < 1) {
            throw new IllegalArgumentException("Initializer must have at least one element");
        }
        buffer = initializer.clone();
    }

    /**
     * Constructs a constant input stream from a string using the standard character to
     * byte mapping.
     * 
     * @param initializer the string will be used as byte constant
     * @throws IllegalArgumentException if the initializer is <code>null</code>
     * @throws IllegalArgumentException if the initializer is empty
     */
    public ConstantInputStream(final String initializer) {
        if (initializer == null) {
            throw new IllegalArgumentException("Initializer must be assigned");
        }
        if (initializer.length() < 1) {
            throw new IllegalArgumentException("Initializer canot be void");
        }
        try {
            buffer = initializer.getBytes(DEFAULT_STRING_ENCODING);
        } catch (java.io.UnsupportedEncodingException e) {
            // this is an coding error situation - should never happen
            throw new RuntimeException(e);
        }

    }

    /**
     * Gets the next byte form the buffer and increments the current pointer.
     * 
     * @return the byte at <code>buffer[ptr]</code>
     */
    private byte peekNextByte() {
        final byte result = buffer[ptr];
        ptr = (ptr + 1) % buffer.length;
        return result;
    }

    /**
     * Determines the number of bytes to be read until the end of the buffer.
     * <p>
     * <b>Note:</b> Since this buffer is unlimited an alternative implementation to this
     * method would be to always return <code>Integer.MAX_VALUE</code>. However this
     * information would be of little value. Instead, returning the number of bytes
     * available until the end of the internal buffer is better and does interfere with
     * the correctness of the class.
     * 
     * @return the number of bytes still to be read until the end of the buffer.
     */
    public final int available() {
        return buffer.length - ptr;
    }

    @Override
    public final void close() {
    }

    @Override
    public final void mark(final int readlimit) {
        super.mark(readlimit);
    }

    @Override
    public final boolean markSupported() {
        return super.markSupported();
    }

    /**
     * Reads a byte from the internal buffer and returns it as an integer.
     * 
     * @return the next byte from the buffer
     * @see java.io.InputStream#read()
     */
    public final int read() {
        final int result = peekNextByte() & Binary.INT_LOW_BYTE_MASK;
        return result;
    }

    /**
     * Fills a buffer with bytes from the internal buffer at a given position.
     * 
     * @param b the byte array to be read into
     * @param off the offset where to perform the reading
     * @param len the number of bytes to read
     * @return the number of bytes read byte into the buffer
     * @see java.io.InputStream#read(byte[], int, int)
     */
    public final int read(final byte[] b, final int off, final int len) {
        final int tgt = off + len;
        for (int i = off; i < tgt; i++) {
            b[i] = peekNextByte();
        }
        return len;
    }

    /**
     * Fills a buffer with bytes taken from the internal buffer.
     * 
     * @param b the byte array to be read into
     * @return the number of bytes read byte into the buffer
     * @see java.io.InputStream#read(byte[])
     */
    public final int read(final byte[] b) {
        return read(b, 0, b.length);
    }

    /**
     * Sets the pointer to the first byte.
     * 
     * @see java.io.InputStream#reset()
     */
    public final void reset() {
        ptr = 0;
    }

    /**
     * Skips n bytes from the buffer.
     * 
     * @param n the number of bytes to be skipped
     * @return the number of actually skipped bytes
     * @throws IOException if the base stream throws an exception
     * @see java.io.InputStream#skip(long)
     */
    public final long skip(final long n) throws IOException {
        for (long i = n; i > 0; i--) {
            peekNextByte();
        }
        return n;
    }
}
