/*
 * Created on 11/Jan/2006
 */
package codebase.iterators;

import java.util.Iterator;

/**
 * Utility class for iterators.
 */
public final class Iterators {

    /**
     * Prevent instantiation.
     */
    private Iterators() {
    }

    /**
     * Consumes all objects from an iterator.
     * <p>
     * This method is used mainly for testing purposes. It avoids having
     * superfluous <code>while</code> statements in the code making it more
     * readable.
     * <p>
     * It performs:
     *
     * <pre>
     *     while (iterator.hasNext()) {
     *        iterator.next()
     *     }
     * </pre>
     *
     * @param iterator the iterator to be exhausted.
     * @return the number of elements read from the iterator.
     */
    public static long exhaust(final Iterator<?> iterator) {
        long count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count += 1;
        }
        return count;
    }
}
