package codebase.streams;

import java.io.IOException;
import java.io.InputStream;

/**
 * A special input stream that returns returns zero bytes.
 * <p>
 * Used mainly for testing purposes.
 */
public class NullInputStream
        extends InputStream {

    /**
     * @return <code>-1</code>
     * @see java.io.InputStream#read()
     * @throws IOException Declared for compatibility with the ancestor class.
     */
    public final int read() throws IOException {
        return -1;
    }

    /**
     * @param b ignored
     * @param off ignored
     * @param len ignored
     * @return <code>0</code>
     * @see java.io.InputStream#read(byte[], int, int)
     * @throws IOException Declared for compatibility with the ancestor class.
     */
    public final int read(final byte[] b, final int off, final int len)
            throws IOException {
        return 0;
    }

    /**
     * @param b ignored
     * @return <code>0</code>
     * @see java.io.InputStream#read(byte[])
     * @throws IOException Declared for compatibility with the ancestor class.
     */
    public final int read(final byte[] b) throws IOException {
        return 0;
    }
}