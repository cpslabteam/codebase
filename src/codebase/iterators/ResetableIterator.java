package codebase.iterators;

import java.util.Iterator;

/**
 * The interface of an iterator that can be reset.
 * <p>
 * This interface specifies the method {@link #reset()} that brings the iterator to the
 * first element.
 * 
 * @param <E> the type of all the objects to be treated.
 * @since 20/Out/2005
 */
public interface ResetableIterator<E>
        extends Iterator<E> {

    /**
     * Resets the item.
     * <p>
     * The item may only be reset in the current state. To check that the item can be
     * reset, the method {@link #supportsReset()} should be invoked first.
     * 
     * @throws UnsupportedOperationException if the operation is not supported by a
     *             descending class
     * @throws IllegalStateException if the operation is supported but called in an
     *             illegal state state of the object
     */
    void reset();

    /**
     * Checks if the item supports reset or not.
     * 
     * @return <code>true</code> if the item can be reset. <code>false</code> otherwise.
     */
    boolean supportsReset();
}
