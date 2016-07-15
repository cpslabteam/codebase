package codebase.io.converters.binary;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.junit.Test;

import junit.framework.TestCase;

public class TestUTFStringConverter extends TestCase {

    /**
     * Tests the standard case of the UTF String converter.
     */
    @Test
    public void testUTFConverterStandard() throws IOException {
        // write a Boolean and a boolean value to the output stream
        ByteArrayOutputStream output = new java.io.ByteArrayOutputStream();
        new UTFStringConverter().write(new DataOutputStream(output), "Hello World");
        new UTFStringConverter().write(new java.io.DataOutputStream(output), "Thats all folks!");

        // read a boolean value from the input stream and compare to what we have written
        java.io.ByteArrayInputStream input = new java.io.ByteArrayInputStream(output.toByteArray());
        assertEquals("Hello World", new UTFStringConverter().read(new DataInputStream(input)));
        assertEquals("Thats all folks!", new UTFStringConverter().read(new DataInputStream(input)));

        // clean up
        input.close();
        output.close();
    }

    /**
     * Tests the UTF String converter on a single character.
     */
    @Test
    public void testUTFConverterSingle() throws IOException {
        // write a Boolean and a boolean value to the output stream
        ByteArrayOutputStream output = new java.io.ByteArrayOutputStream();
        new UTFStringConverter().write(new DataOutputStream(output), "X");

        // read a boolean value from the input stream and compare to what we have written
        java.io.ByteArrayInputStream input = new java.io.ByteArrayInputStream(output.toByteArray());
        assertEquals("X", new UTFStringConverter().read(new DataInputStream(input)));

        // clean up
        input.close();
        output.close();
    }

    /**
     * Tests the empty string case of the UTF String converter.
     */
    @Test
    public void testUTFConverterEmpty() throws IOException {
        // write a Boolean and a boolean value to the output stream
        ByteArrayOutputStream output = new java.io.ByteArrayOutputStream();
        new UTFStringConverter().write(new DataOutputStream(output), "");

        // read a boolean value from the input stream and compare to what we have written
        java.io.ByteArrayInputStream input = new java.io.ByteArrayInputStream(output.toByteArray());
        assertEquals("", new UTFStringConverter().read(new DataInputStream(input)));

        // clean up
        input.close();
        output.close();
    }
}
