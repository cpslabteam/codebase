/*
 * Created on 28/Fev/2005
 */
package codebase;

import java.lang.reflect.Array;

/**
 * Utility class for arrays.
 * <p>
 * This class implements various operations for manipulating arrays of primitive types
 * that are not present in {@link java.util.Arrays} as well as some operations with
 * <tt>Object[]</tt>.
 * <p>
 * The operations include adding elements, cloning, copying, appending, intersecting and
 * other utilities such as flattening on an array and repeating by replication of a value
 * of by a enumeration of a range of values.
 */
public final class Arrays {

    /**
     * Avoid instantiation of utility class.
     */
    private Arrays() {
    }

    /**
     * Adds a new integer to a set of integers represented as an array.
     * <p>
     * This operation implements a set semantics, i.e. it checks to see if the element
     * already exists, if so it does not add the element
     * 
     * @param set the set of integer values
     * @param i the new integer element
     * @return a new array with all the elements of the given set plus the new integer
     *         value if <code>i</code> is not contained in <code>set</code> or, the given
     *         set if <code>i</code> is contained in <code>set</code>. If the set is
     *         <code>null</code> then a singleton set containing <code>i</code> is
     *         returned.
     */
    public static int[] add(final int[] set, final int i) {
        if (set == null) {
            return new int[] { i };
        } else {
            if (pos(i, set) < 0) {
                final int[] result = new int[set.length + 1];
                copy(set, result, 0, 0, set.length);
                final int lastIndex = set.length;
                result[lastIndex] = i;
                return result;
            } else {
                return set;
            }
        }
    }

    /**
     * Adds a new long to a set of longs represented as an array.
     * <p>
     * This operation implements a set semantics, i.e. it checks to see if the element
     * already exists, if so
     * 
     * @param set the set of longs
     * @param i the new long element
     * @return a new array with all the elements of the given set plus the new long value
     *         if <code>i</code> is not contained in <code>set</code> or, the given set if
     *         <code>i</code> is contained in <code>set</code>. If the set is
     *         <code>null</code> then a singleton set containing <code>i</code> is
     *         returned.
     */
    public static long[] add(final long[] set, final long i) {
        if (set == null) {
            return new long[] { i };
        } else {
            if (pos(i, set) < 0) {
                final long[] result = new long[set.length + 1];
                copy(set, result, 0, 0, set.length);
                final int lastIndex = set.length;
                result[lastIndex] = i;
                return result;
            } else {
                return set;
            }
        }
    }

    /**
     * Appends an object array with another.
     * <p>
     * This is an efficient implementation that uses
     * {@link System#arraycopy(java.lang.Object, int, java.lang.Object, int, int)} in its
     * implementation.
     * 
     * @param leftArray the left hand array
     * @param rightArray the right hand array
     * @return a array composed of the the left hand array elements followed by the
     *         elements of the right hand array
     */
    public static Object[] append(Object[] leftArray, Object[] rightArray) {
        final Object[] result = new Object[leftArray.length + rightArray.length];
        System.arraycopy(leftArray, 0, result, 0, leftArray.length);
        System.arraycopy(rightArray, 0, result, leftArray.length, rightArray.length);
        return result;
    }

    /**
     * Appends one byte array with another.
     * 
     * @param left the left hand side array
     * @param right the right hand array.
     * @return an array filled with all elements of the left array followed by the
     *         elements of the right array.
     */
    public static byte[] append(final byte[] left, final byte[] right) {
        final byte[] z = new byte[left.length + right.length];
        System.arraycopy(left, 0, z, 0, left.length);
        System.arraycopy(right, 0, z, left.length, right.length);
        return z;
    }

    /**
     * Appends one byte array with a segment of another array.
     * 
     * @param left the left hand side array
     * @param right the right hand array
     * @param rightStart the right position to start
     * @param length the length of the segment to copy
     * @return an array filled with all elements of the left array followed by the
     *         elements of the right array.
     */
    public static byte[] append(final byte[] left,
                                final byte[] right,
                                final int rightStart,
                                final int length) {
        final byte[] target = new byte[left.length + length];
        System.arraycopy(left, 0, target, 0, left.length);
        System.arraycopy(right, rightStart, target, left.length, length);
        return target;
    }

