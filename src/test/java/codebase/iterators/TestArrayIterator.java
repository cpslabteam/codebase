/*
 * Created on 22/Out/2005
 */
package codebase.iterators;

import codebase.junit.CodeBlock;
import codebase.junit.EnhancedTestCase;

/**
 * Tests the {@link ArrayIterator} class.
 */
public class TestArrayIterator extends
        EnhancedTestCase {


    /**
     * Tests an array iterator over an empty array.
     */
    public final void testZero() {
        assertEquals(new EmptyIterator<Object>(), new ArrayIterator<Object>(new Object[] {}));
    }

    /**
     * Tests an enumerator with one element.
     */
    public final void testOne() {
        final ArrayIterator<Object> oneIterator = new ArrayIterator<Object>(
                new Object[] { Integer.valueOf(7) });

        assertEquals(oneIterator.peek(), Integer.valueOf(7));
        assertEquals(oneIterator.next(), Integer.valueOf(7));
        oneIterator.reset();
        oneIterator.update(Integer.valueOf(14));
        assertEquals(oneIterator.next(), Integer.valueOf(14));

        assertThrows(new CodeBlock() {
            public void execute() {
                oneIterator.peek();
            };
        }, new IllegalStateException());
    }

    /**
     * Tests an enumerator with one element.
     */
    public final void testNormal() {
        final ArrayIterator<Object> oneIterator = new ArrayIterator<Object>(new Object[] {
                Integer.valueOf(7), Integer.valueOf(8), Integer.valueOf(12) });

        assertEquals(oneIterator.peek(), Integer.valueOf(7));
        assertEquals(oneIterator.next(), Integer.valueOf(7));
        assertEquals(oneIterator.peek(), Integer.valueOf(8));
        oneIterator.update(Integer.valueOf(14));
        assertEquals(oneIterator.peek(), Integer.valueOf(14));
        assertEquals(oneIterator.next(), Integer.valueOf(14));
        assertEquals(oneIterator.next(), Integer.valueOf(12));
        assertThrows(new CodeBlock() {
            public void execute() {
                oneIterator.peek();
            };
        }, new IllegalStateException());

        oneIterator.reset();
        assertEquals(oneIterator.next(), Integer.valueOf(7));
        assertEquals(oneIterator.next(), Integer.valueOf(14));
        assertEquals(oneIterator.next(), Integer.valueOf(12));
    }
}
