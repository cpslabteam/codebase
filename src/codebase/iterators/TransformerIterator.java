/*
 * Created on 13/Jul/2004
 */
package codebase.iterators;

import java.util.Iterator;

/**
 * A cursor that applies a function to each element of the input.
 * <p>
 * This cursor is particularly useful for testing purposes
 */
public abstract class TransformerIterator
        extends NextObserverDecorator {
    
    /**
     * Constructs.
     * 
     * @param iterator the cursor to be decorated
     */
    public TransformerIterator(final Iterator iterator) {
        super(iterator);
    }
    
    /**
     * Post-processes the object obtained by {@link #next()} with
     * {@link #transform(Object)}.
     * 
     * @param nextObject the object obtained from {@link #next()}
     * @return the transformed object.
     * @see com.oblog.datafusion.core.functionCursors.NextObserverDecorator#afterNext(java.lang.Object)
     */
    protected final Object afterNext(final Object nextObject) {
        final Object result = transform(nextObject);
        return result;
    }
    
    /**
     * Transforms an input object.
     * <p>
     * This is the method that must be extended to transform the result returned
     * by the decorated cursor.
     * 
     * @param object the next object returned by the decorated cursor that needs
     *            to be transformed
     * @return the transformed object to be returned by calling
     *         <code>next()</code> on this cursor
     */
    protected abstract Object transform(final Object object);
}
