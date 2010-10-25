/*
 * Created on 5/Mar/2005
 */
package codebase.iterators;

import java.util.Iterator;

/**
 * The interface of an iterator proxy.
 * <p>
 * An iterator proxy implements the interface of iterator but allows deferred
 * creation of the object. A new object is created by the method
 * {@link #request()}.
 *
 * @see codebase.iterators.AbstractIteratorProxy
 */
public interface IteratorProxy
        extends Iterator {

    /**
     * Requests a the creation of the real subject Iterator.
     * <p>
     * Implementing classes must return a valid iterator, returning
     * <code>null</code> will trigger an <code>IllegalStatException</code>.
     * <p>
     * This mehtod is called automatically when any other method of the
     * iterator is needed.
     *
     * @return the real subject iterator
     */
    Iterator request();
}
