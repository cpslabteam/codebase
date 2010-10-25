/*
 * Created on 28/Jan/2006
 */
package codebase.collections.queues;

/**
 * Interface of a multi-queue with resource management through <i>open/close</i> semantics.
 * <p>
 * This multi-queue is extended with {@link #open()} and {@link #close()}
 * methods. Opening a maulti-queue does not necessarly mean that all the child
 * queues are openned. In fact child queues can be open on the demand. Similarly
 * they can be closed as they are become empty or closed all at once.
 */
public interface IOMultiQueue
        extends MultiQueue, IOQueue {
    
    /**
     * Opens the multi-queue.
     * <p>
     * This method should be called before any elements are inserted into the
     * queue. <b>Note:</b> Call to the {@link #open()} method may, or may not
     * imply openning all the child queues.
     * 
     * @see IOQueue#close()
     */
    void open();
    
    /**
     * Closes the multi-queue.
     * <p>
     * This method should be called after all the operations with the queue have
     * been performed.
     * <p>
     * <b>Note:</b> Calling close does not mean that all the queues are closed
     * at once.
     * 
     * @see IOQueue#open()
     */
    void close();
}
