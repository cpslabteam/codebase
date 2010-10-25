/*
 * Created on 15/Out/2005
 */
package codebase.collections.queues;

import java.util.NoSuchElementException;

/**
 * A decorator adds <code>peek</code> operation support to a queue.
 * <p>
 * This class implements a <code>peek</code> operation based only on
 * {@link codebase.collections.queues.Queue#enqueue(Object)} and
 * {@link codebase.collections.queues.Queue#dequeue()} operations.
 * <p>
 * It is important to see that the {@link codebase.collections.queues.Queue#peek()} method
 * only shows the element which currently is the next element. The next call of
 * {@link codebase.collections.queues.Queue#peek()} is not guaranteed to return the peeked
 * element. The just-seen object may have been handed to another call to
 * {@link codebase.collections.queues.Queue#dequeue()}.
 */
public abstract class PeekableQueue
        extends DecoratorQueue
        implements Queue {
    
    /**
     * Internally used reference to the next element in this queue.
     */
    protected Object next;
    
    /**
     * Internally used to remember whether the next element has already been
     * computed or not.
     */
    protected boolean computedNext;
    
    /**
     * Creates a new peekable queue.
     * 
     * @param instance the instance queue to which the peek support is to be
     *            added.
     */
    public PeekableQueue(final Queue instance) {
        super(instance);
    }
    
    /**
     * Returns the next element of the queue.
     * <p>
     * The next element is dequeued and assigned to a temporary variable.
     * 
     * @return the <i>next</i> element in the queue.
     * @throws IllegalStateException if the queue is closed.
     * @throws NoSuchElementException if queue has no more elements.
     */
    public final Object peek() throws IllegalStateException,
            NoSuchElementException {
        if (!computedNext) {
            next = super.dequeue();
            computedNext = true;
        }
        return next;
    }
    
    /**
     * Takes out the <i>next</i> element from the queue.
     * 
     * @return the temporary element taken from the queue. If the element as
     *         already been returned it dequeues a fresh element.
     * @throws IllegalStateException if the queue is already closed when this
     *             method is called.
     * @throws NoSuchElementException queue has no more elements.
     */
    public final Object dequeue() throws IllegalStateException,
            NoSuchElementException {
        if (!computedNext) {
            next = super.dequeue();
        }
        computedNext = false;
        return next;
    }
    
    /**
     * Checks if the queue is empty.
     * <p>
     * 
     * @return <code>true</code> if no valid temporary element exists and the
     *         decorated queue is empty. Return <code>false</code> otherwise.
     */
    public final boolean isEmpty() {
        return !computedNext && super.isEmpty();
    }
    
    /**
     * Returns the number of elements in this queue.
     * <p>
     * 
     * @return the number of elements in the decorated queue, adding one if a
     *         valid temporary element exists.
     */
    public final int size() {
        if (computedNext) {
            return super.size() + 1;
        } else {
            return super.size();
        }
    }
    
    /**
     * Clears the underlying queue instance.
     * 
     * @see codebase.collections.queues.Queue#clear()
     */
    public final void clear() {
        super.clear();
        computedNext = false;
        next = null;
    }
    
}
