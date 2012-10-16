/*
 * Created on 15/Out/2005
 */
package codebase.util.iterators;

import java.util.Iterator;

/**
 * An decorator iterator.
 * <p>
 * This class decorates an object implementing the {@link Iterator} interface such that
 * all method calls on the decorator are passed to its decorated object.
 * 
 * @see java.util.Iterator
 */
public abstract class DecoratorIterator<E>
        implements Iterator<E> {

    /**
     * The decorated Iterator that is used for passing method calls to.
     */
    protected Iterator<E> decoratedInstance;

    /**
     * Creates a new decorator iterator.
     * 
     * @param iterator the iterator to be decorated.
     */
    public DecoratorIterator(final Iterator<E> iterator) {
        decoratedInstance = iterator;
    }

    /**
     * Returns the iterator decorated by this decorator iterator, that is used for passing
     * method calls to.
     * 
     * @return the iterator decorated by this decorator iterator.
     */
    public Iterator<E> getDecorated() {
        return decoratedInstance;
    }

    /**
     * Returns <code>true</code> if the iteration has more elements. (In other words,
     * returns <code>true</code> if <code>next</code> would return an element rather than
     * throwing an exception.)
     * <p>
     * This operation is implemented idempotent, i.e., consequent calls to
     * <code>hasNext</code> do not have any effect.
     * </p>
     * 
     * @return <code>true</code> if the iterator has more elements.
     * @throws IllegalStateException if the iterator is already closed when this method is
     *             called.
     */
    public boolean hasNext() throws IllegalStateException {
        return decoratedInstance.hasNext();
    }

    /**
     * Returns the next element in the iteration.
     * 
     * @return the next element in the iteration.
     * @throws IllegalStateException if the iterator is already closed when this method is
     *             called.
     */
    public E next() throws IllegalStateException {
        if (decoratedInstance.hasNext()) {
            return decoratedInstance.next();
        } else {
            throw new IllegalStateException("Decorated instance is empty");
        }
    }

    /**
     * Removes from the underlying data structure the last element returned by the
     * iterator (optional operation).
     * <p>
     * Note, that this operation is optional and might not work for all iterators.
     * </p>
     */
    public void remove() {
        decoratedInstance.remove();
    }
}
