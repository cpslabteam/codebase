/*
 * Created on 28/Jan/2006
 */
package codebase.collections.queues;

import java.util.Iterator;

import codebase.iterators.ArrayIterator;

/**
 * A multi-queue that uses hashing to determine the child queue.
 * 
 * @see codebase.collections.queues.MultiQueue
 */
public class HashMultiQueue
        implements MultiQueue {
    
    /**
     * Queue array.
     */
    protected final Queue[] queues;
    
    /**
     * Current queue index when deuqueing.
     */
    protected int currentQueueIndex;
    
    /**
     * Create a new Hash-Based multi-queue based on an array of children queues.
     * 
     * @param children the array of children queues.
     */
    public HashMultiQueue(final Queue[] children) {
        super();
        if (children == null) {
            throw new IllegalArgumentException(
                "The children queues array has to be assigned");
        }
        
        if (children.length < 1) {
            throw new IllegalArgumentException(
                "The children array must have at least one element.");
        }
        queues = children;
    }
    
    /**
     * Clears all the non-null queues.
     * 
     * @see codebase.collections.queues.Queue#clear()
     */
    public final void clear() {
        for (int i = 0; i < queues.length; i++) {
            final Queue queue = queues[i];
            if (queue != null) {
                queue.clear();
            }
        }
    }
    
    /**
     * Dequeues an element from a child queue.
     * 
     * @return the element dequeued from the next non-empty child queue
     * @throws IllegalStateException if no child queue allows an element to be
     *             dequeued.
     * @see codebase.collections.queues.Queue#dequeue()
     * @see HashMultiQueue#next()
     */
    public final Object dequeue() throws IllegalStateException {
        final Queue queue = next();
        if (queue != null) {
            return queue.dequeue();
        } else {
            throw new IllegalStateException(
                "I could not find any objects to dequeue. "
                        + "No queue was created in the MultiQueue.");
        }
    }
    
    /**
     * @see codebase.collections.queues.MultiQueue#enqueueAt(java.lang.Object, int)
     */
    public final void enqueueAt(final Object object, final int queueIndex) {
        if ((queueIndex < 0) && (queueIndex >= queues.length)) {
            throw new IllegalStateException(
                "Bad or inexistent queue at address "
                        + new Integer(queueIndex).toString());
        }
        
        final Queue queue;
        
        /*
         * Handle the null queue
         */
        if (queues[queueIndex] != null) {
            queue = queues[queueIndex];
        } else {
            queue = handleNullQueue(queueIndex);
            if (queue == null) {
                throw new IllegalStateException(
                    "Child queue is null at index: " + queueIndex);
            }
        }
        
        queue.enqueue(object);
    }
    
    /**
     * @param object the object to be enqued
     * @see codebase.collections.queues.Queue#enqueue(java.lang.Object)
     */
    public final void enqueue(final Object object) {
        final int index = getQueueIndex(object);
        enqueueAt(object, index);
    }
    
    /**
     * Checks if a child queue exists.
     * 
     * @param queueIndex the index of the queue to be checked
     * @return <code>true</code> if a queue is assigned to the given index.
     *         Returns <code>false</code> otherwise.
     */
    public final boolean existsQueue(final int queueIndex) {
        if ((queueIndex < 0) && (queueIndex >= queues.length)) {
            throw new IllegalStateException(
                "Bad or inexistent queue at address " + queueIndex);
        }
        
        return queues[queueIndex] != null;
    }
    
    /**
     * Returns the the index on the queue array for an given object.
     * <p>
     * The <code>null</code> object is inserted qt the queue with index 0.
     * 
     * @param object the object to be translated to an address
     * @return an index i such that 0\ <=i\ <= getQueueCount.
     */
    public final int getQueueIndex(final Object object) {
        if (object == null) {
            // XXX: O NULL DÁ BARRACA!
            return 0;
        } else {
            final int hashAddress = object.hashCode() % queueCount();
            return Math.abs(hashAddress);
        }
    }
    
    /**
     * Returns the number of queues that actualy have some element.
     * 
     * @return the number of non-empty (i.e., created) queues
     */
    protected final int getNonEmptyQueues() {
        int nonEmptyQueues = 0;
        for (int i = 0; i < queues.length; i++) {
            final Queue queue = queues[i];
            if (queue != null) {
                if (!queue.isEmpty()) {
                    nonEmptyQueues += 1;
                }
            }
        }
        return nonEmptyQueues;
    }
    
    /**
     * Finds an alternative queue when enqueuing on a <code>null</code> queue.
     * <p>
     * This method is called by {@link MultiQueue#enqueueAt(Object, int)} when a
     * <code>null</code> queue is found. Descending classes can create a queue
     * on the fly or return antoher queue.
     * <p>
     * <b>Note:</b> The queue returned by this function is not installed in the
     * array. This needs to be ensured by the implementing class by doing:
     * 
     * <pre>
     * <code>
     * queues[index] = myNewQueue;
     * return myNewQueue;
     * </code>
     * </pre>
     * 
     * @param queueIndex the index of the queue not found.
     * @return the next non-null Queue or <code>null</code> if no non-null
     *         queue exists.
     */
    protected final Queue handleNullQueue(final int queueIndex) {
        final int newQueueIndex = nextNonNull(queueIndex);
        if (newQueueIndex > -1) {
            return queues[newQueueIndex];
        } else {
            return null;
        }
    }
    
    /**
     * Checks if the multi-queue is full.
     * 
     * @return <code>true</code> if all the children queues are full. Returns
     *         <code>false</code> if at least one of the child queues allows
     *         element insertion.
     * @see codebase.collections.queues.Queue#isFull()
     */
    public final boolean isFull() {
        for (int i = 0; i < queues.length; i++) {
            
            final Queue queue = queues[i];
            if (queue != null) {
                if (!queue.isFull()) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * Checks if the multi-queue is empty.
     * 
     * @return <code>true</code> if all the children queues are empty. Returns
     *         <code>false</code> if at least one of the child queues has an
     *         element.
     * @see codebase.collections.queues.Queue#isFull()
     */
    public final boolean isEmpty() {
        for (int i = 0; i < queues.length; i++) {
            
            final Queue queue = queues[i];
            if (queue != null) {
                if (!queue.isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * Returns a cursor that allows to iterate over the non-null queues.
     * 
     * @return a cursor obtained from an array of all non-epmty queues
     */
    public final Iterator queueIterator() {
        final int nonEmptyQueueCount = getNonEmptyQueues();
        final Queue[] nonEmptyQueues = new Queue[nonEmptyQueueCount];
        int startIndex = 0;
        for (int i = 0; i < nonEmptyQueueCount; i++) {
            final int queueIndex = nextNonEmpty(startIndex);
            nonEmptyQueues[i] = queues[queueIndex];
            startIndex = queueIndex + 1;
        }
        return new ArrayIterator(nonEmptyQueues);
    }
    
    /**
     * Finds the next non-empty queue starting at a given index.
     * 
     * @param startIndex the index of the next queue to be searched inclusive.
     * @return the next queue that is not <code>null</code> or that is not
     *         empty. If not found it returns -1;
     */
    private int nextNonEmpty(final int startIndex) {
        final int numQueues = queues.length;
        int index = startIndex % numQueues;
        int turns = 0;
        
        // Cycles until it sees a created queue with objects
        while (((queues[index] == null) || (queues[index].isEmpty()))
                && (turns < numQueues)) {
            index = (index + 1) % numQueues;
            turns += 1;
        }
        
        // Checks what we have at the end
        final boolean validQueue = (queues[index] != null)
                && (!queues[index].isEmpty());
        
        // Return the index if all ok
        if (validQueue) {
            return index;
        } else {
            return -1;
        }
    }
    
    /**
     * Finds the next non-null child queue starting at a given index.
     * 
     * @param startIndex the index of the next queue to be searched inclusive.
     * @return the next queue that is not <code>null</code> or that is not
     *         empty. If all children are <code>null</code>, it returns -1.
     */
    protected final int nextNonNull(final int startIndex) {
        final int numQueues = queues.length;
        int index = startIndex % numQueues;
        int turns = 0;
        
        // Cycles until it sees a created queue with objects
        while ((queues[index] == null) && (turns < numQueues)) {
            index = (index + 1) % numQueues;
            turns += 1;
        }
        
        // Return the index if all ok
        if (queues[index] != null) {
            return index;
        } else {
            return -1;
        }
    }
    
    /**
     * Finds the next non-empty and non-null queue.
     * <p>
     * Searches for a queue starting at the current internal child queue index.
     * 
     * @return the next queue that is not <code>null</code> or that is not
     *         empty
     */
    public final Queue next() {
        final int nextQueueIndex = nextNonEmpty(currentQueueIndex);
        final boolean isValidQueue = nextQueueIndex >= 0;
        
        if (isValidQueue) {
            return queues[nextQueueIndex];
        } else {
            return null;
        }
    }
    
    /**
     * Peeks an object from a child queue.
     * <p>
     * Calls the method {@link Queue#peek()} of the next non-empty child queue
     * 
     * @return an object form the current child queue
     * @throws IllegalStateException if no child queue allows an element to be
     *             dequeued.
     * @see codebase.collections.queues.Queue#peek()
     * @see HashMultiQueue#next()
     */
    public final Object peek() throws IllegalStateException {
        final Queue queue = next();
        if (queue != null) {
            return queue.peek();
        } else {
            throw new IllegalStateException(
                "I could not find any objects to dequeue. "
                        + "No queue was created in the MultiQueue.");
        }
    }
    
    /**
     * @return Returns the queueCount.
     */
    public final int queueCount() {
        return queues.length;
    }
    
    /**
     * Computes the size of the multi-queue.
     * 
     * @return the sum of sizes of all children queues.
     * @see codebase.collections.queues.Queue#size()
     */
    public final int size() {
        int totalSize = 0;
        
        for (int i = 0; i < queues.length; i++) {
            final Queue queue = queues[i];
            if (queue != null) {
                totalSize += queue.size();
            }
        }
        return totalSize;
    }
    
}
