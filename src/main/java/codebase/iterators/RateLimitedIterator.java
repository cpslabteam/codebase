package codebase.iterators;

import java.util.Iterator;

import codebase.iterators.AbstractDecoratorIterator;
import codebase.timed.RateLimitedExecutor;

/**
 * A decorator iterator that is limited to a maximum call rate.
 * <p>
 * This class waits a predefined amount of time between each call to {@link #next()} in
 * order to guarantee a desired call rate.
 * <p>
 * Wait time is adjusted dynamically to account for delays in calls to {@link #next()} or
 * variations of execution performance.
 * 
 * @param <E> the type of elements for the input and output of this iterator.
 */
public class RateLimitedIterator<E> extends AbstractDecoratorIterator<E> {
    private final RateLimitedExecutor executor = new RateLimitedExecutor();

    /**
     * Instantiates a new rate-limited iterator.
     *
     * @param iterator to be decorated
     * @param callsPerSecond the target call rate limit
     */
    public RateLimitedIterator(Iterator<E> iterator, int callsPerSecond) {
        super(iterator);
        executor.setRate(callsPerSecond);
    }

    /*
     * Work-around variable declared as a member to hold the next frame returned by the
     * executor. This would not be necessary in Java 8.
     */
    private E nextResult;

    /**
     * @return the next element, waiting, if necessary, to guarantee the call rate.
     */
    @Override
    public E next() {
        synchronized (this) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    nextResult = getDecorated().next();
                }
            });

            return nextResult;
        }
    }
}
