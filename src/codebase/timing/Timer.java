/*
 * Created on 6/Jun/2005
 *  
 */
package codebase.timing;

/**
 * The interface of efficient timers.
 * <p>
 * The timer interface is the base of efficient timers which are based on Ticks
 * as smallest units. Each implementation of this interface can have a different
 * number of ticks per second.
 */
public interface Timer {
    /**
     * Starts the timer.
     */
    void start();
    
    /**
     * Returns the time in ticks since the last start-call.
     * 
     * @return number of ticks
     */
    long getDuration();
    
    /**
     * Returns the number of ticks per second.
     * 
     * @return number of ticks
     */
    long getTicksPerSecond();
    
    /**
     * Returns a String with information about the timer.
     * 
     * @return timer info
     */
    
    String timerInfo();
}
