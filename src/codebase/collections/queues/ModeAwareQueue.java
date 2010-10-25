/*
 * Created on 14/Nov/2004
 */
package codebase.collections.queues;

/**
 * Interface for queues that can only be either in <i>enqueueing</i> or
 * <i>dequeuing</i> mode.
 * <p>
 * This interface is specialization of queue that:
 * <ol>
 * <li>Preserves the elements when switching from <i>enqueing</i> mode to
 * <i>dequeing</i> mode.</li>
 * <li>Resets the queue when switching back from <i>dequeuing</i> mode back to
 * <i>enqeuing</i> mode resets the queue.</li>
 * </ol>
 * This kind queues is intended to serve as an abstraction of a persistent
 * (e.g., disk.resident) queue. Such queues do not allow random access, they are
 * implemented as a sequential file. The queue starts in enqueuing mode. We call
 * it a <i>single mode</i> queue because after enqueing a number of objects
 * they can be dequed them safely. Another requirement is that objects are not
 * stored in the queue by reference. When an object is added to the queue, it is
 * materialized imeditately.
 */
public interface ModeAwareQueue
        extends Queue {
    
    /**
     * Constant the represents the unassigned mode.
     */
    int INVALID_MODE = -1;
    
    /**
     * Constant the represents the enqueuing mode.
     */
    int ENQUEUING_MODE = 0;
    
    /**
     * Constant that represents the dequeuein mode.
     */
    int DEQUEUING_MODE = 1;
    
    /**
     * Sets the queues to dequeuing mode by calling <code>openDequeue()</code>
     * and retrieves the next object from the queue by calling
     * <code>retrieve()</code>.
     * 
     * @return the next object yielded by <code>retrieve()</code>.
     * XXX: @see ModeAwareQueue#retrieveObject()
     */
    Object dequeue();
    
    /**
     * Sets the queue back to enqueing mode, enventually clearing the objects
     * inserted when the queue has last been in enqueuing mode. Adds the object
     * to the queue by calling the <code>stage</code> method.
     * 
     * @param object the object to be enqued.
     * XXX:@see ModeAwareQueue#stageObject(Object)
     */
    void enqueue(final Object object);
    
    /**
     * Checks if the queue is in enqueuing mode.
     * 
     * @return <code>true</code> if <code>state ==  ENQUEUING_STATE</code>.
     */
    boolean isEnqueuing();
    
    /**
     * Checks if the queue is in dequeueing mode.
     * 
     * @return <code>true</code> if <code>state ==  DEQUEUING_STATE</code>.
     */
    boolean isDequeuing();
}
