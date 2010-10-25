/*
 * Created on 11/Jun/2005
 */
package codebase.iterators;


/**
 * An iterator that throws {@link IllegalStateException} when an operation is called.
 * <p>
 * An invalid iterator is used for initializing iterator in situations where
 * <code>null</code> cannot be used.
 */
public final class InvalidIterator
        implements ResetableItemManipulatorIterator {
    
    /**
     * A constant with the invalid cursor.
     */
    public static final InvalidIterator DEFAULT_INSTANCE = new InvalidIterator();
    
    /**
     * Throws an exception signalling that that the current strategy is invalid.
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
    public Object next() throws IllegalStateException {
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
     * @see codebase.iterators.ItemManipulatorIterator#peek()
     * @throws IllegalStateException when called
     */
    public Object peek() throws IllegalStateException {
        reportInvalidIterator();
        return null;
    }
    
    /**
     * @return nothing
     * @see codebase.iterators.ItemManipulatorIterator#supportsPeek()
     * @throws IllegalStateException when called
     */
    public boolean supportsPeek() {
        reportInvalidIterator();
        return false;
    }
    
    /**
     * @return nothing
     * @see codebase.iterators.ItemManipulatorIterator#supportsRemove()
     * @throws IllegalStateException when called
     */
    public boolean supportsRemove() {
        reportInvalidIterator();
        return false;
    }
    
    /**
     * @return nothing
     * @see codebase.iterators.ItemManipulatorIterator#supportsUpdate()
     * @throws IllegalStateException when called
     */
    public boolean supportsUpdate() {
        reportInvalidIterator();
        return false;
    }
    
    /**
     * @param replacement ignored.
     * @see codebase.iterators.ItemManipulatorIterator#update(java.lang.Object)
     * @throws IllegalStateException when called
     */
    public void update(final Object replacement) throws IllegalStateException {
        reportInvalidIterator();
    }
    
    /**
     * @see codebase.iterators.Resetable#reset()
     * @throws IllegalStateException when called
     */
    public void reset() throws IllegalStateException {
        reportInvalidIterator();
    }
    
    /**
     * @return nothing
     * @see codebase.iterators.Resetable#supportsReset()
     * @throws IllegalStateException when called
     */
    public boolean supportsReset() {
        reportInvalidIterator();
        return false;
    }   
}
