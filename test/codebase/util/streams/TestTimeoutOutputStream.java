package codebase.util.streams;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import junit.framework.TestCase;


public class TestTimeoutOutputStream extends
        TestCase {

    /**
     * Tests that writing form the one element stream returns the element.
     */
    public final void testBasicWriteOneElement() throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        TimeoutOutputStream s = new TimeoutOutputStream(os);
        // Basic
        s.write('X');
        assertEquals(os.toString(), "X");

        // Sanity checks for close
        assertFalse(s.isClosed());
        s.close();
        assertTrue(s.isClosed());
    }

    /**
     * Tests that writing multiple elements correctly returns the elements in sequence.
     */
    public void TestWriteMultipleElement() throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        TimeoutOutputStream s = new TimeoutOutputStream(os);
        // Basic
        s.write('X');
        s.write('Y');
        s.write('Z');
        assertEquals(os.toString(), "XYZ");

        //Sanity checks for close
        assertFalse(s.isClosed());
        s.close();
        assertTrue(s.isClosed());
    }

    /**
     * Tests that reading multiple elements correctly returns the elements in sequence.
     */
    public void testBasicWriteMultipleElement() throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        TimeoutOutputStream s = new TimeoutOutputStream(os);
        // Basic
        s.write(new byte[] { 'X', 'Y', 'Z' });
        s.write(new byte[] { 'W', 'U' });

        assertEquals(os.toString(), "XYZWU");
        
        //Sanity checks for close
        assertFalse(s.isClosed());
        s.close();
        assertTrue(s.isClosed());
    }

    /**
     * Tests that with a delay of 1 sec the three characters are read.
     */
    public void testSlowElementBuffer() throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        TimeoutOutputStream s = new TimeoutOutputStream(new DelayedOutputStream(os, 1000), 3000);
        s.write(new byte[] { 'X', 'Y', 'Z' });
        s.write(new byte[] { 'W', 'U' });

        assertEquals(os.toString(), "XYZWU");
        
        //Sanity checks for close
        assertFalse(s.isClosed());
        s.close();
        assertTrue(s.isClosed());
    }

    /**
     * Tests that with a delay of 2 secs, it times out.
     */
    public void testTimeoutElementBuffer() throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        TimeoutOutputStream s = new TimeoutOutputStream(new DelayedOutputStream(os, 2010));

        try {
            s.write(new byte[] { 'X' });
        } catch (TimeoutException e) {
            return;
        }
        fail("Should have timed out!");
        
        //Sanity checks for close
        assertFalse(s.isClosed());
        s.close();
        assertTrue(s.isClosed());
    }

}
