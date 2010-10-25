/*
 * Created on 19/Out/2005
 */
package codebase.tests;

import codebase.Arrays;

import codebase.Arrays;

/**
 * A wrapper object for arrays that makes them comparable.
 * <p>
 * Object arrays cannot be compared using the
 * {@link java.lang.Object#equals(java.lang.Object)}. In some situations we
 * need to compare object arrays using this method.
 * <p>
 * This wrapper allows an object array to be compared to another object array.
 * It implements the {@link java.lang.Object#equals(java.lang.Object)} method.
 * Usage:
 */
public class ComparableArrayWrapper {

    /**
     * Reference to the array being wrapped.
     */
    private final Object[] wrapped;

    /**
     * Contructs a wrapper for a given object array.
     * 
     * @param array the array to be wrapped
     */
    public ComparableArrayWrapper(final Object[] array) {
        wrapped = array;
    }

    /**
     * Returns the hash code of the array object.
     * <p>
     * When we override the method {@link Object#equals(java.lang.Object)} we
     * also have to override this one.
     * 
     * @see java.lang.Object#hashCode()
     */
    public final int hashCode() {
        return wrapped.hashCode();
    }

    /**
     * Compares the wrapped array with another array.
     * 
     * @param another the object to compra to, can be an <code>Object[]</code>
     *            or a {@link ComparableArrayWrapper}
     * @return <code>true</code> if the array being wrapped and the array
     *         passed as argument are both <code>null</code> or are equal
     *         element by elelment
     * @see Arrays#equals(Object[], Object[])
     * @see java.lang.Object#equals(java.lang.Object)
     * @throws IllegalArgumentException if the object being passed as argumen
     *             is neither an object array nor a
     *             {@link ComparableArrayWrapper}
     */
    public final boolean equals(final Object another) {
        final Object[] anotherArray;
        if (another instanceof ComparableArrayWrapper) {
            final ComparableArrayWrapper otherWrapper = (ComparableArrayWrapper) another;
            anotherArray = otherWrapper.getArray();
        } else {
            if (another instanceof Object[]) {
                anotherArray = (Object[]) another;
            } else {
                throw new IllegalArgumentException(
                    "Expecting an object array");
            }
        }
        final boolean isEqual = Arrays.equals(wrapped, anotherArray);
        return isEqual;
    }

    /**
     * Returns the array being wrapped.
     * 
     * @return the reference to the array being wrapped
     */
    public final Object[] getArray() {
        return wrapped;
    }

}
