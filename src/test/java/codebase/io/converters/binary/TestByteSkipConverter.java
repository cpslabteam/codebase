package codebase.io.converters.binary;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import junit.framework.TestCase;

public class TestByteSkipConverter extends
        TestCase {

    /**
     * Tests the {@link ByteSkipConverter} of size 1.
     * <p>
     * This test writes a marker byte, skips one byte, and then reads a second marker
     * byte.
     */
    public void testBytesConverterSingle() throws IOException {
        // write byte markers and a default value to the output stream
        ByteArrayOutputStream output = new java.io.ByteArrayOutputStream();
        new ByteConverter().write(new DataOutputStream(output), (byte) 1);
        new ByteSkipConverter(1).write(new DataOutputStream(output), (byte) -1);
        new ByteConverter().write(new DataOutputStream(output), (byte) 2);

        // create a byte array input stream on the output stream
        java.io.ByteArrayInputStream input = new java.io.ByteArrayInputStream(output.toByteArray());

        // read a byte value from the input stream 
        assertEquals((byte) 1, new ByteConverter().read(new DataInputStream(input)));

        // skip a byte value from the input stream 
        assertEquals(null, new ByteSkipConverter(1).read(new DataInputStream(input)));

        // read the second byte value and check
        assertEquals((byte) 2, new ByteConverter().read(new DataInputStream(input)));

        // clean up
        input.close();
        output.close();
    }
}
