package codebase;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;


/**
 * Utility class for debugging and dumping object to output streams and to the console.
 * <p>
 * Encapsulates utilities to dump the contents of objects for debugging, diagnosis and
 * testing. The utilities include:
 * <ol>
 * <li>Printing the contents of objects or iterators to the console</li>
 * <li>Dumping the contents of objects or iterators to the output stream</li>
 * <li>Converting arrays of objects to string</li>
 * </ol>
 * Another interesting functionality the ability to dumping byte[] as hex strings, which
 * is quite useful to analyze the contents debugging binary buffers.
 * 
 * @since Created on 18/Nov/2004
 */
public final class DebugUtil {

    /**
     * Default string encoding.
     */
    private static final String DEFAULT_STRING_ENCODING = "UTF-8";

    /**
     * Prints a the contents of an iterator to the console.
     * <p>
     * Calling the <code>toString()</code> of all objects returned by the iterator.
     * 
     * @param i is the iterator to be exhausted
     * @throws IllegalStateException is an exception occurs while exhausting the iterator
     */
    public static void dump(final Iterator<?> i) {
        try {
            DebugUtil.dump(i, System.out);
        } catch (IOException e) {
            // CHECKSTYLE:OFF
            // This is a debug utility method that really should print to the screen.
            e.printStackTrace();
            // CHECKSTYLE:ON
        }
    }

    /**
     * Prints the contents of an iterator to the specified output stream.
     * <p>
     * Each element returned by the iterator is dumped preceded by the line number by
     * calling the <code>toString()</code> of all objects returned by the iterator.
     * 
     * @param i is the cursor to be analyzed
     * @param out the output stream to write the dump of each element
     * @throws IOException if an exception occurs while writing on the output stream
     * @throws IllegalStateException is an exception occurs while exhausting the iterator.
     */
    public static void dump(final Iterator<?> i, PrintStream out) throws IOException {
        out.println("---ITERATOR STARTING---");
        out.flush();

        int rowNum = 0;
        if (!i.hasNext()) {
            out.println("The iterator is empty.");
        }
        while (i.hasNext()) {
            Object o = null;
            String str = "";
            try {
                o = i.next();
            } catch (Exception e) {
                throw new IllegalStateException(
                        "Exception when performing i.next():" + e.toString());
            }
            try {
                str = codebase.DebugUtil.toString(o);
            } catch (Exception e) {
                throw new IllegalStateException(
                        "Exception when performing o.toString():" + e.toString());
            }
            
            out.println(Integer.toString(rowNum) + ":" + str);
            out.flush();
            rowNum++;
        }
        out.println("---ITERATOR ENDED---");
        out.flush();
    }

    /**
     * Prints the dump of an object to the console by calling
     * {@link DebugUtil#toString(Object)}.
     * 
     * @param o the object to be dumped
     */
    public static void dump(final Object o) {
        try {
            DebugUtil.dump(o, System.out);
        } catch (IOException e) {
            // CHECKSTYLE:OFF
            // This is a debug utility method that really should print to the screen.
            e.printStackTrace();
            // CHECKSTYLE:ON
        }
    }

    /**
     * Dumps an object to a stream the console by calling
     * {@link DebugUtil#toString(Object)}.
     * 
     * @param o the object to be dumped
     * @param out the output stream to write the object
     * @throws IOException if an exception occurs while writing on the output stream
     */
    public static void dump(final Object o, final OutputStream out) throws IOException {
        out.write(DebugUtil.toString(o).getBytes(DEFAULT_STRING_ENCODING));
        out.flush();
        System.out.flush();
    }

    /**
     * Prints an object[] to the console.
     * 
     * @param objs the object array to be dumped
     */
    public static void dump(final Object[] objs) {
        try {
            DebugUtil.dump(objs, System.out);
        } catch (IOException e) {
            // CHECKSTYLE:OFF
            // This is a debug utility method that really should print to the screen.
            e.printStackTrace();
            // CHECKSTYLE:ON
        }
    }

