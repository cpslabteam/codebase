/*
 * Created on 1/Dez/2005
 */
package codebase.util;

import codebase.util.Math;
import junit.framework.TestCase;

/**
 * Tests the math utility class
 */
public class TestMath
        extends TestCase {
    
    /**
     * Tests that the powers of 10 are correctly computed
     */
    public void testPow10() {
        assertEquals(1L, Math.pow10(0));
        assertEquals(10L, Math.pow10(1));
        assertEquals(100L, Math.pow10(2));
        assertEquals(1000L, Math.pow10(3));
        assertEquals(10000L, Math.pow10(4));
        assertEquals(100000L, Math.pow10(5));
        assertEquals(1000000L, Math.pow10(6));
        assertEquals(10000000L, Math.pow10(7));
        assertEquals(100000000L, Math.pow10(8));
        assertEquals(1000000000L, Math.pow10(9));
        assertEquals(10000000000L, Math.pow10(10));
        assertEquals(100000000000L, Math.pow10(11));
        assertEquals(1000000000000L, Math.pow10(12));
        assertEquals(10000000000000L, Math.pow10(13));
        assertEquals(100000000000000L, Math.pow10(14));
        assertEquals(1000000000000000L, Math.pow10(15));
        assertEquals(10000000000000000L, Math.pow10(16));
        assertEquals(100000000000000000L, Math.pow10(17));
        assertEquals(1000000000000000000L, Math.pow10(18));
    }
    
    public void testLog2Int() {
        /*
         * Test exact values
         */
        assertEquals(0, Math.log2((int) 1));
        assertEquals(1, Math.log2((int) 2));
        assertEquals(2, Math.log2((int) 4));
        assertEquals(6, Math.log2((int) 64));
        assertEquals(10, Math.log2((int) 1024));
        assertEquals(31, Math.log2((int) 0x80000000));
        
        /*
         * Test approximate int value
         */
        assertEquals(1, Math.log2((int) 3));
        assertEquals(3, Math.log2((int) 10));
        assertEquals(6, Math.log2((int) 65));        
        assertEquals(5, Math.log2((int) 63));
        
        /*
         * Test zero behavior
         */
        assertEquals(0, Math.log2((int) 0));
    }
    
    public void testLog2Long() {
        /*
         * Test exact values
         */
        assertEquals(0, Math.log2((long) 1));
        assertEquals(1, Math.log2((long) 2));
        assertEquals(2, Math.log2((long) 4));
        assertEquals(6, Math.log2((long) 64));
        assertEquals(10, Math.log2((long) 1024));
        assertEquals(63, Math.log2((long) 0x8000000000000000L));
        assertEquals(31, Math.log2((long) 0x0000000080000000L));
        assertEquals(63, Math.log2((long) 0x8000000000000000L));
        /*
         * Test approximate int value
         */
        assertEquals(1, Math.log2((long) 3));
        assertEquals(3, Math.log2((long) 10));
        assertEquals(6, Math.log2((long) 65));        
        assertEquals(5, Math.log2((long) 63));
        
        /*
         * Test zero behavior
         */
        assertEquals(0, Math.log2((long) 0));
    }
    public void testSignDouble() {
        double delta = 0.0;
        assertEquals(1.0, Math.sign(2.0), delta);
        assertEquals(-1.0, Math.sign(-2.0), delta);
    }
    
    public void testSignFloat() {
        float delta = 0.0F;
        assertEquals(1.0F, Math.sign(2.0F), delta);
        assertEquals(-1.0F, Math.sign(-2.0F), delta);
    }
    
    public void testSignByte() {
        assertEquals((byte) 1, Math.sign((byte) 2));
        assertEquals((byte) (-1), Math.sign((byte) (-2)));
    }
    
    public void testSignShort() {
        assertEquals((short) 1, Math.sign((short) 2));
        assertEquals((short) (-1), Math.sign((short) (-2)));
    }
    
    public void testSignInt() {
        assertEquals((int) 1, Math.sign((int) (2)));
        assertEquals((int) (-1), Math.sign((int) (-2)));
    }
    
    public void testSignLong() {
        assertEquals(1L, Math.sign(2L));
        assertEquals(-1L, Math.sign(-2L));
    }
    
    /**
     * Tests the hashing of doubles.
     */
    public void testHash() {
        double[] testArray = { Double.NaN, Double.POSITIVE_INFINITY,
                Double.NEGATIVE_INFINITY, 1d, 0d, 1E-14, (1 + 1E-14),
                Double.MIN_VALUE, Double.MAX_VALUE };
        
        for (int i = 0; i < testArray.length; i++) {
            for (int j = 0; j < testArray.length; j++) {
                if (i == j) {
                    assertEquals(Math.hash(testArray[i]), Math
                        .hash(testArray[j]));
                    assertEquals(Math.hash(testArray[j]), Math
                        .hash(testArray[i]));
                } else {
                    assertTrue(Math.hash(testArray[i]) != Math
                        .hash(testArray[j]));
                    assertTrue(Math.hash(testArray[j]) != Math
                        .hash(testArray[i]));
                }
            }
        }
    }
}
