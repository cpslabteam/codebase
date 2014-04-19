package codebase.io.converters.binary;



import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import codebase.io.converters.AbstractFixedSizeConverter;


/**
 * This class provides a converter that is able to read and write
 * <tt>Character</tt> objects. In addition to the read and write methods that
 * read or write <tt>Character</tt> objects this class contains readChar and
 * writeChar methods that convert the <tt>Character</tt> object after reading
 * or before writing it to its primitive <tt>char</tt> type.
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
 *     // write a Character and a char value to the output stream
 *     
 *     CharacterTranslator.DEFAULT_INSTANCE.write(new DataOutputStream(output),
 *         new Character('C'));
 *     CharacterTranslator.DEFAULT_INSTANCE.writeChar(new DataOutputStream(output),
 *         'p');
 *     
 *     // create a byte array input stream on the output stream
 *     
 *     ByteArrayInputStream input = new ByteArrayInputStream(output.toByteArray());
 *     
 *     // read a char value and a Character from the input stream
 *     
 *     char c1 = CharacterTranslator.DEFAULT_INSTANCE.readChar(new DataInputStream(
 *         input));
 *     Character c2 = (Character) CharacterTranslator.DEFAULT_INSTANCE
 *         .read(new DataInputStream(input));
 *     
 *     // print the value and the object
 *     
 *     System.out.println(c1);
 *     System.out.println(c2);
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
public class CharacterConverter
        extends AbstractFixedSizeConverter {
    
    /**
     * This field contains the number of bytes needed to serialize the
     * <tt>char</tt> value of a <tt>Character</tt> object. Because this size
     * is predefined it must not be measured each time.
     */
    public static final int SIZE = 2;
    
    /**
     * Sole constructor. (For invocation by subclass constructors, typically
     * implicit.)
     */
    public CharacterConverter() {
        super(SIZE);
    }
    
    /**
     * Reads the <tt>char</tt> value for the specified (<tt>Character</tt>)
     * object from the specified data input and returns the restored object.
     * <br>
     * This implementation ignores the specified object and returns a new
     * <tt>Character</tt> object. So it does not matter when the specified
     * object is <tt>null</tt>.
     * 
     * @param dataInput the stream to read the <tt>char</tt> value from in
     *            order to return a <tt>Character</tt> object.
     * @return the read <tt>Character</tt> object.
     * @throws IOException if I/O errors occur.
     */
    public Object read(DataInput dataInput) throws IOException {
        return new Character(dataInput.readChar());
    }
    
    /**
     * Reads the <tt>char</tt> value from the specified data input and returns
     * it. <br>
     * This implementation uses the read method and converts the returned
     * <tt>Character</tt> object to its primitive <tt>char</tt> type.
     * 
     * @param dataInput the stream to read the <tt>char</tt> value from.
     * @return the read <tt>char</tt> value.
     * @throws IOException if I/O errors occur.
     */
    public char readChar(DataInput dataInput) throws IOException {
        return ((Character) read(dataInput)).charValue();
    }
    
    /**
     * Writes the <tt>char</tt> value of the specified <tt>Character</tt>
     * object to the specified data output. <br>
     * This implementation calls the write method of the data output with the
     * <tt>char</tt> value of the object.
     * 
     * @param dataOutput the stream to write the <tt>char</tt> value of the
     *            specified <tt>Character</tt> object to.
     * @param object the <tt>Character</tt> object that <tt>char</tt> value
     *            should be written to the data output.
     * @throws IOException includes any I/O exceptions that may occur.
     */
    public void write(DataOutput dataOutput, Object object) throws IOException {
        dataOutput.writeChar(((Character) object).charValue());
    }
    
    /**
     * Writes the specified <tt>char</tt> value to the specified data output.
     * <br>
     * This implementation calls the write method with a <tt>Character</tt>
     * object wrapping the specified <tt>char</tt> value.
     * 
     * @param dataOutput the stream to write the specified <tt>char</tt> value
     *            to.
     * @param c the <tt>char</tt> value that should be written to the data
     *            output.
     * @throws IOException includes any I/O exceptions that may occur.
     */
    public void writeChar(DataOutput dataOutput, char c) throws IOException {
        write(dataOutput, new Character(c));
    }
    
    /**
     * The main method contains some examples how to use a CharacterTranslator.
     * It can also be used to test the functionality of a CharacterTranslator.
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
            // write a Character and a char value to the output stream
            new CharacterConverter().write(
                new java.io.DataOutputStream(output), new Character('C'));
            new CharacterConverter().writeChar(
                new java.io.DataOutputStream(output), 'p');
            // create a byte array input stream on the output stream
            java.io.ByteArrayInputStream input = new java.io.ByteArrayInputStream(
                output.toByteArray());
            // read a char value and a Character from the input stream
            char c1 =  new CharacterConverter()
                .readChar(new java.io.DataInputStream(input));
            Character c2 = (Character) new CharacterConverter()
                .read(new java.io.DataInputStream(input));
            // print the value and the object
            System.out.println(c1);
            System.out.println(c2);
            // close the streams after use
            input.close();
            output.close();
        } catch (IOException ioe) {
            System.out.println("An I/O error occured.");
        }
        System.out.println();
    }
}
