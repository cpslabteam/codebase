/*
 * Created on 6/Fev/2006
 */
package codebase.monitors;

import codebase.timing.Timer;

/**
 * A monitor that simply tracks the elapsed time between {@link #start()} and
 * {@link #stop()}.
 * <p>
 * This monitor is based on a given timer. This way different timers may be
 * used. Additionally, it is possible to know the elaped time through the timer.
 */
public final class ClockMonitor
        extends TimedMonitor
        implements Monitor {
    
    /**
     * Creates a new timer monitor.
     * 
     * @param timer the timer to be used.
     */
    public ClockMonitor(final Timer timer) {
        super(timer);
    }
    
    /**
     * Does nothing.
     * 
     * @param object the object passed in the notification
     * @see codebase.monitors.Monitor#notify(java.lang.Object)
     */
    public void notify(final Object object) {
    }
    
    /**
     * Does nothing.
     * 
     * @see codebase.monitors.TimedMonitor#startMonitor()
     */
    protected void startMonitor() {
    }
    
    /**
     * Does nothing.
     * 
     * @see codebase.monitors.TimedMonitor#stopMonitor()
     */
    protected void stopMonitor() {
    }
    
}
