package codebase.util.streams;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * An input stream that waits for a fixed time each time before reading form another stream.
 * <p>
 * Used primarily for testing purposes.
 */
public class DelayedInputStream extends
        FilterInputStream {

    /**
     * The the number of bytes per second
     */
    private final int intervalMillis;

    /**
     * Constructs an input stream that converges a predefined bandwidth.
     * 
     * @param interval the time to wait before sending the message.
     * @param input The input stream to be wrapped.
     * @throws IllegalArgumentException if the bandwidth is not positive
     */
    public DelayedInputStream(final InputStream input, final int interval) {
        super(input);
        if (interval <= 0) {
            throw new IllegalArgumentException("The interval must be positive");
        }

        intervalMillis = interval;
    }


    /**
     * Reads a byte from the wrapped stream and waits for the given interval
     * 
     * @return the character read from the underlying stream
     * @throws IOException if the read operation of the wrapped buffer fails
     * @see FilterInputStream#read()
     */
    public final int read() throws IOException {
        int c = in.read();
        if (c < 0)
            return c;

        try {
            Thread.sleep(intervalMillis);
        } catch (InterruptedException e) {
            return -1;
        }
        return c;
    }


    /**
     * Skips a specified amount of bytes.
     * <p>
     * Overrides {@link FilterInputStream#skip(long)} to update the progress monitor after
     * the read. Waits a specified amount fo time in order to converge to the required
     * bandwidth.
     * 
     * @param n {@inheritDoc}
     * @throws IOException {@inheritDoc}
     * @return {@inheritDoc}
     */
    public final long skip(final long n) throws IOException {
        long nr = in.skip(n);
        if (nr <= 0)
            return nr;

        try {
            Thread.sleep(intervalMillis);
        } catch (InterruptedException e) {
            return -1;
        }

        return nr;
    }
}
