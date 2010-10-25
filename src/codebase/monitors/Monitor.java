/*
 * Created on 6/Fev/2006
 */
package codebase.monitors;

/**
 * Interface of progress of activity Monitors.
 * <p>
 * Since notifications are synchronous (achived by calling the method
 * {@link #notify(Object)}, implementers of Monitors must handle
 * notifications in a fast and robust manner.
 * <p>
 * If the handling of notifications involves blocking operations, or
 * operations which might throw uncaught exceptions, the notifications should
 * be queued, and the actual processing deferred (e.g., delegated to a
 * separate thread).
 */
public interface Monitor {

    /**
     * Starts the task to be monitored.
     * <p>
     * Takes the appropriate actions like reseting counters or getting systems
     * information, etc.
     */
    void start();

    /**
     * Notifies the monitor of an event.
     *
     * @param object and object that contains the information sent to the
     *            monitor.
     */
    void notify(final Object object);

    /**
     * Ends the task being monitored.
     */
    void stop();
}
