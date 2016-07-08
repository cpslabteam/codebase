package codebase.io.converters.binary;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import codebase.io.converters.Converter;

/**
 * Provides a converter that is able to read and write <tt>String</tt> objects.
 * <p>
 * Example usage:
 * 
 * <pre>
 * // catch IOExceptions
 * try {
 * 
 *     // create a byte array output stream
 *     ByteArrayOutputStream output = new ByteArrayOutputStream();
 * 
 *     // write two strings to the output stream
 *     new StringConverter.write(new DataOutputStream(output), &quot;Hello world!&quot;);
 * 
 *     // create a byte array input stream on the output stream
 *     ByteArrayInputStream input = new ByteArrayInputStream(output.toByteArray());
 * 
 *     // read two strings from the input stream
 *     final String s1 = (String) StringConverter.DEFAULT_INSTANCE.read(new DataInputStream(input));
 * 
 *     // print the value and the object
 *     System.out.println(s);
 * 
 *     // close the streams after use
 *     input.close();
 *     output.close();
 * } catch (IOException e) {
 *     System.out.println(&quot;An I/O error occurred.&quot;);
 * }
 * </pre>
 * 
 * @see DataInput
 * @see DataOutput
 * @see IOException
 */
public class UTFStringConverter
        implements Converter {

    /**
     * This instance can be used for getting a default instance of StringConverter. It is
     * similar to the <i>Singleton Design Pattern</i> (for further details see Creational
     * Patterns, Prototype in <i>Design Patterns: Elements of Reusable Object-Oriented
     * Software</i> by Erich Gamma, Richard Helm, Ralph Johnson, and John Vlissides)
     * except that there are no mechanisms to avoid the creation of other instances of
     * StringConverter.
     */
    public static final UTFStringConverter DEFAULT_INSTANCE = new UTFStringConverter();

    /**
     * Sole constructor. (For invocation by subclass constructors, typically implicit.)
     */
    public UTFStringConverter() {
    }

    /**
     * Reads in a string that has been encoded using a modified UTF-8 format and returns
     * the restored (<tt>String</tt>) object. <br>
     * This implementation ignores the specified object and returns a new <tt>String</tt>
     * object by calling the readUTF method of the specified data input. So it does not
     * matter when the specified object is <tt>null</tt>.
     * 
     * @param dataInput the stream to read a modified UTF-8 representation of a string
     *            from in order to return a <tt>String</tt> object. implementation it is
     *            ignored.
     * @return the read <tt>String</tt> object.
     * @throws IOException if I/O errors occur.
     */
    public Object read(DataInput dataInput) throws IOException {
        return dataInput.readUTF();
    }

    /**
     * Writes the specified <tt>String</tt> object to the specified data output using a
     * modified UTF-8 format. <br>
     * This implementation uses the writeUTF method of the specified data output in order
     * to write the specified object.
     * 
     * @param dataOutput the stream to write the modified UTF-8 representation of the
     *            specified <tt>String</tt> object to.
     * @param object the <tt>String</tt> object that modified UTF-8 representation should
     *            be written to the data output.
     * @throws IOException includes any I/O exceptions that may occur.
     */
    public void write(DataOutput dataOutput, Object object) throws IOException {
        dataOutput.writeUTF((String) object);
    }
}
