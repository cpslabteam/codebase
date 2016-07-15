package codebase.iterators;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for the RateLimitedIterator.
 * <p>
 * The general test strategy consists of calling IteratorsUtils#consume(int,
 * java.util.Iterator) to consume a bulk of elements from a RateLimitedIterator and the
 * verify the call rate.
 */
public class TestRateLimitedIterator {

    /**
     * Checks the limited iterator with a very small number of elements per second.
     */
    @Test
    public void testTimingSlow() {
        RepeaterIterator<Object> objectProvider = new RepeaterIterator<Object>(new Object());

        // Create a rate limited iterator @5objs/sec
        RateLimitedIterator<Object> t = new RateLimitedIterator<Object>(objectProvider, 5);

        // Decorate with a rate counter
        RateCounterIterator<Object> rc = new RateCounterIterator<Object>(t);

        IteratorsUtils.consume(50, rc);
        Assert.assertEquals(5.0, rc.getRate(), 1.0);

        IteratorsUtils.consume(125, rc);
        Assert.assertEquals(5.0, rc.getRate(), 0.5);
    }


    /**
     * Tests the timie limited iterator at a regular rate of 15ojs/sec.
     */
    @Test
    public void testTimingRegular() {
        RepeaterIterator<Object> objectProvider = new RepeaterIterator<Object>(new Object());

        // Create a rate limited iterator @15objs/sec
        RateLimitedIterator<Object> t = new RateLimitedIterator<Object>(objectProvider, 15);

        // Decorate with a rate counter 
        RateCounterIterator<Object> rc = new RateCounterIterator<Object>(t);

        IteratorsUtils.consume(150, rc);
        Assert.assertEquals(15.0, rc.getRate(), 1);
    }

    /**
     * Tests the iterator at a fast rate to validate the absence of simple bottlenecks.
     */
    @Test
    public void testTimingInsane() {
        RepeaterIterator<Object> objectProvider = new RepeaterIterator<Object>(new Object());

        // Create a rate limited iterator @120objs/sec
        RateLimitedIterator<Object> t = new RateLimitedIterator<Object>(objectProvider, 120);

        // Decorate with the rate counter
        RateCounterIterator<Object> rc = new RateCounterIterator<Object>(t);

        IteratorsUtils.consume(600, rc);
        Assert.assertEquals(120.0, rc.getRate(), 1.0);
    }
}
