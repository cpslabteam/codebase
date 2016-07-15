package codebase.io.converters.binary;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.junit.Test;

import junit.framework.TestCase;

public class TestIntegerConverter extends TestCase {

    /**
     * Tests the Integer converter on few simple standard cases.
     */
    @Test
    public void testIntegersConverterStandard() throws IOException {
        // write a Boolean and a boolean value to the output stream
        ByteArrayOutputStream output = new java.io.ByteArrayOutputStream();

        new IntegerConverter().write(new DataOutputStream(output), 123);
        new IntegerConverter().write(new java.io.DataOutputStream(output), 1234567890);

        // create a byte array input stream on the output stream
        java.io.ByteArrayInputStream input = new java.io.ByteArrayInputStream(output.toByteArray());

        // read a boolean value from the input stream and compare to what we have written
        assertEquals(123, new IntegerConverter().read(new DataInputStream(input)));
        assertEquals(1234567890, new IntegerConverter().read(new DataInputStream(input)));

        // clean up
        input.close();
        output.close();
    }


    /**
     * Tests the Integer converter on few extreme cases.
     */
    @Test
    public void testIntegerConverterExtremes() throws IOException {
        // write a Boolean and a boolean value to the output stream
        ByteArrayOutputStream output = new java.io.ByteArrayOutputStream();

        new IntegerConverter().write(new DataOutputStream(output), Integer.MAX_VALUE);
        new IntegerConverter().write(new DataOutputStream(output), 0);
        new IntegerConverter().write(new java.io.DataOutputStream(output), Integer.MIN_VALUE);

        // create a byte array input stream on the output stream
        java.io.ByteArrayInputStream input = new java.io.ByteArrayInputStream(output.toByteArray());

        // read a boolean value from the input stream and compare to what we have written
        assertEquals(Integer.MAX_VALUE, new IntegerConverter().read(new DataInputStream(input)));
        assertEquals(0, new IntegerConverter().read(new DataInputStream(input)));
        assertEquals(Integer.MIN_VALUE, new IntegerConverter().read(new DataInputStream(input)));

        // clean up
        input.close();
        output.close();
    }
}
