/*
 * Created on 18/Jan/2006
 */
package codebase.collections.queues;

import java.util.NoSuchElementException;

/**
 * A decorator that adds resource management to a queue.
 * <p>
 * The methods {@link codebase.collections.queues.IOQueue#open()} and
 * {@link codebase.collections.queues.IOQueue#close()} are final. Classes
 * extending a {@link DecoratorIOQueue} should implement the
 * meethods {@link #openQueue()} and {@link #closeQueue()}.
 */
public class DecoratorIOQueue
        extends DecoratorQueue
        implements IOQueue {

    /**
     * Used to store the state of this queue (if it is opened).
     */
    protected boolean isOpen;

    /**
     * Used to store the state of this queue (if it is closed).
     */
    protected boolean isClosed;

    /**
     * Builds a new resource managed queue decorator.
     *
     * @param instance the queue to be decorated open/close resource
     *            management
     */
    public DecoratorIOQueue(final Queue instance) {
        super(instance);
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
     * Allocates resources for the decorated queue.
     * <p>
     * Should be overriden by implementing classes.
     */
    protected void openQueue() {
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
     * Opens the queue automatically if the method {@link #open()} has not
     * been called.
     *
     * @param object element to be appended to the <i>end</i> of this queue.
     * @throws IllegalStateException if the queue is already closed when this
     *             method is called. *
     * @see codebase.collections.queues.Queue#enqueue(Object)
     */
    public void enqueue(final Object object) throws IllegalStateException {
        if (isClosed) {
            throw new IllegalStateException();
        }
        if (!isOpen) {
            open();
        }
        super.enqueue(object);
    }

    /**
     * Returns the element at the end of the decorated queue.
     * <p>
     * Opens the queue automatically if the method {@link #open()} has not
     * been called.
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

        return super.peek();
    }

    /**
     * Dequeues an element from the decorated queue.
     * <p>
     * Opens the queue automatically if the method {@link #open()} has not
     * been called.
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

        return super.dequeue();
    }
}
