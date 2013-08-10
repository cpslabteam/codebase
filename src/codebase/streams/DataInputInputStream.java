/*
 * Created on 14/Fev/2006
 */
package codebase.streams;

import java.io.DataInput;
import java.io.EOFException;
import java.io.InputStream;
import java.io.IOException;

/**
 * Adapts {@link java.io.DataOutput}into a {@link java.io.OutputStream}.
 * <p>
 * This class constructs an stream over a random access device that implements
 * data output interface like a raw device or a random access file.
 * <p>
 * The methods {@link java.io.InputStream#read(byte[])} and
 * {@link java.io.InputStream#read(byte[], int, int)} are not overriden. These
 * methods require that the actual number of bytes read is returned to the
 * caller. However, the methods {@link DataInput#readFully(byte[])} provide no
 * way of knowing how many bytes were in fact read.
 * <p>
 * For this reason the default implementation provided by the class
 * {@link java.io.InputStream} using {@link java.io.InputStream#read()} is
 * used.
 */
public final class DataInputInputStream
        extends InputStream {
    /**
     * The wrapped DataInput.
     */
    private final DataInput dataInput;

    /**
     * Creates a DataInputInputStream from a DataInput.
     *
     * @param input The DataInput to be wrapped.
     */
    public DataInputInputStream(final DataInput input) {
        this.dataInput = input;
    }

    /**
     * Checks how many bytes are available before blocking.
     *
     * @return 0
     * @see java.io.InputStream#available()
     */
    public int available() {
        return 0;
    }

    /**
     * Closes the input stream.
     * <p>
     * Does nothing. The data input does not allow close.
     *
     * @see java.io.InputStream#close()
     */
    public void close() {
    }

    /**
     * Marks the input stream. Does nothing. Marking is not supported.
     *
     * @param readlimit the number of byte to be remembered.
     * @see java.io.InputStream#mark(int)
     */
    public synchronized void mark(final int readlimit) {
    }

    /**
     * Checks is marking is supported.
     *
     * @return <code>false</code>.
     * @see java.io.InputStream#markSupported()
     */
    public boolean markSupported() {
        return false;
    }

    /**
     * Reads a byte from the DataInput.
     *
     * @return the byte read or <code>-1</code> if an {@link EOFException}
     *         ocurred while reading the data input.
     * @throws IOException if the underlying data input object throws an
     *             {@link IOException}.
     */
    public int read() throws IOException {
        try {
            return dataInput.readUnsignedByte();
        } catch (EOFException e) {
            return -1;
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * Skips bytes from the data input.
     *
     * @param n the number of bytes to be skipped.
     * @return the number of bytes actually skipped.
     * @throws IOException if attempting to skip more than
     *             {@link Integer#MAX_VALUE} bytes.
     * @see java.io.InputStream#skip(long)
     */
    public long skip(final long n) throws IOException {
        if (n > Integer.MAX_VALUE) {
            throw new IOException("Cannot skip as much as " + n + " bytes.");
        } else {
            return dataInput.skipBytes((int) n);
        }
    }

}
