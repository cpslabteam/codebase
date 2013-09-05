/*
 * Created on 05/Jan/2007
 */
package codebase.iterators;

import codebase.junit.EnhancedTestCase;

/**
 * Tests the {@link BoundedIterator} class. TODO: Test remove the behavior
 * 
 * @author Paulo Carreira
 */
public class TestBoundedIterator extends
        EnhancedTestCase {

    /**
     * Tests the scenarios where the bounded iterator returns zero elements.
     */
    public final void testZero() {
        assertEquals(new EmptyIterator<Integer>(), new BoundedIterator<Integer>(
                new EmptyIterator<Integer>(), 7));
        assertEquals(new EmptyIterator<Integer>(), new BoundedIterator<Integer>(
                new EnumeratorIterator(1, 7), 0));
    }

    /**
     * Tests when the bounded iterator returns one element.
     */
    public final void testOne() {
        assertEquals(new EnumeratorIterator(1, 7), new BoundedIterator<Integer>(
                new EnumeratorIterator(1, 8), 6));
        assertEquals(new EnumeratorIterator(1, 4), new BoundedIterator<Integer>(
                new EnumeratorIterator(1, 4), 7));
    }

    /**
     * Tests when the bounded iterator returns multiple elements.
     */
    public final void testMultiple() {
        assertEquals(new EnumeratorIterator(1, 7), new BoundedIterator<Integer>(
                new EnumeratorIterator(1, 10), 6));
        assertEquals(new EnumeratorIterator(1, 7), new BoundedIterator<Integer>(
                new EnumeratorIterator(1, 7), 10));
    }


}
