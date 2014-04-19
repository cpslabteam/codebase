package codebase.io.converters;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.List;

/**
 * Converts an object with multiple convertable fields into a byte
 * representation and vice versa.
 */
public class CompositeConverter
        implements Converter {
    
    /**
     * The converters that are wrapped.
     */
    protected Converter[] componentConverters;
    
    /**
     * The interface for assembling and disassembling objects.
     * <p>
     * To construct {@link CompositeConverter} it is necessary to implement this
     * interface.
     * 
     * @author Paulo Carreira
     */
    public interface ConverterHelper {
        /**
         * A factory method that is used for creating the object.
         * <p>
         * This method will be invoked when the
         * {@link CompositeConverter#read(DataInput)} method is called to build
         * the object from the components.
         * 
         * @param components the array of components to build up the object
         * @return an object that contains the components read.
         */
        Object createFromComponents(final Object[] components);
        
        /**
         * A factory method that converts an object into the components to be
         * written.
         * <p>
         * This will be called when
         * {@link CompositeConverter#write(DataOutput, Object)} is called to
         * generate the objects to be written.
         * 
         * @param object the object to be converted to an array of componenents.
         * @return the components of the input object as an object array
         */
        Object[] toObjectArray(final Object object);
    }
    
    /**
     * The assembly helper to be used.
     */
    protected final ConverterHelper assemblyHelper;
    
    /**
     * Constructs a new Converter that wraps the specified array of converters
     * with the default helper.
     * 
     * @param converters the converter to be wrapped.
     */
    public CompositeConverter(final Converter[] converters) {
        this(converters, DEFAULT_HELPER);
    }
    
    /**
     * Constructs a new Converter that wraps the specified converter and uses
     * the specified assembly helper.
     * 
     * @param converters the converter to be wrapped.
     * @param helper the assembly helper object to be used.
     * @throws IllegalArgumentException if the array of convertes is
     *             <code>null</code> or empty
     * @throws IllegalArgumentException if any of the converters is
     *             <code>null</code>
     * @throws IllegalArgumentException if the helper is <code>null</code>
     */
    public CompositeConverter(final Converter[] converters,
            final ConverterHelper helper) {
        if (converters == null) {
            throw new IllegalArgumentException("Converters cannot be null");
        }
        
        if (converters.length == 0) {
            throw new IllegalArgumentException(
                "Converters should have at least one element");
        }
        
        for (int i = 0; i < converters.length; i++) {
            if (converters[i] == null) {
                throw new IllegalArgumentException("Converter at position " + i
                        + " cannot be null");
            }
        }
        
        if (helper == null) {
            throw new IllegalArgumentException("Helper cannot be null");
        }
        
        this.componentConverters = converters;
        this.assemblyHelper = helper;
    }
    
    /**
     * The default helper that works with arrays of arrays.
     */
    public static final ConverterHelper DEFAULT_HELPER = new ConverterHelper() {
        public Object createFromComponents(final Object[] components) {
            return components;
        }
        
        public Object[] toObjectArray(final Object object) {
            final Object[] result = (Object[]) object;
            return result;
        }
    };
    
    /**
     * Adds two arrays of converters.
     * 
     * @param left the left array
     * @param right the right array
     * @return an array of converters resulting from <tt>left.right</tt>
     */
    public static Converter[] add(final Converter[] left,
            final Converter[] right) {
        assert left != null;
        assert right != null;
        
        final int l = left.length + right.length;
        
        final Converter[] convs = new Converter[l];
        
        int pos = 0;
        for (Converter c : left) {
            convs[pos] = c;
            pos += 1;
        }
        
        for (Converter c : right) {
            convs[pos] = c;
            pos += 1;
        }
        
        return convs;
    }
    
    /**
     * Adds a converter to an array of converters to another.
     * 
     * @param left the left array
     * @param converter the right array
     * @return an array of converters resulting from <tt>left.right</tt>
     */
    public static Converter[] add(final Converter[] left,
            final Converter converter) {
        if (left == null) {
            throw new IllegalArgumentException(
                "The converter array cannot be null");
        }
        
        if (converter == null) {
            throw new IllegalArgumentException(
                "The converter added cannot be null");
        }
        
        final int l = left.length + 1;
        
        final Converter[] convs = new Converter[l];
        
        int pos = 0;
        for (Converter c : left) {
            convs[pos] = c;
            pos += 1;
        }
        
        convs[pos] = converter;
        pos += 1;
        
        return convs;
    }
    
    /**
     * Adds the constituents of a MultiConverter to an array of converters.
     * 
     * @param left the left array
     * @param right the right array
     * @return an array of converters resulting from <tt>left.right</tt>
     */
    public static Converter[] merge(final Converter[] left,
            final CompositeConverter right) {
        assert left != null;
        assert right != null;
        
        return add(left, right.componentConverters);
    }
    
    /**
     * Merges the converts of a list of {@link CompositeConverter} into a
     * converter array.
     * <p>
     * The new converters of each object are concatenated in sequence. The new
     * object are read using the specified helper.
     * 
     * @param converters a list of converters
     * @return an array of converters
     * @throws IllegalArgumentException if any of the elements is
     *             <code>null</code>
     */
    public static Converter[] merge(final List<Converter> converters) {
        Converter[] convs = new Converter[0];
        for (Converter c : converters) {
            if (c == null) {
                throw new IllegalArgumentException("Converter cannot be null");
            }
            
            if (c instanceof CompositeConverter) {
                final CompositeConverter mc = (CompositeConverter) c;
                convs = merge(convs, mc);
            } else {
                convs = add(convs, c);
            }
        }
        
        return convs;
    }
    
    /**
     * Reads the state (the attributes) for the specified object from the
     * specified data input and returns the restored object. <br>
     * This implementation calls the read method of the wrapped converter. When
     * the specified object is <tt>null</tt> it is initialized by invoking the
     * function (factory method).
     * 
     * @param dataInput the stream to read data from in order to restore the
     *            object.
     * @return the restored object.
     * @throws IOException if I/O errors occur.
     */
    public final Object read(final DataInput dataInput) throws IOException {
        final Object[] components = new Object[componentConverters.length];
        for (int i = 0; i < componentConverters.length; i++) {
            components[i] = componentConverters[i].read(dataInput);
        }
        return assemblyHelper.createFromComponents(components);
    }
    
    /**
     * Writes the state (the attributes) of the specified object to the
     * specified data output. <br>
     * This implementation calls the write method of the wrapped converter.
     * 
     * @param dataOutput the stream to write the state (the attributes) of the
     *            object to.
     * @param object the object whose state (attributes) should be written to
     *            the data output.
     * @throws IOException includes any I/O exceptions that may occur.
     */
    public final void write(final DataOutput dataOutput, final Object object)
            throws IOException {
        final Object[] objects = (Object[]) assemblyHelper
            .toObjectArray(object);
        for (int i = 0; i < componentConverters.length; i++) {
            componentConverters[i].write(dataOutput, objects[i]);
        }
    }
    
    /**
     * @return the array of component converters being aggregated by this
     *         multi-coverter.
     */
    public final Converter[] getConverters() {
        return componentConverters;
    }
    
    /**
     * @return a string with a list of the string representations of the child
     *         converters.
     */
    public String toString() {
        final StringBuffer b = new StringBuffer();
        
        final Class<?> c = this.getClass();
        b.append(c.getSimpleName() + "[");
        
        for (int i = 0; i < componentConverters.length; i++) {
            if (i == 0) {
                b.append(componentConverters[i].toString());
            } else {
                b.append(", " + componentConverters[i].toString());
            }
        }
        
        b.append("]");
        return b.toString();
    }
}
