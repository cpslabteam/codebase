/*
 * Created on 6/Fev/2006
 */
package codebase.monitors;

import codebase.timing.Timer;

/**
 * A monitor that tracks the speed of event notification.
 * <p>
 * Uses an internal event counter.
 */
public final class SpeedMonitor
        extends TimedMonitor {
    
    /**
     * Constant the encodes the fact that no speed is assigned to the last speed
     * recording varaibale.
     */
    private static final double NO_SPEED_ASSIGNED = -1.0;
    
    /**
     * Holds the line number. Starts at zero.
     */
    private long countSoFar;
    
    /**
     * Records the last seen speed. This varaible is used so that the correct
     * speed is returned even after calling {@link #stop()}
     */
    private double lastSeenSpeed = NO_SPEED_ASSIGNED;
    
    /**
     * Creates a new speed monitor.
     * 
     * @param timer the timer to be used.
     */
    public SpeedMonitor(final Timer timer) {
        super(timer);
    }
    
    /**
     * Adds one event to the internal counter.
     * 
     * @param object the object used in the notification. Ignored.
     * @see codebase.monitors.Monitor#notify(java.lang.Object)
     */
    public void notify(final Object object) {
        countSoFar += 1;
    }
    
    /**
     * Resets the speed counter.
     * 
     * @see codebase.monitors.TimedMonitor#startMonitor()
     */
    protected void startMonitor() {
        countSoFar = 0;
        lastSeenSpeed = NO_SPEED_ASSIGNED;
    }
    
    /**
     * Does nothing.
     * 
     * @see codebase.monitors.TimedMonitor#stopMonitor()
     */
    protected void stopMonitor() {
        lastSeenSpeed = getSpeed();
    }
    
    /**
     * Returns the speed monitored.
     * 
     * @return the speed in <i>events per milliseconds</i>. Returns
     *         <code>NaN</code> if it cannot compute a valid speed.
     */
    public double getSpeed() {
        if (lastSeenSpeed == NO_SPEED_ASSIGNED) {
            final double time = getTime();
            if (time != 0.0 && time != Double.NaN) {
                return countSoFar / time;
            } else {
                return Double.NaN;
            }
        } else {
            return lastSeenSpeed;
        }
    }
}
