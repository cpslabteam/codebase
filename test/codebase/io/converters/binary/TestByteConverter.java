package codebase.io.converters.binary;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import junit.framework.TestCase;

public class TestByteConverter extends
        TestCase {

    /**
     * Tests the Byte converter on few simple standard cases.
     */
    public void testBytesConverterStandard() throws IOException {
        // write a byte value to the output stream
        ByteArrayOutputStream output = new java.io.ByteArrayOutputStream();

        new ByteConverter().write(new DataOutputStream(output), (byte) 1);
        new ByteConverter().write(new DataOutputStream(output), (byte) 12);
        new ByteConverter().write(new java.io.DataOutputStream(output), (byte) 123);

        // create a byte array input stream on the output stream
        java.io.ByteArrayInputStream input = new java.io.ByteArrayInputStream(output.toByteArray());

        // read a byte value from the input stream and compare to what we have written
        assertEquals((byte) 1, new ByteConverter().read(new DataInputStream(input)));
        assertEquals((byte) 12, new ByteConverter().read(new DataInputStream(input)));
        assertEquals((byte) 123, new ByteConverter().read(new DataInputStream(input)));

        // clean up
        input.close();
        output.close();
    }


    /**
     * Tests the Byte converter on few extreme cases.
     */
    public void testByteConverterExtremes() throws IOException {
        // write a byte value to the output stream
        ByteArrayOutputStream output = new java.io.ByteArrayOutputStream();

        new ByteConverter().write(new DataOutputStream(output), Byte.MAX_VALUE);
        new ByteConverter().write(new DataOutputStream(output), (byte) 0);
        new ByteConverter().write(new java.io.DataOutputStream(output), Byte.MIN_VALUE);

        // create a byte array input stream on the output stream
        java.io.ByteArrayInputStream input = new java.io.ByteArrayInputStream(output.toByteArray());

        // read a byte value from the input stream and compare to what we have written
        assertEquals(Byte.MAX_VALUE, new ByteConverter().read(new DataInputStream(input)));
        assertEquals((byte) 0, new ByteConverter().read(new DataInputStream(input)));
        assertEquals(Byte.MIN_VALUE, new ByteConverter().read(new DataInputStream(input)));

        // clean up
        input.close();
        output.close();
    }
}
