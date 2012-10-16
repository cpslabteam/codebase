/*
 * Created on 16/Mar/2005
 */
package codebase.util.iterators;

import java.util.Iterator;

/**
 * An iterator decorator that add {@link #beforeNext()} e {@link #afterNext(Object)} to
 * intercept the calls to {@link #next()}.
 */
public abstract class NextObserverDecorator<E> extends
        DecoratorIterator<E> {

    /**
     * Creates a cursor that observes method calls.
     * 
     * @param iterator the iterator to be decorated
     */
    public NextObserverDecorator(final Iterator<E> iterator) {
        super(iterator);
    }

    /**
     * Method called after {@link DecoratorCursor#next()}. Implementing classes can
     * override this method and provide
     * 
     * @param nextObject the next object read from the decorated input cursor.
     * @return the next object
     * @see com.oblog.datafusion.core.functionCursors.DecoratorCursor#next()
     */
    protected abstract E afterNext(final Object nextObject);

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
     * @return the next object returned after calling the {@link #afterNext(Object)}
     *         functor
     * @see java.util.Iterator#next()
     */
    public final E next() {
        beforeNext();
        final E nextObject = super.next();
        final E result = afterNext(nextObject);
        return result;
    }
}
