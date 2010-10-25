/*
 * Created on 5/Mar/2005
 */
package codebase.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The skeleton implementation for an iterator proxy.
 * <p>
 * Implements the proxy design pattern for Iterators. When a call is made to any
 * method of the proxy iterator, the content real subject is verified. If it
 * does not exist, the proxy asks for the real subject using the method
 * {@link #request()}. The method is called only once. All methods check that
 * the real subject exists.
 * <p>
 * Descendent classes only need to implement the method {@link #request()}.
 */
public abstract class AbstractIteratorProxy
        implements Iterator, IteratorProxy {
    
    /**
     * The cached reference to real subject.
     */
    private Iterator realSubject;
    
    /**
     * Requests a the creation of the real subject Iterator.
     * <p>
     * Implementing classes must return a valid iterator, returning
     * <code>null</code> will trigger an <code>IllegalStatException</code>.
     * 
     * @return the real subject iterator
     */
    public abstract Iterator request();
    
    /**
     * @return <code>true</code> if the real subject has a next element
     * @see java.util.Iterator#hasNext()
     * @throws IllegalStateException if the real subject throws it
     */
    public final boolean hasNext() throws IllegalStateException {
        if (realSubject == null) {
            realSubject = request();
        }
        return realSubject.hasNext();
    }
    
    /**
     * @return the next element returned by the real subject
     * @see java.util.Iterator#next()
     * @throws IllegalStateException if the real subject throws it
     * @throws NoSuchElementException if the real subject throws it
     */
    public final Object next() throws IllegalStateException,
            NoSuchElementException {
        if (realSubject == null) {
            realSubject = request();
        }
        return realSubject.next();
    }
    
    /**
     * @see java.util.Iterator#remove()
     * @throws IllegalStateException if the real subject throws it
     * @throws UnsupportedOperationException if the real subject throws it
     */
    public final void remove() throws IllegalStateException,
            UnsupportedOperationException {
        if (realSubject == null) {
            realSubject = request();
        }
        realSubject.remove();
    }
}