    /**
     * Checks that all elements of integer array are within bounds.
     * 
     * @param array the integer array to be checked
     * @param max the maximum limit
     * @param min the minimum
     * @return <code>true</code> if for every element <i>e</i> of the array
     *         <code>min<=<i>e</i><=max</code>. Returns <code>false</code> otherwise.
     */
    public static boolean inBounds(final int[] array, final int min, final int max) {
        for (int i = 0; i < array.length; i++) {
            final int e = array[i];
            if ((min > e) || (max < e)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if two arrays are equal element by element.
     * 
     * @param what the first parameter to be compared
     * @param to the seconds parameter to be compared
     * @return <code>true</code> if the arrays are the same, both <code>null</code> or the
     *         elements compare to true using the method
     *         {@link Object#equals(java.lang.Object)}
     */
    public static boolean equals(final Object[] what, final Object[] to) {
        /*
         * If they are the same reference or both null
         */
        if (what == to) {
            return true;
        }

        /*
         * Can we compare them?
         */
        if ((what == null) || (to == null)) {
            return false;
        }

        if (what.length == to.length) {
            /*
             * check all children
             */
            for (int i = 0; i < what.length; ++i) {
                final Object leftElem = what[i];
                final Object rightElem = to[i];
                if ((leftElem != null) && (rightElem != null)) {
                    if (!leftElem.equals(rightElem)) {
                        return false;
                    }
                } else {
                    if (leftElem != rightElem) {
                        return false;
                    }
                }
            }

            /*
             * All them are equal
             */
            return true;
        }

        /*
         * One of them is different. We were unable to compare them
         */
        return false;
    }

    /**
     * Produces an array of {@link Integer} objects from an <code>int[]</code>.
     * <p>
     * Can be used to produce an array of objects to feed an iterator
     * 
     * @param ints the array to be converted
     * @return an array of {@link Integer} objects
     */
    public static Integer[] toIntegerArray(final int[] ints) {
        final Integer[] result = new Integer[ints.length];
        for (int i = 0; i < ints.length; i++) {
            result[i] = Integer.valueOf(ints[i]);
        }
        return result;
    }

    /**
     * Produces an array of {@link Long} objects from a <code>long[]</code>.
     * <p>
     * Can be used to produce an array of objects to feed an iterator
     * 
     * @param longs the array to be converted
     * @return an array of {@link Long} objects
     */
    public static Long[] toLongArray(final long[] longs) {
        final Long[] result = new Long[longs.length];
        for (int i = 0; i < longs.length; i++) {
            result[i] = Long.valueOf(longs[i]);
        }
        return result;
    }

    /**
     * Produces an array of Byte objects from an array of bytes.
     * <p>
     * Can be used to produce an array of objects to feed an iterator
     * 
     * @param bytes the array to be converted
     * @return an array of Byte objects
     */
    public static Byte[] toByteArray(final byte[] bytes) {
        final Byte[] result = new Byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            result[i] = Byte.valueOf(bytes[i]);
        }
        return result;
    }

    /**
     * Fills an segment of an array with a predefined value.
     * 
     * @param value is the value (an Object) that will be replicated to fill the array
     * @param startIndex index where the replication should start
     * @param length number of elements to replicate
     * @param array is the array that will receive the elements
     */
    public static void fill(final Object value,
                            final int startIndex,
                            final int length,
                            final Object[] array) {
        for (int i = startIndex; i < startIndex + length; i++) {
            array[i] = value;
        }
    }

    /**
     * Fills an array with an object value.
     * 
     * @param array is the array that will receive the elements
     * @param value is the value (an Object) whose reference will be replicated to fill
     *            the array
     */
    public static void fill(final Object[] array, final Object value) {
        fill(value, 0, array.length, array);
    }


    /**
     * Converts flattens an object array.
     * <p>
     * 
     * @param o the object to flattened, a simple object or a Object[] with nested
     *            Object[].
     * @return a simple <code>[o]</code> if <code>o</code> is not an array; or an array
     *         <code>[o1,o2,...]</code> if <code>o</code> is an object array.
     */
    public static Object[] flatten(final Object o) {

        if (o == null) {
            return new Object[] { null };
        } else if (o instanceof Object[]) {
            final Object[] oa = (Object[]) o;
            Object[] result = new Object[] {};
            for (int i = 0; i < oa.length; i++) {
                result = codebase.Arrays.append(result, flatten(oa[i]));
            }
            return result;
        } else {
            return new Object[] { o };
        }
    }

    /**
     * Computes the complement of a set of integers.
     * <p>
     * It searches for each element of the set in the universe.
     * <p>
     * <b>Note: </b> This method is not symmetric in the sense that, for example
     * <code>{1} - {0,1,2} = {}</code> and not <code>{0,2}</code> as one could expect.
     * 
     * @param s the set to be complemented
     * @param t the set of integers the represents the universe
     * @return the complement of <code>s</code> with respect to <code>u
     *         </code>
     */
    public static int[] minus(final int[] s, final int[] t) {
        int[] result = new int[] {};
        for (int i = 0; i < s.length; i++) {
            if (pos(s[i], t) < 0) {
                result = add(result, s[i]);
            }
        }
        return result;
    }

    /**
     * Removes an element from a set of integers.
     * <p>
     * It searches for each element of the set in the universe and the removes it.
     * <p>
     * 
     * @param s the set where the element will be searched
     * @param elem the element to be searched
     * @return a reference to a copy of the s without all the ocurrences of the element if
     *         elem was found. If elem was not found returns a copy of s.
     */
    public static int[] remove(final int elem, final int[] s) {
        int[] result = new int[] {};
        for (int i = 0; i < s.length; i++) {
            if (s[i] != elem) {
                result = add(result, s[i]);
            }
        }
        return result;
    }

    /**
     * Removes an element from a set of integers.
     * <p>
     * It searches for each element of the set in the universe and the removes it.
     * 
     * @param s the set where the element will be searched
     * @param elem the element to be searched
     * @return a reference to a copy of the s without all the ocurrences of the element if
     *         elem was found. If elem was not found returns a copy of s.
     */
    public static long[] remove(final long elem, final long[] s) {
        long[] result = new long[] {};
        for (int i = 0; i < s.length; i++) {
            if (s[i] != elem) {
                result = add(result, s[i]);
            }
        }
        return result;
    }

    /**
     * Computes the intersection of sets of integers.
     * <p>
     * It searches for the elements of the left hand set of the right hand set
     * 
     * @param s the left hand set
     * @param t the right hand set
     * @return the array that contains the intersection of the two sets
     */
    public static int[] intersect(final int[] s, final int[] t) {
        int[] result = new int[] {};
        for (int i = 0; i < s.length; i++) {
            if (pos(s[i], t) > -1) {
                result = add(result, s[i]);
            }
        }
        return result;
    }

    /**
     * Verifies if two objects are equal. It recurse down into arrays.
     * 
     * @param left the left hand object
     * @param right the right hand object
     * @return true if the objects are equal and their array elements are equal as well.
     */
    public static boolean isEqualArrayDeep(final Object left, final Object right) {
        if ((left instanceof Object[]) && (right instanceof Object[])) {
            return isEqualArrayDeep1((Object[]) left, (Object[]) right);
        }

        if (left != null) {
            return left.equals(right);
        } else {
            return false;
        }
    }

    /*
     * Tests if two object arrays are equal!
     */
    private static boolean isEqualArrayDeep1(Object[] left, Object[] right) {
        if (left.length == right.length) {
            for (int objIndex = 0; objIndex < left.length; objIndex++) {
                if (!isEqualArrayDeep(left[objIndex], right[objIndex])) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Returns all integers <i>i </i> such that <code>begin</code> &leq; <i>i </i>&lt;
     * <code>end</code>.
     * 
     * @param begin starting value of the enumeration
     * @param end limit value of the enumeration (exclusive)
     * @return all the integers within an interval Note: begin must be less or equal than
     *         end
     */
    public static int[] enumeration(final int begin, final int end) {
        if (begin > end) {
            throw new IllegalArgumentException("start must be less or equal than end");
        }

        final int[] result = new int[end - begin];
        for (int i = begin; i < end; i++) {
            result[i - begin] = i;
        }
        return result;
    }

    /**
     * Copies all objects of one array into another array, starting at a specified
     * position.
     * 
     * @param fromArray array to read the elements from
     * @param toArray an array to write the elements to
     * @param toStartIndex index of the target array where elements should start to be
     *            written.
     */
    public static void copy(final Object[] fromArray, final Object[] toArray, final int toStartIndex) {
        copy(fromArray, 0, toArray, toStartIndex, toArray.length - (1 + toStartIndex));
    }

    /**
     * Clones an array of bytes.
     * 
     * @param array the array to be cloned
     * @return a new array filled with the elements of the argument array
     */
    public static byte[] clone(final byte[] array) {
        return clone(array, 0, array.length);
    }

    /**
     * Clones a segment of an array of bytes.
     * 
     * @param array the array with the segment to be cloned
     * @param start the initial position of the segment
     * @param length the lenght of the segment to be cloned
     * @return a new byte array filled with the elements corresponding to the specified
     *         segment
     */
    public static byte[] clone(final byte[] array, final int start, final int length) {
        final byte[] result = new byte[length];
        System.arraycopy(array, start, result, 0, length);
        return result;
    }

    /**
     * Copies an array of integers into another.
     * 
     * @param source the source array
     * @param target the target array
     * @param from the source start position
     * @param to the target start position
     * @param length how many elements to copy
     */
    public static void copy(final int[] source,
                            final int[] target,
                            final int from,
                            final int to,
                            final int length) {
        System.arraycopy(source, from, target, to, length);
    }

    /**
     * Copies an array of long into another.
     * 
     * @param source the source array
     * @param target the target array
     * @param from the source start position
     * @param to the target start position
     * @param length how many elements to copy
     */
    public static void copy(final long[] source,
                            final long[] target,
                            final int from,
                            final int to,
                            final int length) {
        System.arraycopy(source, from, target, to, length);
    }

    /**
     * Copies a elements of one array into another array. Copies an array from the
     * specified source array, beginning at the specified position, to the specified
     * position of the destination array. A subsequence of array components are copied
     * from the source array referenced by <code>source</code> to the destination array
     * referenced by <code>target</code>. The number of components copied is equal to the
     * <code>size</code> argument. The components at positions <code>sourceOffset</code>
     * through <code>sourceOffset+size-1</code> in the source array are copied into
     * positions <code>targetOffset</code> through <code>targetOffset+size-1</code>,
     * respectively, of the destination array.
     * <p>
     * If the <code>source</code> and <code>target</code> arguments refer to the same
     * array object, then the copying is performed as if the components at positions
     * <code>sourceOffset</code> through <code>sourceOffset+size-1</code> were first
     * copied to a temporary array with <code>size</code> components and then the contents
     * of the temporary array were copied into positions <code>targetOffset</code> through
     * <code>targetOffset+size-1</code> of the destination array.
     * <p>
     * If <code>target</code> is <code>null</code>, then a
     * <code>NullPointerException</code> is thrown.
     * <p>
     * If <code>source</code> is <code>null</code>, then a
     * <code>NullPointerException</code> is thrown and the destination array is not
     * modified.
     * <p>
     * Otherwise, if any of the following is true, an <code>ArrayStoreException</code> is
     * thrown and the destination is not modified:
     * <ul>
     * <li>The <code>source</code> argument refers to an object that is not an array.
     * <li>The <code>target</code> argument refers to an object that is not an array.
     * <li>The <code>source</code> argument and <code>target</code> argument refer to
     * arrays whose component types are different primitive types.
     * <li>The <code>source</code> argument refers to an array with a primitive component
     * type and the <code>target</code> argument refers to an array with a reference
     * component type.
     * <li>The <code>source</code> argument refers to an array with a reference component
     * type and the <code>target</code> argument refers to an array with a primitive
     * component type.
     * </ul>
     * <p>
     * Otherwise, if any of the following is true, an
     * <code>IndexOutOfBoundsException</code> is thrown and the destination is not
     * modified:
     * <ul>
     * <li>The <code>sourceOffset</code> argument is negative.
     * <li>The <code>targetOffset</code> argument is negative.
     * <li>The <code>size</code> argument is negative.
     * <li><code>sourceOffset+size</code> is greater than <code>source.length</code>, the
     * length of the source array.
     * <li><code>targetOffset+size</code> is greater than <code>target.length</code>, the
     * length of the destination array.
     * </ul>
     * <p>
     * Otherwise, if any actual component of the source array from position
     * <code>sourceOffset</code> through <code>sourceOffset+size-1</code> cannot be
     * converted to the component type of the destination array by assignment conversion,
     * an <code>ArrayStoreException</code> is thrown. In this case, let <b><i>k </i> </b>
     * be the smallest nonnegative integer less than length such that
     * <code>source[sourceOffset+</code> <i>k </i> <code>]</code> cannot be converted to
     * the component type of the destination array; when the exception is thrown, source
     * array components from positions <code>sourceOffset</code> through
     * <code>sourceOffset+</code> <i>k </i> <code>-1</code> will already have been copied
     * to destination array positions <code>targetOffset</code> through
     * <code>targetOffset+</code> <i>k </I> <code>-1</code> and no other positions of the
     * destination array will have been modified. (Because of the restrictions already
     * itemized, this paragraph effectively applies only to the situation where both
     * arrays have component types that are reference types.)
     * <p>
     * This method calls {@link java.lang.System#arraycopy(Object,int,Object,int,int)}.
     * 
     * @param source the source array
     * @param from start position in the source array
     * @param target the destination array
     * @param to start position in the destination data
     * @param size the number of array elements to be copied
     * @throws IndexOutOfBoundsException if copying would cause access of data outside
     *             array bounds.
     * @throws ArrayStoreException if an element in the <code>source</code> array could
     *             not be stored into the <code>target</code> array because of a type
     *             mismatch.
     * @throws NullPointerException if either <code>source</code> or <code>target</code>
     *             is <code>null</code>.
     */
    public static void copy(final Object[] source,
                            final int from,
                            final Object[] target,
                            final int to,
                            final int size) {
        final int sourceLength = Array.getLength(source);
        final int targetLength = Array.getLength(target);
        int start = from;
        int finish = to;
        int elemCount = size;

        while (elemCount > 0) {
            final int length = java.lang.Math.min(
                    java.lang.Math.min(sourceLength - start, targetLength - finish), size);
            System.arraycopy(source, start, target, finish, length);
            start = (start + length) % sourceLength;
            finish = (finish + length) % targetLength;
            elemCount -= length;
        }
    }

    /**
     * Returns a the duplicate of an array of any type. Performs a shallow copy of an
     * array generically
     * 
     * @param array the aray to be duplicated
     * @return the reference to the duplicate array
     */
    public static Object duplicate(final Object array) {
        final int size = Array.getLength(array);
        final Object newarray = Array.newInstance(array.getClass().getComponentType(), size);
        System.arraycopy(array, 0, newarray, 0, size);

        return newarray;
    }

    /**
     * Creates an array constituted of references to the same element.
     * 
     * @param what the element to be replicated
     * @param times the number of times to replicate, must be positive
     * @return an object array with the specified number of copies of the same element
     */
    public static Object[] replicate(final Object what, final int times) {
        final Object[] result = new Object[times];
        for (int i = 0; i < times; i++) {
            result[i] = what;
        }
        return result;
    }

    /**
     * Returns a new array of a given size.
     * <p>
     * The new array contains as many elements of the original array as it can hold. N.B.
     * Always returns a new array even if newsize parameter is the same as the old size.
     * 
     * @param source the array to resized
     * @param size the size of the new array, must not be smaller than the size of the
     *            specified array
     * @return a new array with the elements of the original array
     */
    public static Object resize(final Object source, final int size) {
        final int oldSize = Array.getLength(source);

        if (size < oldSize) {
            throw new IllegalArgumentException("Canot skrink the array");
        }

        final Object newarray = Array.newInstance(source.getClass().getComponentType(), size);
        System.arraycopy(source, 0, newarray, 0, size);

        return newarray;
    }

    /**
     * Creates a byte array by repeating the same byte.
     * 
     * @param what the byte to be replicated
     * @param times the number of times to replicate, must be positive
     * @return a byte array filled with the same byte
     */
    public static byte[] replicate(final byte what, final int times) {
        final byte[] result = new byte[times];
        for (int i = 0; i < times; i++) {
            result[i] = what;
        }
        return result;
    }

    /**
     * Searches for a byte on a list of bytes.
     * 
     * @param elem the element to be searched
     * @param array the array to seach on
     * @return the position of the element in the array or <code>-1</code> if the element
     *         is not found.
     */
    public static int pos(final byte elem, final byte[] array) {
        return pos(elem, 0, array);
    }

    /**
     * Searches for a byte on a list of bytes starting at a given index.
     * 
     * @param elem the element to be searched
     * @param startOffset the position where to start
     * @param array the array to seach on
     * @return he position of the element in the array or <code>-1</code> if the element
     *         is not found after the specified offset.
     */
    public static int pos(final byte elem, final int startOffset, final byte[] array) {
        final int n = array.length;
        for (int i = startOffset; i < n; i++) {
            if (array[i] == elem) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Searches for an integer element on a list of integers.
     * 
     * @param elem the element to search for
     * @param array the array of elements
     * @return the position of the element in the array or the <code>-1</code> if the
     *         element is not found
     */
    public static int pos(final int elem, final int[] array) {
        final int length = array.length;
        for (int i = 0; i < length; i++) {
            if (array[i] == elem) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Searches for an charact element on a list of characters.
     * 
     * @param elem the element to search for
     * @param array the array of elements
     * @return the position of the element in the array or the <code>-1</code> if the
     *         element is not found
     */
    public static int pos(final char elem, final char[] array) {
        final int length = array.length;
        for (int i = 0; i < length; i++) {
            if (array[i] == elem) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Searches for an long on a list of longs.
     * 
     * @param elem the element to search for
     * @param array the array of elements
     * @return the position of the element in the array or the <code>-1</code> if the
     *         element is not found
     */
    public static int pos(final long elem, final long[] array) {
        final int length = array.length;
        for (int i = 0; i < length; i++) {
            if (array[i] == elem) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Produces an array with the unique integers that occour on the input array. Formally
     * this function computes the set of unique integers from a bag of integers.
     * 
     * @param unorderedValues the input array with integer values, must not be
     *            <code>null</code>.
     * @return the distinct integer values
     */
    public static int[] unique(final int[] unorderedValues) {
        if (unorderedValues.length == 0) {
            return new int[] {};
        } else {
            final int[] orderedValues = new int[unorderedValues.length];
            copy(unorderedValues, orderedValues, 0, 0, unorderedValues.length);
            java.util.Arrays.sort(orderedValues);

            int lastSeen = orderedValues[0];
            int distincts = 1;
            /*
             * Compacts the array bringing all distinct values together
             */
            for (int i = 1; i < orderedValues.length; i++) {
                if (orderedValues[i] != lastSeen) {
                    distincts += 1;
                    lastSeen = orderedValues[i];
                    final int distinctIndex = distincts - 1;
                    orderedValues[distinctIndex] = lastSeen;
                }
            }

            final int[] uniqueArray = new int[distincts];
            copy(orderedValues, uniqueArray, 0, 0, distincts);
            return uniqueArray;
        }
    }

    /**
     * Finds ass indices that are not <code>null</code>.
     * <p>
     * Produces an array of indices corresponding to all the positions of an array of
     * object that are not <code>null</code>
     * 
     * @param values the input object array, must not be <code>null</code>
     * @return an integer array with the position of the input array that are not
     *         <code>null</code>
     */
    public static int[] findNotNullIndices(final Object[] values) {
        final int[] notNullCandidateIndices = new int[values.length];
        int notNullsSoFar = 0;
        for (int i = 0; i < values.length; i++) {
            if (values[i] != null) {
                notNullsSoFar += 1;
                final int nextNotNullIndex = notNullsSoFar - 1;
                notNullCandidateIndices[nextNotNullIndex] = i;
            }
        }

        final int[] notNullIndices = new int[notNullsSoFar];
        copy(notNullCandidateIndices, notNullIndices, 0, 0, notNullsSoFar);
        return notNullIndices;
    }

    /**
     * Obtains the string representation of an array of integers.
     * 
     * @param bytes the byte values to be converted
     * @return a string of the form <code>[b1, ..., bn]</code>
     */
    public static String toString(final byte[] bytes) {
        final StringBuffer result = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            if (result.length() != 0) {
                result.append("," + Byte.toString(bytes[i]));
            } else {
                result.append(Integer.toString(bytes[i]));
            }
        }
        return "[" + result.toString() + "]";
    }

    /**
     * Obtains the string representation of an array of integers.
     * 
     * @param ints the integer values to be converted
     * @return a string of the form <code>[i1, ..., in]</code>
     */
    public static String toString(final int[] ints) {
        final StringBuffer result = new StringBuffer();
        for (int i = 0; i < ints.length; i++) {
            if (result.length() != 0) {
                result.append("," + Integer.toString(ints[i]));
            } else {
                result.append(Integer.toString(ints[i]));
            }
        }
        return "[" + result.toString() + "]";
    }

    /**
     * Obtains the string representation of an array of longs.
     * 
     * @param longs the long values to be converted
     * @return a string of the form <code>[l1, ..., ln]</code>
     */
    public static String toString(final long[] longs) {
        final StringBuffer result = new StringBuffer();
        for (int i = 0; i < longs.length; i++) {
            if (result.length() != 0) {
                result.append("," + Long.toString(longs[i]));
            } else {
                result.append(Long.toString(longs[i]));
            }
        }
        return "[" + result.toString() + "]";
    }

}
