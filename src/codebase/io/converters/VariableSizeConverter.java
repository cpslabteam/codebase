package codebase.io.converters;


/**
 * The base class of converters that have a variable size.
 * <p>
 * This class defined a protected <code>size</code> variable that should be
 * updated by descending classes. Either when reading or writing.
 * 
 * @author Paulo Carreira
 */
public abstract class VariableSizeConverter
        implements Converter {
    
    /**
     * The size of a converted objects to be updtaed by descending classes.
     */
    protected int size;
    
    /**
     * Constructs a varaible size converter.
     */
    public VariableSizeConverter() {
    }
    
    /**
     * Returns the number of bytes used on the <b>last</b> read or write
     * operation.
     * 
     * @return the number of bytes.
     */
    public final int getSize() {
        return size;
    }
    
}
