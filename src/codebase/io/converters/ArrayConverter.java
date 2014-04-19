package codebase.io.converters;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * A converter that applies another converter multiple times.
 */
public class ArrayConverter
        implements Converter {

    /**
     * The converter that is being wrapped.
     */
    private Converter wrappedConverter;

    /**
     * The number of elements to be read and written.
     */
    private final int occurrences;

    /**
     * Constructs a new Converter that wraps the specified array of converters with the
     * default helper.
     * 
     * @param converter the converter to be wrapped.
     * @param times the number of elements in the array
     */
    public ArrayConverter(final Converter converter, final int times) {
        wrappedConverter = converter;

        if (times < 1) {
            throw new IllegalArgumentException(
                    "An array converter must apply to have at least one element");
        }

        occurrences = times;
    }

    /**
     * Reads an array of object using the wrapped converter.
     * 
     * @param dataInput the stream to read data from in order to restore the object.
     * @return the restored object.
     * @throws IOException if I/O errors occur.
     */
    public final Object read(final DataInput dataInput) throws IOException {
        final Object[] array = new Object[occurrences];

        for (int i = 0; i < occurrences; i++) {
            array[i] = wrappedConverter.read(dataInput);
        }
        return array;
    }

    /**
     * Writes an array of objects using the wrapped converter.
     * 
     * @param dataOutput the stream to write the state (the attributes) of the object to.
     * @param object the object whose state (attributes) should be written to the data
     *            output.
     * @throws IOException includes any I/O exceptions that may occur.
     */
    public final void write(final DataOutput dataOutput, final Object object) throws IOException {
        final Object[] objects = (Object[]) object;

        for (int i = 0; i < occurrences; i++) {
            wrappedConverter.write(dataOutput, objects[i]);
        }
    }

    /**
     * @return a string containing <tt>ClassName[<i>o</i>:<i>d</i>]</tt> where <i>o</i> is
     *         the number of occurrences and <i>d</i> is a string representing the
     *         decorated instance.
     */
    public final String toString() {
        final Class<?> c = this.getClass();
        return c.getSimpleName() + "[" + occurrences + "*" + wrappedConverter.toString() + "]";
    }
}
