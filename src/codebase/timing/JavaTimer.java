/*
 * Created on 6/Jun/2005
 *  
 */
package codebase.timing;

/**
 * The java language timer.
 * <p>
 * Implements the Timer interface based on java.lang.System.currentTimeMillis.
 */
public class JavaTimer
        implements Timer {
    
    /**
     * The starting time of the current timer.
     */
    protected long nanos;
    
    /**
     * Constructs a JavaTimer.
     */
    public JavaTimer() {
    }
    
    /**
     * Starts a JavaTimer.
     */
    public final void start() {
        nanos = System.nanoTime();
    }
    
    /**
     * Returns time in ms since JavaTimer was started.
     * 
     * @return returns time in ms since JavaTimer was started
     */
    public final long getDuration() {
        return System.nanoTime() - nanos;
    }
    
    /**
     * Returns number of ticks per second (1000).
     * 
     * @return returns number of ticks per second (1000)
     */
    public final long getTicksPerSecond() {
        return Timers.NANOS_PER_SECOND;
    }
    
    /**
     * Returns string "nanoTime()".
     * 
     * @return returns string "nanoTime()"
     */
    public final String timerInfo() {
        return "java.lang.System.nanoTime()";
    }
    
    /**
     * Displays the diagnostic information for the timer.
     * 
     * @param args ignored.
     */
    public static void main(final String[] args) {
        Timers.timerTest(new JavaTimer());
    }
}
