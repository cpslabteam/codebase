package codebase.io.converters.binary;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import codebase.io.converters.AbstractFixedSizeConverter;


/**
 * Provides a converter that is able to read and write <tt>Boolean</tt> objects.
 * <p>
 * Example usage:
 * 
 * <pre>
 * // catch IOExceptions
 * 
 * try {
 *     // create a byte array output stream
 *     ByteArrayOutputStream output = new ByteArrayOutputStream();
 * 
 *     // write a Boolean and a boolean value to the output stream
 *     new BooleanConverter.write(new DataOutputStream(output), Boolean.TRUE);
 * 
 *     // create a byte array input stream on the output stream
 *     ByteArrayInputStream input = new ByteArrayInputStream(output.toByteArray());
 * 
 *     // read a boolean value and a Boolean from the input stream
 *     boolean b = (Boolean) new BooleanConverter.readBoolean(new DataInputStream(
 *             input));
 * 
 *     // print the value and the object
 *     System.out.println(b2);
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
public class BooleanConverter extends
        AbstractFixedSizeConverter {

    /**
     * This field contains the number of bytes needed to serialize the <tt>boolean</tt>
     * value of a <tt>Boolean</tt> object. Because this size is predefined it must not be
     * measured each time.
     */
    public static final int SIZE = 1;

    /**
     * Sole constructor. (For invocation by subclass constructors, typically implicit.)
     */
    public BooleanConverter() {
        super(SIZE);
    }

    /**
     * Reads the <tt>boolean</tt> value for the specified (<tt>Boolean</tt>) object from
     * the specified data input and returns the restored object. <br>
     * This implementation ignores the specified object and returns a new <tt>Boolean</tt>
     * object. So it does not matter when the specified object is <tt>null</tt>.
     * 
     * @param dataInput the stream to read the <tt>boolean</tt> value from in order to
     *            return a <tt>Boolean</tt> object.
     * @return the read <tt>Boolean</tt> object.
     * @throws IOException if I/O errors occur.
     */
    public Object read(DataInput dataInput) throws IOException {
        if (dataInput.readBoolean())
            return Boolean.TRUE;
        else
            return Boolean.FALSE;
    }

    /**
     * Writes the <tt>boolean</tt> value of the specified <tt>Boolean</tt> object to the
     * specified data output. <br>
     * This implementation calls the writeBoolean method of the data output with the
     * <tt>boolean</tt> value of the object.
     * 
     * @param dataOutput the stream to write the <tt>boolean</tt> value of the specified
     *            <tt>Boolean</tt> object to.
     * @param object the <tt>Boolean</tt> object that <tt>boolean</tt> value should be
     *            written to the data output.
     * @throws IOException includes any I/O exceptions that may occur.
     */
    public void write(DataOutput dataOutput, Object object) throws IOException {
        dataOutput.writeBoolean(((Boolean) object).booleanValue());
    }
}
