/*
 * Created on 13/Jan/2006
 */
package codebase.streams;

import java.io.IOException;
import java.io.InputStream;

/**
 * A byte counter decorator.
 * <p>
 * A decorating input stream that counts the number of bytes that have passed
 * through so far.
 */
public class CounterInputStream
        extends DecoratorInputStream {

    /** The count of bytes that have passed. */
    private int count;

    /**
     * Constructs a new CountingInputStream.
     *
     * @param in InputStream to delegate to
     */
    public CounterInputStream(final InputStream in) {
        super(in);
    }

    /**
     * Increases the count by super.read(b)'s return count
     *
     * @see java.io.InputStream#read(byte[])
     */
    public int read(final byte[] b) throws IOException {
        int readCount = super.read(b);
        if (readCount >= 0) {
            count += 1;
        }
        return readCount;
    }

    /**
     * Increases the count by super.read(b, off, len)'s return count
     *
     * @see java.io.InputStream#read(byte[], int, int)
     */
    public int read(final byte[] b, final int off, final int len)
            throws IOException {
        final int readCount = super.read(b, off, len);
        if (readCount >= 0) {
            count += readCount;
        }
        return readCount;
    }

    /**
     * Increases the count by 1 if a byte is successfully read.
     *
     * @see java.io.InputStream#read()
     */
    public int read() throws IOException {
        final int readCount = super.read();
        if (readCount >= 0) {
            count += 1;
        }
        return readCount;
    }

    /**
     * Increases the count by the number of skipped bytes.
     *
     * @see java.io.InputStream#skip(long)
     */
    public long skip(final long length) throws IOException {
        final long skipedCount = super.skip(length);
        if (skipedCount >= 0) {
            count += skipedCount;
        }
        return skipedCount;
    }

    /**
     * The number of bytes that have passed through this stream.
     *
     * @return the number of bytes accumulated
     */
    public int getCount() {
        return count;
    }

    /**
     * Set the counter to 0.
     *
     * @return the count previous to resetting.
     */
    public synchronized int resetCount() {
        final int result = count;
        count = 0;
        return result;
    }
}
