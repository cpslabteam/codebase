package codebase.streams;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import junit.framework.TestCase;

/**
 * Unit tests for the {@link TimeoutInputStream} class.
 * <p>
 * Uses a {@link DelayedOutputStream} simulate delays and test the correct handling
 * timeout conditions.
 */
public class TestTimeoutInputStream extends TestCase {

    private static final int DEFAULT_TIMEOUT_DELTA = 100;
    private static final int DEFAULT_TIMEOUT = 2000;

    /**
     * Tests that reading form the one element stream returns the element.
     */
    public final void testBasicReadOneElement() throws IOException {
        InputStream s = new TimeoutInputStream(new StringInputStream("X"));
        // Basic

        assertEquals(s.read(), 'X');

        // Sanity check
        assertEquals(s.available(), 0);
        assertEquals(s.read(), -1);

        s.close();
    }

    /**
     * Tests that reading multiple elements correctly returns the elements in sequence.
     */
    @Test
    public void testBasicReadMultipleElement() throws IOException {
        InputStream s = new TimeoutInputStream(new StringInputStream("XYZ"));
        // Basic
        assertEquals(s.read(), 'X');
        assertEquals(s.read(), 'Y');
        assertEquals(s.read(), 'Z');

        // Sanity check
        assertEquals(s.available(), 0);
        assertEquals(s.read(), -1);

        s.close();
    }

    /**
     * Tests that reading multiple elements correctly returns the buffer elements.
     */
    @Test
    public void testBasicArrayReadMultipleElement() throws IOException {
        InputStream s = new TimeoutInputStream(new StringInputStream("XYZ"));

        // with an array
        byte[] b = new byte[2];
        assertEquals(s.read(b), 2);
        assertEquals(b[0], 'X');
        assertEquals(b[1], 'Y');
        assertEquals(s.read(), 'Z');

        // Sanity check
        assertEquals(s.available(), 0);
        assertEquals(s.read(), -1);

        s.close();
    }

    /**
     * Tests that skipping elements form the buffer correctly cycles through the buffer.
     */
    @Test
    public void testSkipMultipleElement() throws IOException {
        InputStream s = new TimeoutInputStream(new StringInputStream("XYZW"));
        assertEquals(s.read(), 'X');
        assertEquals(2, s.skip(2));
        assertEquals(s.read(), 'W');

        // Sanity check
        assertEquals(s.available(), 0);
        assertEquals(s.read(), -1);

        s.close();
    }

    /**
     * Tests that {@link TimeoutInputStream} does not complain when input input stream
     * delay is high but still lower than the specified timeout.
     * <p>
     * Creates a delayed input stream (from a string) that takes 2 secs minus a safety
     * delta to timeout, reads bytes from the stream in different ways and checks that no
     * timeout occurs.
     */
    @Test
    public void testSlowElementBuffer() throws IOException {
        InputStream s = new TimeoutInputStream(new DelayedInputStream(new StringInputStream("XYZW"),
                DEFAULT_TIMEOUT - DEFAULT_TIMEOUT_DELTA), DEFAULT_TIMEOUT);

        // Test read array
        byte[] b = new byte[2];
        assertEquals(s.read(b), 2);
        assertEquals(b[0], 'X');
        assertEquals(b[1], 'Y');

        // Test skip
        assertEquals(1, s.skip(1));

        // Test read int
        assertEquals(s.read(), 'W');

        // Sanity check
        assertEquals(s.available(), 0);
        assertEquals(s.read(), -1);

        s.close();
    }

    /**
     * Tests that the timeout condition is successfully identified when the input stream
     * delay is greater than the delay specified in the {@link TimeoutInputStream}.
     * <p>
     * Creates a {@link TimeoutInputStream} wrapping a delayed input stream with a delay
     * greater than two seconds, reads a bytes and checks that a {@link TimeoutException}
     * in thrown.
     */
    @Test
    public void testElementTimeout() throws IOException {
        InputStream s = new TimeoutInputStream(new DelayedInputStream(new StringInputStream("XYZ"),
                DEFAULT_TIMEOUT + DEFAULT_TIMEOUT_DELTA), DEFAULT_TIMEOUT);
        try {
            assertEquals(s.read(), 'X');
        } catch (TimeoutException e) {
            s.close();

            return;
        }

        s.close();
        fail("Should have timed out!");
    }
}
