package codebase.util.streams;

import java.io.IOException;
import java.io.InputStream;

import codebase.util.binary.Binary;


/**
 * An input stream that returns bytes from a fixed string.
 * <p>
 */
public class ConstantInputStream
        extends InputStream {

    /**
     * The constant byte buffer
     */
    final byte[] buffer;

    /**
     * Pointer to the current byte in the array
     */
    int ptr = 0;

    /**
     * Constructs a constant input from an array of bytes
     *
     * @param initializer the array of bytes that will be cycled
     * @throws IllegalArgumentException if the initializer is
     *             <code>null</code>
     * @throws IllegalArgumentException if the initializer does not have at
     *             least one byte
     */
    public ConstantInputStream(final byte[] initializer) {
        if (initializer == null) {
            throw new IllegalArgumentException("Initializer must be assigned");
        }
        if (initializer.length < 1) {
            throw new IllegalArgumentException(
                "Initializer must have at least one element");
        }
        buffer = initializer;
    }

    /**
     * Constructs a constant input stream from a string using the standard
     * character to byte mapping
     *
     * @param initializer the string will be used as byte constant
     * @throws IllegalArgumentException if the initializer is
     *             <code>null</code>
     * @throws IllegalArgumentException if the initializer is empty
     */
    public ConstantInputStream(final String initializer) {
        if (initializer == null) {
            throw new IllegalArgumentException("Initializer must be assigned");
        }
        if (initializer.length() < 1) {
            throw new IllegalArgumentException("Initializer canot be void");
        }
        buffer = initializer.getBytes();
    }

    /**
     * Gets the next byte form the buffer and increments the current pointer
     *
     * @return the byte at <code>buffer[ptr]</code>
     */
    private byte nextByte() {
        final byte result = buffer[ptr];
        ptr = (ptr + 1) % buffer.length;
        return result;
    }

    /**
     * @see java.io.InputStream#available()
     */
    public final int available() throws IOException {
        return super.available();
    }

    /**
     * @see java.io.InputStream#close()
     */
    public final void close() throws IOException {
        super.close();
    }

    /**
     * @see java.io.InputStream#mark(int)
     */
    public final synchronized void mark(final int readlimit) {
        super.mark(readlimit);
    }

    /**
     * @see java.io.InputStream#markSupported()
     */
    public final boolean markSupported() {
        return super.markSupported();
    }

    /**
     * Reads a byte and returns it as an integer
     *
     * @see java.io.InputStream#read()
     */
    public final int read() throws IOException {
        final int result = nextByte() & Binary.INT_LOW_BYTE_MASK;
        return result;
    }

    /**
     * Fills a buffer with bytes from the internal buffer
     *
     * @see java.io.InputStream#read(byte[], int, int)
     */
    public final int read(final byte[] b, final int off, final int len)
            throws IOException {
        final int tgt = off + len;
        for (int i = off; i < tgt; i++) {
            b[i] = nextByte();
        }
        return len;
    }

    /**
     * Fills a buffer with bytes taken from the internal buffer
     *
     * @see java.io.InputStream#read(byte[])
     */
    public final int read(final byte[] b) throws IOException {
        return read(b, 0, b.length);
    }

    /**
     * Sets the pointer to the first byte
     *
     * @see java.io.InputStream#reset()
     */
    public final synchronized void reset() throws IOException {
        ptr = 0;
    }

    /**
     * Skips n bytes from the buffer
     *
     * @see java.io.InputStream#skip(long)
     */
    public final long skip(final long n) throws IOException {
        for (long i = n; i > 0; i--) {
            nextByte();
        }
        return n;
    }
}