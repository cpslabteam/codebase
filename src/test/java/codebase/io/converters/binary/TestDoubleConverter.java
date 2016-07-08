package codebase.io.converters.binary;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import junit.framework.TestCase;

public class TestDoubleConverter extends
        TestCase {

    /**
     * Tests the Double converter on few simple standard cases.
     */
    public void testDoublesConverterStandard() throws IOException {
        // write a Boolean and a boolean value to the output stream
        ByteArrayOutputStream output = new java.io.ByteArrayOutputStream();

        new DoubleConverter().write(new DataOutputStream(output), 2.7236512d);
        new DoubleConverter().write(new DataOutputStream(output), 0.0d);
        new DoubleConverter().write(new java.io.DataOutputStream(output), 6.123853d);

        // create a byte array input stream on the output stream
        java.io.ByteArrayInputStream input = new java.io.ByteArrayInputStream(output.toByteArray());

        // read a boolean value from the input stream and compare to what we have written
        assertEquals(2.7236512d, new DoubleConverter().read(new DataInputStream(input)));
        assertEquals(0d, new DoubleConverter().read(new DataInputStream(input)));
        assertEquals(6.123853d, new DoubleConverter().read(new DataInputStream(input)));

        // clean up
        input.close();
        output.close();
    }


    /**
     * Tests the Double converter on few extreme cases.
     */
    public void testDoubleConverterExtremes() throws IOException {
        // write a Boolean and a boolean value to the output stream
        ByteArrayOutputStream output = new java.io.ByteArrayOutputStream();

        new DoubleConverter().write(new DataOutputStream(output), Double.MAX_VALUE);
        new DoubleConverter().write(new java.io.DataOutputStream(output), Double.MIN_VALUE);
        new DoubleConverter().write(new java.io.DataOutputStream(output), Double.NaN);
        new DoubleConverter().write(new java.io.DataOutputStream(output), Double.NEGATIVE_INFINITY);
        new DoubleConverter().write(new java.io.DataOutputStream(output), Double.POSITIVE_INFINITY);

        // create a byte array input stream on the output stream
        java.io.ByteArrayInputStream input = new java.io.ByteArrayInputStream(output.toByteArray());

        // read a boolean value from the input stream and compare to what we have written
        assertEquals(Double.MAX_VALUE, new DoubleConverter().read(new DataInputStream(input)));
        assertEquals(Double.MIN_VALUE, new DoubleConverter().read(new DataInputStream(input)));
        assertEquals(Double.NaN, new DoubleConverter().read(new DataInputStream(input)));
        assertEquals(Double.NEGATIVE_INFINITY,
                new DoubleConverter().read(new DataInputStream(input)));
        assertEquals(Double.POSITIVE_INFINITY,
                new DoubleConverter().read(new DataInputStream(input)));

        // clean up
        input.close();
        output.close();
    }
}
