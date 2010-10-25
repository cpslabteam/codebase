/*
 * Created on 4/Mai/2005
 */
package codebase.iterators;

/**
 * An empty iterator.
 * <p>
 * Every call to its {@link #hasNext()} method will return <tt>false</tt>.
 * This class is useful when you have to return an iteration of some kind, but
 * there are no elements the iteration points to.
 * <p>
 * The class contains a static field, called <tt>DEFAULT_INSTANCE</tt>, which
 * is similar to the design pattern, named <i>Singleton </i>.
 * <p>
 * This iterator is usefull for initialization purposes.
 * 
 * @see InvalidIterator
 */
public final class EmptyIterator
        implements ResetableItemManipulatorIterator {
    
    /**
     * This instance can be used for getting a default instance of an empty
     * cursor. It is similar to the <i>Singleton Design Pattern </i>
     */
    public static final EmptyIterator DEFAULT_INSTANCE = new EmptyIterator();
    
    /**
     * Constructs a new empty cursor that contains no elements.
     */
    public EmptyIterator() {
    }
    
    /**
     * Checks if the iterator has more elements.
     * 
     * @return <tt>false</tt>
     */
    public boolean hasNext() {
        return false;
    }
    
    /**
     * Returns no object. This method should not be called.
     * 
     * @return nothing
     * @throws IllegalStateException because the empty iterator does not return
     *             any object.
     */
    public Object next() {
        throw new IllegalStateException();
    }
    
    /**
     * Default implementation for the method remove.
     * 
     * @throws UnsupportedOperationException because this operation is not
     *             supported on an empty iterator.
     */
    public void remove() {
        throw new UnsupportedOperationException();
    }
    
    /**
     * Implemented for conformance with the interface.
     * <p>
     * Do nothing. We are already reset.
     * 
     * @see codebase.iterators.ResetableIterator#reset()
     */
    public void reset() {
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
     * Implemented for conformance with the interface.
     * <p>
     * Nothing can be peeked from an empty iterator.
     * 
     * @return no obejct is returned.
     * @throws UnsupportedOperationException because an empty iterator does not
     *             have any items to peek
     * @see codebase.iterators.ItemManipulatorIterator#peek()
     */
    public Object peek() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
    
    /**
     * Checks if peek is supported.
     * 
     * @return <code>false</code>
     * @see codebase.iterators.ItemManipulatorIterator#supportsPeek()
     */
    public boolean supportsPeek() {
        return false;
    }
    
    /**
     * Checks if remove is supported.
     * 
     * @return <code>false</code>
     * @see codebase.iterators.ItemManipulatorIterator#supportsRemove()
     */
    public boolean supportsRemove() {
        return false;
    }
    
    /**
     * Checks if updates is supported.
     * 
     * @return <code>false</code>
     * @see codebase.iterators.ItemManipulatorIterator#supportsUpdate()
     */
    public boolean supportsUpdate() {
        return false;
    }
    
    /**
     * Implemented for conformance with the interface.
     * 
     * @param replacement the replacement parameter
     * @throws UnsupportedOperationException because an empty iterator does not
     *             have any items to update
     * @see codebase.iterators.ItemManipulatorIterator#update(java.lang.Object)
     */
    public void update(final Object replacement)
            throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
        
    }
}
