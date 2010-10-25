/*
 * Created on 13/Jul/2004
 */
package codebase.iterators;

import java.util.Iterator;

import codebase.monitors.Monitor;

/**
 * Decorates an {@link Iterator} with a {@link Monitor}.
 * <p>
 * The first call to any method of the {@link java.util.Iterator}, triggers
 * {@link codebase.monitors.Monitor#start()}, signalling the start of the task.
 * The task is ended when {@link #hasNext()} returns <code>false</code>. In
 * this case a call to {@link codebase.monitors.Monitor#stop()} is issued.
 * <p>
 * Each time an elements is retuned by returned by {@link #next()} the {Monitor#
 * notify()} method is called.
 */
public final class MonitoredIterator
        extends DecoratorIterator
        implements Iterator {
    
    /**
     * Monitor for this iterator.
     */
    private final Monitor nextMonitor;
    
    /**
     *
     */
    private boolean started;
    
    /**
     * Constructs a new monitored iterator.
     * 
     * @param iterator the iterator to be decorated
     * @param monitor the monitor to be used.
     */
    public MonitoredIterator(final Iterator iterator, final Monitor monitor) {
        super(iterator);
        nextMonitor = monitor;
    }
    
    /**
     * Returns the next element and notifies the monitor.
     * <p>
     * If an {@link IllegalStateException} is thrown the monitor is not
     * notified.
     * 
     * @return the next object returned by the decorated instance.
     * @see java.util.Iterator#next()
     */
    public Object next() {
        final Object result = super.next();
        /*
         * If an exception occurs the line number is not incremented.
         */
        if (!started) {
            nextMonitor.start();
            started = true;
        }
        nextMonitor.notify(result);
        return result;
    }
    
    /**
     * Checks if the next element exists and begins or ends the monitored task
     * if necessary.
     * 
     * @return the next element retuned by the decorated instance
     * @see java.util.Iterator#hasNext()
     */
    public boolean hasNext() {
        final boolean hasNext = super.hasNext();
        if (!started) {
            nextMonitor.start();
            started = true;
        }
        if (!hasNext) {
            if (started) {
                nextMonitor.stop();
                started = false;
            }
        }
        return hasNext;
    }
    
    /**
     * Removes the current element and begin the monitored task.
     * 
     * @see java.util.Iterator#remove()
     */
    public void remove() {
        super.remove();
        if (!started) {
            nextMonitor.start();
            started = true;
        }
    }
}
