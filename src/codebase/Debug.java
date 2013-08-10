/*
 * Created on 18/Nov/2004
 */
package codebase;

import java.util.Iterator;

import codebase.binary.Binary;

/**
 * Utility class for debugging and logging.
 * <p>
 * This class encapsulates several debugging utilities that can be used for
 * diagnosing purposes while testing
 */
public final class Debug {

    /**
     * Prevent instantiation.
     */
    private Debug() {
    }

    /**
     * Exits the program.
     */
    public static void exit() {
        System.exit(0);
    }

    
    
    /**
     * Computes the string dump of an object.
     * <p>
     * This methos is specially usefull when a class cast exception occurs
     * because it gives us the class fo the object and its contents. It handles
     * iterators and arrays of objects.
     * 
     * @param o the object to be dumped
     * @return the type of the object and its contents
     */
    public static String asString(final Object o) {
        final String result;
        if (o == null) {
            result = "null";
        } else {
            result = o.getClass().toString() + ":" + toString(o);
        }

        return result;
    }

    public static String binaryDump(final byte[] buffer) {
        final String result = Format.visibleAsciiString(new String(buffer), '.') + "  "
                + Binary.toHexString(buffer);
        return result;
    }

    public static final String multiLineDump(final byte[] buffer, final int segmentSize) {
        String result = "";
        int i = 0;
        for (; i + segmentSize <= buffer.length; i += segmentSize) {
            final byte[] segment = new byte[segmentSize];
            System.arraycopy(buffer, i, segment, 0, segmentSize);
            result += binaryDump(segment) + "\n";
        }

        if (i < buffer.length) {
            final int remaining = buffer.length - i;
            final byte[] segment = new byte[remaining];
            System.arraycopy(buffer, i, segment, 0, remaining);
            result += binaryDump(segment) + "\n";
        }
        return result;
    }

    /**
     * Prints a the contents of an iterator
     * <p>
     * Calling the <code>toString()</code> of all objects returned by the
     * iterator.
     * 
     * @param i is the cursor to be analysed
     */
    public static void print(final Iterator i) {
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
                new IllegalStateException("Exception when performing o.toString():"
                        + e.toString());
            }

            System.out.println(new Integer(rowNum).toString() + ":" + str);
            System.out.flush();
            rowNum++;
        }
        System.out.println("---ITERATOR ENDED---");
        System.out.flush();
    }

    /**
     * Prints an object to the console by calling {@link Debug#toString(Object)}
     * 
     * @param o the object to be dumped
     */
    public static void print(final Object o) {
        System.out.println(toString(o));
        System.out.flush();
    }

    /**
     * Prints an integer
     * 
     * @param i the integer to dumped
     */
    public static void print(final int i) {
        print(new Integer(i));
    }

    /**
     * Prints a long
     * 
     * @param l the long to be dumped
     */
    public static void print(final long l) {
        print(new Long(l));
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
        String result = "";
        for (int i = 0; i < objs.length; i++) {
            if (result != "") {
                result += "," + toString(objs[i]);
            } else {
                result += toString(objs[i]);
            }
        }
        result = "[" + result + "]";
        return result;
    }

    /**
     * Obtains the string representation of an array of integers
     * 
     * @param objs the integer values to be converted
     * @return a string of the form <code>[i1, ..., in]</code>
     */
    public static String toString(final int[] objs) {
        String result = "";
        for (int i = 0; i < objs.length; i++) {
            if (result != "") {
                result += "," + Integer.toString(objs[i]);
            } else {
                result += Integer.toString(objs[i]);
            }
        }
        result = "[" + result + "]";
        return result;
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

            final String nextElement = new Integer(rowNum).toString() + ":" + str;
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
