/*
 * Created on 15/Out/2005
 */
package codebase.iterators;

import java.util.Iterator;

import codebase.cursors.Cursors;

/**
 * An decorator iterator.
 * <p>
 * This class decorates an object implementing the {@link Iterator} interface
 * such that all method calls on the decorator are passed to its decorated
 * object.
 * 
 * @see java.util.Iterator
 */
public abstract class DecoratorIterator
        implements Iterator {
    
    /**
     * The decorated Iterator that is used for passing method calls to.
     */
    protected Iterator decoratedInstance;
    
    /**
     * Creates a new decorator iterator.
     * 
     * @param iterator the iterator to be decorated.
     */
    public DecoratorIterator(final Iterator iterator) {
        decoratedInstance = iterator;
    }
    
    /**
     * Returns the iterator decorated by this decorator iterator, that is used
     * for passing method calls to. Note that is returned iterator and the
     * iterator submicodeed at construction time are usually not the same,
     * because the submicodeed iterator will be wrapped by a call to the method
     * {@link xxl.core.iterators.Cursors#wrap(java.util.Iterator)}.
     * 
     * @return the iterator decorated by this decorator iterator.
     */
    public Iterator getDecorated() {
        return decoratedInstance;
    }
    
    /**
     * Returns <code>true</code> if the iteration has more elements. (In other
     * words, returns <code>true</code> if <code>next</code> would return an
     * element rather than throwing an exception.)
     * <p>
     * This operation is implemented idempotent, i.e., consequent calls to
     * <code>hasNext</code> do not have any effect.
     * </p>
     * 
     * @return <code>true</code> if the iterator has more elements.
     * @throws IllegalStateException if the iterator is already closed when this
     *             method is called.
     */
    public boolean hasNext() throws IllegalStateException {
        return decoratedInstance.hasNext();
    }
    
    /**
     * Returns the next element in the iteration.
     * 
     * @return the next element in the iteration.
     * @throws IllegalStateException if the iterator is already closed when this
     *             method is called.
     */
    public Object next() throws IllegalStateException {
        if (decoratedInstance.hasNext()) {
            return decoratedInstance.next();
        } else {
            throw new IllegalStateException("Decorated instance is empty");
        }
    }
    
    /**
     * Removes from the underlying data structure the last element returned by
     * the iterator (optional operation).
     * <p>
     * Note, that this operation is optional and might not work for all
     * iterators.
     * </p>
     */
    public void remove() {
        decoratedInstance.remove();
    }
}
