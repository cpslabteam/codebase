/*
 * Created on 28/Jan/2006
 */
package codebase.collections.queues;

/**
 * An resource managed multi-queue that uses hashing.
 * <p>
 */
public class HashIOMultiQueue
        extends HashMultiQueue
        implements IOMultiQueue {
    
    /**
     * Create a new Hash-Based multi-queue based on an array of children queues.
     * <p>
     * It is not necessary that all the child queues be {@link IOMultiQueue}.
     * 
     * @param children the array of children queues.
     */
    public HashIOMultiQueue(final Queue[] children) {
        super(children);
    }
    
    /**
     * Opens all the child queues.
     * <p>
     * All the child queues that are {@link IOQueue} are open. In order to avoid
     * openning al the queues forefront, It is possible to use a Queue deocrator
     * that performs the opens operation on-demand.
     * 
     * @see codebase.collections.queues.IOQueue#open()
     */
    public final void open() {
        for (int i = 0; i < queues.length; i++) {
            final Queue queue = queues[i];
            if (queue != null) {
                if (queue instanceof IOMultiQueue) {
                    final IOQueue ioQueue = (IOQueue) queue;
                    ioQueue.open();
                }
            }
        }
    }
    
    /**
     * Closes all the child queues.
     * <p>
     * All the child queues that are {@link IOQueue} are closed.
     * 
     * @see codebase.collections.queues.IOQueue#open()
     */
    public final void close() {
        for (int i = 0; i < queues.length; i++) {
            final Queue queue = queues[i];
            if (queue != null) {
                if (queue instanceof IOMultiQueue) {
                    final IOQueue ioQueue = (IOQueue) queue;
                    ioQueue.close();
                }
            }
        }
    }
    
}
