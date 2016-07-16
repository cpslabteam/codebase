package codebase.timed;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * An executor that guarantees a maximum call rate.
 * <p>
 * This executor keeps an internal timer and delays the call to <tt>run()</tt> if needed
 * to converge to the specified call rate.
 * <p>
 * Use this class by setting a desired maximum call rate by calling {@link #setRate(int)}.
 * This class then tracks the call rate of executions. Each time a the
 * {@link #execute(Runnable)} is called the class determines the the call should happen
 * right away or if it must wait a for a predefined period before executing the
 * 'runnable'.
 * <p>
 * The amount of time to wait is computed automatically based on the average per call
 * time.
 */
public class RateLimitedExecutor implements Executor {
    private static final int MINIMUM_SLEEP_MILLIS = 10;

    private int targetCallsPerSecond;
    private long totalElapsedTimeNanos;
    private double totalWaitTimeMillis;

    /**
     * Sets the specified number of calls per second.
     * 
     * @param target the maximum number of calls per second; must be positive.
     */
    public void setRate(int target) {
        assert target > 0 : "Calls per second must be positive";
        targetCallsPerSecond = target;
    }

    /**
     * @return the specified number of calls per second.
     */
    public int getRate() {
        return targetCallsPerSecond;
    }

    /**
     * Executes the given command, waiting, if necessary to guarantee the call rate.
     * 
     * @param command the command to be executed; should not take longer than
     *            <tt>(1/rate) x 1000</tt> milliseconds
     */
    @Override
    public void execute(Runnable command) {
        assert targetCallsPerSecond > 0 : "Target calls per second must be set before execution";

        final double waitMillis = (1000.0 / targetCallsPerSecond);

        final long t = System.nanoTime();
        try {
            final long totalElapsedTimeMillis =
                TimeUnit.MILLISECONDS.convert(totalElapsedTimeNanos, TimeUnit.NANOSECONDS);

            if ((totalElapsedTimeMillis - totalWaitTimeMillis) > 0) {
                Thread.sleep(0);
            } else {
                Thread.sleep((int) waitMillis + MINIMUM_SLEEP_MILLIS / 2);
            }
        } catch (InterruptedException e) {
            /*
             * This should never happen. If it does, it means that this thread was stopped
             * while sleeping.
             */
            Thread.currentThread().interrupt();
        }

        command.run();

        totalWaitTimeMillis += waitMillis;
        totalElapsedTimeNanos += System.nanoTime() - t;
    }
}
