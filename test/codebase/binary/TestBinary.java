/*
 * Created on 4/Mai/2005
 */
package codebase.binary;

import codebase.Arrays;
import codebase.Binary;
import codebase.iterators.ArrayIterator;
import codebase.iterators.EmptyIterator;
import codebase.junit.EnhancedTestCase;

/**
 * Tests the {@link Binary} utility functions.
 */
public class TestBinary extends
        EnhancedTestCase {

    public void testGetHighNibble() {
        assertEquals(0, Binary.getHighNibble(0x00000000));
        assertEquals(0x0C, Binary.getHighNibble(0xC0));
        assertEquals(0x0C, Binary.getHighNibble(0xC1));
        assertEquals(0x0F, Binary.getHighNibble(0xF2));
    }

    public void testGetHighByte() {
        assertEquals(0, Binary.getHighByte(0x00000000));
        assertEquals(0x0C, Binary.getHighByte(0x0C00));
        assertEquals(0x0C, Binary.getHighByte(0x0C11));
        assertEquals(0xFF, Binary.getHighByte(0xFF33));
    }

    public void testGetLowNibble() {
        assertEquals(0, Binary.getLowNibble(0x00000000));
        assertEquals(0x0C, Binary.getLowNibble(0x0C));
        assertEquals(0x0C, Binary.getLowNibble(0x1C));
        assertEquals(0x0F, Binary.getLowNibble(0x2F));
    }

    public void testGetLowByte() {
        assertEquals(0, Binary.getLowerByte(0x00000000));
        assertEquals(0x0C, Binary.getLowerByte(0x0C));
        assertEquals(0x1C, Binary.getLowerByte(0x2D1C));
        assertEquals(0xFF, Binary.getLowerByte(0x33FF));
    }

    public void testSetHighNibble() {
        assertEquals(0x00, Binary.setHighNibble(0x00, 0x00));
        assertEquals(0x10, Binary.setHighNibble(0x00, 0x01));

        /* idempotence */
        assertEquals(0x10, Binary.setHighNibble(0x10, 0x01));

        /* no interference */
        assertEquals(0x1F, Binary.setHighNibble(0xFF, 0x01));
        assertEquals(0xF1, Binary.setHighNibble(0x11, 0x0F));

        /* normal */
        assertEquals(0xAB, Binary.setHighNibble(0x0B, 0x0A));
    }

    public void testSetLowNibble() {
        assertEquals(0x00, Binary.setLowNibble(0x00, 0x00));
        assertEquals(0x01, Binary.setLowNibble(0x00, 0x01));

        /* idempotence */
        assertEquals(0x01, Binary.setLowNibble(0x01, 0x01));

        /* no interference */
        assertEquals(0xF1, Binary.setLowNibble(0xF1, 0x01));
        assertEquals(0x1F, Binary.setLowNibble(0x11, 0x0F));

        /* normal */
        assertEquals(0xAB, Binary.setLowNibble(0xA0, 0x0B));
    }

    public void testSwapNibbles() {
        assertEquals(0x00, Binary.swapNibbles(0x00));
        assertEquals(0x11, Binary.swapNibbles(0x11));
        assertEquals(0x12, Binary.swapNibbles(0x21));
        assertEquals(0xF0, Binary.swapNibbles(0x0F));
        assertEquals(0xFF, Binary.swapNibbles(0xFF));
        assertEquals(0xAB, Binary.swapNibbles(0xBA));
    }

    public void testComposeByte() {
        assertEquals((byte) 0x00, Binary.toByte(0x00, 0x00));
        assertEquals((byte) 0x11, Binary.toByte(0x01, 0x01));
        assertEquals((byte) 0x23, Binary.toByte(0x02, 0x03));
        assertEquals((byte) 0xCA, Binary.toByte(0x0C, 0x0A));
        assertEquals((byte) 0xFF, Binary.toByte(0x0F, 0x0F));
        assertEquals((byte) 0x24, Binary.toByte(0x12, 0x34));
    }

    public void testToInteger() {
        assertEquals(0, Binary.toInteger((byte) 0, (byte) 0, (byte) 0, (byte) 0));
        assertEquals(1, Binary.toInteger((byte) 1, (byte) 0, (byte) 0, (byte) 0));
        assertEquals(256, Binary.toInteger((byte) 0, (byte) 1, (byte) 0, (byte) 0));
        assertEquals(65536, Binary.toInteger((byte) 0, (byte) 0, (byte) 1, (byte) 0));
        assertEquals(0x78563412,
                Binary.toInteger((byte) 0x12, (byte) 0x34, (byte) 0x56, (byte) 0x78));
    }

    public void testToByteArray() {
        byte[] decoding = new byte[Binary.SIZE_OF_INT];
        Binary.toByteArray(0, decoding, 0);

        assertEquals(0, decoding[0]);
        assertEquals(0, decoding[1]);
        assertEquals(0, decoding[2]);
        assertEquals(0, decoding[3]);

        Binary.toByteArray(0x78563412, decoding, 0);
        assertEquals(0x12, decoding[0]);
        assertEquals(0x34, decoding[1]);
        assertEquals(0x56, decoding[2]);
        assertEquals(0x78, decoding[3]);
    }

    public void testToHexString() {
        assertEquals("00", Binary.toHexString(new byte[] { 0 }));
        assertEquals("01", Binary.toHexString(new byte[] { 1 }));
        assertEquals(
                "1234567890abcdef",
                Binary.toHexString(new byte[] { 0x12, 0x34, 0x56, 0x78, (byte) 0x90, (byte) 0xAB,
                        (byte) 0xCD, (byte) 0xEF }));
    }

    public void testToHexByte() {
        assertEquals("00", Binary.toHex((byte) 0));
        assertEquals("01", Binary.toHex((byte) 1));
        assertEquals("ff", Binary.toHex((byte) 0xFF));
        assertEquals("f0", Binary.toHex((byte) 0xF0));
        assertEquals("0f", Binary.toHex((byte) 0x0F));
        assertEquals("99", Binary.toHex((byte) 0x99));
    }

    public void testToHexChar() {
        assertEquals("00", Binary.toHex('\u0000'));
        assertEquals("01", Binary.toHex('\u0001'));
        assertEquals("ff", Binary.toHex('\u00ff'));
        assertEquals("03f0", Binary.toHex('\u03F0'));
    }

    public void testToHexInt() {
        assertEquals("00000000", Binary.toHex((int) 0));
        assertEquals("00000001", Binary.toHex((int) 1));
        assertEquals("000000ff", Binary.toHex((int) 0xFF));
        assertEquals("ffffffff", Binary.toHex((int) 0xFFFFFFFF));
        assertEquals("12345678", Binary.toHex((int) 0x12345678));
        assertEquals("99999999", Binary.toHex((int) 0x99999999));
    }

    private static final EmptyIterator<Integer> EMPTY_ITERATOR = new EmptyIterator<Integer>();

    public void testDecodeIntegersToBytes() {
        // Emtpty
        final int[] input1 = new int[] {};
        assertEquals(
                EMPTY_ITERATOR,
                new ArrayIterator<Byte>(Arrays.toByteArray(Binary.decodeIntegersToBytes(input1, 0))));

        // with one integer
        final int[] input2 = new int[] { 7 };
        assertEquals(
                new ArrayIterator<Byte>(new Byte[] { Byte.valueOf((byte) 7),
                        Byte.valueOf((byte) 0), Byte.valueOf((byte) 0), Byte.valueOf((byte) 0) }),
                new ArrayIterator<Byte>(Arrays.toByteArray(Binary.decodeIntegersToBytes(input2, 4))));

        // with two integers
        final int[] input3 = new int[] { 7, 0x12345678 };
        assertEquals(
                new ArrayIterator<Byte>(new Byte[] { Byte.valueOf((byte) 7),
                        Byte.valueOf((byte) 0), Byte.valueOf((byte) 0), Byte.valueOf((byte) 0),
                        Byte.valueOf((byte) 0x78), Byte.valueOf((byte) 0x56),
                        Byte.valueOf((byte) 0x34), Byte.valueOf((byte) 0x12) }),
                new ArrayIterator<Byte>(Arrays.toByteArray(Binary.decodeIntegersToBytes(input3, 8))));
    }

    public void testGetSetBits() {
        assertEquals(0, Binary.getSetBits(0));
        assertEquals(2, Binary.getSetBits(0x11));
        assertEquals(3, Binary.getSetBits(0x07));
        assertEquals(8, Binary.getSetBits(0xFF));
    }

    public void testEncodeBytesToIntegers() {
        // EmptyIterator
        final byte[] input1 = new byte[] {};
        assertEquals(
                EMPTY_ITERATOR,
                new ArrayIterator<Integer>(Arrays.toIntegerArray(Binary.encodeBytesToIntegers(
                        input1, 0, 0))));

        // with one integer
        final byte[] input2 = new byte[] { (byte) 7, (byte) 0, (byte) 0, (byte) 0 };
        assertEquals(
                new ArrayIterator<Integer>(new Integer[] { Integer.valueOf(7) }),
                new ArrayIterator<Integer>(Arrays.toIntegerArray(Binary.encodeBytesToIntegers(
                        input2, 0, 4))));

        // with two integers
        final byte[] input3 = new byte[] { (byte) 7, (byte) 0, (byte) 0, (byte) 0, (byte) 0x78,
                (byte) 0x56, (byte) 0x34, (byte) 0x12 };

        assertEquals(
                new ArrayIterator<Integer>(new Integer[] { Integer.valueOf(7),
                        Integer.valueOf(0x12345678) }),
                new ArrayIterator<Integer>(Arrays.toIntegerArray(Binary.encodeBytesToIntegers(
                        input3, 0, 8))));
    }
}
