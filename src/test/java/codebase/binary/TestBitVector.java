/*
 * Created on 5/Mai/2005
 */
package codebase.binary;

import org.junit.Test;

import junit.framework.TestCase;

/**
 * Tests the {@link BitVector} class.
 */
public class TestBitVector extends TestCase {

    @Test
    public void testSetClearGet() {
        // Use a vector with size 1
        BitVector v1 = new BitVector(1);
        assertEquals(false, v1.get(0));
        v1.set(0);
        assertEquals(true, v1.get(0));

        // Use a vector with size 19
        BitVector v2 = new BitVector(19);
        assertEquals(false, v2.get(0));
        assertEquals(false, v2.get(18));
        v2.set(0);
        assertEquals(true, v2.get(0));
        v2.set(18);
        assertEquals(true, v2.get(18));

        // Check non-interference
        assertEquals(true, v2.get(0));

        // Check cleared bit
        v2.clear(18);
        assertEquals(false, v2.get(18));
    }

    @Test
    public void testCount() {
        BitVector v1 = new BitVector(1);
        assertEquals(0, v1.count());
        v1.set(0);
        assertEquals(1, v1.count());

        BitVector v2 = new BitVector(19);
        v2.set(0);
        v2.set(18);
        v2.set(2);
        v2.set(7);
        v2.set(13);
        assertEquals(5, v2.count());
    }
}
