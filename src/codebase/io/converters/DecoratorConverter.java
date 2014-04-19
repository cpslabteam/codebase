package codebase.io.converters;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Base class for converter decorators.
 * 
 * @author Paulo Carreira
 */
public abstract class DecoratorConverter
        implements Converter {
    
    /**
     * The converter that is being wrapped.
     */
    private Converter wrappedConverter;
    
    /**
     * Creates convertr decorator based that decorates the specified intance.
     * 
     * @param converter the instance to be decorated.
     */
    public DecoratorConverter(final Converter converter) {
        if (converter == null) {
            throw new IllegalArgumentException("Converter cannot be null");
        }
        
        wrappedConverter = converter;
    }
    
    /**
     * @return the converter specified in the constructor.
     */
    public final Converter getDecoratedInstance() {
        return wrappedConverter;
    }
    
    /**
     * @return the result of read operation of the decorated instance.
     */
    public Object read(final DataInput dataInput) throws IOException {
        return wrappedConverter.read(dataInput);
    }
    
    /**
     * Writes the supplied object through the decorated instance.
     */
    public void write(final DataOutput dataOutput, final Object object)
            throws IOException {
        wrappedConverter.write(dataOutput, object);
    }
    
    /**
     * @return a string containing <tt>ClassName[<i>d</i>]</tt> where <i>d</i>
     *         is a string representing the decorated instance.
     */
    public String toString() {
        final Class<?> c = this.getClass();
        return c.getSimpleName() + "[" + getDecoratedInstance().toString()
                + "]";
    }
}
