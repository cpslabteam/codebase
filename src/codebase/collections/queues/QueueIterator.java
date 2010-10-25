/*
 * Created on 21/Jul/2005
 */
package codebase.collections.queues;

import java.util.Iterator;

/**
 * An iterator over the elements of a queue.
 * <p>
 * It iterates over the elements of a queue object by invoking
 * {@link codebase.collections.queues.Queue#dequeue()} operations.
 * <p>
 * This class provides a convenient way of iterating over the elements of a
 * queue by adapting the queue into an iterator. Additionally can undergo all
 * the operations avaliable for iterators.
 */
public class QueueIterator
        implements Iterator {
    
    /**
     * The instance of the queue be iterated.
     */
    private final Queue queueInstance;
    
    /**
     * Creates a new iterator from a queue.
     * 
     * @param queue the queue to iterated
     * @throws IllegalArgumentException if the queue is not assigned
     */
    public QueueIterator(final Queue queue) {
        if (queue == null) {
            throw new IllegalArgumentException("The queue must be assigned");
        }
        queueInstance = queue;
    }
    
    /**
     * Checks if the underlying queue has elements.
     * 
     * @return <code>true</code> if the queue is not empty.
     * @see java.util.Iterator#hasNext()
     */
    public final boolean hasNext() {
        return !queueInstance.isEmpty();
    }
    
    /**
     * Returns the current object from the queue.
     * 
     * @return the next element dequeued
     * @see java.util.Iterator#next()
     */
    public final Object next() {
        final Object result = queueInstance.dequeue();
        return result;
    }
    
    /**
     * Removes an object from the underlying queue.
     * 
     * @see java.util.Iterator#remove()
     */
    public final void remove() {
        queueInstance.dequeue();
    }
}
