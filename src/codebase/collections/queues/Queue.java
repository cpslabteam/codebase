/*
 * Created on 15/Out/2005
 */
package codebase.collections.queues;

import java.util.NoSuchElementException;

/**
 * Interface of a queue.
 * <p>
 * A queue data structure that enforces a specific ordering <i>strategy</i>
 * while accessing the elements. Elements are added (or <i>enqueued</i>) and
 * removed (or <i>dequeued</i>) from the queue in a specific order. A queue is
 * normally understood as a FIFO (first-in, first-out) structure. However, there
 * are other types of queues (like, e.g. <i>priority queues</i>) with different
 * access strategies to the elements.
 * <p>
 * In contrast to sets, allow duplicate elements, they have bag semantics. More
 * formally, queues typically allow pairs of elements <code>e1</code> and
 * <code>e2</code> such that <code>e1.equals(e2)</code>, and they allow
 * multiple <code>null</code> elements if they allow <code>null</code>
 * elements at all.
 * <p>
 */
public interface Queue {
    
    /**
     * Appends the specified element to the <i>end</i> of this queue. The
     * <i>end</i> of the queue is given by its <i>strategy</i>.
     * 
     * @param object element to be appended at the <i>end</i> of this queue.
     * @throws IllegalStateException if the queue is already closed when this
     *             method is called.
     */
    void enqueue(final Object object) throws IllegalStateException;
    
    /**
     * Returns the element which currently is the <i>next</i> element in the
     * queue.
     * <p>
     * The element is not removed from the queue (in contrast to the method
     * {@link Queue#dequeue()}.
     * <p>
     * It is important to note that the <tt>peek</tt> method only shows the
     * element which currently is the next element. The next call of
     * <tt>dequeue</tt> is not guranteed to return the peeked element.
     * 
     * @return the <i>next</i> element in the queue.
     * @throws IllegalStateException if the queue is already closed when this
     *             method is called.
     * @throws NoSuchElementException queue has no more elements.
     */
    Object peek() throws IllegalStateException, NoSuchElementException;
    
    /**
     * Returns the <i>next</i> element in the queue and removes it.
     * <p>
     * Which is the <i>next</i> element of the in the queue is given by the
     * queue strategy.
     * 
     * @return the <i>next</i> element in the queue.
     * @throws IllegalStateException if the queue is already closed when this
     *             method is called.
     * @throws NoSuchElementException queue has no more elements.
     */
    Object dequeue() throws IllegalStateException, NoSuchElementException;
    
    /**
     * Checks if the queue is empty.
     * <p>
     * Returns <code>false</code> if {@link Queue#dequeue()} would return an
     * element rather than throwing an exception).
     * 
     * @return <code>false</code> if the queue has more elements.
     */
    boolean isEmpty();
    
    /**
     * Checks if the queue is full.
     * <p>
     * Returns <code>true</code> if {@link Queue#enqueue(Object)} would throw an
     * exception.
     * <p>
     * An unbounded queue always returns <code>false</code>.
     * 
     * @return <code>true</code> if the queue does not accept more elements.
     */
    boolean isFull();
    
    /**
     * Returns the number of elements in this queue.
     * 
     * @return the number of elements in this queue.
     */
    int size();
    
    /**
     * Removes all of the elements from the queue.
     * <p>
     * The queue will be empty after this call returns so that
     * <code>size() == 0</code>.
     */
    void clear();
}
