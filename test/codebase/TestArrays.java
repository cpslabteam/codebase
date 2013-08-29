/*
 * Created on 2/Abr/2005
 */
package codebase;

import codebase.junit.EnhancedTestCase;

/**
 * Test cases for the {@link Arrays} utility class.
 */
public class TestArrays extends
        EnhancedTestCase {

    public void testAddIntegers() {
        assertEquals(new int[] { 1 }, Arrays.add(new int[] { 1 }, 1));

        assertEquals(new int[] { 1, 2 }, Arrays.add(new int[] { 1 }, 2));

        assertEquals(new int[] { 1, 2 }, Arrays.add(new int[] { 1, 2 }, 2));

        assertEquals(new int[] { 1, 7, 3 }, Arrays.add(new int[] { 1, 7 }, 3));
    }

    public void testAddLongs() {
        assertEquals(new long[] { 1L }, Arrays.add(new long[] { 1L }, 1L));

        assertEquals(new long[] { 1L, 2L }, Arrays.add(new long[] { 1L }, 2L));

        assertEquals(new long[] { 1L, 2L }, Arrays.add(new long[] { 1L, 2L }, 2L));

        assertEquals(new long[] { 1L, 7L, 3L }, Arrays.add(new long[] { 1L, 7L }, 3L));
    }

    public void testRemoveIntegers() {
        assertEquals(new int[] {}, Arrays.remove(1, new int[] { 1 }));

        assertEquals(new int[] { 1 }, Arrays.remove(2, new int[] { 1, 2 }));

        assertEquals(new int[] { 1 }, Arrays.remove(2, new int[] { 1, 2, 2 }));

        assertEquals(new int[] { 1, 7 }, Arrays.remove(3, new int[] { 1, 3, 7 }));
    }

    public void testRemoveLongs() {
        assertEquals(new long[] {}, Arrays.remove(1L, new long[] { 1L }));

        assertEquals(new long[] { 1L }, Arrays.remove(2L, new long[] { 1L, 2L }));

        assertEquals(new long[] { 1L }, Arrays.remove(2L, new long[] { 1L, 2L, 2L }));

        assertEquals(new long[] { 1L, 7L }, Arrays.remove(3L, new long[] { 1L, 3L, 7L }));
    }

    public void testInBounds() {
        assertTrue(Arrays.inBounds(new int[] {}, 0, 0));
        assertTrue(Arrays.inBounds(new int[] {}, 1, -1));
        assertTrue(Arrays.inBounds(new int[] { 0 }, 0, 0));
        assertTrue(Arrays.inBounds(new int[] { 1 }, 0, 1));
        assertTrue(Arrays.inBounds(new int[] { 1, 2 }, 1, 2));
        assertTrue(Arrays.inBounds(new int[] { 1, 2, 3, 7 }, 1, 7));
        assertTrue(Arrays.inBounds(new int[] { -3, 2, 3, 7 }, -4, 8));

        assertTrue(!Arrays.inBounds(new int[] { -1, 0, 1 }, 0, 0));
        assertTrue(!Arrays.inBounds(new int[] { -3, 2, 3, 7 }, -3, 6));
        assertTrue(!Arrays.inBounds(new int[] { -3, 2, 3, 7 }, -2, 7));
    }

    public void testEquals() {
        // null with null
        assertTrue(Arrays.equals(null, null));

        // empty with empty
        assertTrue(Arrays.equals(new Object[] {}, new Object[] {}));

        // singleton
        assertTrue(Arrays.equals(new Object[] { Integer.valueOf(7) },
                new Object[] { Integer.valueOf(7) }));

        // pairs
        assertTrue(Arrays.equals(new Object[] { Integer.valueOf(1), Integer.valueOf(7) },
                new Object[] { Integer.valueOf(1), Integer.valueOf(7) }));

        // generic
        assertTrue(Arrays.equals(
                new Object[] { Integer.valueOf(2), Integer.valueOf(3), Integer.valueOf(7) },
                new Object[] { Integer.valueOf(2), Integer.valueOf(3), Integer.valueOf(7) }));

        // empty with null
        assertTrue(!Arrays.equals(new Object[] {}, null));

        // different sizes
        assertTrue(!Arrays.equals(new Object[] { Integer.valueOf(7), Integer.valueOf(9) },
                new Object[] { Integer.valueOf(9), Integer.valueOf(7) }));

        // Exchanged
        assertTrue(!Arrays.equals(new Object[] { Integer.valueOf(7), Integer.valueOf(7) },
                new Object[] { Integer.valueOf(7) }));

    }

    public void testComplement() {
        // void with void
        assertEquals(new int[] {}, Arrays.minus(new int[] {}, new int[] {}));

        // singleton with void
        assertEquals(new int[] { 1 }, Arrays.minus(new int[] { 1 }, new int[] {}));

        // void with singleton
        assertEquals(new int[] {}, Arrays.minus(new int[] {}, new int[] { 1 }));

        // singleton1 with singleton2
        assertEquals(new int[] { 1 }, Arrays.minus(new int[] { 1 }, new int[] { 2 }));

        // singleton with singleton
        assertEquals(new int[] {}, Arrays.minus(new int[] { 1 }, new int[] { 1 }));

        // singleton with {singleton, singleton2}
        assertEquals(new int[] {}, Arrays.minus(new int[] { 1 }, new int[] { 1, 2 }));

        // singleton with {singleton1, singleton2}
        assertEquals(new int[] { 1 }, Arrays.minus(new int[] { 1 }, new int[] { 2, 3 }));

        // generic
        assertEquals(new int[] { 1, 2, 3 },
                Arrays.minus(new int[] { 1, 2, 3, 4 }, new int[] { 4, 5 }));
    }

    public void testIntersect() {
        // void with void
        assertEquals(new int[] {}, Arrays.intersect(new int[] {}, new int[] {}));

        // singleton with void
        assertEquals(new int[] {}, Arrays.intersect(new int[] { 1 }, new int[] {}));

        // void with singleton
        assertEquals(new int[] {}, Arrays.intersect(new int[] { 1 }, new int[] {}));

        // singleton1 with singleton2
        assertEquals(new int[] {}, Arrays.intersect(new int[] { 1 }, new int[] { 2 }));

        // singleton with singleton
        assertEquals(new int[] { 1 }, Arrays.intersect(new int[] { 1 }, new int[] { 1 }));

        // singleton with {singleton, singleton2}
        assertEquals(new int[] { 1 }, Arrays.intersect(new int[] { 1 }, new int[] { 1, 2 }));

        // singleton with {singleton1, singleton2}
        assertEquals(new int[] {}, Arrays.intersect(new int[] { 1 }, new int[] { 2, 3 }));

        // generic
        assertEquals(new int[] { 2, 4 },
                Arrays.intersect(new int[] { 1, 2, 3, 4, 5 }, new int[] { 2, 4 }));
    }

    public void testUnique() {
        assertEquals(new int[] {}, Arrays.unique(new int[] {}));

        assertEquals(new int[] { 1 }, Arrays.unique(new int[] { 1 }));

        assertEquals(new int[] { 1, 2 }, Arrays.unique(new int[] { 1, 2 }));

        assertEquals(new int[] { 1, 2 }, Arrays.unique(new int[] { 1, 2, 1 }));

        assertEquals(new int[] { 1 }, Arrays.unique(new int[] { 1, 1 }));

        assertEquals(new int[] { 1, 2, 3 }, Arrays.unique(new int[] { 1, 2, 1, 3, 1, 2, 1 }));
    }

    public void testEnum() {
        assertEquals(new int[] {}, Arrays.enumeration(0, 0));

        assertEquals(new int[] {}, Arrays.enumeration(2, 2));

        assertEquals(new int[] { 2 }, Arrays.enumeration(2, 3));

        assertEquals(new int[] { 2, 3, 4 }, Arrays.enumeration(2, 5));
    }

    public void testFlatten() {
        assertEquals(new Object[] {}, Arrays.flatten(new Object[] {}));

        final Object o = new Object();
        assertEquals(new Object[] { o }, Arrays.flatten(o));

        final Object o1 = new Object();
        final Object o2 = new Object();
        assertEquals(new Object[] { o1, o2 }, Arrays.flatten(new Object[] { o1, o2 }));

        assertEquals(
                new Object[] { o1, o2 },
                Arrays.flatten(new Object[] { new Object[] {}, o1, new Object[] {}, o2,
                        new Object[] {} }));

        final Object o3 = new Object();
        assertEquals(new Object[] { o1, o2, o3 },
                Arrays.flatten(new Object[] { o1, o2, new Object[] { o3 } }));

        final Object ox = new Object();
        final Object o4 = new Object();
        final Object o5 = new Object();
        assertEquals(
                new Object[] { ox, o1, ox, o2, o3, o4, o5 },
                Arrays.flatten(new Object[] { new Object[] { ox }, o1, new Object[] { ox }, o2,
                        new Object[] { o3, o4, o5 } }));
    }

    public void testFindNotNullIndices() {
        assertEquals(new int[] {}, Arrays.findNotNullIndices(new Object[] {}));

        assertEquals(new int[] {}, Arrays.findNotNullIndices(new Object[] { null }));

        assertEquals(new int[] { 0 }, Arrays.findNotNullIndices(new Object[] { new Object() }));

        assertEquals(new int[] {},
                Arrays.findNotNullIndices(new Object[] { null, null, null, null }));

        assertEquals(new int[] { 0 },
                Arrays.findNotNullIndices(new Object[] { new Object(), null }));

        assertEquals(new int[] { 1 },
                Arrays.findNotNullIndices(new Object[] { null, new Object() }));

        assertEquals(new int[] { 0, 1 },
                Arrays.findNotNullIndices(new Object[] { new Object(), new Object() }));

        assertEquals(
                new int[] { 1, 2, 4 },
                Arrays.findNotNullIndices(new Object[] { null, new Object(), new Object(), null,
                        new Object(), null }));
    }
}
