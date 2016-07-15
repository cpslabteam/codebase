/*
 * Created on 2/Abr/2005
 */
package codebase;

import org.junit.Test;

import codebase.junit.EnhancedTestCase;

/**
 * Tests the {@link ArrayUtil} utility class.
 */
public class TestArrayUtil extends EnhancedTestCase {

    @Test
    public void testAddIntegers() {
        assertEquals(new int[] { 1 }, ArrayUtil.add(new int[] { 1 }, 1));

        assertEquals(new int[] { 1, 2 }, ArrayUtil.add(new int[] { 1 }, 2));

        assertEquals(new int[] { 1, 2 }, ArrayUtil.add(new int[] { 1, 2 }, 2));

        assertEquals(new int[] { 1, 7, 3 }, ArrayUtil.add(new int[] { 1, 7 }, 3));
    }

    @Test
    public void testAddLongs() {
        assertEquals(new long[] { 1L }, ArrayUtil.add(new long[] { 1L }, 1L));

        assertEquals(new long[] { 1L, 2L }, ArrayUtil.add(new long[] { 1L }, 2L));

        assertEquals(new long[] { 1L, 2L }, ArrayUtil.add(new long[] { 1L, 2L }, 2L));

        assertEquals(new long[] { 1L, 7L, 3L }, ArrayUtil.add(new long[] { 1L, 7L }, 3L));
    }

    @Test
    public void testRemoveIntegers() {
        assertEquals(new int[] {}, ArrayUtil.remove(1, new int[] { 1 }));

        assertEquals(new int[] { 1 }, ArrayUtil.remove(2, new int[] { 1, 2 }));

        assertEquals(new int[] { 1 }, ArrayUtil.remove(2, new int[] { 1, 2, 2 }));

        assertEquals(new int[] { 1, 7 }, ArrayUtil.remove(3, new int[] { 1, 3, 7 }));
    }

    @Test
    public void testRemoveLongs() {
        assertEquals(new long[] {}, ArrayUtil.remove(1L, new long[] { 1L }));

        assertEquals(new long[] { 1L }, ArrayUtil.remove(2L, new long[] { 1L, 2L }));

        assertEquals(new long[] { 1L }, ArrayUtil.remove(2L, new long[] { 1L, 2L, 2L }));

        assertEquals(new long[] { 1L, 7L }, ArrayUtil.remove(3L, new long[] { 1L, 3L, 7L }));
    }

    @Test
    public void testInBounds() {
        assertTrue(ArrayUtil.inBounds(new int[] {}, 0, 0));
        assertTrue(ArrayUtil.inBounds(new int[] {}, 1, -1));
        assertTrue(ArrayUtil.inBounds(new int[] { 0 }, 0, 0));
        assertTrue(ArrayUtil.inBounds(new int[] { 1 }, 0, 1));
        assertTrue(ArrayUtil.inBounds(new int[] { 1, 2 }, 1, 2));
        assertTrue(ArrayUtil.inBounds(new int[] { 1, 2, 3, 7 }, 1, 7));
        assertTrue(ArrayUtil.inBounds(new int[] { -3, 2, 3, 7 }, -4, 8));

        assertTrue(!ArrayUtil.inBounds(new int[] { -1, 0, 1 }, 0, 0));
        assertTrue(!ArrayUtil.inBounds(new int[] { -3, 2, 3, 7 }, -3, 6));
        assertTrue(!ArrayUtil.inBounds(new int[] { -3, 2, 3, 7 }, -2, 7));
    }

    @Test
    public void testEquals() {
        // null with null
        assertTrue(ArrayUtil.equals(null, null));

        // empty with empty
        assertTrue(ArrayUtil.equals(new Object[] {}, new Object[] {}));

        // singleton
        assertTrue(ArrayUtil.equals(new Object[] { Integer.valueOf(7) },
                new Object[] { Integer.valueOf(7) }));

        // pairs
        assertTrue(ArrayUtil.equals(new Object[] { Integer.valueOf(1), Integer.valueOf(7) },
                new Object[] { Integer.valueOf(1), Integer.valueOf(7) }));

        // generic
        assertTrue(ArrayUtil.equals(
                new Object[] { Integer.valueOf(2), Integer.valueOf(3), Integer.valueOf(7) },
                new Object[] { Integer.valueOf(2), Integer.valueOf(3), Integer.valueOf(7) }));

        // empty with null
        assertTrue(!ArrayUtil.equals(new Object[] {}, null));

        // different sizes
        assertTrue(!ArrayUtil.equals(new Object[] { Integer.valueOf(7), Integer.valueOf(9) },
                new Object[] { Integer.valueOf(9), Integer.valueOf(7) }));

        // Exchanged
        assertTrue(!ArrayUtil.equals(new Object[] { Integer.valueOf(7), Integer.valueOf(7) },
                new Object[] { Integer.valueOf(7) }));

    }

