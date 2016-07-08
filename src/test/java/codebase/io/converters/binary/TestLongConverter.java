package codebase.io.converters.binary;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import junit.framework.TestCase;

public class TestLongConverter extends
        TestCase {

    /**
     * Tests the Long converter on few simple standard cases.
     */
    public void testLongsConverterStandard() throws IOException {
        // write a Boolean and a boolean value to the output stream
        ByteArrayOutputStream output = new java.io.ByteArrayOutputStream();

        new LongConverter().write(new DataOutputStream(output), 1234567890L);
        new LongConverter().write(new DataOutputStream(output), 0L);
        new LongConverter().write(new java.io.DataOutputStream(output), 123456789012345L);

        // create a byte array input stream on the output stream
        java.io.ByteArrayInputStream input = new java.io.ByteArrayInputStream(output.toByteArray());

        // read a boolean value from the input stream and compare to what we have written
        assertEquals(1234567890L, new LongConverter().read(new DataInputStream(input)));
        assertEquals(0L, new LongConverter().read(new DataInputStream(input)));
        assertEquals(123456789012345L, new LongConverter().read(new DataInputStream(input)));

        // clean up
        input.close();
        output.close();
    }


    /**
     * Tests the Long converter on few extreme cases.
     */
    public void testLongConverterExtremes() throws IOException {
        // write a Boolean and a boolean value to the output stream
        ByteArrayOutputStream output = new java.io.ByteArrayOutputStream();

        new LongConverter().write(new DataOutputStream(output), Long.MAX_VALUE);
        new LongConverter().write(new java.io.DataOutputStream(output), Long.MIN_VALUE);

        // create a byte array input stream on the output stream
        java.io.ByteArrayInputStream input = new java.io.ByteArrayInputStream(output.toByteArray());

        // read a boolean value from the input stream and compare to what we have written
        assertEquals(Long.MAX_VALUE, new LongConverter().read(new DataInputStream(input)));
        assertEquals(Long.MIN_VALUE, new LongConverter().read(new DataInputStream(input)));

        // clean up
        input.close();
        output.close();
    }
}
