/*
 * Created on 20/Out/2005
 */
package codebase.iterators;

/**
 * The interface of a resetable items.
 * <p>
 * This interface specifies the methods for reseting an item and for checking
 * that the item is resetable.
 */
public interface Resetable {

    /**
     * Resets the item.
     * <p>
     * The item may only be reset in the current state. To check that the item
     * can be reset, the method {@link #supportsReset()} should be invoked
     * first.
     *
     * @throws UnsupportedOperationException if the operation is not supported
     *             by a descending class
     * @throws IllegalStateException if the operation is supported but called
     *             in an illegal state state of the object
     */
    void reset() throws UnsupportedOperationException, IllegalStateException;

    /**
     * Checks if the item supports reset or not.
     *
     * @return <code>true</code> if the item can be reset.
     *         <code>false</code> otherwise.
     */
    boolean supportsReset();
}
