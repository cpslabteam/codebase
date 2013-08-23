package codebase.streams;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * A decorator stream.
 * <p>
 * This stream passes the method calls a decorated stream and doesn't change which methods
 * are being called.
 * <p>
 * In fact it works as alternative base class to {@link java.io.FilterInputStream} to
 * increase reusability. The class [@link java.io.FilterInputStream} changes the methods
 * being called. For example such as <code>read(byte[])</code> became
 * <code>read(byte[], int, int)</code>.
 * <p>
 * The reference to the decorated class is stored in a protected superclass member.
 */
public class DecoratorInputStream extends
        FilterInputStream {

    /**
     * Constructs a new input stream decorator.
     * 
     * @param decorated the InputStream to delegate to
     */
    public DecoratorInputStream(final InputStream decorated) {
        super(decorated);

    }

    @Override
    public int read() throws IOException {
        return in.read();
    }

    @Override
    public int read(byte[] target) throws IOException {
        return in.read(target);
    }

    @Override
    public int read(final byte[] target, final int startPos, final int endPos) throws IOException {
        return in.read(target, startPos, endPos);
    }

    @Override
    public long skip(final long byteCount) throws IOException {
        return in.skip(byteCount);
    }

    @Override
    public int available() throws IOException {
        return in.available();
    }

    @Override
    public void close() throws IOException {
        in.close();
    }

    @Override
    public synchronized void mark(final int index) {
        in.mark(index);
    }

    @Override
    public synchronized void reset() throws IOException {
        in.reset();
    }

    @Override
    public boolean markSupported() {
        return in.markSupported();
    }
}
