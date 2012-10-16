/*
 * Created on 11/Jun/2005
 */
package codebase.util.iterators;


/**
 * An iterator that throws {@link IllegalStateException} when an operation is called.
 * <p>
 * An invalid iterator is used for initializing iterator in situations where
 * <code>null</code> cannot be used.
 */
public final class InvalidIterator<E>
        implements ManipulatableIterator<E> {

    /**
     * A constant with the invalid cursor.
     */
    public static final InvalidIterator<Object> DEFAULT_INSTANCE = new InvalidIterator<Object>();

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
    public boolean hasNext() throws IllegalStateException {
        reportInvalidIterator();
        return false;
    }

    /**
     * @return nothing
     * @see java.util.Iterator#next()
     * @throws IllegalStateException when called
     */
    public E next() throws IllegalStateException {
        reportInvalidIterator();
        return null;
    }

    /**
     * @see java.util.Iterator#remove()
     * @throws IllegalStateException when called
     */
    public void remove() throws IllegalStateException {
        reportInvalidIterator();
    }

    /**
     * @return nothing
     * @see codebase.util.iterators.ManipulatableIterator#peek()
     * @throws IllegalStateException when called
     */
    public E peek() throws IllegalStateException {
        reportInvalidIterator();
        return null;
    }

    /**
     * @return nothing
     * @see codebase.util.iterators.ManipulatableIterator#supportsPeek()
     * @throws IllegalStateException when called
     */
    public boolean supportsPeek() {
        reportInvalidIterator();
        return false;
    }

    /**
     * @return nothing
     * @see codebase.util.iterators.ManipulatableIterator#supportsRemove()
     * @throws IllegalStateException when called
     */
    public boolean supportsRemove() {
        reportInvalidIterator();
        return false;
    }

    /**
     * @return nothing
     * @see codebase.util.iterators.ManipulatableIterator#supportsUpdate()
     * @throws IllegalStateException when called
     */
    public boolean supportsUpdate() {
        reportInvalidIterator();
        return false;
    }

    /**
     * @param replacement ignored.
     * @see codebase.util.iterators.ManipulatableIterator#update(java.lang.Object)
     * @throws IllegalStateException when called
     */
    public void update(final Object replacement) throws IllegalStateException {
        reportInvalidIterator();
    }

    /*
     * 
     * @throws IllegalStateException when called
     */
    public void reset() throws IllegalStateException {
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
