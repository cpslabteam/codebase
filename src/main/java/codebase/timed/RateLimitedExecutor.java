package codebase.timed;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * An executor that guarantees a maximum call rate.
 * <p>
 * This executor keeps an internal timer and delays the call to <tt>run()</tt> if needed
 * to converge to the specified call rate.
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
   * <p>
   * 
   * @param command the command to be executed; should not take longer than
   *          <tt>(1/rate) x 1000</tt> milliseconds
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
       * This should never happen. If it does, it means that this thread was stopped while
       * sleeping.
       */
      Thread.currentThread().interrupt();
    }

    command.run();

    totalWaitTimeMillis += waitMillis;
    totalElapsedTimeNanos += System.nanoTime() - t;
  }
}
