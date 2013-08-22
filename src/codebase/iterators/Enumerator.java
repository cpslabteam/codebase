/*
 * Created on 11/Mai/2005
 */
package codebase.iterators;

/**
 * An integer enumerator.
 * <p>
 * Returns an ascending or descending sequence of integer objects within a given range.
 * <p>
 * There are three different ways to generate integer objects with an enumerator:
 * <ul>
 * <li>Specifying a range, i.e., the start-position and end-position are user defined.</li>
 * <li>Specifying only the end-position; In this way <i>start</i>=<i>0</i>.</li>
 * <li>Specifying no range; In this way <i>start</i>=<tt>0</tt>,<i>end</i>=
 * {@link java.lang.Integer#MAX_VALUE}.</li>
 * </ul>
 * In the first case an ascending or a descending sequence can be generated depending on
 * the given start- and end-position.
 * <p>
 * <b>Note: </b> that the start-position of the integer sequence will be returned by the
 * enumerator, but not the end-position. So only the integer elements of the interval
 * [<i>start</i>,<i>end</i>) will be returned by the enumerator.
 * 
 * @see java.util.Iterator
 */
public final class Enumerator
        implements ManipulatableIterator<Integer> {

    /**
     * The start of the returned integer sequence (inclusive).
     */
    protected int from;

    /**
     * The end of the returned integer sequence (exclusive).
     */
    protected int to;

    /**
     * The int value returned by the next call to <tt>next</tt> or <tt>peek</tt>.
     */
    protected int nextInt;

    /**
     * If <tt>true</tt> the sequence is ascending, else the sequence is descending.
     */
    protected boolean up;

    /**
     * Creates a new enumerator instance with a specified range, i.e., the start- and an
     * end-position must be defined.
     * 
     * @param start start of the returned integer sequence (inclusive).
     * @param limit end of the returned integer sequence (exclusive).
     */
    public Enumerator(final int start, final int limit) {
        this.from = start;
        this.to = limit;
        this.up = start <= limit;
        this.nextInt = start;
    }

    /**
     * Creates a new enumerator instance with a user defined end position.
     * <p>
     * The returned integer sequence starts with <tt>0</tt> and ends with
     * <tt>number-1</tt>.
     * 
     * @param number the end of the returned integer sequence (exclusive).
     */
    public Enumerator(final int number) {
        this(0, number);
    }

    /**
     * Creates an enumerator instance.
     * <p>
     * The returned integer sequence starts with <tt>0</tt> and ends with
     * {@link java.lang.Integer#MAX_VALUE} <tt>-1</tt>.
     */
    public Enumerator() {
        this(0, Integer.MAX_VALUE);
    }

    /**
     * Checks if the iteration has more elements.
     * <p>
     * In other words, returns <code>true</code> if {@link #next()} if the enumeration has
     * not been exhausted
     * 
     * @return <code>true</code> if the enumerator has more elements. Returns
     *         <code>false</code> otherwise.
     */
    public boolean hasNext() {
        final boolean hasNext;
        if (up) {
            hasNext = nextInt < to;
        } else {
            hasNext = nextInt > to;

        }
        return hasNext;
    }

    /**
     * Returns the next element in the iteration.
     * <p>
     * When {@link #next()} is called for the first time the value returned is the start
     * value in the constructor. The next value is computed by adding or subtracting to
     * the last returned element, respectively if, the limit is greater or lower that the
     * start.
     * 
     * @return an {@link Integer} object with the next element in the enumeration.
     */
    public Integer next() {
        final int result = nextInt;
        if (up) {
            nextInt += 1;
        } else {
            nextInt -= 1;

        }
        return Integer.valueOf(result);
    }

    /**
     * Implemented for conformance with the interface.
     * 
     * @throws UnsupportedOperationException because it is not possible to remove elements
     *             from an enumerator iterator.
     */
    public void remove() {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the iterator to the first element.
     * 
     * @see codebase.iterators.ResetableIterator#reset()
     */
    public void reset() {
        nextInt = from;
    }

    /**
     * An enumerator supports reset.
     * 
     * @return <code>true</code>
     * @see codebase.iterators.ResetableIterator#supportsReset()
     */
    public boolean supportsReset() {
        return true;
    }

    /**
     * Checks if the current element is valid.
     * 
     * @return <code>true</code> if the internal element pointer is small that the array
     *         length
     */
    private boolean isValid() {
        final boolean isValid;
        if (up) {
            isValid = nextInt < to;
        } else {
            isValid = nextInt > to;

        }

        return isValid;
    }

    /**
     * Returns the current integer in the enumerator.
     * 
     * @return the current integer element of the enumerator
     * @see codebase.iterators.ManipulatableIterator#peek()
     * @throws IllegalStateException when peeking an object past the upper bound of the
     *             enumerator
     */
    public Integer peek() {
        if (isValid()) {
            return Integer.valueOf(nextInt);
        } else {
            throw new IllegalStateException();
        }
    }

    /**
     * Checks if an enumerator allow direct access.
     * 
     * @return <code>true</code>
     * @see codebase.iterators.ManipulatableIterator#supportsPeek()
     */
    public boolean supportsPeek() {
        return true;
    }

    /**
     * Checks if an array iterator supports remove.
     * 
     * @return <code>false</code>.
     * @see codebase.iterators.ManipulatableIterator#supportsRemove()
     */
    public boolean supportsRemove() {
        return false;
    }

    /**
     * Checks if an enumerator supports remove.
     * 
     * @return <code>false</code>. An enumerator is read-only.
     * @see codebase.iterators.ManipulatableIterator#supportsUpdate()
     */
    public boolean supportsUpdate() {
        return false;
    }

    /**
     * Implemented for conformance with the interface.
     * 
     * @param replacement the replacement for the underlying object.
     * @throws UnsupportedOperationException is read-only
     * @see codebase.iterators.ManipulatableIterator#update(java.lang.Object)
     */
    public void update(final Integer replacement) {
        throw new UnsupportedOperationException();
    }
}
