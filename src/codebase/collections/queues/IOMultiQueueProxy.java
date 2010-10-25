/*
 * Created on 10/Mar/2005
 */
package codebase.collections.queues;

/**
 * Interface of a proxy for an {@link codebase.collections.queues.IOMultiQueue}.
 * 
 * @see codebase.collections.queues.IOMultiQueue
 */
public interface IOMultiQueueProxy {
    
    /**
     * Returns the queue being managed on behalf of this proxy.
     * <p>
     * Returning <code>null</code> will trigger an
     * {@link IllegalStateException}
     * 
     * @return a {@link MultiQueue} object
     */
    IOMultiQueue request();
}
