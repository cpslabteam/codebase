package codebase.timed;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * An executor that guarantees that a command is executed after a pre-assigned time
 */
public class TimeLimitedExecutor implements Executor {

  private double wait;
  private long totalElapsedTimeNanos;
  private double totalWaitTimeMillis;

  /**
   * Sets the wait time.
   * 
   * @param w is the wait time and must be positive.
   */
  public void setWait(double w) {
    assert w >= 0 : "Wait cannot be negative";
    wait = w > 10 ? w : 10;
  }

  /**
   * @return the wait time.
   */
  public double getWait() {
    return wait;
  }

  /**
   * Executes the given command, waiting a pre-assigned time.
   */
  @Override
  public void execute(Runnable command) {
    final double waitMillis = wait;

    final long t = System.nanoTime();
    try {
      final long totalElapsedTimeMillis =
        TimeUnit.MILLISECONDS.convert(totalElapsedTimeNanos, TimeUnit.NANOSECONDS);

      if ((totalElapsedTimeMillis - totalWaitTimeMillis) > 0) {
        Thread.sleep(0);
      } else {
        Thread.sleep((int) Math.ceil(waitMillis));
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
