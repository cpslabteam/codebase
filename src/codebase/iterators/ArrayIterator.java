/*
 * Created on 4/Mai/2005
 */
package codebase.iterators;

/**
 * An iterator for an array of objects.
 * <p>
 * Iterates over the elements of an array of objects.
 * <p>
 * <b>Note: </b> In case we have an array of primitive types have to first
 * ocnvert it into the corresponding array of Objects. This array of objects can
 * then be used to feed the iterator.
 */
public final class ArrayIterator
        extends AbstractArrayIterator
        implements ResetableItemManipulatorIterator {
    /**
     * The reference to the object array.
     */
    private final Object[] array;
    
    /**
     * Constructs a new iterator for a given array.
     * 
     * @param sourceArray the source array
     * @throws IllegalArgumentException if the array is not defined
     */
    public ArrayIterator(final Object[] sourceArray) {
        super(checkNotNull(sourceArray).length);
        
        array = sourceArray;
    }
    
    /**
     * Checks if the array is <code>null</code>.
     * 
     * @param sourceArray the array to be checked
     * @return the source array
     * @throws IllegalArgumentException if the array is in fact
     *             <code>null</code>
     */
    private static Object[] checkNotNull(final Object[] sourceArray) {
        if (sourceArray == null) {
            throw new IllegalArgumentException(
                "The source array cannot be null");
        } else {
            return sourceArray;
        }
    }
    
    /**
     * Returns the array element.
     * 
     * @param pos the array position
     * @return the element of the array specified by position
     * @see codebase.iterators.AbstractArrayIterator#getElement(int)
     */
    protected Object getElement(final int pos) {
        return array[pos];
    }
    
    /**
     * Checks if update is supported.
     * 
     * @return <code>true</code>
     * @see codebase.iterators.ItemManipulatorIterator#supportsUpdate()
     */
    public boolean supportsUpdate() {
        return true;
    }
    
    /**
     * Updates the underlying array with the given object at the current
     * position.
     * 
     * @param replacement the object to be replaced
     * @see codebase.iterators.ItemManipulatorIterator#update(java.lang.Object)
     * @throws IllegalStateException if the current offset for update is not
     *             valid
     */
    public void update(final Object replacement) throws IllegalStateException {
        if (isValid()) {
            array[offset] = replacement;
        } else {
            throw new IllegalStateException();
        }
    }
}
