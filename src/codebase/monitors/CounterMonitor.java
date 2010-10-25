/*
 * Created on 6/Fev/2006
 */
package codebase.monitors;

/**
 * A monitor that counts the number of notifications.
 * <p>
 * A counter monitor uses an internal counter that is reset each time
 * {@link #start()} is invoked.
 */
public class CounterMonitor
        implements Monitor {
    
    /**
     * Holds the line number. Starts at zero.
     */
    private long countSoFar;
    
    /**
     * Constructs a new counter monitor.
     */
    public CounterMonitor() {
        super();
        countSoFar = 0;
    }
    
    /**
     * Obtains the number of lines so far.
     * 
     * @return Returns the value of {@link #countSoFar}.
     */
    public final long count() {
        return countSoFar;
    }
    
    /**
     * Resets the internal line counter.
     * <p>
     * Sets the value of {@link #countSoFar} to zero.
     */
    public final void start() {
        countSoFar = 0;
    }
    
    /**
     * Increments the line count.
     * <p>
     * 
     * @param object ignored
     */
    public final void notify(final Object object) {
        countSoFar += 1;
    }
    
    /**
     * Does nothing.
     * 
     * @see codebase.monitors.Monitor#stop()
     */
    public final void stop() {
    }
}
