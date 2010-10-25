/*
 * Created on 05/Jan/2007
 */
package codebase.iterators;

import codebase.tests.EnhancedTestCase;

/**
 * Tests the {@link BoundedIterator} class.
 *  
 * TODO: Test remove 
 * 
 * @author Paulo Carreira
 */
public class TestBoundedIterator
        extends EnhancedTestCase {
    
    /**
     * Tests the scenarios where the bounded iterator returns zero elements.
     */
    public final void testZero() {
        assertEquals(new EmptyIterator(), new BoundedIterator(
            new EmptyIterator(), 7));
        assertEquals(new EmptyIterator(), new BoundedIterator(new Enumerator(1,
            7), 0));
    }
    
    /**
     * Tests when the bounded iterator returns one element.
     */
    public final void testOne() {                
        assertEquals(new Enumerator(1, 7), new BoundedIterator(new Enumerator(
            1, 8), 6));
        assertEquals(new Enumerator(1, 4), new BoundedIterator(new Enumerator(
            1, 4), 7));
    }
    
    /**
     * Tests when the bounded iterator returns multiple element.
     */
    public final void testMultiple() {
        assertEquals(new Enumerator(1, 7), new BoundedIterator(new Enumerator(
            1, 10), 6));
        assertEquals(new Enumerator(1, 7), new BoundedIterator(new Enumerator(
            1, 7), 10));
    }
    
   
}
