package codebase.io.converters.binary;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import junit.framework.TestCase;

public class TestFloatConverter extends
        TestCase {

    /**
     * Tests the Float converter on few simple standard cases.
     */
    public void testFloatsConverterStandard() throws IOException {
        // write a Boolean and a boolean value to the output stream
        ByteArrayOutputStream output = new java.io.ByteArrayOutputStream();

        new FloatConverter().write(new DataOutputStream(output), 2.7236512f);
        new FloatConverter().write(new DataOutputStream(output), 0.0f);
        new FloatConverter().write(new java.io.DataOutputStream(output), 6.123853f);

        // create a byte array input stream on the output stream
        java.io.ByteArrayInputStream input = new java.io.ByteArrayInputStream(output.toByteArray());

        // read a boolean value from the input stream and compare to what we have written
        assertEquals(2.7236512f, new FloatConverter().read(new DataInputStream(input)));
        assertEquals(0f, new FloatConverter().read(new DataInputStream(input)));
        assertEquals(6.123853f, new FloatConverter().read(new DataInputStream(input)));

        // clean up
        input.close();
        output.close();
    }


    /**
     * Tests the Float converter on few extreme cases.
     */
    public void testFloatConverterExtremes() throws IOException {
        // write a Boolean and a boolean value to the output stream
        ByteArrayOutputStream output = new java.io.ByteArrayOutputStream();

        new FloatConverter().write(new DataOutputStream(output), Float.MAX_VALUE);
        new FloatConverter().write(new java.io.DataOutputStream(output), Float.MIN_VALUE);
        new FloatConverter().write(new java.io.DataOutputStream(output), Float.NaN);
        new FloatConverter().write(new java.io.DataOutputStream(output), Float.NEGATIVE_INFINITY);
        new FloatConverter().write(new java.io.DataOutputStream(output), Float.POSITIVE_INFINITY);
        
        // create a byte array input stream on the output stream
        java.io.ByteArrayInputStream input = new java.io.ByteArrayInputStream(output.toByteArray());

        // read a boolean value from the input stream and compare to what we have written
        assertEquals(Float.MAX_VALUE, new FloatConverter().read(new DataInputStream(input)));
        assertEquals(Float.MIN_VALUE, new FloatConverter().read(new DataInputStream(input)));
        assertEquals(Float.NaN, new FloatConverter().read(new DataInputStream(input)));
        assertEquals(Float.NEGATIVE_INFINITY, new FloatConverter().read(new DataInputStream(input)));
        assertEquals(Float.POSITIVE_INFINITY, new FloatConverter().read(new DataInputStream(input)));

        // clean up
        input.close();
        output.close();
    }
}
