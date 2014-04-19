package codebase.io.converters.binary;


import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import codebase.io.converters.AbstractFixedSizeConverter;


/**
 * This class provides a converter that is able to read and write
 * <tt>Boolean</tt> objects. In addition to the read and write methods that
 * read or write <tt>Boolean</tt> objects this class contains readBoolean and
 * writeBoolean methods that convert the <tt>Boolean</tt> object after reading
 * or before writing it to its primitive <tt>boolean</tt> type.
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
 *     // write a Boolean and a boolean value to the output stream
 *     
 *     BooleanConverter.DEFAULT_INSTANCE.write(new DataOutputStream(output),
 *         new Boolean(true));
 *     BooleanConverter.DEFAULT_INSTANCE.writeBoolean(
 *         new DataOutputStream(output), false);
 *     
 *     // create a byte array input stream on the output stream
 *     
 *     ByteArrayInputStream input = new ByteArrayInputStream(output.toByteArray());
 *     
 *     // read a boolean value and a Boolean from the input stream
 *     
 *     boolean b1 = BooleanConverter.DEFAULT_INSTANCE
 *         .readBoolean(new DataInputStream(input));
 *     Boolean b2 = (Boolean) BooleanConverter.DEFAULT_INSTANCE
 *         .read(new DataInputStream(input));
 *     
 *     // print the value and the object
 *     
 *     System.out.println(b1);
 *     System.out.println(b2);
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
public class BooleanConverter
        extends AbstractFixedSizeConverter {
    
    /**
     * This field contains the number of bytes needed to serialize the
     * <tt>boolean</tt> value of a <tt>Boolean</tt> object. Because this
     * size is predefined it must not be measured each time.
     */
    public static final int SIZE = 1;
    
    /**
     * Sole constructor. (For invocation by subclass constructors, typically
     * implicit.)
     */
    public BooleanConverter() {
        super(SIZE);
    }
    
    /**
     * Reads the <tt>boolean</tt> value for the specified (<tt>Boolean</tt>)
     * object from the specified data input and returns the restored object.
     * <br>
     * This implementation ignores the specified object and returns a new
     * <tt>Boolean</tt> object. So it does not matter when the specified
     * object is <tt>null</tt>.
     * 
     * @param dataInput the stream to read the <tt>boolean</tt> value from in
     *            order to return a <tt>Boolean</tt> object.
     * @param object the (<tt>Boolean</tt>) object to be restored. In this
     *            implementation it is ignored.
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
     * Reads the <tt>boolean</tt> value from the specified data input and
     * returns it. <br>
     * This implementation uses the read method and converts the returned
     * <tt>Boolean</tt> object to its primitive <tt>boolean</tt> type.
     * 
     * @param dataInput the stream to read the <tt>boolean</tt> value from.
     * @return the read <tt>boolean</tt> value.
     * @throws IOException if I/O errors occur.
     */
    public boolean readBoolean(DataInput dataInput) throws IOException {
        return ((Boolean) read(dataInput)).booleanValue();
    }
    
    /**
     * Writes the <tt>boolean</tt> value of the specified <tt>Boolean</tt>
     * object to the specified data output. <br>
     * This implementation calls the writeBoolean method of the data output with
     * the <tt>boolean</tt> value of the object.
     * 
     * @param dataOutput the stream to write the <tt>boolean</tt> value of the
     *            specified <tt>Boolean</tt> object to.
     * @param object the <tt>Boolean</tt> object that <tt>boolean</tt> value
     *            should be written to the data output.
     * @throws IOException includes any I/O exceptions that may occur.
     */
    public void write(DataOutput dataOutput, Object object) throws IOException {
        dataOutput.writeBoolean(((Boolean) object).booleanValue());
    }
    
    /**
     * Writes the specified <tt>boolean</tt> value to the specified data
     * output. <br>
     * This implementation calls the write method with a <tt>Boolean</tt>
     * object wrapping the specified <tt>boolean</tt> value.
     * 
     * @param dataOutput the stream to write the specified <tt>boolean</tt>
     *            value to.
     * @param b the <tt>boolean</tt> value that should be written to the data
     *            output.
     * @throws IOException includes any I/O exceptions that may occur.
     */
    public void writeBoolean(DataOutput dataOutput, boolean b)
            throws IOException {
        write(dataOutput, new Boolean(b));
    }
    
    /**
     * The main method contains some examples how to use a BooleanConverter. It
     * can also be used to test the functionality of a BooleanConverter.
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
            // write a Boolean and a boolean value to the output stream
            new BooleanConverter().write(
                new java.io.DataOutputStream(output), new Boolean(true));
            new BooleanConverter().writeBoolean(
                new java.io.DataOutputStream(output), false);
            // create a byte array input stream on the output stream
            java.io.ByteArrayInputStream input = new java.io.ByteArrayInputStream(
                output.toByteArray());
            // read a boolean value and a Boolean from the input stream
            boolean b1 = new BooleanConverter()
                .readBoolean(new java.io.DataInputStream(input));
            Boolean b2 = (Boolean) new BooleanConverter()
                .read(new java.io.DataInputStream(input));
            // print the value and the object
            System.out.println(b1);
            System.out.println(b2);
            // close the streams after use
            input.close();
            output.close();
        } catch (IOException ioe) {
            System.out.println("An I/O error occured.");
        }
        System.out.println();
    }
}
