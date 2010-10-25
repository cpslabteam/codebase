/*
 * Created on 28/Fev/2005
 *  
 */
package codebase.timing;

import codebase.Format;

/**
 * Utilities for timers.
 * <p>
 */
public final class Timers {
    
    /**
     * A constant that represents the number of microseconds per second.
     */
    public static final long NANOS_PER_SECOND = 1000 * 1000 * 1000;
    
    /**
     * A constant that represents the number of millisecomds per second.
     */
    public static final long MILLIS_PER_SECOND = 1000;
    
    /**
     * The number of nanoseconds per millisecond.
     */
    static final double NANOS_PER_MILLI = 1000000.0;
    
    /**
     *  
     */
    private Timers() {
    }
    
    /**
     * Warms up the timer by calling start and getDuration 10000 times. This is
     * useful when using the java hot spot technology.
     * 
     * @param t timer to be used
     */
    public static void warmup(final Timer t) {
        final int loopLimit = 10000;
        for (int i = 0; i < loopLimit; i++) {
            t.start();
            t.getDuration();
        }
    }
    
    /**
     * Calculates the number of ticks elapsed between two reads of the timer.
     * <p>
     * This is also the time needed to perform timer calls without performing
     * any other operations in between.
     * 
     * @param t timer to be used
     * @return returns the time calculated
     */
    public static long getZeroTime(final Timer t) {
        long sum = 0;
        long zero;
        final int loopLimit = 1000;
        for (int i = 0; i < loopLimit; i++) {
            t.start();
            zero = t.getDuration();
            sum += zero;
        }
        return sum / loopLimit;
    }
    
    /**
     * Calculates the timer resolution by calling getDuration until the value is
     * different from zero.
     * 
     * @param t the timer to be used
     * @return returns the timer resolution calculated
     */
    public static long getTimerResolution(final Timer t) {
        long time;
        long numCalls = 0;
        
        t.start();
        /*
         * Perform busy wait until system time changes JavaTimer changes after
         * approx. 10 ms
         */
        do {
            time = t.getDuration();
            numCalls += 1;
        } while (time == 0);
        
        return time;
    }
    
    /**
     * Calculates the time in seconds for a given time and timer.
     * <p>
     * If the time given is smaller that the time elapsed between two calls to
     * {@link Timer#getDuration()}, it returns the smallest possible time
     * elapsed: <code> 1.0 / getTicksPerSecond()</code>.
     * 
     * @param timer The timer which was used.
     * @param zeroTime The zero time.
     * @param time The time which was meassured by the timer.
     * @return time in seconds.
     */
    public static double getTimeInSeconds(final Timer timer,
            final long zeroTime, final long time) {
        if (time < zeroTime) {
            return 1.0 / timer.getTicksPerSecond();
        } else {
            return ((double) (time - zeroTime)) / timer.getTicksPerSecond();
        }
    }
    
    /**
     * Computes the time in milliseconds corresponding to a number of ticks of
     * the timer.
     * 
     * @param timer is the timer object used. Needed for computing the number of
     *            ticks per second.
     * @param zeroTime the zero time for this timer pre-computed
     * @param totalTicks the number of ticks we wnat to translate
     * @return the accumulated time in the stop watch in milliseconds
     */
    public static double ticksToMillis(final Timer timer, final long zeroTime,
            final long totalTicks) {
        if (totalTicks == 0) {
            return 0.0;
        } else {
            return getTimeInSeconds(timer, zeroTime, totalTicks)
                    * MILLIS_PER_SECOND;
        }
    }
    
    /**
     * Computes the number of ticks that corresponds to a quantity of
     * milliseconds.
     * <p>
     * The timer may not have the resolution for representing tha milliseconds
     * requested. If, for example the resolution of the timer is 1ms, we are not
     * able to sucessfully represent in ticks 0.1ms. In this case at least one
     * ticks is returned. This means that no time can be spent whout at least
     * waiting one tick.
     * 
     * @param timer the times to be used
     * @param millis the amount of time in milliseconds
     * @return the number of ticks that must pass to complete the specified
     *         milliseconds
     */
    public static long millisToTicks(final Timer timer, final double millis) {
        final double ticksPerMilli = timer.getTicksPerSecond()
                / MILLIS_PER_SECOND;
        final long result = (long) Math.ceil(millis * ticksPerMilli);
        return result;
    }
    
    /**
     * Sleeps for a specified amount of time.
     * <p>
     * Is uses the <code>thread.sleep(int)</code> method and wraps the
     * exception to a runtime exception.
     * 
     * @param millis the time in milliseconds
     * @throws IllegalArgumentException if millis is not positive
     */
    public static void sleep(final int millis) {
        if (millis < 0) {
            throw new IllegalArgumentException(
                "The time ammount must be positive");
        }
        
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Returns the number of nanoseconds that correspond to a milisecond.
     * 
     * @param millis the time in miliseconds, greater than zero.
     * @return the number of nanoseconds that aproximates the specificed
     *         miliseconds
     */
    public static long millisToNanos(final double millis) {
        return (long) Math.ceil(millis * NANOS_PER_MILLI);
    }
    
    /**
     * Returns the number of miliseconds that correspond to a given amount of
     * nanos.
     * 
     * @param nanos the time nanoseconds, greater than zero.
     * @return the number of milliseconds that the specified amount of
     *         nanoseconds represents
     */
    public static double nanosToMillis(final long nanos) {
        return (double) nanos / (double) NANOS_PER_MILLI;
    }
    
    /**
     * Performs a timer test and outputs the data on stdout.
     * 
     * @param t the timer to be tested.
     */
    public static void timerTest(final Timer t) {
        System.out.println("Timer info: " + t.timerInfo());
        System.out.println("Warming up timer");
        warmup(t);
        System.out.println("Zero time: " + getZeroTime(t) + " ticks");
        System.out.println("Timer resolution: "
                + (double) getTimerResolution(t)
                / (double) t.getTicksPerSecond() + " seconds");
    }
    
}
