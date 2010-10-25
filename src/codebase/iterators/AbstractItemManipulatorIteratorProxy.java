/*
 * Created on 5/Mar/2005
 */
package codebase.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 */
public abstract class AbstractItemManipulatorIteratorProxy
        implements Iterator, ItemManipulatorIteratorProxy {
    
    /**
     * The cached reference to real subject.
     */
    private ItemManipulatorIterator realSubject;
    
    /**
     * Requests a the creation of the real subject ItemManipulatorIterator.
     * <p>
     * Implementing classes must return a valid iterator, returning
     * <code>null</code> will trigger an <code>IllegalStatException</code>.
     * 
     * @return the real subject iterator
     */
    public abstract ItemManipulatorIterator request();
    
    /**
     * @see java.util.Iterator#hasNext()
     * @throws IllegalStateException
     */
    public final boolean hasNext() throws IllegalStateException {
        if (realSubject == null) {
            realSubject = request();
        }
        return realSubject.hasNext();
    }
    
    /**
     * @see java.util.Iterator#next()
     * @throws IllegalStateException
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
     * @throws IllegalStateException
     */
    public final void remove() throws IllegalStateException,
            UnsupportedOperationException {
        if (realSubject == null) {
            realSubject = request();
        }
        realSubject.remove();
    }
    
    /**
     * @see codebase.iterators.ItemManipulatorIterator#peek()
     */
    public Object peek() throws UnsupportedOperationException,
            IllegalStateException {
        if (realSubject == null) {
            realSubject = request();
        }
        return realSubject.peek();
    }
    
    /**
     * @see codebase.iterators.ItemManipulatorIterator#supportsPeek()
     */
    public boolean supportsPeek() {
        if (realSubject == null) {
            realSubject = request();
        }
        return realSubject.supportsPeek();
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see codebase.interfaces.Resetable#reset()
     */
    public void reset() throws UnsupportedOperationException,
            IllegalStateException {
        if (realSubject == null) {
            realSubject = request();
        }
        realSubject.reset();
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see codebase.interfaces.Resetable#supportsReset()
     */
    public boolean supportsReset() {
        if (realSubject == null) {
            realSubject = request();
        }
        return realSubject.supportsReset();
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see codebase.iterators.ItemManipulatorIterator#supportsRemove()
     */
    public boolean supportsRemove() {
        if (realSubject == null) {
            realSubject = request();
        }
        return realSubject.supportsRemove();
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see codebase.iterators.ItemManipulatorIterator#supportsUpdate()
     */
    public boolean supportsUpdate() {
        if (realSubject == null) {
            realSubject = request();
        }
        return realSubject.supportsUpdate();
    }
    
    /**
     * @see codebase.iterators.ItemManipulatorIterator#update(java.lang.Object)
     */
    public void update(final Object replacement)
            throws UnsupportedOperationException, IllegalStateException {
        if (realSubject == null) {
            realSubject = request();
        }
        realSubject.update(replacement);
    }
    
}
