/*
 * Created on 4/Mai/2005
 */
package codebase.iterators;

import codebase.cursors.IteratorCursor;

/**
 * An iterator with a single element.
 * <p>
 * This iterator wraps one element as an iterator. The method
 * {@link IteratorCursor#hasNext()} can only be called once.
 * 
 * @see java.util.Iterator
 */
public final class SingletonIterator
        implements ResetableItemManipulatorIterator {
    
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
    private Object element;
    
    /**
     * Constructs a new empty cursor that contains no elements.
     * 
     * @param elem the element to be used by the iterator
     */
    public SingletonIterator(final Object elem) {
        element = elem;
    }
    
    /**
     * Checks if calling {@link SingletonIterator#next()} will return an
     * element.
     * <p>
     * 
     * @return Returns <code>true</code> the element was not returned and not
     *         removed <code>false</code> otherwise.
     */
    public boolean hasNext() {
        return !wasReturned && !wasRemoved;
    }
    
    /**
     * Gets the underlying element.
     * 
     * @return the element specified in the constructor
     * @throws IllegalStateException if the object was alredy returned or was
     *             removed
     */
    public Object next() {
        if (wasReturned || wasRemoved) {
            throw new IllegalStateException();
        }
        
        wasReturned = true;
        return element;
    }
    
    /**
     * Removes the element.
     * 
     * @throws IllegalStateException if the element was already removed or
     *             returned
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
     * This operation does not set the internal
     * {@link SingletonIterator#wasReturned} flag.
     * 
     * @return a reference to item specified in the constructor.
     * @throws IllegalStateException if the last element was already returned or
     *             removed
     * @see codebase.iterators.ItemManipulatorIterator#peek()
     */
    public Object peek() throws IllegalStateException {
        if (wasReturned || wasRemoved) {
            throw new IllegalStateException();
        }
        
        return element;
    }
    
    /**
     * Checks if we can access the current item.
     * 
     * @return <code>true</code>
     * @see codebase.iterators.ItemManipulatorIterator#supportsPeek()
     */
    public boolean supportsPeek() {
        return true;
    }
    
    /**
     * Checks if the repeatable element allows removing an element.
     * 
     * @return <code>true</code>.
     * @see codebase.iterators.ItemManipulatorIterator#supportsRemove()
     */
    public boolean supportsRemove() {
        return true;
    }
    
    /**
     * Checks if the repeater supports update.
     * 
     * @return <code>true</code>
     * @see codebase.iterators.ItemManipulatorIterator#supportsUpdate()
     */
    public boolean supportsUpdate() {
        return true;
    }
    
    /**
     * Udpates the current element.
     * 
     * @param replacement the element to replace the current one
     * @throws IllegalStateException if the last element was already returned or
     *             removed
     * @see codebase.iterators.ItemManipulatorIterator#update(java.lang.Object)
     */
    public void update(final Object replacement) {
        if (wasReturned || wasRemoved) {
            throw new IllegalStateException();
        }
        
        element = replacement;
    }
}