    @Test
    public void testComplement() {
        // void with void
        assertEquals(new int[] {}, ArrayUtil.minus(new int[] {}, new int[] {}));

        // singleton with void
        assertEquals(new int[] { 1 }, ArrayUtil.minus(new int[] { 1 }, new int[] {}));

        // void with singleton
        assertEquals(new int[] {}, ArrayUtil.minus(new int[] {}, new int[] { 1 }));

        // singleton1 with singleton2
        assertEquals(new int[] { 1 }, ArrayUtil.minus(new int[] { 1 }, new int[] { 2 }));

        // singleton with singleton
        assertEquals(new int[] {}, ArrayUtil.minus(new int[] { 1 }, new int[] { 1 }));

        // singleton with {singleton, singleton2}
        assertEquals(new int[] {}, ArrayUtil.minus(new int[] { 1 }, new int[] { 1, 2 }));

        // singleton with {singleton1, singleton2}
        assertEquals(new int[] { 1 }, ArrayUtil.minus(new int[] { 1 }, new int[] { 2, 3 }));

        // generic
        assertEquals(new int[] { 1, 2, 3 },
                ArrayUtil.minus(new int[] { 1, 2, 3, 4 }, new int[] { 4, 5 }));
    }

    @Test
    public void testIntersect() {
        // void with void
        assertEquals(new int[] {}, ArrayUtil.intersect(new int[] {}, new int[] {}));

        // singleton with void
        assertEquals(new int[] {}, ArrayUtil.intersect(new int[] { 1 }, new int[] {}));

        // void with singleton
        assertEquals(new int[] {}, ArrayUtil.intersect(new int[] { 1 }, new int[] {}));

        // singleton1 with singleton2
        assertEquals(new int[] {}, ArrayUtil.intersect(new int[] { 1 }, new int[] { 2 }));

        // singleton with singleton
        assertEquals(new int[] { 1 }, ArrayUtil.intersect(new int[] { 1 }, new int[] { 1 }));

        // singleton with {singleton, singleton2}
        assertEquals(new int[] { 1 }, ArrayUtil.intersect(new int[] { 1 }, new int[] { 1, 2 }));

        // singleton with {singleton1, singleton2}
        assertEquals(new int[] {}, ArrayUtil.intersect(new int[] { 1 }, new int[] { 2, 3 }));

        // generic
        assertEquals(new int[] { 2, 4 },
                ArrayUtil.intersect(new int[] { 1, 2, 3, 4, 5 }, new int[] { 2, 4 }));
    }

    @Test
    public void testUnique() {
        assertEquals(new int[] {}, ArrayUtil.unique(new int[] {}));

        assertEquals(new int[] { 1 }, ArrayUtil.unique(new int[] { 1 }));

        assertEquals(new int[] { 1, 2 }, ArrayUtil.unique(new int[] { 1, 2 }));

        assertEquals(new int[] { 1, 2 }, ArrayUtil.unique(new int[] { 1, 2, 1 }));

        assertEquals(new int[] { 1 }, ArrayUtil.unique(new int[] { 1, 1 }));

        assertEquals(new int[] { 1, 2, 3 }, ArrayUtil.unique(new int[] { 1, 2, 1, 3, 1, 2, 1 }));
    }

    @Test
    public void testEnum() {
        assertEquals(new int[] {}, ArrayUtil.enumeration(0, 0));

        assertEquals(new int[] {}, ArrayUtil.enumeration(2, 2));

        assertEquals(new int[] { 2 }, ArrayUtil.enumeration(2, 3));

        assertEquals(new int[] { 2, 3, 4 }, ArrayUtil.enumeration(2, 5));
    }

    @Test
    public void testFlatten() {
        assertEquals(new Object[] {}, ArrayUtil.flatten(new Object[] {}));

        final Object o = new Object();
        assertEquals(new Object[] { o }, ArrayUtil.flatten(o));

        final Object o1 = new Object();
        final Object o2 = new Object();
        assertEquals(new Object[] { o1, o2 }, ArrayUtil.flatten(new Object[] { o1, o2 }));

        assertEquals(new Object[] { o1, o2 }, ArrayUtil.flatten(
                new Object[] { new Object[] {}, o1, new Object[] {}, o2, new Object[] {} }));

        final Object o3 = new Object();
        assertEquals(new Object[] { o1, o2, o3 },
                ArrayUtil.flatten(new Object[] { o1, o2, new Object[] { o3 } }));

        final Object ox = new Object();
        final Object o4 = new Object();
        final Object o5 = new Object();
        assertEquals(new Object[] { ox, o1, ox, o2, o3, o4, o5 }, ArrayUtil.flatten(new Object[] {
                new Object[] { ox }, o1, new Object[] { ox }, o2, new Object[] { o3, o4, o5 } }));
    }

    @Test
    public void testFindNotNullIndices() {
        assertEquals(new int[] {}, ArrayUtil.findNotNullIndices(new Object[] {}));

        assertEquals(new int[] {}, ArrayUtil.findNotNullIndices(new Object[] { null }));

        assertEquals(new int[] { 0 }, ArrayUtil.findNotNullIndices(new Object[] { new Object() }));

        assertEquals(new int[] {},
                ArrayUtil.findNotNullIndices(new Object[] { null, null, null, null }));

        assertEquals(new int[] { 0 },
                ArrayUtil.findNotNullIndices(new Object[] { new Object(), null }));

        assertEquals(new int[] { 1 },
                ArrayUtil.findNotNullIndices(new Object[] { null, new Object() }));

        assertEquals(new int[] { 0, 1 },
                ArrayUtil.findNotNullIndices(new Object[] { new Object(), new Object() }));

        assertEquals(new int[] { 1, 2, 4 }, ArrayUtil.findNotNullIndices(
                new Object[] { null, new Object(), new Object(), null, new Object(), null }));
    }
}
