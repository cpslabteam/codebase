package codebase.streams;


import java.io.IOException;
import java.io.InputStream;

import junit.framework.TestCase;

/**
 * Unit tests for the {@link TimeoutInputStream} class.
 * <p>
 * Uses a {@link DelayedOutputStream} simulate delays and test the correct handling
 * timeout conditions.
 */
public class TestTimeoutInputStream extends
        TestCase {
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
     * Tests that with a delay of 1.9 sec the three characters are read.
     */
    public void testSlowElementBuffer() throws IOException {
        InputStream s = new TimeoutInputStream(new DelayedInputStream(
                new StringInputStream("XYZW"), 1990));
        byte[] b = new byte[2];
        assertEquals(s.read(b), 2);
        assertEquals(b[0], 'X');
        assertEquals(b[1], 'Y');

        assertEquals(1, s.skip(1));
        assertEquals(s.read(), 'W');

        // Sanity check
        assertEquals(s.available(), 0);
        assertEquals(s.read(), -1);

        s.close();
    }

    /**
     * Tests that with a delay of 2 secs, it times out.
     */
    public void testElementTimeout() throws IOException {
        InputStream s = new TimeoutInputStream(new DelayedInputStream(new StringInputStream("XYZ"),
                2100));
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
