package codebase.streams;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * A decorator stream.
 * <p>
 * This stream passes the method calls a decorated stream and doesn't change
 * which methods are being called.
 * <p>
 * In fact it works as alternative base class to
 * {@link java.io.FilterInputStream} to increase reusability. The class [@link
 * java.io.FilterInputStream} changes the methods being called. For example
 * such as <code>read(byte[])</code> became
 * <code>read(byte[], int, int)</code>.
 * <p>
 * The reference to the decorated class is stored in a protected superclass
 * member.
 */
public class DecoratorInputStream
        extends FilterInputStream {

    /**
     * Constructs a new input stream decorator.
     *
     * @param decorated the InputStream to delegate to
     */
    public DecoratorInputStream(final InputStream decorated) {
        super(decorated);

    }

    /**
     * @see java.io.InputStream#read()
     */
    public int read() throws IOException {
        return in.read();
    }

    /**
     * @see java.io.InputStream#read(byte[])
     */
    public int read(byte[] target) throws IOException {
        return in.read(target);
    }

    /**
     * @see java.io.InputStream#read(byte[], int, int)
     */
    public int read(final byte[] target, final int startPos, final int endPos)
            throws IOException {
        return in.read(target, startPos, endPos);
    }

    /**
     * @see java.io.InputStream#skip(long)
     */
    public long skip(final long byteCount) throws IOException {
        return in.skip(byteCount);
    }

    /**
     * @see java.io.InputStream#available()
     */
    public int available() throws IOException {
        return in.available();
    }

    /**
     * @see java.io.InputStream#close()
     */
    public void close() throws IOException {
        in.close();
    }

    /**
     * @see java.io.InputStream#mark(int)
     */
    public synchronized void mark(final int index) {
        in.mark(index);
    }

    /**
     * @see java.io.InputStream#reset()
     */
    public synchronized void reset() throws IOException {
        in.reset();
    }

    /**
     * @see java.io.InputStream#markSupported()
     */
    public boolean markSupported() {
        return in.markSupported();
    }
}
