package codebase.io.converters.binary;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import junit.framework.TestCase;

public class TestBooleanConverter extends
        TestCase {

    public void testBooleanConverter() throws IOException {
        // write a Boolean value to the output stream
        ByteArrayOutputStream output = new java.io.ByteArrayOutputStream();

        new BooleanConverter().write(new DataOutputStream(output), Boolean.TRUE);

        new BooleanConverter().write(new java.io.DataOutputStream(output), Boolean.FALSE);

        // create a byte array input stream on the output stream
        java.io.ByteArrayInputStream input = new java.io.ByteArrayInputStream(output.toByteArray());

        // read a boolean value from the input stream and compare to what we have written
        assertEquals(Boolean.TRUE, new BooleanConverter().read(new DataInputStream(input)));
        assertEquals(Boolean.FALSE, new BooleanConverter().read(new DataInputStream(input)));

        // clean up
        input.close();
        output.close();
    }
}
