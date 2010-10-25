/*
 * Created on 14/Nov/2004
 */
package codebase.collections.queues;

/**
 * Implementation skeleton for loose queues.
 */
public abstract class ModeAwareIOQueue
        extends BaseIOQueue
        implements ModeAwareQueue {
    
    /**
     * The queue starts in the enqueuing mode.
     */
    private int mode = INVALID_MODE;
    
    /**
     * Constructor that puts the queue imedialy in the enqueuing state.
     */
    public ModeAwareIOQueue() {
        super();
    }
    
    /**
     * Sets the queues to dequeuing mode by calling <code>openDequeue()</code>
     * and retrieves the next object from the queue by calling
     * <code>retrieve()</code>.
     * 
     * @return the next object yielded by <code>retrieve()</code>.
     * @see ModeAwareIOQueue#retrieveObject()
     * 
     */
    public final Object retrieve() {
        setDequeuingMode();
        return retrieveObject();
    }
    
    /**
     * Sets the queue back to enqueing mode, enventually clearing the objects
     * inserted when the queue has last been in enqueuing mode. Adds the object
     * to the queue by calling the <code>stage</code> method.
     * 
     * @param object the object to be enqued.
     * @see ModeAwareIOQueue#stageObject(Object)
     * 
     */
    public final void stage(final Object object) {
        setEnqueingMode();
        stageObject(object);
    }
    
    /**
     * Checks if the queue is in enqueuing mode.
     * 
     * @return <code>true</code> if <code>state ==  ENQUEUING_STATE</code>.
     */
    public final boolean isEnqueuing() {
        return ENQUEUING_MODE == mode;
    }
    
    /**
     * Checks if the queue is in dequeueing mode.
     * 
     * @return <code>true</code> if <code>state ==  DEQUEUING_STATE</code>.
     */
    public final boolean isDequeuing() {
        return DEQUEUING_MODE == mode;
    }
    
    /**
     * Brings the queue to <i>enqueing</i> mode.
     * <p>
     * Ensures that the queue is in <i>enqueing</i> mode if necessary call the
     * extending class method to set the queue to enqueuing mode.
     * 
     * @see ModeAwareIOQueue#placeQueue()
     */
    public final void setEnqueingMode() {
        if (!isEnqueuing()) {
            placeQueue();
            mode = ENQUEUING_MODE;
        }
    }
    
    /**
     * Sets the queue to <i>dequeuing</i> mode.
     * <p>
     * Ensures that the queue is in <i>dequeuing</i> mode. If necessary is
     * calls the extending class method for setting the queue to dequeuing mode.
     * 
     * @see ModeAwareIOQueue#flushQueue()
     */
    private void setDequeuingMode() {
        if (!isDequeuing()) {
            flushQueue();
            mode = DEQUEUING_MODE;
        }
    }
    
    /**
     * Sets the queue to enqueuing mode.
     * <p>
     * This method must be implemented by the underlying classes to set the
     * queue to enqueing mode.
     */
    protected abstract void placeQueue();
    
    /**
     * Sets the queue to dequeueing mode.
     * <p>
     * This method must be implemented by the underlying class to set the queue
     * to dequeueing mode
     */
    protected abstract void flushQueue();
    
    /**
     * Postpone the implemementation of this method. XXX: complete this
     * 
     * @return the object peeked from the queue
     * @see BaseIOQueue#peekObject()
     */
    public abstract Object peekObject();
    
    /**
     * Retrieves the next object from the queue.
     * <p>
     * This method is only called after the queue is in <i>dequeueing</i> mode.
     * E
     * 
     * @see ModeAwareIOQueue#retrieve()
     * @return the next object from the underlying queue
     */
    protected abstract Object retrieveObject();
    
    /**
     * Stages an object into the underlying queue.
     * <p>
     * This is the method that the extending queues have to implement to add new
     * objects to the queue after bringing the queue to <i>enqueing</i> mode.
     * 
     * @param object the object to be enqueued.
     * @see ModeAwareIOQueue#enqueueObject
     */
    protected abstract void stageObject(final Object object);
}
