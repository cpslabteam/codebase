package codebase.iterators;

import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;

import codebase.iterators.ArrayIterator;
import codebase.iterators.RepeaterIterator;

/**
 * Basic sanity tests for the <tt>Iterators</tt> utilities.
 */
public class TestIteratorsUtils {

    /**
     * Consumes items in parallel and measures the time-to-finish.
     * 
     * @return the cost of the call in milliseconds
     */
    private static long timedConsumeParallel(int n, int timeout, Iterator<?>... iterators)
            throws InterruptedException {
        final long t = System.currentTimeMillis();
        IteratorsUtils.consumeParallel(n, timeout, iterators);
        return System.currentTimeMillis() - t;
    }

    /**
     * Checks the special case where the parallel consumer consumes elements from one
     * iterator.
     * <p>
     * The reported frame rate must be the same as the timed iterator.
     */
    @Test
    public void testConsumeParallelSingleConsumption() throws InterruptedException {
        RepeaterIterator<Object> objectProvider = new RepeaterIterator<Object>(new Object());
        RateCounterIterator<Object> rci =
            new RateCounterIterator<Object>(new RateLimitedIterator<Object>(objectProvider, 7));

        IteratorsUtils.consumeParallel(150, 10, rci);

        Assert.assertEquals(7.0, rci.getRate(), 0.5);
    }

    /**
     * Checks the situation where the input finishes before the timeout.
     * <p>
     * When the input finishes before the the timeout, we
     * IteratorsUtils#consumeParallel(int, int, Iterator...)} must return at once and
     * not wait foe the timeout.
     */
    @Test
    public void testConsumeParallelSingleFinishBeforeTimeout() throws InterruptedException {
        Iterator<Object> objectProvider =
            new ArrayIterator<Object>(new Object[] { new Object(), new Object(), new Object() });

        RateCounterIterator<Object> rci =
            new RateCounterIterator<Object>(new RateLimitedIterator<Object>(objectProvider, 5));

        // Try consuming 100 elements @5objs/sec (less 1 sec) with a timeout of 3s
        Assert.assertTrue(3 * 1000 > 2 * timedConsumeParallel(100, 3, rci));
    }

    /**
     * Checks the when the method returns no later than the timeout.
     * <p>
     * The time out must be at most the specified.
     */
    @Test
    public void testConsumeParallelSingleFinishAfterTimeout() throws InterruptedException {
        RepeaterIterator<Object> objectProvider = new RepeaterIterator<Object>(new Object());
        RateCounterIterator<Object> rci =
            new RateCounterIterator<Object>(new RateLimitedIterator<Object>(objectProvider, 5));

        // Try consuming 25 elements @5fps (5s total) with a timeout of 3s
        Assert.assertEquals(3 * 1000, timedConsumeParallel(25, 3, rci), 500);
    }

    /**
     * Checks the independence of the parallel consumption of two iterators.
     * <p>
     * The reported rate must be the frame rate of each stream.
     */
    @Test
    public void testConsumeParallelIndependenceTwo() throws InterruptedException {
        RepeaterIterator<Object> objectProvider = new RepeaterIterator<Object>(new Object());

        RateCounterIterator<Object> rci1 =
            new RateCounterIterator<Object>(new RateLimitedIterator<Object>(objectProvider, 7));

        RateCounterIterator<Object> rci2 =
            new RateCounterIterator<Object>(new RateLimitedIterator<Object>(objectProvider, 11));

        Assert.assertEquals(10 * 1000, timedConsumeParallel(150, 10, rci1, rci2), 500);

        Assert.assertEquals(7.0, rci1.getRate(), 1.0);
        Assert.assertEquals(11.0, rci2.getRate(), 1.0);
    }

    /**
     * Checks the independence of the parallel consumption of multiple streams.
     * <p>
     * The reported rate must be the frame rate of each input.
     */
    @Test
    public void testConsumeParallelIndependenceMultiple() throws InterruptedException {
        RepeaterIterator<Object> objectProvider = new RepeaterIterator<Object>(new Object());

        RateCounterIterator<Object> rci1 =
            new RateCounterIterator<Object>(new RateLimitedIterator<Object>(objectProvider, 7));

        RateCounterIterator<Object> rci2 =
            new RateCounterIterator<Object>(new RateLimitedIterator<Object>(objectProvider, 11));

        RateCounterIterator<Object> rci3 =
            new RateCounterIterator<Object>(new RateLimitedIterator<Object>(objectProvider, 13));

        Assert.assertEquals(10000, timedConsumeParallel(150, 10, rci1, rci2, rci3), 500);

        Assert.assertEquals(7.0, rci1.getRate(), 1.0);
        Assert.assertEquals(11.0, rci2.getRate(), 1.0);
        Assert.assertEquals(13.0, rci3.getRate(), 1.0);
    }
}
