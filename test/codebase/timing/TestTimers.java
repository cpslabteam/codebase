/*
 * Created on 6/Jun/2005
 *
 */
package codebase.timing;

import codebase.tests.EnhancedTestCase;
import codebase.timing.JavaTimer;
import codebase.timing.Timer;
import codebase.timing.Timers;

/**
 * Tests the timer utility functions
 */
public class TestTimers
        extends EnhancedTestCase {
    
    /**
     * Tests that zero ticks correspond to zero millis. Tests that that 1 tick
     * is greater that 0.
     */
    public void testTicksToMillis() {
        final double ERROR = 0.000001;
        final Timer timer = new JavaTimer();
        final long zeroTimer = Timers.getZeroTime(timer);
        
        assertDelta(0.0, Timers.ticksToMillis(timer, zeroTimer, 0), ERROR);
        assertTrue(Timers.ticksToMillis(timer, zeroTimer, 1) > 0.0);
    }
    
    /**
     * Tests that zero millis corresponds to zero ticks. Checks that one
     * millisecond is at least one tick.
     */
    public void testMillisToTicks() {
        final double ERROR = 0.000001;
        final Timer timer = new JavaTimer();
        
        assertDelta(0.0, Timers.millisToTicks(timer, 0.0), ERROR);
        assertTrue(Timers.millisToTicks(timer, 1.0) > 0);
        assertTrue(Timers.millisToTicks(timer, 0.1) > 0);
        assertTrue(Timers.millisToTicks(timer, 0.01) > 0);
        assertTrue(Timers.millisToTicks(timer, 0.001) > 0);
        assertTrue(Timers.millisToTicks(timer, 0.0001) > 0);
    }
    
    public void testIntegratedMillisTicks() {
        final double ERROR = 0.01;
        final Timer timer = new JavaTimer();
        final long zeroTimer = Timers.getZeroTime(timer);
        
        assertDelta(0.0, Timers.ticksToMillis(timer, zeroTimer, Timers
            .millisToTicks(timer, 0.0)), ERROR);
        
        assertDelta(1.0, Timers.ticksToMillis(timer, zeroTimer, Timers
            .millisToTicks(timer, 1.0)), ERROR);
        
        assertDelta(10.0, Timers.ticksToMillis(timer, zeroTimer, Timers
            .millisToTicks(timer, 10.0)), ERROR);
        
        assertDelta(11.0, Timers.ticksToMillis(timer, zeroTimer, Timers
            .millisToTicks(timer, 11.0)), ERROR);
    }
    
    
    public void testGetTimeInSeconds() {
        Timer t = new JavaTimer();
        Timers.warmup(t);
        final long zeroTime = Timers.getZeroTime(t);
        t.start();
               
        Timers.sleep(10);
        assertEquals(0.01, Timers
            .getTimeInSeconds(t, zeroTime, t.getDuration()), 0.01);
        Timers.sleep(10);
        assertEquals(0.02, Timers
            .getTimeInSeconds(t, zeroTime, t.getDuration()), 0.01);
    }
    
}
