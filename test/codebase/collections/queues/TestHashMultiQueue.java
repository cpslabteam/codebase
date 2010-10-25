/*
 * Created on 31/Jan/2006
 */
package codebase.collections.queues;

import codebase.collections.queues.HashMultiQueue;
import codebase.collections.queues.ListQueue;
import codebase.collections.queues.MultiQueue;
import codebase.collections.queues.Queue;
import junit.framework.TestCase;

/**
 * Tests the {@link codebase.collections.queues.HashMultiQueue} class. TODO
 */
public class TestHashMultiQueue
        extends TestCase {

    /**
     * @return a multi queue with only one element
     */
    private MultiQueue makeOneElementMultiQueue() {
        return new HashMultiQueue(new Queue[] {new ListQueue()});
    }

    /**
     * @return a queue with 3 elements and with <code>null</code> objects.
     */
    private MultiQueue makeMultiQueueWithHoles() {
        return new HashMultiQueue(new Queue[] {
                null, new ListQueue(), null, null, new ListQueue(),
                new ListQueue(), null});
    }

    /*
     * Base queue tests
     */

    /**
     * Checks that after the elements are inserted via
     * {@link Queue#enqueue(Object)} they can be retrieved via
     * {@link Queue#dequeue()}.
     *
     * @see BaseQueueTests#testEnqueueDequeue(Queue)
     */
    public final void testBaseEnqueueDequeue() {
        BaseQueueTests.testEnqueueDequeue(makeOneElementMultiQueue());
        BaseQueueTests.testEnqueueDequeue(makeMultiQueueWithHoles());
    }

    /**
     * Tests that a queue empty before enqueuing becomes non-empty after
     * enqueueing.
     *
     * @see BaseQueueTests#testIsEmpty(Queue)
     */
    public final void testIsEmpty() {
        BaseQueueTests.testIsEmpty(makeOneElementMultiQueue());
        BaseQueueTests.testIsEmpty(makeMultiQueueWithHoles());
    }

    /**
     * Tests that a queue becomes empty after calling {@link Queue#clear()}.
     *
     * @see BaseQueueTests#testClear(Queue)
     */
    public final void testClear() {
        BaseQueueTests.testClear(makeOneElementMultiQueue());
        BaseQueueTests.testClear(makeMultiQueueWithHoles());
    }

    /**
     * Tests the peek operation.
     *
     * @see BaseQueueTests#testPeek(Queue)
     */
    public final void testPeek() {
        BaseQueueTests.testPeek(makeOneElementMultiQueue());
        BaseQueueTests.testPeek(makeMultiQueueWithHoles());
    }

    /**
     * Test that the size of the queue is increment or decremented as elements
     * are inserted or removed from the queue.
     *
     * @see BaseQueueTests#testSizeIncreaseDecrease(Queue)
     */
    public final void testSizeIncreaseDecrease() {
        BaseQueueTests.testSizeIncreaseDecrease(makeOneElementMultiQueue());
        BaseQueueTests.testSizeIncreaseDecrease(makeMultiQueueWithHoles());
    }

    /**
     * Test that the size of the list queue is set to zero after
     * {@link Queue#clear()}.
     *
     * @see BaseQueueTests#testSizeClear(Queue)
     */
    public final void testSizeClear() {
        BaseQueueTests.testSizeClear(makeOneElementMultiQueue());
        BaseQueueTests.testSizeClear(makeMultiQueueWithHoles());
    }

    /**
     * Tests that the list queue size is invariant to the peek operation.
     *
     * @see BaseQueueTests#testSizePeekInvariance(Queue)
     */
    public final void testSizePeekInvariance() {
        BaseQueueTests.testSizePeekInvariance(makeOneElementMultiQueue());
        BaseQueueTests.testSizePeekInvariance(makeMultiQueueWithHoles());
    }
}
