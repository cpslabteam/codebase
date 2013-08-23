package codebase.streams;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * An output stream that waits for a predefined interval before writing <b>each byte</b>
 * into another stream.
 * <p>
 * Used primarily for testing the timeout behavior of other classes.
 */
public class DelayedOutputStream extends
        FilterOutputStream {

    /**
     * The the number of bytes per second.
     */
    private final int intervalMillis;

    /**
     * Constructs an output stream that converges a predefined bandwidth.
     * 
     * @param interval the time to wait before sending the message.
     * @param output the output stream to be wrapped.
     * @throws IllegalArgumentException if the bandwidth is not positive
     */
    public DelayedOutputStream(final OutputStream output, final int interval) {
        super(output);
        if (interval <= 0) {
            throw new IllegalArgumentException("The interval must be positive");
        }

        intervalMillis = interval;
    }

    /**
     * Writes a byte waiting for a predefined amount of time writting.
     * 
     * @param b the byte to be written.
     * @throws IOException when if writing on the decorated stream fails or if the the
     *             stream was not allowed to wait the specified amount of milliseconds
     *             before writting the byte
     */
    @Override
    public void write(int b) throws IOException {
        try {
            Thread.sleep(intervalMillis);
        } catch (InterruptedException e) {
            throw new TimeoutException(e);
        }

        super.write(b);
    }
}
