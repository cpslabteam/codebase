/*
 * Created on 19/Out/2005
 */
package codebase.junit;

import codebase.Arrays;

/**
 * A wrapper object for arrays that makes them comparable.
 * <p>
 * Object arrays cannot be compared using the
 * {@link java.lang.Object#equals(java.lang.Object)}. In some situations we need to
 * compare object arrays using this method.
 * <p>
 * This wrapper allows an object array to be compared to another object array. It
 * implements the {@link java.lang.Object#equals(java.lang.Object)} method. Usage:
 */
public class ComparableArrayWrapper {

    /**
     * Reference to the array being wrapped.
     */
    private final Object[] wrapped;

    /**
     * Constructs a wrapper for a given object array.
     * 
     * @param array the array to be wrapped
     */
    public ComparableArrayWrapper(final Object[] array) {
        wrapped = array.clone();
    }

    /**
     * Returns the hash code of the array object.
     * <p>
     * When we override the method {@link Object#equals(java.lang.Object)} we also have to
     * override this one.
     * 
     * @return the hash code of the wrapped array
     * @see java.lang.Object#hashCode()
     */
    public final int hashCode() {
        return java.util.Arrays.hashCode(wrapped);
    }

    /**
     * Compares the wrapped array with another array.
     * 
     * @param another the object to compare to, can be an <code>Object[]</code> or a
     *            {@link ComparableArrayWrapper}
     * @return <code>true</code> if the array being wrapped and the array passed as
     *         argument are both <code>null</code> or are equal element by element
     * @see Arrays#equals(Object[], Object[])
     * @see java.lang.Object#equals(java.lang.Object)
     * @throws IllegalArgumentException if the object being passed as argument is neither
     *             an object array nor a {@link ComparableArrayWrapper}
     */
    public final boolean equals(final Object another) {
        final Object[] anotherArray;
        if (another instanceof ComparableArrayWrapper) {
            final ComparableArrayWrapper otherWrapper = (ComparableArrayWrapper) another;
            anotherArray = otherWrapper.getArray();
        } else
            try {
                /*
                 * FindBugs complains that we cannot convert to Object[] 
                 * because Object[] is not an ascendent or descendant of 
                 * ComparableArrayWrapper. However, to simplify the client 
                 * code we should allow comparing with Object[].
                 */
                anotherArray = coerce(another);
            } catch (ClassCastException e) {
                throw new IllegalArgumentException("Expecting an object array");
            }

        final boolean isEqual = Arrays.equals(wrapped, anotherArray);
        return isEqual;
    }

    /*
     * This method is used only to avoid FidBugs from complaining.
     */
    private static Object[] coerce(Object o) {
        return (Object[]) o;
    }

    /**
     * Returns the array being wrapped.
     * 
     * @return the reference to the array being wrapped
     */
    public final Object[] getArray() {
        return wrapped.clone();
    }

}
