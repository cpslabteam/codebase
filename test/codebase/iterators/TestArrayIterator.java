/*
 * Created on 22/Out/2005
 */
package codebase.iterators;

import codebase.iterators.ArrayIterator;
import codebase.iterators.EmptyIterator;
import codebase.junit.CodeBlock;
import codebase.junit.EnhancedTestCase;

public class TestArrayIterator
        extends EnhancedTestCase {
    
    /**
     * Tests an array iterator over a null array.
     */
    public final void testNull() {
        assertThrows(new CodeBlock() {
            public void execute() {
                new ArrayIterator<Object>(null);
            };
        }, new IllegalArgumentException());
    }
    
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
            new Object[] { new Integer(7) });
        
        assertEquals(oneIterator.peek(), new Integer(7));
        assertEquals(oneIterator.next(), new Integer(7));
        oneIterator.reset();
        oneIterator.update(new Integer(14));
        assertEquals(oneIterator.next(), new Integer(14));
        
        assertThrows(new CodeBlock() {
            public void execute() {
                oneIterator.peek();
            };
        }, new IllegalStateException());
    }
    
    /**
     * Tests an enumerator with one element
     */
    public final void testNormal() {
        final ArrayIterator<Object> oneIterator = new ArrayIterator<Object>(new Object[] {
                new Integer(7), new Integer(8), new Integer(12) });
        
        assertEquals(oneIterator.peek(), new Integer(7));
        assertEquals(oneIterator.next(), new Integer(7));
        assertEquals(oneIterator.peek(), new Integer(8));
        oneIterator.update(new Integer(14));
        assertEquals(oneIterator.peek(), new Integer(14));
        assertEquals(oneIterator.next(), new Integer(14));
        assertEquals(oneIterator.next(), new Integer(12));
        assertThrows(new CodeBlock() {
            public void execute() {
                oneIterator.peek();
            };
        }, new IllegalStateException());
        
        oneIterator.reset();
        assertEquals(oneIterator.next(), new Integer(7));
        assertEquals(oneIterator.next(), new Integer(14));
        assertEquals(oneIterator.next(), new Integer(12));
    }
}
