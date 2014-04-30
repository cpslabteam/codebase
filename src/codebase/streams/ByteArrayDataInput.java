package codebase.streams;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;

import codebase.BinaryUtil;

/**
 * A {@link DataInput} that reads data from a byte[].
 * <p>
 * Calls to <tt>read</tt> methods will be translated to array byte[] accesses.
 * <p>
 * For performance (yes!) this class assumes that the caller will not go past EOF (or end
 * of bytes)
 * 
 * @see ByteArrayDataOutput
 */
public class ByteArrayDataInput implements DataInput {
    private static final int LINE_BUFFER_INCREMENT_SIZE = 128;

    /**
     * The internal input data buffer.
     */
    private byte[] inputBuffer;

    /**
     * The next byte to return.
     */
    private int pos = 0;

    /**
     * Buffer used to hold the bytes read for a line in subsequent calls to
     * {@link #readLine()}.
     */
    private char[] lineBuffer;


    public ByteArrayDataInput() {
    }

    public ByteArrayDataInput(byte[] data) {
        this.inputBuffer = data.clone();
    }

    private int read() {
        assert 0 <= pos && pos < inputBuffer.length : "Position is valid";

        return ((int) inputBuffer[pos++]) & BinaryUtil.INT_LOW_BYTE_MASK;
    }

    /**
     * Gets a copy of the internal input buffer.
     * 
     * @return the internal input buffer
     */
    public byte[] getBytes() {
        return inputBuffer.clone();
    }

    @Override
    public final boolean readBoolean() throws IOException {
        if (pos >= inputBuffer.length - 1) {
            throw new EOFException("Not enough bytes left to read a boolean");
        }

        final int b = read();
        return (b != 0);
    }

    @Override
    public final byte readByte() throws IOException {
        if (pos > inputBuffer.length - 1) {
            throw new EOFException("Not enough bytes left to read a byte");
        }

        final int b = read();
        return (byte) (b);
    }

    @Override
    public final char readChar() throws IOException {
        if (pos > inputBuffer.length - BinaryUtil.SIZE_OF_CHAR) {
            throw new EOFException("Not enough bytes left to read a byte");
        }

        final int b1 = read();
        final int b2 = read();
        return (char) ((b1 << BinaryUtil.BIT_SIZE_OF_BYTE) + (b2 << 0));
    }

    @Override
    public final double readDouble() throws IOException {
        if (pos > inputBuffer.length - BinaryUtil.SIZE_OF_DOUBLE) {
            throw new EOFException("Not enough bytes left to read a double");
        }

        return Double.longBitsToDouble(readLong());
    }

    @Override
    public final float readFloat() throws IOException {
        if (pos > inputBuffer.length - BinaryUtil.SIZE_OF_FLOAT) {
            throw new EOFException("Not enough bytes left to read a float");
        }

        return Float.intBitsToFloat(readInt());
    }

    @Override
    public void readFully(byte[] b) throws IOException {
        readFully(b, 0, b.length);
    }

    @Override
    public void readFully(byte[] b, int off, int len) throws IOException {
        System.arraycopy(inputBuffer, pos, b, off, len);
        pos += len;
    }

    @Override
    public final int readInt() throws IOException {
        if (pos > inputBuffer.length - BinaryUtil.SIZE_OF_INT) {
            throw new EOFException("Not enough bytes left to read a float");
        }

        final int b1 = read();
        final int b2 = read();
        final int b3 = read();
        final int b4 = read();
        return ((b1 << BinaryUtil.BIT_SIZE_OF_THREE_BYTES)
                + (b2 << BinaryUtil.BIT_SIZE_OF_TWO_BYTES) + (b3 << BinaryUtil.BIT_SIZE_OF_BYTE)
                + (b4 << 0));
    }

    @Override
    public final String readLine() throws IOException {
        if (lineBuffer == null) {
            lineBuffer = new char[LINE_BUFFER_INCREMENT_SIZE];
        }

        char[] buf = lineBuffer;
        int room = buf.length;
        int offset = 0;
        int c;

        loop: while (true) {
            c = read();
            switch (c) {
                case -1:
                case '\n':
                    break loop;

                case '\r':
                    read();
                    break loop;

                default:
                    if (--room < 0) {
                        buf = new char[offset + LINE_BUFFER_INCREMENT_SIZE];
                        room = buf.length - offset - 1;
                        System.arraycopy(lineBuffer, 0, buf, 0, offset);
                        lineBuffer = buf;
                    }
                    buf[offset++] = (char) c;
                    break;
            }
        }
        if ((c == -1) && (offset == 0)) {
            return null;
        }
        return String.copyValueOf(buf, 0, offset);
    }

    @Override
    public final long readLong() throws IOException {
        final byte[] readBuffer = new byte[BinaryUtil.SIZE_OF_LONG];
        readFully(readBuffer, 0, BinaryUtil.SIZE_OF_LONG);
        //CHECKSTYLE:OFF - uses byte position and number of bit rotations
        return (((long) readBuffer[0] << 56)
                + ((long) (readBuffer[1] & BinaryUtil.INT_LOW_BYTE_MASK) << 48)
                + ((long) (readBuffer[2] & BinaryUtil.INT_LOW_BYTE_MASK) << 40)
                + ((long) (readBuffer[3] & BinaryUtil.INT_LOW_BYTE_MASK) << 32)
                + ((long) (readBuffer[4] & BinaryUtil.INT_LOW_BYTE_MASK) << 24)
                + ((long) (readBuffer[5] & BinaryUtil.INT_LOW_BYTE_MASK) << 16)
                + ((long) (readBuffer[6] & BinaryUtil.INT_LOW_BYTE_MASK) << 8)
                + ((long) (readBuffer[7] & BinaryUtil.INT_LOW_BYTE_MASK) << 0));
        //CHECKSTYLE:ON
    }

    @Override
    public final short readShort() throws IOException {
        final int b1 = read();
        final int b2 = read();
        return (short) ((b1 << BinaryUtil.BIT_SIZE_OF_BYTE) + (b2 << 0));
    }

    @Override
    public final int readUnsignedByte() throws IOException {
        final int b = read();
        return b;
    }

    @Override
    public final int readUnsignedShort() throws IOException {
        final int b1 = read();
        final int b2 = read();
        return (b1 << BinaryUtil.BIT_SIZE_OF_BYTE) + (b2 << 0);
    }

    @Override
    public final String readUTF() throws IOException {
        return DataInputStream.readUTF(this);
    }

    @Override
    public int skipBytes(int n) throws IOException {
        pos += n;
        return n;
    }
}
