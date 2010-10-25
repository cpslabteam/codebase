/*
 * Created on 18/Jan/2006
 */
package codebase.collections.queues;

import java.util.NoSuchElementException;

/**
 * A synchronized queue decorator.
 * <p>
 * This queue decorator transforms another queue into a thread-safe queue. Any
 * processes enqueing elements will block if the queue is full. Any processes
 * dequeuing elements will block if the queue is empty.
 */
public final class SynchronizedQueue
        extends DecoratorQueue
        implements Queue {
    
    /**
     * Creates a new synchronized queue wrapper.
     * 
     * @param instance the queue instance to be wrapped
     */
    public SynchronizedQueue(final Queue instance) {
        super(instance);
    }
    
    /**
     * Enqueues an object in the wrapped queue.
     * <p>
     * If the queue is full, it waits until an object is removed from the queue
     * by another thread.
     * 
     * @param object the object to be enqueued.
     * @see codebase.collections.queues.Queue#enqueue(java.lang.Object)
     */
    public void enqueue(final Object object) throws IllegalStateException {
        synchronized (this) {
            while (super.isFull()) {
                try {
                    /*
                     * block until data is available
                     */
                    wait();
                } catch (InterruptedException e) {
                    // log interrupted exception
                }
            }
            try {
                super.enqueue(object);
            } finally {
                notifyAll();
            }
        }
    }
    
    /**
     * Not avaliable in a synchronized queue.
     * 
     * @return nothing
     * @throws UnsupportedOperationException when called
     * @see codebase.collections.queues.Queue#peek()
     */
    public Object peek() throws UnsupportedOperationException {
        throw new UnsupportedOperationException(
            "A synchronized queue does not allow peeks");
    }
    
    /**
     * Removes an object from the queue.
     * <p>
     * If the queue is empty, waits until an element becomes available.
     * 
     * @see codebase.collections.queues.Queue#dequeue()
     */
    public Object dequeue() throws IllegalStateException,
            NoSuchElementException {
        synchronized (this) {
            while (super.isEmpty()) {
                try {
                    /*
                     * block until data is available
                     */
                    wait();
                } catch (InterruptedException e) {
                    // log interrupted exception
                }
            }
            try {
                final Object element = super.dequeue();
                notifyAll();
                return element;
            } catch (IllegalStateException e) {
                notifyAll();
                throw e;
            } catch (NoSuchElementException e) {
                notifyAll();
                throw e;
            }
        }
    }
    
    /**
     * TODO: Implement later with a synchonozed flag. We can not size while
     * another thread is inside enqueue or dequeue
     */
    public int size() {
        return super.size();
    }
    
    /**
     * TODO: Implement later with a synchonozed flag. We can not isEmpty while
     * someone is inside enqueue or dequeue
     */
    public boolean isEmpty() {
        return super.isEmpty();
    }
    
    /**
     * TODO: Implement later with a synchonized flag. We can not isFull while
     * someone is inside enqueue or dequeue
     */
    public boolean isFull() {
        return super.isFull();
    }
    
    /**
     * TODO: Implement later with a synchonozed flag. We can not clear while
     * someone is inside enqueue or dequeue
     */
    public void clear() {
        synchronized (this) {
            super.clear();
        }
    }
    
}
