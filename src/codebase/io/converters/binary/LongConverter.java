package codebase.io.converters.binary;


import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import codebase.io.converters.AbstractFixedSizeConverter;


/**
 * This class provides a converter that is able to read and write <tt>Long</tt>
 * objects. In addition to the read and write methods that read or write
 * <tt>Long</tt> objects this class contains readLong and writeLong methods
 * that convert the <tt>Long</tt> object after reading or before writing it to
 * its primitive <tt>long</tt> type.
 * <p>
 * Example usage (1).
 * 
 * <pre>
 * // catch IOExceptions
 * 
 * try {
 *     
 *     // create a byte array output stream
 *     
 *     ByteArrayOutputStream output = new ByteArrayOutputStream();
 *     
 *     // write a Long and a long value to the output stream
 *     
 *     LongConverter.DEFAULT_INSTANCE.write(new DataOutputStream(output),
 *         new Long(1234567890l));
 *     LongConverter.DEFAULT_INSTANCE.writeLong(new DataOutputStream(output),
 *         123456789012345l);
 *     
 *     // create a byte array input stream on the output stream
 *     
 *     ByteArrayInputStream input = new ByteArrayInputStream(output.toByteArray());
 *     
 *     // read a long value and a Long from the input stream
 *     
 *     long l1 = LongConverter.DEFAULT_INSTANCE
 *         .readLong(new DataInputStream(input));
 *     Long l2 = (Long) LongConverter.DEFAULT_INSTANCE.read(new DataInputStream(
 *         input));
 *     
 *     // print the value and the object
 *     
 *     System.out.println(l1);
 *     System.out.println(l2);
 *     
 *     // close the streams after use
 *     
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
public class LongConverter
        extends AbstractFixedSizeConverter {
    
    /**
     * This field contains the number of bytes needed to serialize the
     * <tt>long</tt> value of a <tt>Long</tt> object. Because this size is
     * predefined it must not be measured each time.
     */
    public static final int SIZE = 8;
    
    /**
     * Sole constructor. (For invocation by subclass constructors, typically
     * implicit.)
     */
    public LongConverter() {
        super(SIZE);
    }
    
    /**
     * Reads the <tt>long</tt> value for the specified (<tt>Long</tt>)
     * object from the specified data input and returns the restored object.
     * <br>
     * This implementation ignores the specified object and returns a new
     * <tt>Long</tt> object. So it does not matter when the specified object
     * is <tt>null</tt>.
     * 
     * @param dataInput the stream to read the <tt>long</tt> value from in
     *            order to return a <tt>Long</tt> object.
     * @return the read <tt>Long</tt> object.
     * @throws IOException if I/O errors occur.
     */
    public Object read(DataInput dataInput) throws IOException {
        return new Long(dataInput.readLong());
    }
    
    /**
     * Reads the <tt>long</tt> value from the specified data input and returns
     * it. <br>
     * This implementation uses the read method and converts the returned
     * <tt>Long</tt> object to its primitive <tt>long</tt> type.
     * 
     * @param dataInput the stream to read the <tt>long</tt> value from.
     * @return the read <tt>long</tt> value.
     * @throws IOException if I/O errors occur.
     */
    public long readLong(DataInput dataInput) throws IOException {
        return ((Long) read(dataInput)).longValue();
    }
    
    /**
     * Writes the <tt>long</tt> value of the specified <tt>Long</tt> object
     * to the specified data output. <br>
     * This implementation calls the writeLong method of the data output with
     * the <tt>long</tt> value of the object.
     * 
     * @param dataOutput the stream to write the <tt>long</tt> value of the
     *            specified <tt>Long</tt> object to.
     * @param object the <tt>Long</tt> object that <tt>long</tt> value
     *            should be written to the data output.
     * @throws IOException includes any I/O exceptions that may occur.
     */
    public void write(DataOutput dataOutput, Object object) throws IOException {
        dataOutput.writeLong(((Long) object).longValue());
    }
    
    /**
     * Writes the specified <tt>long</tt> value to the specified data output.
     * <br>
     * This implementation calls the write method with a <tt>Long</tt> object
     * wrapping the specified <tt>long</tt> value.
     * 
     * @param dataOutput the stream to write the specified <tt>long</tt> value
     *            to.
     * @param l the <tt>long</tt> value that should be written to the data
     *            output.
     * @throws IOException includes any I/O exceptions that may occur.
     */
    public void writeLong(DataOutput dataOutput, long l) throws IOException {
        write(dataOutput, new Long(l));
    }
    
    /**
     * The main method contains some examples how to use a LongConverter. It can
     * also be used to test the functionality of a LongConverter.
     * 
     * @param args array of <tt>String</tt> arguments. It can be used to
     *            submit parameters when the main method is called.
     */
    public static void main(String[] args) {
        
        // ////////////////////////////////////////////////////////////////
        // Usage example (1). //
        // ////////////////////////////////////////////////////////////////
        
        // catch IOExceptions
        try {
            // create a byte array output stream
            java.io.ByteArrayOutputStream output = new java.io.ByteArrayOutputStream();
            // write a Long and a long value to the output stream
            new LongConverter().write(new java.io.DataOutputStream(output),
                new Long(1234567890l));
            new LongConverter().writeLong(new java.io.DataOutputStream(output),
                123456789012345l);
            // create a byte array input stream on the output stream
            java.io.ByteArrayInputStream input = new java.io.ByteArrayInputStream(
                output.toByteArray());
            // read a long value and a Long from the input stream
            long l1 = new LongConverter().readLong(new java.io.DataInputStream(
                input));
            Long l2 = (Long) new LongConverter()
                .read(new java.io.DataInputStream(input));
            // print the value and the object
            System.out.println(l1);
            System.out.println(l2);
            // close the streams after use
            input.close();
            output.close();
        } catch (IOException ioe) {
            System.out.println("An I/O error occured.");
        }
        System.out.println();
    }
}
