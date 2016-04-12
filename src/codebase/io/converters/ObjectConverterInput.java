package codebase.io.converters;

import java.io.DataInput;
import java.io.IOException;
import java.io.ObjectInput;

/**
 * An {@link ObjectInput} that write objects through a {@link Converter}.
 */
public class ObjectConverterInput
        implements ObjectInput {

    private final DataInput dataInput;
    private final Converter objectConverter;

    public ObjectConverterInput(DataInput dataInput, Converter nodeConverter) {
        this.objectConverter = nodeConverter;
        this.dataInput = dataInput;
    }

    public Converter getConverter() {
        return objectConverter;
    }

    public DataInput getDataInput() {
        return dataInput;
    }

    @Override
    public void readFully(byte[] b) throws IOException {
        dataInput.readFully(b);
    }

    @Override
    public void readFully(byte[] b, int off, int len) throws IOException {
        dataInput.readFully(b, off, len);
    }

    @Override
    public int skipBytes(int n) throws IOException {
        return dataInput.skipBytes(n);
    }

    @Override
    public boolean readBoolean() throws IOException {
        return dataInput.readBoolean();
    }

    @Override
    public byte readByte() throws IOException {
        return dataInput.readByte();
    }

    @Override
    public int readUnsignedByte() throws IOException {
        return dataInput.readUnsignedByte();
    }

    @Override
    public short readShort() throws IOException {
        return dataInput.readShort();
    }

    @Override
    public int readUnsignedShort() throws IOException {
        return dataInput.readUnsignedShort();
    }

    @Override
    public char readChar() throws IOException {
        return dataInput.readChar();
    }

    @Override
    public int readInt() throws IOException {
        return dataInput.readInt();
    }

    @Override
    public long readLong() throws IOException {
        return dataInput.readLong();
    }

    @Override
    public float readFloat() throws IOException {
        return dataInput.readFloat();
    }

    @Override
    public double readDouble() throws IOException {
        return dataInput.readDouble();
    }

    @Override
    public String readLine() throws IOException {
        return dataInput.readLine();
    }

    @Override
    public String readUTF() throws IOException {
        return dataInput.readUTF();
    }

    @Override
    public Object readObject() throws IOException {
        return objectConverter.read(dataInput);
    }

    @Override
    public int read() throws IOException {
        return dataInput.readByte();
    }

    @Override
    public int read(byte[] b) throws IOException {
        return read(b, 0, b.length);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        dataInput.readFully(b, off, len);
        return len;
    }

    @Override
    public long skip(long n) throws IOException {
        if (n > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Value must fit in an integer.");
        }
        return dataInput.skipBytes((int) n);
    }

    @Override
    public int available() throws IOException {
        return 0;
    }

    @Override
    public void close() throws IOException {
        // Closing an object converter does nothing.
    }
}
