package codebase.streams;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import junit.framework.TestCase;


/**
 * Unit tests for the {@link DelayedOutputStream} class.
 */
public class TestDelayedOutputStream extends TestCase {

    /**
     * Tests that writing one byte results in waiting the wait time specified.
     */
    public void testWriteOnce() throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        DelayedOutputStream dos = new DelayedOutputStream(os, 2000);

        long t = System.currentTimeMillis();
        dos.write('X');
        assertEquals(System.currentTimeMillis() - t, 2000, 10);
    }

    /**
     * Tests that writing bytes in sequence causes an add up in wait time.
     */
    public void testDelayedOutputStream() throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        DelayedOutputStream dos = new DelayedOutputStream(os, 1000);

        long t = System.currentTimeMillis();
        dos.write(new byte[] { 'X', 'Y', 'Z' });

        assertEquals(System.currentTimeMillis() - t, 3000, 50);
    }
}
