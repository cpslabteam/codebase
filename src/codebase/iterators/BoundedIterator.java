package codebase.iterators;

import java.util.Iterator;

/**
 * An iterator decorator that establishes a bound in the number of elements.
 * <p>
 * This class bounds the number of elements returned by an iterator and it can be used to
 * set artificial limits on possible unbounded iterators. It re-implements the method
 * {@link Iterator#next()} adding limit checking. It can be used decorator
 * 
 * @param <E> the type of all the objects to be treated.
 * @author Paulo Carreira
 */
public class BoundedIterator<E> extends AbstractDecoratorIterator<E> {
    /**
     * The maximum number of elements.
     */
    private final int elementLimit;

    /**
     * The number of elements returned so far.
     */
    private int returnedSoFar;

    /**
     * Creates a new bounded iterator.
     * 
     * @param instance the queue to be bound
     * @param limit the number of elements, must be positive or zero
     * @throws IllegalArgumentException if the limit is not positive or zero.
     */
    public BoundedIterator(final Iterator<E> instance, final int limit) {
        super(instance);
        if (limit < 0) {
            throw new IllegalArgumentException("The limit must be positive or zero");
        }

        elementLimit = limit;
    }

    /**
     * Checks if the element limit has not been reached.
     * 
     * @return <code>true</code> if the number of elements returned so far is smaller that
     *         the specified limit; returns <code>false</code> otherwise.
     */
    public final boolean hasNext() {
        return (returnedSoFar < elementLimit) && super.hasNext();
    }

    /**
     * Returns the next object in the iterator.
     * 
     * @return the next object from the decorated iterator
     * @throws IllegalStateException if limit has been reached
     */
    public final E next() {
        if (returnedSoFar < elementLimit) {
            returnedSoFar += 1;
            return getDecorated().next();
        } else {
            throw new IllegalStateException("Element limit reached");
        }
    }

    /**
     * Removes as item from the decorated iterator.
     * <p>
     * If the <i>remove</i> operation of the decorated instance succeeds, then the number
     * of elements returned so far is decremented.
     * 
     * @throws IllegalStateException if no element can be removed
     */
    public final void remove() {
        try {
            getDecorated().remove();
            returnedSoFar += 1;
        } catch (Exception e) {
            throw new IllegalStateException("Cannot remove element", e);
        }
    }
}
