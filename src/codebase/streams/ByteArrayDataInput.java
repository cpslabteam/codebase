package codebase.streams;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;

import codebase.binary.Binary;

/**
 * A ByteArrayDataInput.
 * <p>
 * For performance (yes!) this class assumes that the caller will not go past EOF (or end
 * of bytes)
 */
public class ByteArrayDataInput
        implements DataInput {
    /**
     * The data buffer
     */
    byte[] data;

    /**
     * The next byte to return.
     */
    int pos = 0;

    public ByteArrayDataInput() {
    }

    public ByteArrayDataInput(byte[] data) {
        this.data = data;
    }

    public byte[] getBytes() {
        return data;
    }

    public void setBytes(byte[] d) {
        this.data = d;
        pos = 0;
    }

    /***************************************************************************
     * DataInputStream methods
     */

    public void readFully(byte b[]) throws IOException {
        readFully(b, 0, b.length);
    }

    public void readFully(byte b[], int off, int len) throws IOException {
        System.arraycopy(data, pos, b, off, len);
        pos += len;
    }

    public int skipBytes(int n) throws IOException {
        pos += n;
        return n;
    }

    public int read() {
        return ((int) data[pos++]) & Binary.INT_LOW_BYTE_MASK;
    }

    public final boolean readBoolean() throws IOException {
        final int b = read();
        return (b != 0);
    }

    public final byte readByte() throws IOException {
        final int b = read();
        return (byte) (b);
    }

    public final int readUnsignedByte() throws IOException {
        final int b = read();
        return b;
    }

    public final short readShort() throws IOException {
        final int b1 = read();
        final int b2 = read();
        return (short) ((b1 << 8) + (b2 << 0));
    }

    public final int readUnsignedShort() throws IOException {
        final int b1 = read();
        final int b2 = read();
        return (b1 << 8) + (b2 << 0);
    }

    public final char readChar() throws IOException {
        final int b1 = read();
        final int b2 = read();
        return (char) ((b1 << 8) + (b2 << 0));
    }

    public final int readInt() throws IOException {
        final int b1 = read();
        final int b2 = read();
        final int b3 = read();
        final int b4 = read();
        return ((b1 << 24) + (b2 << 16) + (b3 << 8) + (b4 << 0));
    }

    public final long readLong() throws IOException {
        final byte[] readBuffer = new byte[8];
        readFully(readBuffer, 0, 8);
        return (((long) readBuffer[0] << 56) + ((long) (readBuffer[1] & 255) << 48)
                + ((long) (readBuffer[2] & 255) << 40) + ((long) (readBuffer[3] & 255) << 32)
                + ((long) (readBuffer[4] & 255) << 24) + ((readBuffer[5] & 255) << 16)
                + ((readBuffer[6] & 255) << 8) + ((readBuffer[7] & 255) << 0));
    }

    public final float readFloat() throws IOException {
        return Float.intBitsToFloat(readInt());
    }

    public final double readDouble() throws IOException {
        return Double.longBitsToDouble(readLong());
    }

    private char lineBuffer[];

    public final String readLine() throws IOException {
        char buf[] = lineBuffer;

        if (buf == null) {
            buf = lineBuffer = new char[128];
        }

        int room = buf.length;
        int offset = 0;
        int c;

        loop: while (true) {
            switch (c = read()) {
                case -1:
                case '\n':
                    break loop;

                case '\r':
                    read();
                    break loop;

                default:
                    if (--room < 0) {
                        buf = new char[offset + 128];
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

    public final String readUTF() throws IOException {
        return DataInputStream.readUTF(this);
    }
}
