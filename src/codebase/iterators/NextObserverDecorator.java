/*
 * Created on 16/Mar/2005
 */
package codebase.iterators;

import java.util.Iterator;

import codebase.cursors.DecoratorCursor;

/**
 * An iterator decorator that add {@link #beforeNext()} e
 * {@link #afterNext(Object)}.
 */
public abstract class NextObserverDecorator
        extends DecoratorIterator {
    
    /**
     * Creates a cursor that observes mehtod calls.
     * 
     * @param iterator the iterator to be decorated
     */
    public NextObserverDecorator(final Iterator iterator) {
        super(iterator);
    }
    
    /**
     * Method called after {@link DecoratorCursor#next()}. Implementing classes
     * can override this mehtod and provide
     * 
     * @param nextObject the next object read from the decoreated input cursor.
     * @return the next object
     * @see com.oblog.datafusion.core.functionCursors.DecoratorCursor#next()
     */
    protected abstract Object afterNext(final Object nextObject);
    
    /**
     * Method called before <code>super.next</code>.
     * 
     * @see com.oblog.datafusion.core.functionCursors.DecoratorCursor#next()
     */
    protected void beforeNext() {
    }
    
    /**
     * Gets the next element of the cursor.
     * 
     * @return the next object retuned after calling the
     *         {@link #afterNext(Object0)} functor
     * @see java.util.Iterator#next()
     * @see com.oblog.datafusion.core.functionCursors.NextObserverDecorator#beforeNext()
     * @see com.oblog.datafusion.core.functionCursors.NextObserverDecorator#afterNext(Object)
     */
    public final Object next() {
        beforeNext();
        final Object nextObject = super.next();
        final Object result = afterNext(nextObject);
        return result;
    }
}
