package codebase.io.converters.display;

import java.io.DataInput;
import java.io.IOException;

import junit.framework.TestCase;
import codebase.StringUtil;
import codebase.streams.ByteArrayDataInput;

public class TestIntegerDisplayConverter extends TestCase {

    final DataInput getDataInputFor(String s) {
        return new ByteArrayDataInput(s.getBytes(StringUtil.UTF8));
    }

    public void testReadSimple() throws IOException {
        IntegerDisplayConverter c = new IntegerDisplayConverter();
        final Integer i = (Integer) c.read(getDataInputFor("123"));

        assertEquals((int) i, 123);
    }

    public void testReadSimpleNegative() throws IOException {
        IntegerDisplayConverter c = new IntegerDisplayConverter();
        final Integer i = (Integer) c.read(getDataInputFor("-123"));

        assertEquals((int) i, -123);
    }

    public void testReadMinimalZero() throws IOException {
        IntegerDisplayConverter c = new IntegerDisplayConverter();
        final Integer i = (Integer) c.read(getDataInputFor("0"));

        assertEquals((int) i, 0);
    }

    public void testReadMinimal() throws IOException {
        IntegerDisplayConverter c = new IntegerDisplayConverter();
        final Integer i = (Integer) c.read(getDataInputFor("1"));

        assertEquals((int) i, 1);
    }

    public void testReadNegativeMinimal() throws IOException {
        IntegerDisplayConverter c = new IntegerDisplayConverter();
        final Integer i = (Integer) c.read(getDataInputFor("-1"));

        assertEquals((int) i, -1);
    }

    public void testReadMultipleZero() throws IOException {
        IntegerDisplayConverter c = new IntegerDisplayConverter();
        final Integer i = (Integer) c.read(getDataInputFor("0000000000"));

        assertEquals((int) i, 0);
    }

    /**
     * Tests that the converts reads until a character that is not a digit is found.
     */
    public void testReadUntil() throws IOException {
        IntegerDisplayConverter c = new IntegerDisplayConverter();
        final Integer i = (Integer) c.read(getDataInputFor("123="));

        assertEquals((int) i, 123);
    }


    /**
     * Tests that the converter skips trash until a digit is found.
     */
    public void testSkipTrashUntilDigits() throws IOException {
        IntegerDisplayConverter c = new IntegerDisplayConverter();
        final Integer i = (Integer) c.read(getDataInputFor("  xxx123"));

        assertEquals((int) i, 123);
    }

    public void testReadMaxValue() throws IOException {
        IntegerDisplayConverter c = new IntegerDisplayConverter();
        final Integer i = (Integer) c.read(getDataInputFor(Integer.toString(Integer.MAX_VALUE)));

        assertEquals((int) i, Integer.MAX_VALUE);
    }

    public void testReadMinValue() throws IOException {
        IntegerDisplayConverter c = new IntegerDisplayConverter();
        final Integer i = (Integer) c.read(getDataInputFor(Integer.toString(Integer.MIN_VALUE)));

        assertEquals((int) i, Integer.MIN_VALUE);
    }
}
