/*
 * Created on 4/Mai/2005
 */
package codebase.iterators;

/**
 * Abstract class for iterators over array-like collections.
 * <p>
 * Iterates over the elements of an array-like collection (A collection that
 * allows direct acess to its elements). The descending classes should implement
 * the {@link #getElement(int)}.
 * <p>
 * Nothing is assumed regarding update operations. A descending class may or may
 * not allow update.
 */
public abstract class AbstractArrayIterator
        implements ResetableItemManipulatorIterator {
    
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
        // Bug corrected: We were throwing an exception the array length was
        // zero. When it is zero is behaves as empty iterator.
        size = length;
    }
    
    /**
     * Checks if the current element is valid.
     * 
     * @return <code>true</code> if the internal element pointer is small that
     *         the array length
     */
    protected final boolean isValid() {
        return (offset < size);
    }
    
    /**
     * Check if there are more elements in the array.
     * 
     * @return <code>true</code> if the the end of the array has not yet been
     *         reached
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
    protected abstract Object getElement(final int pos);
    
    /**
     * Returns the next element in the array.
     * 
     * @return the element at the current offset
     */
    public final Object next() {
        final Object o = getElement(offset);
        offset += 1;
        return o;
    }
    
    /**
     * Void implementation.
     * 
     * @throws UnsupportedOperationException
     */
    public final void remove() {
        throw new UnsupportedOperationException(
            "Can not remove from an array iterator.");
    }
    
    /**
     * Resets the iterator to the first element in the array.
     * 
     * @see codebase.iterators.Resetable#reset()
     */
    public final void reset() {
        offset = 0;
    }
    
    /**
     * Checks if the array iterator suports reset.
     * 
     * @return <code>true</code>
     * @see codebase.iterators.Resetable#supportsReset()
     */
    public final boolean supportsReset() {
        return true;
    }
    
    /**
     * Return the current element.
     * 
     * @see codebase.iterators.ItemManipulatorIterator#peek()
     * @return the element at the current offset
     * @see AbstractArrayIterator#offset
     */
    public final Object peek() {
        if (isValid()) {
            final Object o = getElement(offset);
            return o;
        } else {
            throw new IllegalStateException();
        }
    }
    
    /**
     * Check is an array supports direct access.
     * 
     * @return <code>true</code>
     * @see codebase.iterators.ItemManipulatorIterator#supportsPeek()
     */
    public final boolean supportsPeek() {
        return true;
    }
    
    /**
     * Checks if an array iterator supports remove.
     * 
     * @return <code>false</code>.
     * @see codebase.iterators.ItemManipulatorIterator#supportsRemove()
     */
    public final boolean supportsRemove() {
        return false;
    }
}
