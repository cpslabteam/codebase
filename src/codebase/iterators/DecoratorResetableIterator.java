/*
 * Created on 20/Out/2005
 */
package codebase.iterators;

/**
 * A decorator for resetable iterators.
 */
public class DecoratorResetableIterator
        extends DecoratorIterator
        implements ResetableIterator {
    
    /**
     * The reference to the decorated instance.
     */
    private final DecoratorResetableIterator decoratedInstance;
    
    /**
     * Builds a new resetable decorator iterator.
     * 
     * @param resetableIterator the reference to the decorated instance
     */
    public DecoratorResetableIterator(
            final DecoratorResetableIterator resetableIterator) {
        super(resetableIterator);
        decoratedInstance = resetableIterator;
    }
    
    /**
     * @see codebase.iterators.ResetableIterator#reset()
     */
    public void reset() {
        decoratedInstance.reset();
    }
    
    /**
     * Checks if the decorated instance supports reset.
     * 
     * @return <code>true</code> if the decorate disntance returns
     *         <code>true</code>; <code>false</code> otherwise.
     * @see codebase.iterators.ResetableIterator#supportsReset()
     */
    public boolean supportsReset() {
        final boolean result = decoratedInstance.supportsReset();
        return result;
    }
    
}
