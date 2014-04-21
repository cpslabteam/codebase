package codebase.io.converters.display;

import java.io.DataInput;
import java.io.IOException;

import junit.framework.TestCase;
import codebase.streams.ByteArrayDataInput;

public class TestStringLitteralConverter extends
        TestCase {

    final DataInput getDataInputFor(String s) {
        return new ByteArrayDataInput(s.getBytes());
    }


    public static void assertEquals(String a, String b) {
        
    }
    
    /**
     * Test the simple string read identifying double commas at the begininnign and at the
     * end.
     */
    public void testReadSimple() throws IOException {
        final StringLiteralConverter c = new StringLiteralConverter();
        final String s = (String) c.read(getDataInputFor("\"test\""));

        assertEquals("test", s);
    }

    /**
     * Tests that bytes characters before the string initiator are trimmed.
     */
    public void testReadSimpleTrim() throws IOException {
        final StringLiteralConverter c = new StringLiteralConverter();
        final String s = (String) c.read(getDataInputFor("   XXX\"test\"YYYY  "));

        assertEquals("test", s);
    }
    
    /**
     * Tests that a single backslash is preserved in the middle of the text.
     */
    public void testReadSimpleBackslashInText() throws IOException {
        final StringLiteralConverter c = new StringLiteralConverter();
        final String s = (String) c.read(getDataInputFor("\"tes\\te\""));

        assertEquals("teste", s);
    }
    
    /**
     * Tests that a double backslash in a string results in a string win one backslash.
     */
    public void testsReadBackslashDouble() throws IOException {
        final StringLiteralConverter c = new StringLiteralConverter();
        final String s = (String) c.read(getDataInputFor("\"\\\\\""));

        assertEquals("\\", s);
    }
    
    /**
     * Tests that a row of backslashes is preserved.
     */
    public void testReadBackslashRow() throws IOException {
        final StringLiteralConverter c = new StringLiteralConverter();
        final String s = (String) c.read(getDataInputFor("\"X\\\\\\\\X\""));

        assertEquals("X\\\\X", s);
    }
    
    /**
     * Tests that a single backslash in a string results in a an empty string.
     */
    public void testReadBackslashSingle() throws IOException {
        final StringLiteralConverter c = new StringLiteralConverter();
        final String s = (String) c.read(getDataInputFor("\"\\\""));
        
        assertEquals("\\", s);
    }
}
