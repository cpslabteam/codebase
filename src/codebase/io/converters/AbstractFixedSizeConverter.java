package codebase.io.converters;


/**
 * The base class of converters that have a fixed size.
 * <p>
 * Each object must be serialized to the same number of bytes. In other words it
 * is expected that read and write operations always transfer the same amount of
 * bytes.
 * <p>
 * The {@link #getSize()} method is implemented returning a predefined size
 * specified in the constructor.
 * 
 * @author Paulo Carreira
 */
public abstract class AbstractFixedSizeConverter
		implements FixedSizeConverter {

	/**
	 * The size of a converted objects.
	 */
	private final int converterSize;

	/**
	 * Constructs a fixed-size converter.
	 * 
	 * @param s The size of the converted objects
	 */
	public AbstractFixedSizeConverter(final int s) {
		this.converterSize = s;
	}

	/**
	 * Returns the number of bytes used for read or write operation.
	 * <p>
	 * Each object must be serialized to the same number of bytes.
	 * 
	 * @return the number of bytes.
	 */
	public final int getSize() {
		return converterSize;
	}

	 /**
     * Creates a string representation of the converter.
     * <p>
     * Every converter should implement this interface to simplify debugging.
     * 
     * @return a string representation of the converter with the simplifed class
     *         name and other parameters of the converter.
     */
    
	/**
	 * @return a string containing <tt>ClassName(<i>n</i>)</tt> where <i>n</i>
	 *         is the size.
	 */
	public final String toString() {
		final Class<?> c = this.getClass();
		return c.getSimpleName() + "(" + getSize() + ")";
	}
}
