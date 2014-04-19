package codebase.io.converters.binary;


import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import codebase.io.converters.AbstractFixedSizeConverter;


/**
 * Provides a converter that is able to read and write <tt>Double</tt> objects.
 * <p>
 * Example usage:
 * 
 * <pre>
 * 
 * // catch IOExceptions
 * try {
 * 
 *     // create a byte array output stream
 *     ByteArrayOutputStream output = new ByteArrayOutputStream();
 * 
 *     // write a Double and a double value to the output stream
 *     new DoubleConverter.write(new DataOutputStream(output), new Double(2.7236512));
 * 
 *     // create a byte array input stream on the output stream
 *     ByteArrayInputStream input = new ByteArrayInputStream(output.toByteArray());
 * 
 *     // read a double value and a Double from the input stream
 *     final double d = (Double) new DoubleConverter.readDouble(new DataInputStream(input));
 * 
 *     // print the value and the object
 *     System.out.println(d);
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
public class DoubleConverter extends
        AbstractFixedSizeConverter {

    /**
     * This field contains the number of bytes needed to serialize the <tt>double</tt>
     * value of a <tt>Double</tt> object. Because this size is predefined it must not be
     * measured each time.
     */
    public static final int SIZE = 8;

    /**
     * Sole constructor. (For invocation by subclass constructors, typically implicit.)
     */
    public DoubleConverter() {
        super(SIZE);
    }

    /**
     * Reads the <tt>double</tt> value for the specified (<tt>Double</tt>) object from the
     * specified data input and returns the restored object. <br>
     * This implementation ignores the specified object and returns a new <tt>Double</tt>
     * object. So it does not matter when the specified object is <tt>null</tt>.
     * 
     * @param dataInput the stream to read the <tt>double</tt> value from in order to
     *            return a <tt>Double</tt> object.
     * @return the read <tt>Double</tt> object.
     * @throws IOException if I/O errors occur.
     */
    public Object read(DataInput dataInput) throws IOException {
        return new Double(dataInput.readDouble());
    }

    /**
     * Reads the <tt>double</tt> value from the specified data input and returns it. <br>
     * This implementation uses the read method and converts the returned <tt>Double</tt>
     * object to its primitive <tt>double</tt> type.
     * 
     * @param dataInput the stream to read the <tt>double</tt> value from.
     * @return the read <tt>double</tt> value.
     * @throws IOException if I/O errors occur.
     */
    public double readDouble(DataInput dataInput) throws IOException {
        return ((Double) read(dataInput)).doubleValue();
    }

    /**
     * Writes the <tt>double</tt> value of the specified <tt>Double</tt> object to the
     * specified data output. <br>
     * This implementation calls the writeDouble method of the data output with the
     * <tt>double</tt> value of the object.
     * 
     * @param dataOutput the stream to write the <tt>double</tt> value of the specified
     *            <tt>Double</tt> object to.
     * @param object the <tt>Double</tt> object that <tt>double</tt> value should be
     *            written to the data output.
     * @throws IOException includes any I/O exceptions that may occur.
     */
    public void write(DataOutput dataOutput, Object object) throws IOException {
        dataOutput.writeDouble(((Double) object).doubleValue());
    }

    /**
     * Writes the specified <tt>double</tt> value to the specified data output. <br>
     * This implementation calls the write method with a <tt>Double</tt> object wrapping
     * the specified <tt>double</tt> value.
     * 
     * @param dataOutput the stream to write the specified <tt>double</tt> value to.
     * @param d the <tt>double</tt> value that should be written to the data output.
     * @throws IOException includes any I/O exceptions that may occur.
     */
    public void writeDouble(DataOutput dataOutput, double d) throws IOException {
        write(dataOutput, new Double(d));
    }

    /**
     * The main method contains some examples how to use a DoubleConverter. It can also be
     * used to test the functionality of a DoubleConverter.
     * 
     * @param args array of <tt>String</tt> arguments. It can be used to submit parameters
     *            when the main method is called.
     */
    public static void main(String[] args) {

        // ////////////////////////////////////////////////////////////////
        // Usage example (1). //
        // ////////////////////////////////////////////////////////////////

        // catch IOExceptions
        try {
            // create a byte array output stream
            java.io.ByteArrayOutputStream output = new java.io.ByteArrayOutputStream();
            // write a Double and a double value to the output stream
            new DoubleConverter()
                    .write(new java.io.DataOutputStream(output), new Double(2.7236512));
            new DoubleConverter().writeDouble(new java.io.DataOutputStream(output), 6.123853d);
            // create a byte array input stream on the output stream
            java.io.ByteArrayInputStream input = new java.io.ByteArrayInputStream(
                    output.toByteArray());
            // read a double value and a Double from the input stream
            double d1 = new DoubleConverter().readDouble(new java.io.DataInputStream(input));
            Double d2 = (Double) new DoubleConverter().read(new java.io.DataInputStream(input));
            // print the value and the object
            System.out.println(d1);
            System.out.println(d2);
            // close the streams after use
            input.close();
            output.close();
        } catch (IOException ioe) {
            System.out.println("An I/O error occured.");
        }
        System.out.println();
    }
}
