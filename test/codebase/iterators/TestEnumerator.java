/*
 * Created on 3/Set/2005
 */
package codebase.iterators;

import codebase.junit.CodeBlock;
import codebase.junit.EnhancedTestCase;

/**
 * Tests the {@link Enumerator} class.
 */
public class TestEnumerator extends
        EnhancedTestCase {

    /**
     * Tests an enumerator with zero elements.
     */
    public final void testZero() {
        assertEquals(new EmptyIterator<Integer>(), new Enumerator(0, 0));
        assertEquals(new EmptyIterator<Integer>(), new Enumerator(1, 1));
        assertEquals(new EmptyIterator<Integer>(), new Enumerator(7, 7));
    }

    /**
     * Tests an enumerator with one element.
     */
    public final void testOne() {
        assertEquals(new ArrayIterator<Object>(new Object[] { Integer.valueOf(0) }),
                new Enumerator(0, 1));
        assertEquals(new ArrayIterator<Object>(new Object[] { Integer.valueOf(0) }),
                new Enumerator(0, -1));
        assertEquals(new ArrayIterator<Object>(new Object[] { Integer.valueOf(1) }),
                new Enumerator(1, 2));
        assertEquals(new ArrayIterator<Object>(new Object[] { Integer.valueOf(-1) }),
                new Enumerator(-1, -2));
        assertEquals(new ArrayIterator<Object>(new Object[] { Integer.valueOf(7) }),
                new Enumerator(7, 8));
        assertEquals(new ArrayIterator<Object>(new Object[] { Integer.valueOf(-7) }),
                new Enumerator(-7, -8));
    }

    /**
     * Tests an enumerator with multiple element.
     */
    public final void testMultiple() {
        assertEquals(
                new ArrayIterator<Object>(new Object[] { Integer.valueOf(0), Integer.valueOf(1),
                        Integer.valueOf(2) }), new Enumerator(0, 3));
        assertEquals(
                new ArrayIterator<Object>(new Object[] { Integer.valueOf(0), Integer.valueOf(-1),
                        Integer.valueOf(-2) }), new Enumerator(0, -3));
        assertEquals(
                new ArrayIterator<Object>(new Object[] { Integer.valueOf(2), Integer.valueOf(3),
                        Integer.valueOf(4) }), new Enumerator(2, 5));
        assertEquals(
                new ArrayIterator<Object>(new Object[] { Integer.valueOf(-2), Integer.valueOf(-3),
                        Integer.valueOf(-4) }), new Enumerator(-2, -5));
    }

    /**
     * Tests that the reset method works correcltly.
     */
    public final void testReset() {
        Enumerator e = new Enumerator(0, 3);
        assertEquals(e.next(), Integer.valueOf(0));
        assertEquals(e.next(), Integer.valueOf(1));
        e.reset();
        assertEquals(
                new ArrayIterator<Object>(new Object[] { Integer.valueOf(0), Integer.valueOf(1),
                        Integer.valueOf(2) }), e);
    }


    /**
     * Tests that the reset method works correctly.
     */
    public final void testPeek() {
        final Enumerator e = new Enumerator(0, 3);
        assertEquals(e.peek(), Integer.valueOf(0));
        assertEquals(e.next(), Integer.valueOf(0));

        // Peek is idempotent
        assertEquals(e.peek(), Integer.valueOf(1));
        assertEquals(e.peek(), Integer.valueOf(1));

        assertEquals(e.next(), Integer.valueOf(1));
        assertEquals(e.next(), Integer.valueOf(2));

        assertTrue(e.supportsPeek());

        assertThrows(new CodeBlock() {
            public void execute() {
                e.peek();
            }
        }, new IllegalStateException());
    }
}
