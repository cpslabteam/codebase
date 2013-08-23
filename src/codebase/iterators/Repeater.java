/*
 * Created on 3/Set/2005
 */
package codebase.iterators;

import java.util.NoSuchElementException;

/**
 * Iterator iterates over the same element a given number of times.
 * <p>
 * A limit in the number of times the element is repeated, <i>n</i>, is specified in the
 * constructor. The given element will be delivered by calls to the {@link #next()}
 * method. If a limit is not specified the same repeatable will infinitely be returned to
 * the caller.
 * 
 * @param <E> the type of all the objects to be treated.
 */
public final class Repeater<E>
        implements ManipulatableIterator<E> {

    /**
     * The repeatable repeatedly returned to the caller.
     */
    private final E repeatable;

    /**
     * The number of times the repeatable is still to be repeated.
     */
    private int left;

    /**
     * The initially specified limit.
     */
    private int initial;

    /**
     * A flag to signal if the repeatable should be repeated infinitely often.
     */
    private boolean unlimited;

    /**
     * Creates a new repeater iteratorthat reiterates the input element only for a finite
     * number of times.
     * 
     * @param object the element that should repeatedly be returned to the caller.
     * @param n the number of times the element should be repeated.
     */
    public Repeater(final E object, final int n) {
        repeatable = object;
        initial = n;
        left = initial;
        unlimited = false;
    }

    /**
     * Creates a new repeater. This repeater reiterates the input element infinitely
     * often.
     * 
     * @param object the element that should repeatedly be returned to the caller.
     */
    public Repeater(final E object) {
        this.repeatable = object;
        this.unlimited = true;
    }

    /**
     * Checks if the current element is valid.
     * 
     * @return <code>true</code> if the repeater is unlimited or if the the last element
     *         was not yet returned
     */
    private boolean hasEnded() {
        if (unlimited) {
            return false;
        } else {
            return left <= 0;
        }
    }

    /**
     * Checks if the iteration has more elements.
     * 
     * @return <code>true</code> if the repeater has more elements. Returns
     *         <code>false</code> otherwise.
     */
    public boolean hasNext() {
        final boolean hasNotEnded = !hasEnded();
        return hasNotEnded;
    }

    /**
     * Returns another reference to the repeatable object specified in the constructor.
     * 
     * @return the next element in the iteration.
     */
    public E next() {
        if (!unlimited) {
            if (left == 0) {
                throw new NoSuchElementException("All elements exhausted from the repeater");
            }

            left -= 1;
        }

        return repeatable;
    }

    /**
     * Consumes one element from the repeat sequence.
     * <p>
     * If the sequence is unlimited does nothing, else decreases the number of items left.
     * 
     * @throws IllegalStateException if the sequence as ended
     */
    public void remove() {
        if (!hasEnded()) {
            if (!unlimited) {
                left -= 1;
            }
        } else {
            throw new IllegalStateException();
        }
    }

    /**
     * @see codebase.iterators.ResetableIterator#reset()
     */
    public void reset() {
        left = initial;
    }

    /**
     * A repeater is resetable.
     * 
     * @return <code>true</code>
     * @see codebase.iterators.ResetableIterator#supportsReset()
     */
    public boolean supportsReset() {
        return true;
    }

    /**
     * Returns a reference to the item being repeated.
     * 
     * @return a reference to item specified in the constructor.
     * @throws IllegalStateException if the last element was already returned
     * @see codebase.iterators.ManipulatableIterator#peek()
     */
    public E peek() {
        if (!hasEnded()) {
            return repeatable;
        } else {
            throw new IllegalStateException();
        }
    }

    /**
     * Checks if we can access the current item.
     * 
     * @return <code>true</code>
     * @see codebase.iterators.ManipulatableIterator#supportsPeek()
     */
    public boolean supportsPeek() {
        return true;
    }

    /**
     * Checks if the repeatable element allows removing an element.
     * 
     * @return <code>true</code>.
     * @see codebase.iterators.ManipulatableIterator#supportsRemove()
     */
    public boolean supportsRemove() {
        return true;
    }

    /**
     * Checks if the repeater supports update.
     * 
     * @return <code>false</code>
     * @see codebase.iterators.ManipulatableIterator#supportsUpdate()
     */
    public boolean supportsUpdate() {
        return false;
    }

    /**
     * Implemented for conformance with the interface.
     * <p>
     * A repeater is read-only. If this was note the case, the implementation of the
     * repeater would be much more complex. It would be necessary to where the repeat
     * sequence was updated to handle {@link #reset()} adequately.
     * 
     * @param replacement the object to replace the current one. Ignored.
     * @throws UnsupportedOperationException when called
     * @see codebase.iterators.ManipulatableIterator#update(java.lang.Object)
     */
    public void update(final Object replacement) {
        throw new UnsupportedOperationException();
    }
}
