package codebase.io.converters.binary;


import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import codebase.io.converters.AbstractFixedSizeConverter;


/**
 * This class provides a converter that is able to read and write <tt>Float</tt> objects.
 * In addition to the read and write methods that read or write <tt>Float</tt> objects
 * this class contains readFloat and writeFloat methods that convert the <tt>Float</tt>
 * object after reading or before writing it to its primitive <tt>float</tt> type.
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
 *     // write a Float and a float value to the output stream
 *     new FloatConverter.write(new DataOutputStream(output), new Float(2.7236512));
 * 
 *     // create a byte array input stream on the output stream
 *     ByteArrayInputStream input = new ByteArrayInputStream(output.toByteArray());
 * 
 *     // read a float value and a Float from the input stream
 *     final float f = (Float) new FloatConverter.readFloat(new DataInputStream(input));
 * 
 *     // print the value and the object
 *     System.out.println(f);
 * 
 *     // close the streams after use
 *     input.close();
 *     output.close();
 * } catch (IOException ioe) {
 *     System.out.println(&quot;An I/O error occured.&quot;);
 * }
 * </pre>
 * 
 * @see DataInput
 * @see DataOutput
 * @see IOException
 */
public class FloatConverter extends
        AbstractFixedSizeConverter {

    /**
     * This field contains the number of bytes needed to serialize the <tt>float</tt>
     * value of a <tt>Float</tt> object. Because this size is predefined it must not be
     * measured each time.
     */
    public static final int SIZE = 4;

    /**
     * Sole constructor. (For invocation by subclass constructors, typically implicit.)
     */
    public FloatConverter() {
        super(SIZE);
    }

    /**
     * Reads the <tt>float</tt> value for the specified (<tt>Float</tt>) object from the
     * specified data input and returns the restored object. <br>
     * This implementation ignores the specified object and returns a new <tt>Float</tt>
     * object. So it does not matter when the specified object is <tt>null</tt>.
     * 
     * @param dataInput the stream to read the <tt>float</tt> value from in order to
     *            return a <tt>Float</tt> object.
     * @return the read <tt>Float</tt> object.
     * @throws IOException if I/O errors occur.
     */
    public Object read(DataInput dataInput) throws IOException {
        return new Float(dataInput.readFloat());
    }

    /**
     * Writes the <tt>float</tt> value of the specified <tt>Float</tt> object to the
     * specified data output. <br>
     * This implementation calls the writeFloat method of the data output with the
     * <tt>float</tt> value of the object.
     * 
     * @param dataOutput the stream to write the <tt>float</tt> value of the specified
     *            <tt>Float</tt> object to.
     * @param object the <tt>Float</tt> object that <tt>float</tt> value should be written
     *            to the data output.
     * @throws IOException includes any I/O exceptions that may occur.
     */
    public void write(DataOutput dataOutput, Object object) throws IOException {
        dataOutput.writeFloat(((Float) object).floatValue());
    }
}
