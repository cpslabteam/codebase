/*
 * Created on 22/Out/2005
 */
package codebase.iterators;

import codebase.tests.CodeBlock;
import codebase.tests.EnhancedTestCase;

public class TestArrayIterator
        extends EnhancedTestCase {
    
    /**
     * Tests an array iterator over a null array.
     */
    public final void testNull() {
        assertThrows(new CodeBlock() {
            public void execute() {
                new ArrayIterator(null);
            };
        }, new IllegalArgumentException());
    }
    
    /**
     * Tests an array iterator over an empty array.
     */
    public final void testZero() {
        assertEquals(new EmptyIterator(), new ArrayIterator(new Object[] {}));
    }
    
    /**
     * Tests an enumerator with one element.
     */
    public final void testOne() {
        final ArrayIterator oneIterator = new ArrayIterator(
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
        final ArrayIterator oneIterator = new ArrayIterator(new Object[] {
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
