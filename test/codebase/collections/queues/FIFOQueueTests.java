/*
 * Created on 31/Jan/2006
 */
package codebase.collections.queues;

import codebase.collections.queues.Queue;
import codebase.tests.EnhancedTestCase;

/**
 * Base tests for FIFO queues.
 */
final class FIFOQueueTests {

    /**
     * Prevent instantiation
     */
    private FIFOQueueTests() {
    }

    /**
     * Tests the FIFO behavior of a queue
     *
     * @param queue an empty queue object
     */
    static void testFIFOBehavior(final Queue queue) {
        final Object e1 = new Integer(7);
        final Object e2 = new Integer(19);
        final Object e3 = new Integer(27);

        queue.enqueue(e1);
        queue.enqueue(e2);
        queue.enqueue(e3);

        EnhancedTestCase.assertEquals(e1, queue.dequeue());
        EnhancedTestCase.assertEquals(e2, queue.dequeue());
        EnhancedTestCase.assertEquals(e3, queue.dequeue());
    }
}
