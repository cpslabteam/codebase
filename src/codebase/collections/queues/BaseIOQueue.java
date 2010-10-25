/*
 * Created on 25/Jan/2006
 */
package codebase.collections.queues;

import java.util.NoSuchElementException;

/**
 * Implementation skeleton for resource managed queues.
 * <p>
 * This implementation guarantees that the <i>open</i> and <i>close</i>
 * operations are idempotent, i.e., for example afer opening a queue the queue
 * ramains in the <i>open</i> state no matter how many times the {{@link #open()}
 * method is called.
 * <p>
 * The methods of the interface, like {@link codebase.collections.queues.IOQueue#open()} and
 * {@link codebase.collections.queues.IOQueue#close()},
 * {@link codebase.collections.queues.Queue#enqueue(Object)} are final. Descending classes
 * should implement the methods {@link #openQueue()} and {@link #closeQueue()}
 * as well as {@link #peekObject()} and {@link #stage(Object)} and
 */
public abstract class BaseIOQueue {
    
    /**
     * Used to store the state of this queue (if it is opened).
     */
    protected boolean isOpen;
    
    /**
     * Used to store the state of this queue (if it is closed).
     */
    protected boolean isClosed;
    
    /**
     * Creates an idempotent resource managed queue.
     */
    public BaseIOQueue() {
        super();
    }
    
    /**
     * Allocates the queue resouces.
     * <p>
     * Calls {@link #openQueue()} if the queue is not <i>open</i>.
     * 
     * @see codebase.collections.queues.IOQueue#open()
     */
    public final void open() {
        if (!isOpen) {
            this.openQueue();
            isOpen = true;
        }
    }
    
    /**
     * Allocates resources for the decorated queue.
     * <p>
     * Should be overriden by implementing classes.
     */
    protected void openQueue() {
    }
    
    /**
     * Releases the allocated resources.
     * <p>
     * Calls {@link #closeQueue()} if the queue is not <i>closed</i>.
     * 
     * @see codebase.collections.queues.IOQueue#close()
     */
    public final void close() {
        if (!isClosed) {
            this.closeQueue();
            isClosed = true;
        }
    }
    
    /**
     * Releases the allocated resources for the decorated queue.
     * <p>
     * Should be overriden by implementing classes.
     */
    protected void closeQueue() {
    }
    
    /**
     * Inserts an element into the decorated queue.
     * <p>
     * Opens the queue automatically if the method {@link #open()} has not been
     * called.
     * 
     * @param object element to be appended to the <i>end</i> of this queue.
     * @throws IllegalStateException if the queue is already closed when this
     *             method is called or if the object cannot be inserted in the
     *             queue.
     * @see codebase.collections.queues.Queue#enqueue(Object)
     */
    public final void enqueue(final Object object) throws IllegalStateException {
        if (isClosed) {
            throw new IllegalStateException();
        }
        if (!isOpen) {
            open();
        }
        stage(object);
    }
    
    /**
     * Inserts an object in the queue using IO.
     * <p>
     * Extending queues have to implement this method to add new objects to the
     * queue while in enqueing mode.
     * <p>
     * When the object is added, it is expected to be materialized atonce, i.e.,
     * <i>staged</i>. Any change to the object attributes will should not afect
     * the enqued object.
     * 
     * @param object the object to be inserted
     * @throws IllegalStateException if no object can be inserted.
     */
    protected abstract void stage(final Object object)
            throws IllegalStateException;
    
    /**
     * Returns the element at the end of the decorated queue.
     * <p>
     * Opens the queue automatically if the method {@link #open()} has not been
     * called.
     * 
     * @return the object peeked from the decorated queue.
     * @throws IllegalStateException if the queue is already closed when this
     *             method is called.
     * @throws NoSuchElementException if the decorated queue is empty.
     * @see codebase.collections.queues.Queue#peek()
     */
    public final Object peek() throws IllegalStateException,
            NoSuchElementException {
        if (isClosed) {
            throw new IllegalStateException();
        }
        if (!isOpen) {
            open();
        }
        
        return peekObject();
    }
    
    /**
     * Returns the object available to be dequeued.
     * 
     * @return the next avaliable object at the end of the queue.
     * @throws IllegalStateException if the object cannot be determined.
     * @throws NoSuchElementException if the queue is empty.
     */
    protected abstract Object peekObject() throws IllegalStateException,
            NoSuchElementException;
    
    /**
     * Dequeues an element from the decorated queue.
     * <p>
     * Opens the queue automatically if the method {@link #open()} has not been
     * called.
     * 
     * @return the <i>next</i> element in the queue.
     * @throws IllegalStateException if the queue is already closed when this
     *             method is called.
     * @throws NoSuchElementException queue has no more elements in the
     *             decorated queue.
     */
    public final Object dequeue() throws IllegalStateException,
            NoSuchElementException {
        if (isClosed) {
            throw new IllegalStateException();
        }
        if (!isOpen) {
            open();
        }
        
        return retrieve();
    }
    
    /**
     * Dequeues an object using IO.
     * <p>
     * Retrieves an object from secondary storage using the IO mechanism.
     * Implementing classes may use specific materialization approaches to
     * retrieve the objects.
     * 
     * @return the next taken from the end of the queue.
     * @throws IllegalStateException if the object cannot be determined.
     * @throws NoSuchElementException if the queue is empty.
     */
    protected abstract Object retrieve() throws IllegalStateException,
            NoSuchElementException;
    
}
