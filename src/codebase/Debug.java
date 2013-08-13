/*
 * Created on 18/Nov/2004
 */
package codebase;

import java.util.Iterator;

import codebase.binary.Binary;

/**
 * Utility class for debugging and logging.
 * <p>
 * This class encapsulates several debugging utilities that can be used for diagnosing
 * purposes while testing
 */
public final class Debug {

    /**
     * Prevent instantiation.
     */
    private Debug() {
    }

    /**
     * Obtains the string dump of an object taking care of nulls and arrays.
     * <p>
     * This method is specially useful when a class cast exception occurs because it gives
     * us the class for the object and its contents. It handles iterators and arrays of
     * objects.
     * 
     * @param o the object to be dumped
     * @return the type of the object and its contents
     */
    public static String toStringDump(final Object o) {
        final String result;
        if (o == null) {
            result = "null";
        } else {
            result = o.getClass().toString() + ":" + toString(o);
        }

        return result;
    }

    /**
     * Create a string with a binary dump.
     * 
     * @param buffer the byte buffer
     * @return a line in the format XXXX HHHHHHHHH where X is a character and HH is its
     *         corresponding hex code.
     */
    public static String toBinaryStringDump(final byte[] buffer) {
        final String result = Format.visibleAsciiString(new String(buffer), '.') + "  "
                + Binary.toHexString(buffer);
        return result;
    }

    /**
     * Create a multi-line string with a binary dump.
     * 
     * @param buffer the byte buffer
     * @param segmentSize the number of bytes of the buffer to be dumped on each line
     * @return XXXX HHHHHHHHH\n ... XXXX HHHHHHHHH here X is a character and HH is its
     *         corresponding hex code.
     * @see #toBinaryStringDump(byte[])
     */
    public static String toBinaryStringMultiLineDump(final byte[] buffer, final int segmentSize) {
        final StringBuffer result = new StringBuffer();
        int i = 0;
        for (; i + segmentSize <= buffer.length; i += segmentSize) {
            final byte[] segment = new byte[segmentSize];
            System.arraycopy(buffer, i, segment, 0, segmentSize);
            result.append(toBinaryStringDump(segment) + "\n");
        }

        if (i < buffer.length) {
            final int remaining = buffer.length - i;
            final byte[] segment = new byte[remaining];
            System.arraycopy(buffer, i, segment, 0, remaining);
            result.append(toBinaryStringDump(segment) + "\n");
        }

        return result.toString();
    }

    /**
     * Prints a the contents of an iterator
     * <p>
     * Calling the <code>toString()</code> of all objects returned by the iterator.
     * 
     * @param i is the cursor to be analyzed
     * @throws IllegalStateException is an exception in
     */
    public static void print(final Iterator<String> i) {
        System.out.println("---ITERATOR STARTING---");
        System.out.flush();

        int rowNum = 0;
        if (!i.hasNext()) {
            System.out.println("The iterator is empty.");
        }
        while (i.hasNext()) {
            Object o = null;
            String str = "";
            try {
                o = i.next();
            } catch (Exception e) {
                throw new IllegalStateException("Exception when performing i.next():"
                        + e.toString());
            }
            try {
                str = codebase.Debug.toString(o);
            } catch (Exception e) {
                throw new IllegalStateException("Exception when performing o.toString():"
                        + e.toString());
            }

            System.out.println(Integer.toString(rowNum) + ":" + str);
            System.out.flush();
            rowNum++;
        }
        System.out.println("---ITERATOR ENDED---");
        System.out.flush();
    }

    /**
     * Prints an object to the console by calling {@link Debug#toString(Object)}.
     * 
     * @param o the object to be dumped
     */
    public static void print(final Object o) {
        System.out.println(toString(o));
        System.out.flush();
    }

    /**
     * Prints an integer.
     * 
     * @param i the integer to dumped
     */
    public static void print(final int i) {
        print(Integer.toString(i));
    }

    /**
     * Prints a long.
     * 
     * @param l the long to be dumped
     */
    public static void print(final long l) {
        print(Long.toString(l));
    }

    /**
     * Obtains a string that represents an object.
     * <p>
     * Handles <code>null</code> and object arrays correctly.
     * 
     * @param o Object to print as a string
     * @return the string representation of the object
     */
    public static String toString(final Object o) {
        String result = "";
        if (o == null) {
            result = "null";
        } else {
            if (o instanceof Object[]) {
                result = toString((Object[]) o);
            } else if (o instanceof int[]) {
                result = toString((int[]) o);
            } else if (o instanceof String) {
                result = "'" + o.toString() + "'";
            } else {

                result = o.toString();
            }
        }
        return result;

    }

    /**
     * Obtains the string representation of an array of objects.
     * 
     * @param objs the objects array to be converted
     * @return a string of the form <code>[o1, ..., on]</code>
     */
    public static String toString(final Object[] objs) {
        final StringBuffer result = new StringBuffer();
        for (int i = 0; i < objs.length; i++) {
            if (result.length() != 0) {
                result.append("," + toString(objs[i]));
            } else {
                result.append(toString(objs[i]));
            }
        }

        return "[" + result.toString() + "]";
    }

    /**
     * Obtains the string representation of an array of integers.
     * 
     * @param objs the integer values to be converted
     * @return a string of the form <code>[i1, ..., in]</code>
     */
    public static String toString(final int[] objs) {
        final StringBuffer result = new StringBuffer();
        for (int i = 0; i < objs.length; i++) {
            if (result.length() != 0) {
                result.append("," + Integer.toString(objs[i]));
            } else {
                result.append(Integer.toString(objs[i]));
            }
        }
        return "[" + result.toString() + "]";
    }

    /**
     * Obtains the string representation of an iterator.
     * 
     * @param iterator the iterator with the elements to be converted
     * @return a string of the form <code><i1, ..., in></code>
     */
    public static String toString(final Iterator<?> iterator) {
        String result = "";

        int rowNum = 0;
        if (!iterator.hasNext()) {
            result = "EmptyIterator iterator";
        }
        while (iterator.hasNext()) {
            final Object o;
            String str = "";
            try {
                o = iterator.next();
            } catch (Exception e) {
                str = "Exception when performing iterator.next():" + e.toString();
                break;
            }
            try {
                str = codebase.Debug.toString(o);
            } catch (Exception e) {
                str = "Exception when performing o.toString():" + e.toString();
                break;
            }

            final String nextElement = Integer.toString(rowNum) + ":" + str;
            if (result.length() == 0) {
                result = nextElement;
            } else {
                result = result + ", " + nextElement;
            }

            rowNum += 1;
        }
        return "<" + result + ">";
    }
}
