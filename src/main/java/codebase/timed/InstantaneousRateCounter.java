package codebase.timed;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * Utility class that tracks call rates with a sliding sample window.
 * <p>
 * This class can be used to compute instantaneous access rates or I/O or other
 * operations. For example, for measuring records per second, frames per second, among
 * other.
 * <p>
 * Use this class by reporting events through {@link #reportEvent()} and then querying the
 * event rate by calling {@link #getRate()}.
 */
public final class InstantaneousRateCounter {

    private static final int DEFAULT_INSTANTANEOUS_WINDOW_SIZE = 2;

    private static final double NANOS_IN_A_SECOND = TimeUnit.SECONDS.toNanos(1);

    /**
     * An event that represents returning a frame at a given instant.
     * <p>
     * Events expire automatically after they have been in the queue for longer that the
     * window length.
     */
    private class TimestampedEvent implements Delayed {
        private final long observationTimeStamp;

        TimestampedEvent(long nanoTimeStamp) {
            observationTimeStamp = nanoTimeStamp;
        }

        @Override
        public int compareTo(Delayed o) {
            long delta = (getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
            return (int) codebase.MathUtil.sign(delta);
        }

        @Override
        public boolean equals(Object obj) {
            return this == obj;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            long elapsedSinceObservation = System.nanoTime() - observationTimeStamp;
            long timeToWindow = TimeUnit.NANOSECONDS.convert(sampleWindowSize, TimeUnit.SECONDS)
                    - elapsedSinceObservation;

            return unit.convert(timeToWindow, TimeUnit.NANOSECONDS);
        }

        @Override
        public int hashCode() {
            return Long.valueOf(observationTimeStamp).hashCode();
        }
    }

    /**
     * The size of the sample window in seconds.
     */
    private final int sampleWindowSize;

    /**
     * Creates an access observations tracker with a default sample window.
     */
    public InstantaneousRateCounter() {
        this(DEFAULT_INSTANTANEOUS_WINDOW_SIZE);
    }

    /**
     * Creates an access observations tracker with a given sample window.
     * 
     * @param window the sample window in seconds; must be positive
     */
    public InstantaneousRateCounter(final int window) {
        assert window > 0 : " Sample window must be positive";
        sampleWindowSize = window;

        reset();
    }

    /**
     * The queue of frame access observations that are automatically dropped when they
     * expire.
     */
    private final DelayQueue<TimestampedEvent> eventQueue = new DelayQueue<TimestampedEvent>();

    /**
     * The start timestamp since the object was constructed, or the last call to reset().
     */
    private long startTimestampNanos;

    /**
     * Adds an observation to the sample window with the current timestamp.
     */
    public void reportEvent() {
        this.reportEvent(System.nanoTime());
    }

    /**
     * Adds an observation to the sample window with a given timestamp.
     * 
     * @param nanoTimeStamp the time stamp.
     */
    public void reportEvent(long nanoTimeStamp) {
        eventQueue.put(new TimestampedEvent(nanoTimeStamp));
        eventQueue.poll();
    }


    /**
     * Gets the current instantaneous rate.
     * <p>
     * If the elapsed time is smaller than the window size, it considers the elapse time
     * as the window. <b>NB</b>In this case subsequent calls to this method may return
     * distinct values.
     *
     * @return the number of observations inside the sample window divided by the size of
     *         the window in seconds; or the number of events divided by the elapsed time,
     *         if the elapse time is smaller that the sample window size.
     */
    public double getRate() {
        final long timeElapsedSinceStart = System.nanoTime() - startTimestampNanos;

        if (TimeUnit.SECONDS.convert(timeElapsedSinceStart,
                TimeUnit.NANOSECONDS) >= sampleWindowSize) {
            return eventQueue.size() / (1.0 * sampleWindowSize);
        } else {
            return eventQueue.size() / (1.0 * (timeElapsedSinceStart / NANOS_IN_A_SECOND));
        }
    }

    /**
     * Gets the number of observations inside the time window.
     *
     * @return the observation count.
     */
    public long getCount() {
        return eventQueue.size();
    }

    /**
     * Gets the window size.
     * 
     * @return the sample window size.
     */
    public int getWindowSize() {
        return sampleWindowSize;
    }

    /**
     * Resets the rate counter.
     */
    public void reset() {
        startTimestampNanos = System.nanoTime();
        eventQueue.clear();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("InstantaneousRateCounter [windowSize=");
        builder.append(getWindowSize());
        builder.append(", queue count=");
        builder.append(getCount());
        builder.append(", rate=");
        builder.append(getRate());
        builder.append("]");
        return builder.toString();
    }
}
