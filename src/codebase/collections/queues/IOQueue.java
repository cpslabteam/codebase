/*
 * Created on 19/Mar/2005
 */
package codebase.collections.queues;

/**
 * Interface for queues featuring resource management with <i>open/close</i>
 * semantics.
 * <p>
 * A resource managed queue is necessary for queues that have to reserve and
 * release systems resources used for storing the elements. For instance a queue
 * may store its elements on disk, or through a JDBC connection.
 * <p>
 * This interface extends the base {@link codebase.collections.queues.Queue} interface
 * adding the {@link #open()} and {@link #close()} methods.
 */
public interface IOQueue
        extends Queue {
    
    /**
     * Opens the queue resources.
     * <p>
     * This method should be called before any elements are inserted into the
     * queue. Otherwise, calling to methods like {@link Queue#enqueue(Object)}
     * or {@link Queue#peek()} are not guaranteed to yield proper results.
     * <p>
     * This operation is idempotent. Multiple calls to {@link #open()} have no
     * effect. The queue remains in the state <i>opened</i> until its
     * {@link #close()} method is called.
     * <p>
     * <b>Note:</b> Call to the {@link #open()} method on a closed queue may
     * not open it again. The state of the queue may be restorable when
     * resources are released.
     * 
     * @see IOQueue#close()
     */
    void open();
    
    /**
     * Closes the queue resources.
     * <p>
     * This method should be called after all the operations with the queue have
     * been performed. After closing a queue, calling to methods like
     * {@link Queue#enqueue(Object)} or {@link Queue#peek()} will fail.
     * <p>
     * This operation is idempotent. Multiple calls to {@link #close()} have no
     * effect. The queue remains in the state remains in the state <i>closed</i>
     * until its {@link #open()} method is called.
     * <p>
     * <b>Note:</b> Call to the {@link #open()} method on a closed queue may
     * not open it again. The state of the queue may be restorable when
     * resources are released.
     * 
     * @see IOQueue#open()
     */
    void close();
}
