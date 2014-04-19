package codebase.io.converters;

import java.io.DataOutput;
import java.io.IOException;
import java.io.ObjectOutput;

/**
 * An {@link DataOutput} that write objects through a {@link Converter}.
 */
public class ObjectConverterOutput
        implements ObjectOutput {

    private final DataOutput dataOutput;
    private final Converter objectConverter;

    public ObjectConverterOutput(DataOutput dataOutput, Converter nodeConverter) {
        this.dataOutput = dataOutput;
        this.objectConverter = nodeConverter;
    }

    public DataOutput getDataOutput() {
        return dataOutput;
    }

    public Converter getConverter() {
        return objectConverter;
    }

    @Override
    public void writeBoolean(boolean v) throws IOException {
        dataOutput.writeBoolean(v);
    }

    @Override
    public void writeByte(int v) throws IOException {
        dataOutput.writeByte(v);

    }

    @Override
    public void writeShort(int v) throws IOException {
        dataOutput.writeShort(v);

    }

    @Override
    public void writeChar(int v) throws IOException {
        dataOutput.writeChar(v);

    }

    @Override
    public void writeInt(int v) throws IOException {
        dataOutput.writeInt(v);

    }

    @Override
    public void writeLong(long v) throws IOException {
        dataOutput.writeLong(v);

    }

    @Override
    public void writeFloat(float v) throws IOException {
        dataOutput.writeFloat(v);
    }

    @Override
    public void writeDouble(double v) throws IOException {
        dataOutput.writeDouble(v);
    }

    @Override
    public void writeBytes(String s) throws IOException {
        dataOutput.writeBytes(s);

    }

    @Override
    public void writeChars(String s) throws IOException {
        dataOutput.writeChars(s);
    }

    @Override
    public void writeUTF(String s) throws IOException {
        dataOutput.writeUTF(s);

    }

    @Override
    public void writeObject(Object obj) throws IOException {
        objectConverter.write(dataOutput, obj);
    }

    @Override
    public void write(int b) throws IOException {
        dataOutput.write(b);
    }

    @Override
    public void write(byte[] b) throws IOException {
        dataOutput.write(b);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        dataOutput.write(b, off, len);

    }

    @Override
    public void flush() throws IOException {
    }

    @Override
    public void close() throws IOException {
    }
}
