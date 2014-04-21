package codebase.io.converters.binary;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import junit.framework.TestCase;

public class TestCharacterConverter extends
        TestCase {

    /**
     * Tests the Character converter on few simple standard cases.
     */
    public void testCharactersConverterStandard() throws IOException {
        // write a Boolean and a boolean value to the output stream
        ByteArrayOutputStream output = new java.io.ByteArrayOutputStream();

        new CharacterConverter().write(new DataOutputStream(output), 'A');
        new CharacterConverter().write(new DataOutputStream(output), ' ');
        new CharacterConverter().write(new java.io.DataOutputStream(output), 'Z');
        new CharacterConverter().write(new java.io.DataOutputStream(output), '\u0362');

        // create a Character array input stream on the output stream
        java.io.ByteArrayInputStream input = new java.io.ByteArrayInputStream(output.toByteArray());

        // read a boolean value from the input stream and compare to what we have written
        assertEquals('A', new CharacterConverter().read(new DataInputStream(input)));
        assertEquals(' ', new CharacterConverter().read(new DataInputStream(input)));
        assertEquals('Z', new CharacterConverter().read(new DataInputStream(input)));
        assertEquals('\u0362', new CharacterConverter().read(new DataInputStream(input)));

        // clean up
        input.close();
        output.close();
    }


    /**
     * Tests the Character converter on few extreme cases.
     */
    public void testCharacterConverterExtremes() throws IOException {
        // write a Boolean and a boolean value to the output stream
        ByteArrayOutputStream output = new java.io.ByteArrayOutputStream();

        new CharacterConverter().write(new DataOutputStream(output), Character.MAX_VALUE);
        new CharacterConverter().write(new DataOutputStream(output), (char) 0);
        new CharacterConverter().write(new java.io.DataOutputStream(output), Character.MIN_VALUE);

        // create a Character array input stream on the output stream
        java.io.ByteArrayInputStream input = new java.io.ByteArrayInputStream(output.toByteArray());

        // read a boolean value from the input stream and compare to what we have written
        assertEquals(Character.MAX_VALUE, new CharacterConverter().read(new DataInputStream(input)));
        assertEquals((char) 0, new CharacterConverter().read(new DataInputStream(input)));
        assertEquals(Character.MIN_VALUE, new CharacterConverter().read(new DataInputStream(input)));

        // clean up
        input.close();
        output.close();
    }
}
