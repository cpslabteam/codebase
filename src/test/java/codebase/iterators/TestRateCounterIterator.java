package codebase.iterators;

import org.junit.Assert;
import org.junit.Test;

import codebase.iterators.RepeaterIterator;

/**
 * Tests the RateCounterIterator.
 */
public class TestRateCounterIterator {

    /**
     * Tests the stability of the rate counter in normal conditions.
     */
    @Test
    public void testRateNormal() {
        RepeaterIterator<Object> objectProvider = new RepeaterIterator<Object>(new Object());

        // Create a rate limited iterator @25objs/sec
        RateLimitedIterator<Object> t = new RateLimitedIterator<Object>(objectProvider, 25);

        // Decorate with a rate counter (window of 5 seconds)
        RateCounterIterator<Object> rc = new RateCounterIterator<Object>(t, 5);

        IteratorsUtils.consume(250, rc);
 
        Assert.assertEquals(25.0, rc.getRate(), 1.0);
    }

    /**
     * Tests the rate counter with a very small window.
     */
    @Test
    public void testRateSmallWindow() {
        RepeaterIterator<Object> objectProvider = new RepeaterIterator<Object>(new Object());

        // Create a rate limited iterator @25objs/sec
        RateLimitedIterator<Object> t = new RateLimitedIterator<Object>(objectProvider, 25);

        // Decorate with a rate counter (window of 1 second)
        RateCounterIterator<Object> rc = new RateCounterIterator<Object>(t, 1);

        IteratorsUtils.consume(125, rc);

        Assert.assertEquals(25.0, rc.getRate(), 2.0);
    }

    /**
     * Tests the rate when the input iterator ends before window time length has elapsed
     * end.
     */
    @Test
    public void testRateBeforeWidowEnd() {
        RepeaterIterator<Object> objectProvider = new RepeaterIterator<Object>(new Object());

        // Create a rate limited iterator @25objs/sec
        RateLimitedIterator<Object> t = new RateLimitedIterator<Object>(objectProvider, 25);

        // Decorate with a rate counter (a window of 5 seconds)
        RateCounterIterator<Object> rc = new RateCounterIterator<Object>(t, 1);

        IteratorsUtils.consume(50, rc);

        Assert.assertEquals(25.0, rc.getRate(), 2.0);
    }
}
