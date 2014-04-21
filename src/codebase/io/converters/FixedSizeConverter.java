package codebase.io.converters;


/**
 * Interface of converters that have a fixed size.
 * <p>
 * Each object must be serialized to the same number of bytes. In other words it
 * is expected that read and write operations allways transfer the same amount
 * of bytes.
 * <p>
 * The {@link #getSize()} method is implemented returning a predefined size
 * specified in the constructor.
 * 
 * @author Paulo Carreira
 */
public interface FixedSizeConverter
        extends Converter {
    
    /**
     * Gets the number of bytes used for read or write operations.
     * <p>
     * Each object must be serialized to the same number of bytes.
     * 
     * @return the number of bytes.
     */
    int getSize();
}
