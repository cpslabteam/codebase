package codebase.streams;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import codebase.StringUtil;
import junit.framework.TestCase;


/**
 * Unit tests for the {@link TimeoutOutputStream} class.
 * <p>
 * Uses a {@link DelayedOutputStream} simulate delays and test the correct handling
 * timeout conditions.
 */
public class TestTimeoutOutputStream extends TestCase {

    /**
     * Tests that writing form the one element stream returns the element.
     */
    public final void testBasicWriteOneElement() throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        TimeoutOutputStream s = new TimeoutOutputStream(os);
        s.open();

        // Basic
        s.write('X');
        assertEquals(os.toString(StringUtil.UTF8_NAME), "X");

        // Sanity checks for close
        assertFalse(s.isClosed());
        s.close();
        assertTrue(s.isClosed());
    }

    /**
     * Tests that writing multiple elements correctly returns the elements in sequence.
     */
    @Test
    public void testWriteMultipleElement() throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        TimeoutOutputStream s = new TimeoutOutputStream(os);
        s.open();

        // Basic
        s.write('X');
        s.write('Y');
        s.write('Z');
        assertEquals(os.toString(StringUtil.UTF8_NAME), "XYZ");

        //Sanity checks for close
        assertFalse(s.isClosed());
        s.close();
        assertTrue(s.isClosed());
    }

    /**
     * Tests that reading multiple elements correctly returns the elements in sequence.
     */
    @Test
    public void testBasicWriteMultipleElement() throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        TimeoutOutputStream s = new TimeoutOutputStream(os);
        s.open();

        // Basic
        s.write(new byte[] { 'X', 'Y', 'Z' });
        s.write(new byte[] { 'W', 'U' });

        assertEquals(os.toString(StringUtil.UTF8_NAME), "XYZWU");

        //Sanity checks for close
        assertFalse(s.isClosed());
        s.close();
        assertTrue(s.isClosed());
    }

    @Test
    public void testDelayedOutputStream() throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        DelayedOutputStream dos = new DelayedOutputStream(os, 1000);

        long t = System.currentTimeMillis();

        dos.write(new byte[] { 'X', 'Y', 'Z' });

        assertEquals(System.currentTimeMillis() - t, 3000, 50);
    }

    /**
     * Tests that with a delay of 1 sec the three characters are read.
     */
    @Test
    public void testSlowElementBuffer() throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        TimeoutOutputStream s = new TimeoutOutputStream(new DelayedOutputStream(os, 1000),
                3000 + 500, TimeUnit.MILLISECONDS);
        s.open();

        s.write(new byte[] { 'X', 'Y', 'Z' });
        s.write(new byte[] { 'W', 'U' });

        assertEquals(os.toString(StringUtil.UTF8_NAME), "XYZWU");

        //Sanity checks for close
        assertFalse(s.isClosed());
        s.close();
        assertTrue(s.isClosed());
    }

    /**
     * Tests that with a delay of 2 secs, it times out.
     */
    @Test
    public void testTimeoutElementBuffer() throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        TimeoutOutputStream s =
            new TimeoutOutputStream(new DelayedOutputStream(os, 2100), 2000, TimeUnit.MILLISECONDS);
        s.open();

        try {
            s.write(new byte[] { 'X' });
            fail("Should have timed out!");
        } catch (TimeoutException e) {
            super.assertTrue(true);
        }

        s.close();
    }


    /**
     * Tests that writing to a TimeoutOutputStream that is closed fails regardless of the
     * base stream's state.
     * <p>
     */
    public final void testWithTimeoutStreamClosed() throws IOException {
        ByteArrayOutputStream base = new ByteArrayOutputStream();
        TimeoutOutputStream tos = new TimeoutOutputStream(base);
        tos.open();
        tos.close();

        boolean passed = false;
        // Basic
        try {
            tos.write('X');
        } catch (Exception e) {
            passed = true;
        }
        assertTrue(passed);
        assertTrue(tos.isClosed());
    }

}
