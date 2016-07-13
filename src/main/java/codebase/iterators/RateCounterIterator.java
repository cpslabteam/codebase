package codebase.iterators;

import java.util.Iterator;

import codebase.iterators.AbstractDecoratorIterator;
import codebase.timed.InstantaneousRateCounter;
/**
 * A decorator iterator the tracks the instantaneous call rate to {@link #next()}.
 * 
 * @param <E> the type of elements of the of the input and output of this iterator.
 */
public class RateCounterIterator<E> extends AbstractDecoratorIterator<E> {
    private static final int DEFAULT_SAMPLE_WINDOW_SIZE_SECS = 2;

    private final int sampleWindowSize;
    private final InstantaneousRateCounter nextElementRequestEvents;
    private long totalCount;

    /**
     * Creates a frame rate counter with a default sample window of
     * {@value #DEFAULT_SAMPLE_WINDOW_SIZE_SECS} seconds.
     * 
     * @param iterator the iterator to be decorated
     */
    public RateCounterIterator(final Iterator<E> iterator) {
        this(iterator, DEFAULT_SAMPLE_WINDOW_SIZE_SECS);
    }

    /**
     * Creates a frame rate counter with a given sample window.
     * 
     * @param iterator the stream to be decorated
     * @param window the sample window in seconds; must be positive
     */
    public RateCounterIterator(Iterator<E> iterator, final int window) {
        super(iterator);

        assert window > 0 : " Sample window must be positive";
        sampleWindowSize = window;

        nextElementRequestEvents = new InstantaneousRateCounter(sampleWindowSize);
    }

    /**
     * Gets the current instantaneous request rate.
     * <p>
     * If the elapsed time is smaller than the window size, it considers the elapse time
     * as the window.
     *
     * @return the number of frames inside the sample window divided by the size of the
     *         window in seconds.
     */
    public final double getRate() {
        return nextElementRequestEvents.getRate();
    }

    /**
     * Gets the number of frames inside the time window.
     *
     * @return the frame count
     */
    public final long getWindowCount() {
        return nextElementRequestEvents.getCount();
    }

    /**
     * Gets the total number of calls recorded.
     *
     * @return the total frame count
     */
    public final long getCount() {
        return totalCount;
    }

    /**
     * Gets the next element.
     * <p>
     * Records an event to compute the rate.
     * 
     * @return the next element form the decorated stream.
     */
    @Override
    public E next() {
        nextElementRequestEvents.reportEvent();
        ++totalCount;
        return getDecorated().next();
    }

    /**
     * Resets the frame rate counter.
     */
    public void reset() {
        nextElementRequestEvents.reset();
    }
}
