package codebase.iterators;

/**
 * An iterator with a single element.
 * <p>
 * This iterator wraps one element as an iterator. The method
 * {@link SingletonIterator#hasNext()} can only be called once.
 * 
 * @param <E> the type of all the objects to be treated.
 * @see java.util.Iterator
 * @since 4/Mai/2005
 */
public final class SingletonIterator<E>
        implements UpdatableIterator<E> {

    /**
     * Tracks if the element has been returned by a call to
     * {@link SingletonIterator#next()}.
     */
    private boolean wasReturned;

    /**
     * Tracks if the element was removed.
     */
    private boolean wasRemoved;

    /**
     * The element of the iterator.
     */
    private E element;

    /**
     * Constructs a new empty cursor that contains no elements.
     * 
     * @param elem the element to be used by the iterator
     */
    public SingletonIterator(final E elem) {
        element = elem;
    }

    /**
     * Checks if calling {@link SingletonIterator#next()} will return an element.
     * <p>
     * 
     * @return Returns <code>true</code> the element was not returned and not removed
     *         <code>false</code> otherwise.
     */
    public boolean hasNext() {
        return !wasReturned && !wasRemoved;
    }

    /**
     * Gets the underlying element.
     * 
     * @return the element specified in the constructor
     * @throws IllegalStateException if the object was alredy returned or was removed
     */
    public E next() {
        if (wasReturned || wasRemoved) {
            throw new IllegalStateException();
        }

        wasReturned = true;
        return element;
    }

    /**
     * Removes the element.
     * 
     * @throws IllegalStateException if the element was already removed or returned
     */
    public void remove() {
        if (wasReturned || wasRemoved) {
            throw new IllegalStateException();
        }
        wasRemoved = true;
    }

    /**
     * Resets the iterator
     * <p>
     * If the element was removed it will *not* be made available again.
     * 
     * @see codebase.iterators.ResetableIterator#reset()
     */
    public void reset() {
        wasReturned = false;
    }

    /**
     * Checks if reset is supported.
     * 
     * @return <code>true</code>
     * @see codebase.iterators.ResetableIterator#supportsReset()
     */
    public boolean supportsReset() {
        return true;
    }

    /**
     * Returns the element.
     * <p>
     * This operation does not set the internal {@link SingletonIterator#wasReturned}
     * flag.
     * 
     * @return a reference to item specified in the constructor.
     * @throws IllegalStateException if the last element was already returned or removed
     * @see codebase.iterators.UpdatableIterator#peek()
     */
    public E peek() {
        if (wasReturned || wasRemoved) {
            throw new IllegalStateException();
        }

        return element;
    }

    /**
     * Checks if we can access the current item.
     * 
     * @return <code>true</code>
     * @see codebase.iterators.UpdatableIterator#supportsPeek()
     */
    public boolean supportsPeek() {
        return true;
    }

    /**
     * Checks if the repeatable element allows removing an element.
     * 
     * @return <code>true</code>.
     * @see codebase.iterators.UpdatableIterator#supportsRemove()
     */
    public boolean supportsRemove() {
        return true;
    }

    /**
     * Checks if the repeater supports update.
     * 
     * @return <code>true</code>
     * @see codebase.iterators.UpdatableIterator#supportsUpdate()
     */
    public boolean supportsUpdate() {
        return true;
    }

    /**
     * Updates the current element.
     * 
     * @param replacement the element to replace the current one
     * @throws IllegalStateException if the last element was already returned or removed
     * @see codebase.iterators.UpdatableIterator#update(java.lang.Object)
     */
    public void update(final E replacement) {
        if (wasReturned || wasRemoved) {
            throw new IllegalStateException();
        }

        element = replacement;
    }
}
