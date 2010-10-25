/*
 * Created on 6/Fev/2006
 */
package codebase.monitors;

import codebase.timing.Timer;
import codebase.timing.Timers;

/**
 * Abstract class of monitors that track the time elapsed between
 * {@link #start()} and {@link #stop()}.
 * <p>
 * This monitor is based on a given timer. This way different timers may be
 * used. Additionally, it is possible to know the elaped time through the timer.
 */
public abstract class TimedMonitor
        implements Monitor {
    
    /**
     * The timer being user as stop watch.
     */
    private final Timer stopWatch;
    
    /**
     * Number of ticks observed when stop was called.
     */
    private long stopWatchTicks;
    
    /**
     * Timer resolution.
     */
    private final long zeroTime;
    
    /**
     * Flag that indicates that the timer was started.
     */
    private boolean isStarted;
    
    /**
     * Flag that indicates that the timer was stopped.
     */
    private boolean isStopped;
    
    /**
     * Creates a new timer monitor and warms-up the timer.
     * 
     * @param timer the timer to be used.
     */
    public TimedMonitor(final Timer timer) {
        super();
        stopWatch = timer;
        Timers.warmup(stopWatch);
        zeroTime = Timers.getZeroTime(stopWatch);
    }
    
    /**
     * Gets the time elapsed mediating {@link #start()} and {@link #stop()}.
     * 
     * @return the time in milliseconds or <code>NaN</code> if the monitor was
     *         not yet started.
     * @throws IllegalStateException if the timer was not stopped.
     */
    public final double getTime() throws IllegalStateException {
        if (!isStopped) {
            return Double.NaN;
        } else {
            return Timers.ticksToMillis(stopWatch, zeroTime, stopWatchTicks);
        }
    }
    
    /**
     * Gets the time elapsed since that last call to {@link #start()}.
     * 
     * @return the time in milliseconds, or <code>NaN</code> if the monitor
     *         was not yet started.
     */
    public final double getTimeSoFar() {
        if (!isStarted) {
            return Double.NaN;
        } else {
            return Timers.ticksToMillis(stopWatch, zeroTime, stopWatchTicks);
        }
    }
    
    /**
     * Should be implemented by descending classes.
     * 
     * @param object the object passed in the notification
     * @see codebase.monitors.Monitor#notify(java.lang.Object)
     */
    public abstract void notify(final Object object);
    
    /**
     * Starts the timer.
     * 
     * @see #startMonitor()
     */
    public final void start() {
        startMonitor();
        stopWatch.start();
        isStarted = true;
        isStopped = false;
    }
    
    /**
     * Starts the monitoring task.
     * <p>
     * Implemented to provide aditional behavior when monitor start.
     */
    protected abstract void startMonitor();
    
    /**
     * Stops the timer and saves the number of ticks of the timer.
     * 
     * @see codebase.monitors.Monitor#stop()
     * @see #stopMonitor()
     * @throws IllegalStateException if the task was not started.
     */
    public final void stop() throws IllegalStateException {
        if (!isStarted) {
            throw new IllegalStateException("The trask was not started");
        }
        stopWatchTicks = stopWatch.getDuration();
        isStopped = true;
        stopMonitor();
    }
    
    /**
     * Stops the monitoring task.
     * <p>
     * Implemented to provide aditional behavior when monitor stops.
     */
    protected abstract void stopMonitor();
    
}