    /**
     * Dumps an object[] to a stream..
     * 
     * @param objs the objects to be dumped
     * @param out the output stream to write the object
     * @throws IOException if an exception occurs while writing on the output stream
     */
    public static void dump(final Object[] objs, OutputStream out) throws IOException {
        out.write(ArrayUtil.toString(objs, ", ").getBytes(DEFAULT_STRING_ENCODING));
        out.flush();
    }

    /**
     * Prints the binary dump of a byte[] buffer to the standard output by calling
     * {@link #toHexStringDump(byte[])}.
     * <p>
     * This method can be used to for example to conveniently dump data from a stream as
     * follows: <code>
     *    byte[] buffer = new byte[1024];
          someInputStream.read(buffer);
          Debug.dumpToHexString(b, 32);
     * </code>
     * 
     * @param buffer the buffer to be dumped
     */
    public static void dumpToHexString(final byte[] buffer) {
        try {
            DebugUtil.dumpToHexString(buffer, System.out);
        } catch (IOException e) {
            // CHECKSTYLE:OFF
            // This is a debug utility method that really should print to the screen.
            e.printStackTrace();
            // CHECKSTYLE:ON
        }
    }

    /**
     * Prints the binary dump of a byte[] buffer <i>in multiple lines</i> to the standard
     * output by calling {@link #toHexStringDump(byte[], int)}.
     * 
     * @param buffer the buffer to be dumped
     * @param size the number of bytes to be dumped from the buffer on each line.
     */
    public static void dumpToHexString(final byte[] buffer, final int size) {
        try {
            DebugUtil.dumpToHexString(buffer, System.out, size);
        } catch (IOException e) {
            // CHECKSTYLE:OFF
            // This is a debug utility method that really should print to the screen.
            e.printStackTrace();
            // CHECKSTYLE:ON
        }
    }

    /**
     * Dumps the binary dump of a byte[] buffer to the given output stream by calling
     * {@link #toHexStringDump(byte[])}.
     * <p>
     * The object will be dumped according to the format of
     * {@link #dumpToHexString(byte[])}.
     * 
     * @param buffer the object to be dumped
     * @param out the output stream to write the buffer to
     * @throws IOException if an exception occurs while writing on the output stream
     * @see #toHexStringDump(byte[])
     */
    public static void dumpToHexString(final byte[] buffer, final OutputStream out)
            throws IOException {
        out.write(toHexStringDump(buffer).getBytes(DEFAULT_STRING_ENCODING));
        out.flush();
    }

    /**
     * Dumps the binary dump of a byte[] buffer to the given output stream by calling
     * {@link #toHexStringDump(byte[], int)}.
     * <p>
     * The object will be dumped according to the format of
     * {@link #dumpToHexString(byte[])}.
     * 
     * @param buffer the object to be dumped
     * @param out the output stream to write the buffer to
     * @param size the size of each segment (in bytes) to take from the buffer
     * @throws IOException if an exception occurs while writing on the output stream
     * @see #toHexStringDump(byte[], int)
     */
    public static void dumpToHexString(final byte[] buffer, final OutputStream out, final int size)
            throws IOException {
        out.write(toHexStringDump(buffer, size).getBytes(DEFAULT_STRING_ENCODING));
        out.flush();
    }

    /**
     * Prints the dump of an object along with its class type to the standard output by
     * calling {@link #toStringWithClass(Object)}.
     * 
     * @param o the object to be dumped
     */
    public static void dumpWithClass(final Object o) {
        try {
            DebugUtil.dumpWithClass(o, System.out);
        } catch (IOException e) {
            // CHECKSTYLE:OFF
            // This is a debug utility method that really should print to the screen.
            e.printStackTrace();
            // CHECKSTYLE:ON
        }
    }

