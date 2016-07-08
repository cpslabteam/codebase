/*
 * Created on 13/Jan/2006
 */
package codebase.streams;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * A stream that informs about the read operations performed.
 */
public class TeeInputStream extends
        FilterInputStream {

    /**
     * The position of the last next byte to be read.
     */
    private int pos;

    /**
     * Call-back interface for informing about the data read from the input stream.
     */
    public interface Tee {
        /**
         * Informs that one byte was read the specified position.
         * 
         * @param pos the position where the byte was read
         * @param b the byte read
         */
        void read(final int pos, final int b);

        /**
         * Informs that an array of bytes was read at the specified position.
         * 
         * @param pos the position
         * @param bytes the bytes read
         */
        void read(final int pos, final byte[] bytes);

        /**
         * Informs that the underlying stream was reset.
         */
        void reset();
    }

    /**
     * The {@link Tee} object.
     */
    private final Tee isTee;

    /**
     * Constructs a new CountingInputStream.
     * 
     * @param in InputStream to delegate to
     * @param tee the {@link Tee} object to be used
     */
    public TeeInputStream(final InputStream in, final Tee tee) {
        super(in);
        isTee = tee;
    }

    /**
     * Reads a byte buffer from the base stream.
     * <p>
     * Increases the count by super.read(b)'s return count.
     * 
     * @param b the buffer
     * @return the number of bytes read
     * @throws IOException if reading from the base stream fails
     * @see java.io.InputStream#read(byte[])
     */
    public int read(final byte[] b) throws IOException {
        int n = super.read(b);
        if (n >= 0) {
            isTee.read(pos, b);
            pos += 1;
        }
        return n;
    }

    /**
     * Reads a byte segment from the base buffer.
     * <p>
     * Increases the count by super.read(b, off, len)'s return count.
     * 
     * @param b the buffer
     * @param off the start offset
     * @param len the number of bytes to be read
     * @return the value read from the base stream
     * @throws IOException if reading from the base stream fails
     * @see java.io.InputStream#read(byte[], int, int)
     */
    public int read(final byte[] b, final int off, final int len) throws IOException {
        final int n = super.read(b, off, len);
        if (n >= 0) {
            final byte[] read = new byte[len];
            System.arraycopy(b, off, read, 0, len);
            isTee.read(pos, read);

            pos += n;
        }
        return n;
    }

    /**
     * Increases the count by 1 if a byte is successfully read.
     * 
     * @throws IOException if reading from the base stream fails
     * @return the value read from the base stream
     * @see java.io.InputStream#read()
     */
    public int read() throws IOException {
        final int readByte = super.read();
        if (readByte >= 0) {
            isTee.read(pos, readByte);
            pos += 1;
        }
        return readByte;
    }

    /**
     * Skips bytes but does not change the position.
     * 
     * @param n the number of bytes to be skipped
     * @return the number of bytes skipped by the base stream
     * @throws IOException if reading from the base stream fails
     * @see java.io.InputStream#skip(long)
     */
    public long skip(final long n) throws IOException {
        final long skipedCount = super.skip(n);
        return skipedCount;
    }

    /**
     * The number of bytes that have passed through this stream.
     * 
     * @return the number of bytes accumulated
     */
    public final int getPos() {
        return pos;
    }

    /**
     * Resets the base stream.
     * 
     * @throws IOException if the base stream fails with that exception
     * @see java.io.InputStream#reset()
     */
    public void reset() throws IOException {
        super.reset();
        pos = 0;
    }
}
