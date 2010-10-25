/*
 * Created on 9/Dez/2004
 */
package codebase.collections.queues;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Interface of queue constituted of other queues.
 * <p>
 * A multi-queue is a queue made up of an array of queues. Each child queue
 * associated has index, given by its position in the array.
 * <p>
 * Enqueueing an element in a multi-queue means inserting it in one of the child
 * queues. The index of the child queue where each element is to be inserted is
 * left to the {@link #getQueueIndex(Object)} method. One way to perform the
 * selection of the child queue consists of hashing the object.
 * <p>
 * Dequeueing an object from the multi-queue is abstracted in the method
 * {@link #next()}. One simple implementation consists of dequeuing an object
 * from the first non-empty. However other strategies can be implemented.
 */
public interface MultiQueue
        extends Queue {
    
    /**
     * Appends the specified element to the <i>end</i> of the queue specified
     * by {@link #getQueueIndex(Object)}.
     * <p>
     * This method is provided for compatibility with the {@link Queue}
     * interface. Implementers of this method are expected to implement it usign
     * the methods {@link #getQueueIndex(Object)} and
     * {@link #enqueueAt(Object, int)}.
     * 
     * @param object element to be appended to a child queue.
     * @throws IllegalStateException if the queue is already closed when this
     *             method is called.
     */
    void enqueue(final Object object) throws IllegalStateException;
    
    /**
     * Returns the <i>next</i> element in the queue given by {@link #next()}.
     * <p>
     * This method is provided for compatibility with the {@link Queue}
     * interface. The next element of a multi-queue, by definition is the
     * element given by the next available child queue.
     * 
     * @return the <i>next</i> element from a tghe child queue.
     * @throws IllegalStateException if the queue is already closed when this
     *             method is called.
     * @throws NoSuchElementException queue has no more elements.
     */
    Object dequeue() throws NoSuchElementException;
    
    /**
     * Inserts an object on a given child queue.
     * <p>
     * The address of the queue where the object will be stored should have been
     * previously obtained by calling the method {@link #getQueueIndex(Object)}.
     * 
     * @param object the object to be enqued.
     * @param queueIndex index of the child queue where the object is to be
     *            stored
     * @see MultiQueue#getQueueIndex(Object)
     */
    void enqueueAt(final Object object, final int queueIndex);
    
    /**
     * Computes the the index on the queue array for an given object.
     * <p>
     * We allow this method to be public so that it can be used together with
     * the method enqueueAt.
     * 
     * @param object the object to be translated to an address
     * @return an index <i>i</i> such that
     *         <code>0&le;<i>i</i>&lt;{@link #queueCount()}</code>.
     */
    int getQueueIndex(final Object object);
    
    /**
     * Returns an iterator over the child queues.
     * <p>
     * Iterates over the children queues of the multi-queue.
     * 
     * @return a cursor obtained from an array of all non-epmty queues
     */
    Iterator queueIterator();
    
    /**
     * Computes the number of child queues in the queue.
     * 
     * @return the number of child queues in the multi-queue.
     */
    int queueCount();
    
    /**
     * Returns the next child queue to be dequeued.
     * <p>
     * Implementers of this method can implement <i>round-robin</i> or other
     * policy, for example, based on the number of elemenents.
     * <p>
     * <b>Note:</b> The returned queue must allays be assigned and reeady for
     * dequeuing.
     * 
     * @return the next queue that contains an element to be dequeued. Returns
     *         <code>null</code> if no valid queue can be returned.
     */
    Queue next();
}
