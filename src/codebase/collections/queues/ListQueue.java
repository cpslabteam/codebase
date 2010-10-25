/*
 * Created on 15/Out/2005
 */
package codebase.collections.queues;

import java.util.LinkedList;
import java.util.List;

/**
 * A list-based FIFO queue implementation.
 * <p>
 * This class provides an implementation of the {@link codebase.collections.queues.Queue}
 * interface with a FIFO (<i>first in, first out</i>) strategy that internally
 * uses a elementList to store its elements.
 * <p>
 * It implements the {@link codebase.collections.queues.Queue#peek()} method.
 * <p>
 * The performance of the queue depends on the performance of the internally
 * used element list (e.g., an ArrayList guarantees for insertion in amortized
 * constant time, that is, adding n elements requires O(n) time).
 * <p>
 */
public final class ListQueue
        implements Queue {
    
    /**
     * The elementList used to store the elements of the queue.
     */
    protected List elementList;
    
    /**
     * Constructs a queue containing the elements of a given element list.
     * 
     * @param list the element list that is used store the queue elements
     */
    public ListQueue(final List list) {
        this.elementList = list;
    }
    
    /**
     * Constructs an empty queue.
     * <p>
     * This queue instantiates a new {@link LinkedList} in order to store its
     * elements.
     */
    public ListQueue() {
        this(new LinkedList());
    }
    
    /**
     * Inserts the specified element at the <i>end</i> of this queue.
     * 
     * @param object element to be inserted at the <i>end</i> of this queue.
     */
    public void enqueue(final Object object) {
        elementList.add(object);
    }
    
    /**
     * Returns the <i>next</i> element in the queue without removing it from
     * the queue.
     * 
     * @return the <i>next</i> element in the queue.
     */
    public Object peek() {
        return elementList.get(0);
    }
    
    /**
     * Takes the <i>next</i> element from the queue and removes it from the
     * queue.
     * 
     * @return the <i>next</i> element in the queue.
     */
    public Object dequeue() {
        return elementList.remove(0);
    }
    
    /**
     * Removes all of the elements from this queue.
     * <p>
     * The queue will be empty after this call returns so that
     * <code>size() == 0</code>.
     * <p>
     * Clears the internal element list.
     */
    public void clear() {
        elementList.clear();
    }
    
    /**
     * Computes the size of the queue.
     * 
     * @return the size of the underlying element list
     * @see codebase.collections.queues.Queue#size()
     */
    public int size() {
        return elementList.size();
    }
    
    /**
     * Checks if the queue is full.
     * 
     * @return <code>false</code> beacause a list constainer is unbounded.
     * @see codebase.collections.queues.Queue#isFull()
     */
    public boolean isFull() {
        return false;
    }
    
    /**
     * Checks if the queue is empty.
     * 
     * @return <code>true</code> if the element list is empty. Returns
     *         <code>false</code> otherwise.
     * @see codebase.collections.queues.Queue#isEmpty()
     */
    public boolean isEmpty() {
        return elementList.isEmpty();
    }
}
