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
 * convert it into the corresponding array of Objects. This array of objects can
 * then be used to feed the iterator.
 */
public final class ArrayIterator<E>
        extends AbstractArrayIterator<E>
        implements ManipulatableIterator<E> {
    /**
     * The reference to the object array.
     */
    private final E[] array;
    
    /**
     * Constructs a new iterator for a given array.
     * 
     * @param sourceArray the source array
     * @throws IllegalArgumentException if the array is not defined
     */
    public ArrayIterator(final E[] sourceArray) {
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
    protected E getElement(final int pos) {
        return array[pos];
    }
    
    /**
     * Checks if update is supported.
     * 
     * @return <code>true</code>
     * @see codebase.iterators.ManipulatableIterator#supportsUpdate()
     */
    public boolean supportsUpdate() {
        return true;
    }
    
    /**
     * Updates the underlying array with the given object at the current
     * position.
     * 
     * @param replacement the object to be replaced
     * @see codebase.iterators.ManipulatableIterator#update(java.lang.Object)
     * @throws IllegalStateException if the current offset for update is not
     *             valid
     */
    public void update(final E replacement) throws IllegalStateException {
        if (isValid()) {
            array[offset] = replacement;
        } else {
            throw new IllegalStateException();
        }
    }
}
