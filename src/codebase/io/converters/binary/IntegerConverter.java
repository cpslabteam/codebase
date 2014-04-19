package codebase.io.converters.binary;


import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import codebase.io.converters.AbstractFixedSizeConverter;

/**
 * This class provides a converter that is able to read and write
 * <tt>Integer</tt> objects. In addition to the read and write methods that
 * read or write <tt>Integer</tt> objects this class contains readInt and
 * writeInt methods that convert the <tt>Integer</tt> object after reading or
 * before writing it to its primitive <tt>int</tt> type.
 * <p>
 * Example usage (1).
 * 
 * <pre>
 * // catch IOExceptions
 * 
 * try {
 * 
 * 	// create a byte array output stream
 * 
 * 	ByteArrayOutputStream output = new ByteArrayOutputStream();
 * 
 * 	// write an Integer and an int value to the output stream
 * 
 * 	IntegerConverter.DEFAULT_INSTANCE
 * 			.write(new DataOutputStream(output), new Integer(42));
 * 	IntegerConverter.DEFAULT_INSTANCE.writeInt(new DataOutputStream(output), 666);
 * 
 * 	// create a byte array input stream on the output stream
 * 
 * 	ByteArrayInputStream input = new ByteArrayInputStream(output.toByteArray());
 * 
 * 	// read an int value and an Integer from the input stream
 * 
 * 	int i1 = IntegerConverter.DEFAULT_INSTANCE.readInt(new DataInputStream(input));
 * 	Integer i2 = (Integer) IntegerConverter.DEFAULT_INSTANCE.read(new DataInputStream(
 * 			input));
 * 
 * 	// print the value and the object
 * 
 * 	System.out.println(i1);
 * 	System.out.println(i2);
 * 
 * 	// close the streams after use
 * 
 * 	input.close();
 * 	output.close();
 * }
 * catch (IOException ioe) {
 * 	System.out.println(&quot;An I/O error occured.&quot;);
 * }
 * </pre>
 * 
 * @see DataInput
 * @see DataOutput
 * @see IOException
 */
public class IntegerConverter
		extends AbstractFixedSizeConverter {

	/**
	 * This field contains the number of bytes needed to serialize the
	 * <tt>int</tt> value of an <tt>Integer</tt> object. Because this size
	 * is predefined it must not be measured each time.
	 */
	public static final int SIZE = 4;

	/**
	 * Sole constructor. (For invocation by subclass constructors, typically
	 * implicit.)
	 */
	public IntegerConverter() {
		super(SIZE);
	}

	/**
	 * Reads the <tt>int</tt> value for the specified (<tt>Integer</tt>)
	 * object from the specified data input and returns the restored object.
	 * <br>
	 * This implementation ignores the specified object and returns a new
	 * <tt>Integer</tt> object. So it does not matter when the specified
	 * object is <tt>null</tt>.
	 * 
	 * @param dataInput the stream to read the <tt>int</tt> value from in
	 *            order to return an <tt>Integer</tt> object.
	 * @return the read <tt>Integer</tt> object.
	 * @throws IOException if I/O errors occur.
	 */
	public Object read(DataInput dataInput) throws IOException {
		return new Integer(dataInput.readInt());
	}

	/**
	 * Reads the <tt>int</tt> value from the specified data input and returns
	 * it. <br>
	 * This implementation uses the read method and converts the returned
	 * <tt>Integer</tt> object to its primitive <tt>int</tt> type.
	 * 
	 * @param dataInput the stream to read the <tt>int</tt> value from.
	 * @return the read <tt>int</tt> value.
	 * @throws IOException if I/O errors occur.
	 */
	public int readInt(DataInput dataInput) throws IOException {
		return ((Integer) read(dataInput)).intValue();
	}

	/**
	 * Writes the <tt>int</tt> value of the specified <tt>Integer</tt>
	 * object to the specified data output. <br>
	 * This implementation calls the writeInt method of the data output with the
	 * <tt>int</tt> value of the object.
	 * 
	 * @param dataOutput the stream to write the <tt>int</tt> value of the
	 *            specified <tt>Integer</tt> object to.
	 * @param object the <tt>Integer</tt> object that <tt>int</tt> value
	 *            should be written to the data output.
	 * @throws IOException includes any I/O exceptions that may occur.
	 */
	public void write(DataOutput dataOutput, Object object) throws IOException {
		dataOutput.writeInt(((Integer) object).intValue());
	}

	/**
	 * Writes the specified <tt>int</tt> value to the specified data output.
	 * <br>
	 * This implementation calls the write method with an <tt>Integer</tt>
	 * object wrapping the specified <tt>int</tt> value.
	 * 
	 * @param dataOutput the stream to write the specified <tt>int</tt> value
	 *            to.
	 * @param i the <tt>int</tt> value that should be written to the data
	 *            output.
	 * @throws IOException includes any I/O exceptions that may occur.
	 */
	public void writeInt(DataOutput dataOutput, int i) throws IOException {
		write(dataOutput, new Integer(i));
	}

	/**
	 * The main method contains some examples how to use an IntegerConverter. It
	 * can also be used to test the functionality of an IntegerConverter.
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
			// write an Integer and an int value to the output stream
			new IntegerConverter().write(new java.io.DataOutputStream(output),
					new Integer(42));
			new IntegerConverter().writeInt(new java.io.DataOutputStream(output), 666);
			// create a byte array input stream on the output stream
			java.io.ByteArrayInputStream input = new java.io.ByteArrayInputStream(output
					.toByteArray());
			// read an int value and an Integer from the input stream
			int i1 = new IntegerConverter().readInt(new java.io.DataInputStream(input));
			Integer i2 = (Integer) new IntegerConverter()
					.read(new java.io.DataInputStream(input));
			// print the value and the object
			System.out.println(i1);
			System.out.println(i2);
			// close the streams after use
			input.close();
			output.close();
		}
		catch (IOException ioe) {
			System.out.println("An I/O error occured.");
		}
		System.out.println();
	}
}
