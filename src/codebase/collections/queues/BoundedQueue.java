/*
 * Created on 18/Jan/2006
 */
package codebase.collections.queues;

/**
 * A queue decorator that establishes a bound in the number of elements.
 * <p>
 * This class transforms an unbounded queue into a bounded one. It re-implements
 * the method {@link Queue#enqueue(Object)} adding limit checking.
 * <p>
 * This decorator can be used to set artificial limits on unbounded queues.
 * Another use of this decorator is to build safe versions of unbounded queues,
 * since the absence of bound is usually <i>theoretical</i>. In other words,
 * even an unbounded queue may be bounded, for example, by the number of objects
 * storable in memory.
 */
public class BoundedQueue
        extends DecoratorQueue {
    
    /**
     * The maximum number of elements.
     */
    private final int elementLimit;
    
    /**
     * Creates a new bounded queue.
     * 
     * @param instance the queue to be bound
     * @param limit the number of elements *
     * @throws IllegalArgumentException if the limit is not positive.
     */
    public BoundedQueue(final Queue instance, final int limit) {
        super(instance);
        if (limit < 1) {
            throw new IllegalArgumentException("The limit must be positive");
        }
        
        elementLimit = limit;
    }
    
    /**
     * Inserts an object in the queue.
     * 
     * @param object the object to be enqueued
     * @see codebase.collections.queues.Queue#enqueue(java.lang.Object)
     * @throws IllegalStateException if the queue is full
     */
    public final void enqueue(final Object object) {
        if (this.isFull()) {
            throw new IllegalStateException("The queue is full");
        } else {
            super.enqueue(object);
        }
    }
    
    /**
     * Checks if the queue is full.
     * 
     * @return <code>true</code> if the decorated queue is full or the size of
     *         the queue is greater than the bound.
     * @see codebase.collections.queues.Queue#isFull()
     */
    public final boolean isFull() {
        return (super.size() > elementLimit) || super.isFull();
    }
}
