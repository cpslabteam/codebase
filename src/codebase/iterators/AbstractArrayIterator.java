package codebase.iterators;

/**
 * Abstract class for iterators over array-like collections.
 * <p>
 * Iterates over the elements of an array-like collection (A collection that allows direct
 * access to its elements). The descending classes should implement the
 * {@link #getElement(int)}.
 * <p>
 * Nothing is assumed regarding update operations. A descending class may or may not allow
 * update.
 * 
 * @param <E> the type of all the objects to be treated by this iterator.
 * 
 * @since Created on 4/Mai/2005
 */
public abstract class AbstractArrayIterator<E>
        implements UpdatableIterator<E> {

    /**
     * The number of objects to be consumed.
     */
    protected final int size;

    /**
     * Current position.
     */
    protected int offset;

    /**
     * Constructs a new array iterator with a predefined size.
     * 
     * @param length the size of the array. Can be zero.
     */
    public AbstractArrayIterator(final int length) {
        if (length < 0) {
            // Bug corrected: We were throwing an exception the array length was
            // zero. When it is zero is behaves as empty iterator.
            throw new IllegalArgumentException("Length of the iterator cannot be negative");
        }
        
        size = length;
    }

    /**
     * Checks if the current element is valid.
     * 
     * @return <code>true</code> if the internal element pointer is small that the array
     *         length
     */
    protected final boolean isValid() {
        return (offset < size);
    }

    /**
     * Check if there are more elements in the array.
     * 
     * @return <code>true</code> if the the end of the array has not yet been reached
     */
    public final boolean hasNext() {
        return offset < size;
    }

    /**
     * Returns an element at a specified position.
     * 
     * @param pos the position in the array
     * @return the element at the specified array position
     */
    protected abstract E getElement(final int pos);

    /**
     * Returns the next element in the array.
     * 
     * @return the element at the current offset
     */
    public final E next() {
        final E o = getElement(offset);
        offset += 1;
        return o;
    }

    /**
     * Void implementation.
     * 
     * @throws UnsupportedOperationException when called
     */
    public final void remove() {
        throw new UnsupportedOperationException("Can not remove from an array iterator.");
    }

    /**
     * Resets the iterator to the first element in the array.
     */
    public final void reset() {
        offset = 0;
    }

    /**
     * Checks if the array iterator supports reset.
     * 
     * @return <code>true</code>
     */
    public final boolean supportsReset() {
        return true;
    }

    /**
     * Return the current element.
     * 
     * @see codebase.iterators.UpdatableIterator#peek()
     * @return the element at the current offset
     * @see AbstractArrayIterator#offset
     */
    public final E peek() {
        if (isValid()) {
            final E o = getElement(offset);
            return o;
        } else {
            throw new IllegalStateException();
        }
    }

    /**
     * Check is an array supports direct access.
     * 
     * @return <code>true</code>
     * @see codebase.iterators.UpdatableIterator#supportsPeek()
     */
    public final boolean supportsPeek() {
        return true;
    }

    /**
     * Checks if an array iterator supports remove.
     * 
     * @return <code>false</code>.
     * @see codebase.iterators.UpdatableIterator#supportsRemove()
     */
    public final boolean supportsRemove() {
        return false;
    }
}
