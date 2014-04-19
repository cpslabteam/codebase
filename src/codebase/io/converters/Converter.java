package codebase.io.converters;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * The interface of a Converter.
 * <p>
 * A converter is an object that reads and writes other objects using specific
 * formats. The converter objects are used to read and write differents formats
 * of files and message streams both in binary and display formats.
 * <p>
 * Writing an object is called <i>serialization</i>; reading an object is
 * called <i>deserialization</i>.
 * <p>
 * This class can be used for serializing objects that cannot implement the
 * {@link Convertable} interface.
 * <p>
 * It prevents the one important drawback of the default SDK serialization
 * mechanism: the overhead of writing additional data (like the class id) to the
 * output stream.
 * <p>
 * The {@link #write(DataOutput, Object)} and {@link #read(DataInput)} methods
 * are used by the Converter to read and write the Objects in specific formats
 * (other that the SDK serailization format).
 * <p>
 * As a principle, the <code>read</code> method must read the values in the
 * same sequence and with the same types as were written by <code>write</code>.
 * Reading a written object must succeed.
 * <p>
 * Caution should be taked when implementing <code>read</code> and
 * <code>write</code> methods for complex objects in order to avoid prevent
 * unbounded recursive calls.
 * 
 * @see java.io.DataInput
 * @see java.io.DataOutput
 * @see java.io.IOException
 */
public interface Converter {
    
    /**
     * Updates or reads a new object from the input.
     * <p>
     * It reads the state (the attributes) for the specified object from the
     * specified data input and returns the restored object. The state of the
     * specified object will be lost.
     * <p>
     * If the object reference is <code>null</code>, a new object should be
     * created.
     * 
     * @param dataInput the input to read data from
     * @return the reference to the object given or a new created object
     * @throws IOException if I/O errors occur
     */
    Object read(final DataInput dataInput) throws IOException;
    
    /**
     * Writes the specified object to the output.
     * 
     * @param dataOutput the output to write the object to
     * @param object the object to be written, cannot be <code>null</code>
     * @throws IOException if and I/O exceptions occurs
     */
    void write(final DataOutput dataOutput, final Object object)
            throws IOException;
    
    /**
     * Creates a string representation of the converter.
     * <p>
     * Every converter should impelement this interface to simplify debuging.
     * 
     * @return a string representaiton of the converter with the simplifed class
     *         name and other parameters of the converter.
     */
    String toString();
}
