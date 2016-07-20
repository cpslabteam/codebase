package codebase.timed;

import java.util.concurrent.Executor;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests the {@link TimeLimitedExecutor}.
 * <p>
 * Tests that the time limited executor guarantees accurate rate given a call wait time and that is behaves
 * correctly when the wait time is reseted at run-time.
 */
public class TestTimeLimitedExecutor {

    static void instrumentedExecute(final Executor e,
                                    final int times,
                                    final InstantaneousRateCounter counter) {
        for (int i = 0; i < times; i++) {
            e.execute(new Runnable() {
                @Override
                public void run() {
                    counter.reportEvent();
                }
            });
        }
    }

    static void instrumentedExecute(final TimeLimitedExecutor e,
                                    final int times,
                                    final InstantaneousRateCounter counter,
                                    int... rates) {
        for (int i = 0; i < times; i++) {
            final int nextRate = rates[i % rates.length];
            e.setWait(1000.0/nextRate);
            e.execute(new Runnable() {
                @Override
                public void run() {
                    counter.reportEvent();
                }
            });
        }
    }

    /**
     * Checks the call rate of a time limited executor.
     * <p>
     * Creates a simple executor and rate counter; executes a number of calls and then
     * checks the measured call rate.
     */
    @Test
    public void testSimple() {
        TimeLimitedExecutor rle = new TimeLimitedExecutor();
        InstantaneousRateCounter c = new InstantaneousRateCounter();

        //for a rate of 20 we need to wait 50 milliseconds between frames 

        rle.setWait(50);

        instrumentedExecute(rle, 20, c);

        Assert.assertEquals(20, c.getRate(), 1.0);
    }

    /**
     * Checks that the call rate is stable through a large sample window.
     * <p>
     * Creates a rate counter with a large sample window and executes the executor for a
     * period smaller than the window.
     */
    @Test
    public void testCallRateStabilityLargeWindow() {
        TimeLimitedExecutor rle = new TimeLimitedExecutor();
        InstantaneousRateCounter c = new InstantaneousRateCounter();

        //for a rate of 25 we need to wait 40 milliseconds between frames
        
        rle.setWait(40);

        instrumentedExecute(rle, 23, c);

        Assert.assertEquals(25, c.getRate(), 1.0);
    }


    /**
     * Checks that the call rate is invariable in time.
     * <p>
     * Executes and performs waits, checking that the call rate is not altered.
     */
    @Test
    public void testCallRateInvariabiltiy() throws InterruptedException {
        TimeLimitedExecutor rle = new TimeLimitedExecutor();
        InstantaneousRateCounter c = new InstantaneousRateCounter();

        //for a rate of 10 we need to wait 100 milliseconds between frames
        
        rle.setWait(100);

        // execute for 5 times = 1 sec -- larger error if we lie behind the window
        instrumentedExecute(rle, 10, c);
        Assert.assertEquals(10, c.getRate(), 2);

        // execute for 20 times = 2.5 sec
        instrumentedExecute(rle, 15, c);
        Assert.assertEquals(10, c.getRate(), 1);

        // Wait 2 seconds
        Thread.sleep(2000);
        Assert.assertEquals(10, c.getRate(), 1);

        // Wait another 3 seconds
        Thread.sleep(3000);
        Assert.assertEquals(10, c.getRate(), 1);

    }

    /**
     * Checks that reseting the call rate to a new value converges to the last set rate.
     * <p>
     * Changes the rate of the executor and checks the rate change using a rate counter.
     */
    @Test
    public void testResetRate() {
        TimeLimitedExecutor rle = new TimeLimitedExecutor();
        InstantaneousRateCounter c = new InstantaneousRateCounter();

        //for a rate of 10 we need to wait 100 milliseconds between frames
        
        rle.setWait(100);
        instrumentedExecute(rle, 50, c);

        Assert.assertEquals(10, c.getRate(), 1.0);

        // Sets the second rate and execute the calls
        rle.setWait(50);
        instrumentedExecute(rle, 50, c);

        Assert.assertEquals(20, c.getRate(), 1.0);
    }

    /**
     * Checks that reseting frame rates converges to the average frame rate.
     * <p>
     * The instantaneous rate counter window is smaller than the
     */
    @Test
    public void testCovergenceToAverage() {
        TimeLimitedExecutor rle = new TimeLimitedExecutor();
        InstantaneousRateCounter c = new InstantaneousRateCounter();

        instrumentedExecute(rle, 40, c, 14, 16);
        Assert.assertEquals((14 + 16) / 2, c.getRate(), 0.5);

        instrumentedExecute(rle, 40, c, 14, 15, 16);
        Assert.assertEquals((14 + 15 + 16) / 3, c.getRate(), 0.5);

        instrumentedExecute(rle, 40, c, 10, 15, 20);
        Assert.assertEquals((10 + 15 + 20) / 3, c.getRate(), 0.5);

        instrumentedExecute(rle, 40, c, 10, 14, 16, 20);
        Assert.assertEquals((10 + 14 + 16 + 20) / 4, c.getRate(), 0.5);
    }
}
