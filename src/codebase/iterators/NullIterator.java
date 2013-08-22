/*
 * Created on 11/Jun/2005
 */
package codebase.iterators;

import java.util.NoSuchElementException;


/**
 * An iterator that throws {@link IllegalStateException} when an operation is called.
 * <p>
 * An invalid iterator is used for initializing iterator in situations where
 * <code>null</code> cannot be used.
 * 
 * @param <E> the type of all the objects to be treated.
 */
public final class NullIterator<E>
        implements ManipulatableIterator<E> {

    /**
     * A constant with the invalid cursor.
     */
    public static final NullIterator<Object> DEFAULT_INSTANCE = new NullIterator<Object>();

    /**
     * Throws an exception signaling that that the current strategy is invalid.
     */
    private static void reportInvalidIterator() {
        throw new IllegalStateException("The current iterator is invalid");
    }

    /**
     * @return nothing
     * @see java.util.Iterator#hasNext()
     * @throws IllegalStateException when called
     */
    public boolean hasNext() {
        reportInvalidIterator();
        return false;
    }

    /**
     * @return nothing
     * @see java.util.Iterator#next()
     * @throws IllegalStateException when called
     */
    public E next() {
        throw new NoSuchElementException();
    }

    /**
     * @see java.util.Iterator#remove()
     * @throws IllegalStateException when called
     */
    public void remove() {
        reportInvalidIterator();
    }

    /**
     * @return nothing
     * @see codebase.iterators.ManipulatableIterator#peek()
     * @throws IllegalStateException when called
     */
    public E peek() {
        reportInvalidIterator();
        return null;
    }

    /**
     * @return nothing
     * @see codebase.iterators.ManipulatableIterator#supportsPeek()
     * @throws IllegalStateException when called
     */
    public boolean supportsPeek() {
        reportInvalidIterator();
        return false;
    }

    /**
     * @return nothing
     * @see codebase.iterators.ManipulatableIterator#supportsRemove()
     * @throws IllegalStateException when called
     */
    public boolean supportsRemove() {
        reportInvalidIterator();
        return false;
    }

    /**
     * @return nothing
     * @see codebase.iterators.ManipulatableIterator#supportsUpdate()
     * @throws IllegalStateException when called
     */
    public boolean supportsUpdate() {
        reportInvalidIterator();
        return false;
    }

    /**
     * @param replacement ignored.
     * @see codebase.iterators.ManipulatableIterator#update(java.lang.Object)
     * @throws IllegalStateException when called
     */
    public void update(final Object replacement) {
        reportInvalidIterator();
    }

    /*
     * 
     * @throws IllegalStateException when called
     */
    public void reset() {
        reportInvalidIterator();
    }

    /*
     * @throws IllegalStateException when called
     */
    public boolean supportsReset() {
        reportInvalidIterator();
        return false;
    }
}
