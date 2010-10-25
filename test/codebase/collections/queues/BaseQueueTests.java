/*
 * Created on 31/Jan/2006
 */
package codebase.collections.queues;

import codebase.collections.queues.Queue;
import codebase.tests.EnhancedTestCase;

/**
 * The tests of the interface {@link Queue}
 * <p>
 * This utilty class allow to test that a given queue object passes the basic
 * queue tests.
 */
final class BaseQueueTests {
    
    /**
     * Prevent instantiation
     */
    private BaseQueueTests() {
    }
    
    /**
     * Checks that after the elements are inserted via
     * {@link Queue#enqueue(Object)} they can be retrieved via
     * {@link Queue#dequeue()}.
     * <p>
     * No assumptions are made on the element ordering. The order by which the
     * elements are returned does not have to be order by which the elements
     * were inserted.
     * 
     * @param queue the object of the queue to be tested
     */
    public static void testEnqueueDequeue(final Queue queue) {
        final Object e1 = new Integer(7);
        final Object e2 = new Integer(19);
        final Object e3 = new Integer(27);
        
        queue.enqueue(e1);
        queue.enqueue(e2);
        queue.enqueue(e3);
        
        final Object d1 = queue.dequeue();
        final Object d2 = queue.dequeue();
        final Object d3 = queue.dequeue();
        
        EnhancedTestCase.assertBagEquals(new Object[] { e1, e2, e3 },
            new Object[] { d1, d2, d3 });
    }
    
    /**
     * Tests that a queue empty before enqueuing becomes non-empty after
     * enqueueing.
     * <p>
     * Inserts and element in the queue and tests that it has become non-empty.
     * It then deques the element and checks that the queue has become emprt
     * again.
     * 
     * @param queue an empty queue object
     */
    public static void testIsEmpty(final Queue queue) {
        final Object e1 = new Integer(7);
        EnhancedTestCase.assertTrue(queue.isEmpty());
        queue.enqueue(e1);
        EnhancedTestCase.assertTrue(!queue.isEmpty());
        queue.dequeue();
        EnhancedTestCase.assertTrue(queue.isEmpty());
    }
    
    /**
     * Tests that a queue becomes empty after calling {@link Queue#clear()}.
     * <p>
     * Checks that {@link Queue#clear()} can be called on an empty queue. Then
     * it inserts elements, clears the queue cand veifies that it has become
     * empty.
     * 
     * @param queue an empty queue object
     */
    public static void testClear(final Queue queue) {
        EnhancedTestCase.assertTrue(queue.isEmpty());
        
        final Object e1 = new Integer(7);
        final Object e2 = new Integer(19);
        final Object e3 = new Integer(27);
        
        queue.enqueue(e1);
        queue.enqueue(e2);
        queue.enqueue(e3);
        
        EnhancedTestCase.assertTrue(!queue.isEmpty());
        
        queue.clear();
        EnhancedTestCase.assertTrue(queue.isEmpty());
    }
    
    /**
     * Tests the peek operation.
     * <p>
     * Inserts an element and checks that the peek operation, if supported
     * returns that element.
     * 
     * @param queue an empty queue object
     */
    public static void testPeek(final Queue queue) {
        final Object e1 = new Integer(7);
        queue.enqueue(e1);
        EnhancedTestCase.assertEquals(1, queue.size());
        try {
            final Object o = queue.peek();
            EnhancedTestCase.assertEquals(e1, o);
        } catch (UnsupportedOperationException e) {
            // no problem
        }
    }
    
    /**
     * Test that the size of the queue is increment or decremented as elements
     * are inserted or removed from the queue.
     * <p>
     * Inserts elements and verifies that the size of queue increses then, it
     * takes the elements to verify that the size decresases. Finally, it clears
     * the queue and verifies that the size was set to zero.
     * 
     * @param queue an empty queue object
     */
    public static void testSizeIncreaseDecrease(final Queue queue) {
        EnhancedTestCase.assertTrue(queue.isEmpty());
        EnhancedTestCase.assertEquals(0, queue.size());
        
        final Object e1 = new Integer(7);
        final Object e2 = new Integer(19);
        final Object e3 = new Integer(27);
        
        // Check increase
        int initialSize;
        
        initialSize = queue.size();
        queue.enqueue(e1);
        EnhancedTestCase.assertEquals(initialSize + 1, queue.size());
        
        initialSize = queue.size();
        queue.enqueue(e2);
        EnhancedTestCase.assertEquals(initialSize + 1, queue.size());
        
        initialSize = queue.size();
        queue.enqueue(e3);
        EnhancedTestCase.assertEquals(initialSize + 1, queue.size());
        
        // Check decrease
        initialSize = queue.size();
        queue.dequeue();
        EnhancedTestCase.assertEquals(initialSize - 1, queue.size());
        
        initialSize = queue.size();
        queue.dequeue();
        EnhancedTestCase.assertEquals(initialSize - 1, queue.size());
        
        initialSize = queue.size();
        queue.dequeue();
        EnhancedTestCase.assertEquals(initialSize - 1, queue.size());
        
    }
    
    /**
     * Test that the size of the queue is set to zero after
     * {@link Queue#clear()}.
     * <p>
     * 
     * @param queue an empty queue object
     */
    public static void testSizeClear(final Queue queue) {
        final Object e1 = new Integer(7);
        final Object e2 = new Integer(19);
        final Object e3 = new Integer(27);
        
        // Check clear
        queue.enqueue(e1);
        queue.enqueue(e2);
        queue.enqueue(e3);
        
        EnhancedTestCase.assertEquals(3, queue.size());
        queue.clear();
        EnhancedTestCase.assertEquals(0, queue.size());
    }
    
    /**
     * Tests that a queue size is invariant to the peek operation.
     * 
     * @param queue an empty queue object
     */
    public static void testSizePeekInvariance(final Queue queue) {
        final Object e1 = new Integer(7);
        queue.enqueue(e1);
        EnhancedTestCase.assertEquals(1, queue.size());
        try {
            queue.peek();
            EnhancedTestCase.assertEquals(1, queue.size());
        } catch (UnsupportedOperationException e) {
            // no problem, the queue just does not support peek.
        }
    }
}
