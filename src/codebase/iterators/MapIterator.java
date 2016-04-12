package codebase.iterators;

import java.util.Iterator;

/**
 * An iterator decorator that applies a function to each element of a decorated iterator
 * intercept the calls to {@link #next()}.
 * 
 * @param <A> the type of the objects of the input iterator.
 * @param <B> the result type of applying the mapping function.
 */
public abstract class MapIterator<A, B>
        implements Iterator<B> {

    /**
     * The interface of a function to map each object on an iterator.
     * 
     * @param <A> the input type
     * @param <B> the output type
     */
    public interface MapFunction<A, B> {

        /**
         * Maps each object.
         * 
         * @param input an object taken from the input iterator
         * @return an object, which can be <code>null</code>
         */
        B apply(A input);
    }

    /**
     * The decorated Iterator that is used for passing method calls to.
     */
    protected final Iterator<A> decoratedInstance;

    /**
     * The function instance.
     */
    private final MapFunction<A, B> mapFunction;

    /**
     * Creates a cursor that observes method calls.
     * 
     * @param iterator the iterator to be decorated
     * @param f the mapping function to convert the input objects
     */
    public MapIterator(final Iterator<A> iterator, MapFunction<A, B> f) {
        decoratedInstance = iterator;
        mapFunction = f;
    }

    /**
     * Returns the iterator decorated by this decorator iterator, that is used for passing
     * method calls to.
     * 
     * @return the iterator decorated by this decorator iterator.
     */
    public Iterator<A> getDecorated() {
        return decoratedInstance;
    }

    /**
     * Returns <code>true</code> if the iteration has more elements. (In other words,
     * returns <code>true</code> if <code>next</code> would return an element rather than
     * throwing an exception.)
     * <p>
     * This operation is implemented idempotent, i.e., consequent calls to
     * <code>hasNext</code> do not have any effect.
     * </p>
     * 
     * @return <code>true</code> if the iterator has more elements.
     * @throws IllegalStateException if the iterator is already closed when this method is
     *             called.
     */
    public boolean hasNext() {
        return decoratedInstance.hasNext();
    }

    /**
     * Returns the next element in the iteration.
     * 
     * @return the next element in the iteration.
     * @throws IllegalStateException if the iterator is already closed when this method is
     *             called.
     */
    public B next() {
        if (decoratedInstance.hasNext()) {
            return mapFunction.apply(decoratedInstance.next());
        } else {
            throw new IllegalStateException("Decorated instance is empty");
        }
    }

    /**
     * Removes from the underlying data structure the last element returned by the
     * iterator (optional operation).
     * <p>
     * Note, that this operation is optional and might not work for all iterators.
     * </p>
     */
    public void remove() {
        decoratedInstance.remove();
    }

}