    /**
     * Dumps an object along with its class type to a given output stream by calling
     * {@link #toStringWithClass(Object)}.
     * <p>
     * The object will be dumped according to the format of
     * {@link #dumpWithClass(Object, OutputStream)}. The string is converted into a byte[]
     * using ' UTF-8' encoding.
     * 
     * @param o the object to be dumped
     * @param out the output stream to write the object
     * @throws IOException if an exception occurs while writing on the output stream
     * @see #toStringWithClass(Object)
     */
    public static void dumpWithClass(final Object o, final OutputStream out) throws IOException {
        out.write(toStringWithClass(o).getBytes(DEFAULT_STRING_ENCODING));
        out.flush();
    }

    /**
     * Create a string with a binary dump.
     * 
     * @param buffer the byte buffer
     * @return a line in the format <code>XXXX HHHHHHHHH</code> where <code>X</code> is a
     *         character and <code>HH</code> is its corresponding hex code.
     */
    public static String toHexStringDump(final byte[] buffer) {
        try {
            final String result =
                StringUtil.visibleAsciiString(new String(buffer, DEFAULT_STRING_ENCODING), '.')
                        + "  " + BinaryUtil.toHexString(buffer);
            return result;
        } catch (UnsupportedEncodingException e) {
            // this is an coding error situation - should never happen
            throw new RuntimeException(e);
        }
    }

    /**
     * Create a multi-line string with a binary dump.
     * 
     * @param buffer the byte buffer
     * @param size the number of bytes of the buffer to be dumped on each line
     * @return a line in the format <code>XXXX HHHHHHHHH\n ... XXXX HHHHHHHHH</code> here
     *         <code>X</code> is a character and <code>HH</code> is its corresponding hex
     *         code.
     * @see #toHexStringDump(byte[])
     */
    public static String toHexStringDump(final byte[] buffer, final int size) {
        final StringBuffer result = new StringBuffer();
        int i = 0;
        for (; i + size <= buffer.length; i += size) {
            final byte[] segment = new byte[size];
            System.arraycopy(buffer, i, segment, 0, size);
            result.append(toHexStringDump(segment) + "\n");
        }

        if (i < buffer.length) {
            final int remaining = buffer.length - i;
            final byte[] segment = new byte[remaining];
            System.arraycopy(buffer, i, segment, 0, remaining);
            result.append(toHexStringDump(segment) + "\n");
        }

        return result.toString();
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
                str = codebase.DebugUtil.toString(o);
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

    /**
     * Obtains a string that represents an object.
     * <p>
     * An <code>null</code> reference is translated <code>null</code>, a String object to
     * <code>'object'</code> and and object array to <code>[o1,...,on]</code>.
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
                result = "[" + ArrayUtil.toString((Object[]) o, ", ") + "]";
            } else if (o instanceof byte[]) {
                result = "[" + ArrayUtil.toString((byte[]) o, ", ") + "]";
            } else if (o instanceof int[]) {
                result = "[" + ArrayUtil.toString((int[]) o, ", ") + "]";
            } else if (o instanceof long[]) {
                result = "[" + ArrayUtil.toString((long[]) o, ", ") + "]";
            } else if (o instanceof String) {
                result = "'" + o.toString() + "'";
            } else {
                result = o.toString();
            }
        }
        return result;

    }

    /**
     * Obtains the string dump of an object along with its class type.
     * <p>
     * This method is specially useful when a class cast exception occurs because it gives
     * us the class for the object and its contents. It handles iterators and arrays of
     * objects.
     * 
     * @param o the object to be dumped
     * @return a string in the form <code><i>c</i>:<i>o</i></code> where
     *         <code><i>c</i></code> is the class type and <code><i>o</i></code> is the
     *         object dump.
     */
    public static String toStringWithClass(final Object o) {
        final String result;
        if (o == null) {
            result = "null";
        } else {
            result = o.getClass().toString() + ":" + DebugUtil.toString(o);
        }

        return result;
    }

    /**
     * Prevent instantiation.
     */
    private DebugUtil() {
    }
}
