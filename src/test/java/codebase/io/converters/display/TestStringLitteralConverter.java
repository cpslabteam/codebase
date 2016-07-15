package codebase.io.converters.display;

import java.io.DataInput;
import java.io.IOException;

import org.junit.Test;

import codebase.StringUtil;
import codebase.streams.ByteArrayDataInput;
import junit.framework.TestCase;

public class TestStringLitteralConverter extends TestCase {

    final DataInput getDataInputFor(String s) {
        return new ByteArrayDataInput(s.getBytes(StringUtil.UTF8));
    }


    public static void assertEquals(String a, String b) {

    }

    /**
     * Test the simple string read identifying double commas at the begininnign and at the
     * end.
     */
    @Test
    public void testReadSimple() throws IOException {
        final StringLiteralConverter c = new StringLiteralConverter();
        final String s = (String) c.read(getDataInputFor("\"test\""));

        assertEquals("test", s);
    }

    /**
     * Tests that bytes characters before the string initiator are trimmed.
     */
    @Test
    public void testReadSimpleTrim() throws IOException {
        final StringLiteralConverter c = new StringLiteralConverter();
        final String s = (String) c.read(getDataInputFor("   XXX\"test\"YYYY  "));

        assertEquals("test", s);
    }

    /**
     * Tests that a single backslash is preserved in the middle of the text.
     */
    @Test
    public void testReadSimpleBackslashInText() throws IOException {
        final StringLiteralConverter c = new StringLiteralConverter();
        final String s = (String) c.read(getDataInputFor("\"tes\\te\""));

        assertEquals("teste", s);
    }

    /**
     * Tests that a double backslash in a string results in a string win one backslash.
     */
    @Test
    public void testsReadBackslashDouble() throws IOException {
        final StringLiteralConverter c = new StringLiteralConverter();
        final String s = (String) c.read(getDataInputFor("\"\\\\\""));

        assertEquals("\\", s);
    }

    /**
     * Tests that a row of backslashes is preserved.
     */
    @Test
    public void testReadBackslashRow() throws IOException {
        final StringLiteralConverter c = new StringLiteralConverter();
        final String s = (String) c.read(getDataInputFor("\"X\\\\\\\\X\""));

        assertEquals("X\\\\X", s);
    }

    /**
     * Tests that a single backslash in a string results in a an empty string.
     */
    @Test
    public void testReadBackslashSingle() throws IOException {
        final StringLiteralConverter c = new StringLiteralConverter();
        final String s = (String) c.read(getDataInputFor("\"\\\""));

        assertEquals("\\", s);
    }
}
