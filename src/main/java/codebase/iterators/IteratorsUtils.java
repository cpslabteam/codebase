package codebase.iterators;

import java.util.Iterator;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Utility class with utilities for iterators.
 */
public final class IteratorsUtils {

    /*
     * Prevents the instantiation of the utility class.
     */
    private IteratorsUtils() {
    }

    /**
     * Creates a thread-interruptible consumer.
     * 
     * @return a consumer that will stop upon a call to t.interrupt()
     */
    private static Runnable newInterruptibleConsumer(final Iterator<?> iterator, final int n) {
        return new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < n; i++) {
                    if (Thread.currentThread().isInterrupted()) {
                        return;
                    }

                    if (iterator.hasNext()) {
                        iterator.next();
                    }
                }
            }
        };
    }

    /**
     * Consumes a predefined number of elements from an iterator.
     * 
     * @param n the number of elements to consume; should not be negative
     * @param iterator used as input to consumed elements from.
     */
    public static void consume(final int n, final Iterator<?> iterator) {
        assert n >= 0 : "Number of elements to consume should not be negative";

        for (int i = 0; i < n; i++) {
            if (iterator.hasNext()) {
                iterator.next();
            }
        }
    }

    /**
     * Consumes elements from multiple iterators in parallel.
     * <p>
     * This method returns when all the iterators have been consumed or when a timeout
     * occurs, whichever comes first.
     * 
     * @param n the number of elements to be consumed from each iterator
     * @param timeout the timeout in seconds
     * @param iterators the iterators to be exhausted
     * @throws InterruptedException if any thread is interrupted
     */
    public static void consumeParallel(final int n,
                                       final int timeout,
                                       final Iterator<?>... iterators)
            throws InterruptedException {
        final ExecutorService e = Executors.newFixedThreadPool(iterators.length);

        for (Iterator<?> it : iterators) {
            e.submit(newInterruptibleConsumer(it, n));
        }

        /*
         * We need to call shutdown() otherwise the Executor.awaitTermination will keep on
         * blocked if the threads finish before timeout.
         */
        e.shutdown();

        boolean finished = e.awaitTermination(timeout, TimeUnit.SECONDS);

        boolean mustCleanUp = !finished;
        if (mustCleanUp) {
            e.shutdownNow();
        }
    }

}
