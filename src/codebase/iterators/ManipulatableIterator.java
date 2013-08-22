/*
 * Created on 22/Out/2005
 */
package codebase.iterators;

/**
 * The interface for iterators that support the manipulation of items.
 * <p>
 * This iterator allows:
 * <ol>
 * <li>Direct access to the current element without consuming it through the
 * method {@link #peek()}.</li>
 * <li>Updating the current element through the method {@link #update(Object)}.</li>
 * <li>Removing the current element from the collection underlying the iterator
 * through the method {@link #remove()}.</li>
 * </ol>
 * <p>
 * Before calling any of the methods above the caller should check that the
 * operation is supported by calling , respectively, {@link #supportsPeek()},
 * {@link #supportsUpdate()} and {@link #supportsRemove()}.
 * 
 * @param <E> the type of all the objects to be treated.
 */
public interface ManipulatableIterator<E>
        extends ResetableIterator<E> {
    
    /**
     * Peeks the current item.
     * <p>
     * The item may only be peeked if the iterator is in a valid state. To check
     * that the item can be peeked, the method {@link #supportsPeek()} should be
     * invoked first.
     * <p>
     * This operation is idempotent, multiple calls to the method allways return
     * the same result.
     * 
     * @return the current item
     * @throws UnsupportedOperationException if the operation is not supported
     *             by a descending class
     * @throws IllegalStateException if the operation is supported but called in
     *             an illegal state state of the object
     */
    E peek();
    
    /**
     * Checks if the item supports peek or not.
     * 
     * @return <code>true</code> if the item can be peeked. <code>false</code>
     *         otherwise.
     */
    boolean supportsPeek();
    
    /**
     * Updates the current item.
     * <p>
     * Updates the element in the in the underlying collection by replacing it
     * with the object specified. This element is the element of the iteration
     * returned by the {@link java.util.Iterator#next()} or {@link #peek()}.
     * 
     * @param replacement the object to replace the current object in the
     *            underlying collection.
     * @throws UnsupportedOperationException if the operation is not supported
     * @throws IllegalStateException if the replacement cannot occur in the
     *             current state of the object
     */
    void update(final E replacement);
    
    /**
     * Check is the iterator supports the update operation.
     * 
     * @return <code>true</code> if the operation is supported.
     *         <code>false</code> otherwise.
     */
    boolean supportsUpdate();
    
    /**
     * @see java.util.Iterator#remove()
     * @throws UnsupportedOperationException if the <i>remove</i> operation is
     *             not supported by this Iterator.
     * @throws IllegalStateException if the <code>next</code> method has not
     *             yet been called, or the <code>remove</code> method has
     *             already been called after the last call to the
     *             <code>next</code> method.
     */
    void remove(); 
    
    /**
     * Checks if remove is supported.
     * 
     * @return Returns <code>true</code> if the items of the underlying
     *         collection can be removed. Returns <code>false</code>
     *         otherwise.
     */
    boolean supportsRemove();
}
