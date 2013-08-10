package codebase.streams;

import java.io.DataOutput;
import java.io.OutputStream;
import java.io.IOException;

/**
 * Adapts {@link java.io.DataOutput}into a {@link java.io.OutputStream}.
 * <p>
 * This class constructs an stream over a random access device that implements data output
 * interface like a raw device or a random access file.
 */
public final class DataOutputOutputStream extends
        OutputStream {
    /**
     * DataOuput which is wrapped.
     */
    protected final DataOutput dataOutput;

    /**
     * Constructs a new Wrapper, which wraps a DataOutput to an OutputStream.
     * 
     * @param output DataOutput to be wrapped.
     */
    public DataOutputOutputStream(final DataOutput output) {
        this.dataOutput = output;
    }

    /**
     * Writes a byte to the data output.
     * 
     * @param i byte value to be written.
     * @throws IOException if the underlying data output object throws an
     *             {@link IOException}.
     * @see java.io.OutputStream#write(int)
     */
    public void write(final int i) throws IOException {
        dataOutput.write(i);

    }

    /**
     * Writes a buffer segment into the data output.
     * 
     * @param buffer the byte array to be treated
     * @param offset the starting offset of the segment in the array
     * @param len the length of the segment
     * @throws IOException if the underlying data output object throws an
     *             {@link IOException}.
     * @see java.io.OutputStream#write(byte[], int, int)
     */
    public void write(final byte[] buffer, final int offset, final int len) throws IOException {
        dataOutput.write(buffer, offset, len);
    }

    /**
     * Writes a buffer into the data output.
     * 
     * @param buffer the byte buffer
     * @throws IOException if the underlying data output object throws an
     *             {@link IOException}
     * @see java.io.OutputStream#write(byte[])
     */
    public void write(final byte[] buffer) throws IOException {
        dataOutput.write(buffer);
    }

}
