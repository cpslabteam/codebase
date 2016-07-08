package codebase.io.converters.binary;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import codebase.io.converters.AbstractFixedSizeConverter;

/**
 * Provides a converter that is able to read and write <tt>Long</tt> objects.
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
 *     // write a Long and a long value to the output stream
 *     new LongConverter.write(new DataOutputStream(output), new Long(1234567890l));
 * 
 *     // create a byte array input stream on the output stream
 *     ByteArrayInputStream input = new ByteArrayInputStream(output.toByteArray());
 * 
 *     // read a long value and a Long from the input stream
 *     final long l = new LongConverter.readLong(new DataInputStream(input));
 * 
 *     // print the value and the object
 *     System.out.println(l);
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
public class LongConverter extends
        AbstractFixedSizeConverter {

    /**
     * This field contains the number of bytes needed to serialize the <tt>long</tt> value
     * of a <tt>Long</tt> object. Because this size is predefined it must not be measured
     * each time.
     */
    public static final int SIZE = 8;

    /**
     * Sole constructor. (For invocation by subclass constructors, typically implicit.)
     */
    public LongConverter() {
        super(SIZE);
    }

    /**
     * Reads the <tt>long</tt> value for the specified (<tt>Long</tt>) object from the
     * specified data input and returns the restored object. <br>
     * This implementation ignores the specified object and returns a new <tt>Long</tt>
     * object. So it does not matter when the specified object is <tt>null</tt>.
     * 
     * @param dataInput the stream to read the <tt>long</tt> value from in order to return
     *            a <tt>Long</tt> object.
     * @return the read <tt>Long</tt> object.
     * @throws IOException if I/O errors occur.
     */
    public Object read(DataInput dataInput) throws IOException {
        return Long.valueOf(dataInput.readLong());
    }

    /**
     * Writes the <tt>long</tt> value of the specified <tt>Long</tt> object to the
     * specified data output. <br>
     * This implementation calls the writeLong method of the data output with the
     * <tt>long</tt> value of the object.
     * 
     * @param dataOutput the stream to write the <tt>long</tt> value of the specified
     *            <tt>Long</tt> object to.
     * @param object the <tt>Long</tt> object that <tt>long</tt> value should be written
     *            to the data output.
     * @throws IOException includes any I/O exceptions that may occur.
     */
    public void write(DataOutput dataOutput, Object object) throws IOException {
        dataOutput.writeLong(((Long) object).longValue());
    }
}
