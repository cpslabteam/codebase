/*
 * Created on 18/Jan/2006
 */
package codebase.collections.queues;

import java.util.NoSuchElementException;

/**
 * The base implementation of decorator queues.
 * <p>
 * This class provides the default implementation for all methods of a queue
 * decorator, which consists of forwarding the method call to the decorated
 * instance. A particular queue decorator should extended this class and
 * override the methods it is interested in.
 */
public abstract class DecoratorQueue
        implements Queue {
    
    /**
     * Reference to the decorated instance.
     */
    protected final Queue decoratedInstance;
    
    /**
     * Creates a new queue decorator.
     * 
     * @param instance the queue instance being decorated.
     */
    public DecoratorQueue(final Queue instance) {
        decoratedInstance = instance;
    }
    
    /**
     * @see codebase.collections.queues.Queue#enqueue(java.lang.Object)
     * @param object the object to be enequed in the decorated instance
     */
    public void enqueue(final Object object) {
        decoratedInstance.enqueue(object);
    }
    
    /**
     * @see codebase.collections.queues.Queue#peek()
     * @return the element peeked from the decoreated instance
     * @throws NoSuchElementException thrown by the decorated instance
     */
    public Object peek() throws NoSuchElementException {
        return decoratedInstance.peek();
    }
    
    /**
     * @see codebase.collections.queues.Queue#dequeue()
     * @return the element dequeued from the decorated instance.
     * @throws NoSuchElementException thrown by the decorated instance
     * @throws IllegalStateException thrown by the decorated instance
     */
    public Object dequeue() throws IllegalStateException,
            NoSuchElementException {
        return decoratedInstance.dequeue();
    }
    
    /**
     * @see codebase.collections.queues.Queue#isEmpty()
     * @return <code>true</code> if the decorated instance is empty. Retuns
     *         <code>false</code> otherwise.
     */
    public boolean isEmpty() {
        return decoratedInstance.isEmpty();
    }
    
    /**
     * @see codebase.collections.queues.Queue#isFull()
     * @return <code>true</code> if the decorated instance is full. Retuns
     *         <code>false</code> otherwise.
     */
    public boolean isFull() {
        return decoratedInstance.isFull();
    }
    
    /**
     * @see codebase.collections.queues.Queue#size()
     * @return the size of the decorated instance
     */
    public int size() {
        return decoratedInstance.size();
    }
    
    /**
     * @see codebase.collections.queues.Queue#clear()
     */
    public void clear() {
        decoratedInstance.clear();
    }
    
}
