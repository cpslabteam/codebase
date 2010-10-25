/*
 * Created on 19/Mar/2005
 */
package codebase.collections.queues;

import java.util.Iterator;

/**
 * Utility class for queues.
 */
public final class Queues {
    
    /**
     * Prevent instantiation of this class.
     */
    private Queues() {
    }
    
    /**
     * Loads a queue from an iterator.
     * <p>
     * The queue will filled by consuming all the elements of the iterator.
     * 
     * @param queue the queue to be filled
     * @param iterator the iterator that feeds the input objects
     */
    public static void loadQueue(final Queue queue, final Iterator iterator) {
        while (iterator.hasNext()) {
            final Object element = iterator.next();
            queue.enqueue(element);
        }
    }
}
