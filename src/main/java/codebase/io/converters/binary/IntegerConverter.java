package codebase.io.converters.binary;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import codebase.io.converters.AbstractFixedSizeConverter;

/**
 * Provides a converter that is able to read and write <tt>Integer</tt> objects.
 * <p>
 * Example usage:
 * 
 * <pre>
 * // catch IOExceptions
 * try {
 *     // create a byte array output stream
 *     ByteArrayOutputStream output = new ByteArrayOutputStream();
 * 
 *     // write an Integer and an int value to the output stream
 * 
 *     new IntegerConverterwrite(new DataOutputStream(output), 42);
 * 
 *     // create a byte array input stream on the output stream
 *     ByteArrayInputStream input = new ByteArrayInputStream(output.toByteArray());
 * 
 *     // read an int value and an Integer from the input stream
 *     final int i = (Integer) new IntegerConverter.readInt(new DataInputStream(input));
 * 
 *     // print the value and the object
 *     System.out.println(i1);
 * 
 *     // close the streams after use
 *     input.close();
 *     output.close();
 * } catch (IOException e) {
 *     System.out.println(&quot;An I/O error occured.&quot;);
 * }
 * </pre>
 * 
 * @see DataInput
 * @see DataOutput
 * @see IOException
 */
public class IntegerConverter extends
        AbstractFixedSizeConverter {

    /**
     * This field contains the number of bytes needed to serialize the <tt>int</tt> value
     * of an <tt>Integer</tt> object. Because this size is predefined it must not be
     * measured each time.
     */
    public static final int SIZE = 4;

    /**
     * Sole constructor. (For invocation by subclass constructors, typically implicit.)
     */
    public IntegerConverter() {
        super(SIZE);
    }

    /**
     * Reads the <tt>int</tt> value for the specified (<tt>Integer</tt>) object from the
     * specified data input and returns the restored object. <br>
     * This implementation ignores the specified object and returns a new <tt>Integer</tt>
     * object. So it does not matter when the specified object is <tt>null</tt>.
     * 
     * @param dataInput the stream to read the <tt>int</tt> value from in order to return
     *            an <tt>Integer</tt> object.
     * @return the read <tt>Integer</tt> object.
     * @throws IOException if I/O errors occur.
     */
    public Object read(DataInput dataInput) throws IOException {
        return Integer.valueOf(dataInput.readInt());
    }

    /**
     * Writes the <tt>int</tt> value of the specified <tt>Integer</tt> object to the
     * specified data output. <br>
     * This implementation calls the writeInt method of the data output with the
     * <tt>int</tt> value of the object.
     * 
     * @param dataOutput the stream to write the <tt>int</tt> value of the specified
     *            <tt>Integer</tt> object to.
     * @param object the <tt>Integer</tt> object that <tt>int</tt> value should be written
     *            to the data output.
     * @throws IOException includes any I/O exceptions that may occur.
     */
    public void write(DataOutput dataOutput, Object object) throws IOException {
        dataOutput.writeInt(((Integer) object).intValue());
    }

}
