package codebase.timed;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * An executor that guarantees that a command is executed after a pre-assigned time.
 */
public class TimeLimitedExecutor implements Executor {

    private static final int MINIMUM_JVM_WAIT_MILLIS = 10;
    
    private double waitMillis;
    private long totalElapsedTimeNanos;
    private double totalWaitTimeMillis;

    /**
     * Sets the wait time.
     * 
     * @param w is the wait time and must be positive.
     */
    public void setWait(double w) {
        assert w >= 0 : "Wait cannot be negative";
        waitMillis = w > MINIMUM_JVM_WAIT_MILLIS ? w : MINIMUM_JVM_WAIT_MILLIS;
    }

    /**
     * @return the wait time.
     */
    public double getWait() {
        return waitMillis;
    }

    /**
     * Executes the given command, waiting a pre-assigned time.
     */
    @Override
    public void execute(Runnable command) {
        final double wait = waitMillis;

        final long t = System.nanoTime();
        try {
            final long totalElapsedTimeMillis =
                TimeUnit.MILLISECONDS.convert(totalElapsedTimeNanos, TimeUnit.NANOSECONDS);

            if ((totalElapsedTimeMillis - totalWaitTimeMillis) > 0) {
                Thread.sleep(0);
            } else {
                Thread.sleep((int) Math.ceil(wait));
            }
        } catch (InterruptedException e) {
            /*
             * This should never happen. If it does, it means that this thread was stopped
             * while sleeping.
             */
            Thread.currentThread().interrupt();
        }

        command.run();

        totalWaitTimeMillis += wait;
        totalElapsedTimeNanos += System.nanoTime() - t;
    }
}
