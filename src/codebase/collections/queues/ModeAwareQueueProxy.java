/*
 * Created on 10/Mar/2005
 *  
 */
package codebase.collections.queues;

/**
 * Interface of a proxy for {@link ModeAwareQueue}.
 * 
 * @see ModeAwareQueue
 */
public interface ModeAwareQueueProxy {
    
    /**
     * Returns the queue being managed on behalf of this proxy. Returning
     * <code>null</code> will trigger an <code>IllegalStatException</code>
     * 
     * @return a loose queue
     */
    ModeAwareQueue request();
}
