package codebase.streams;

import java.io.DataOutput;
import java.io.EOFException;
import java.io.IOException;
import java.io.UTFDataFormatException;

import codebase.Binary;

/**
 * A {@link DataOutput} that writes to a byte array.
 * <p>
 * Data will be written into a byte[] passed as a parameter to the constructor. This
 * byte[] should be large enough to hold the bytes written by subsequent calls to
 * <code>write</code> methods.
 * <p>
 * Every <code>write</code> will throw an {@link EOFException} if an attempt is made to
 * write past the size of the base byte[] buffer.
 * 
 * @see ByteArrayDataOutput
 */
public class ByteArrayDataOutput
        implements DataOutput {

    /**
     * Maximum size in bytes of an UTF message.
     */
    private static final int UTF_BYTES_MESSAGE_SIZE_LIMIT = 65535;

    /**
     * Writes a string to the specified DataOutput using Java modified UTF-8 encoding in a
     * machine-independent manner.
     * <p>
     * First, two bytes are written to out as if by the <code>writeShort</code> method
     * giving the number of bytes to follow. This value is the number of bytes actually
     * written out, not the length of the string. Following the length, each character of
     * the string is output, in sequence, using the modified UTF-8 encoding for the
     * character. If no exception is thrown, the counter <code>written</code> is
     * incremented by the total number of bytes written to the output stream. This will be
     * at least two plus the length of <code>str</code>, and at most two plus thrice the
     * length of <code>str</code>.
     * 
     * @param str a string to be written.
     * @param out destination to write to
     * @return The number of bytes written
     * @throws IOException if an I/O error occurs.
     * @throws UTFDataFormatException if str does not fit the on the maximum size of an
     *             UTF message
     */
    static int writeUTF(String str, DataOutput out) throws IOException {
        int strlen = str.length();
        int utflen = 0;
        char[] charr = new char[strlen];
        int c, count = 0;

        str.getChars(0, strlen, charr, 0);

        // CHECKSTYLE:OFF
        /*
         * uses the byte limits for UTF to determine
         * the length in bytes of the utf message
         */
        for (int i = 0; i < strlen; i++) {
            c = charr[i];
            if ((c >= 0x0001) && (c <= 0x007F)) {
                utflen++;
            } else if (c > 0x07FF) {
                utflen += 3;
            } else {
                utflen += 2;
            }
        }
        // CHECKSTYLE:ON

        if (utflen > UTF_BYTES_MESSAGE_SIZE_LIMIT)
            throw new UTFDataFormatException();

        // CHECKSTYLE:OFF - uses bitwise rotate and masking operations
        final byte[] bytearr = new byte[utflen + 2];
        bytearr[count++] = (byte) ((utflen >>> 8) & 0xFF);
        bytearr[count++] = (byte) ((utflen >>> 0) & 0xFF);
        for (int i = 0; i < strlen; i++) {
            c = charr[i];
            if ((c >= 0x0001) && (c <= 0x007F)) {
                bytearr[count++] = (byte) c;
            } else if (c > 0x07FF) {
                bytearr[count++] = (byte) (0xE0 | ((c >> 12) & 0x0F));
                bytearr[count++] = (byte) (0x80 | ((c >> 6) & 0x3F));
                bytearr[count++] = (byte) (0x80 | ((c >> 0) & 0x3F));
            } else {
                bytearr[count++] = (byte) (0xC0 | ((c >> 6) & 0x1F));
                bytearr[count++] = (byte) (0x80 | ((c >> 0) & 0x3F));
            }
        }
        // CHECKSTYLE:ON

        out.write(bytearr);
        return utflen + 2;
    }

    /**
     * The buffer buffer to write to.
     */
    private byte[] outputBuffer;

    /**
     * Position of the next byte.
     */
    private int pos = 0;

    public ByteArrayDataOutput(final byte[] data) {
        this.outputBuffer = data.clone();
    }

    @Override
    public void write(byte[] b) throws IOException {
        write(b, 0, b.length);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        if ((pos + len) >= outputBuffer.length) {
            throw new EOFException("Ouput buffer full");
        }

        System.arraycopy(b, off, outputBuffer, pos, len);
        pos += b.length;
    }

    @Override
    public void write(int b) throws IOException {
        if (pos >= outputBuffer.length) {
            throw new EOFException("Ouput buffer full");
        }

        outputBuffer[pos++] = (byte) b;
    }

    @Override
    public final void writeBoolean(boolean v) throws IOException {
        write(v ? 1 : 0);
    }

    @Override
    public final void writeByte(int v) throws IOException {
        if (pos >= outputBuffer.length) {
            throw new EOFException("Ouput buffer full");
        }

        outputBuffer[pos++] = (byte) v;
    }

    /**
     * Writes out the string to the underlying output stream as a sequence of bytes. Each
     * character in the string is written out, in sequence, by discarding its high eight
     * bits. If no exception is thrown, the counter <code>written</code> is incremented by
     * the length of <code>s</code>.
     * 
     * @param s a string of bytes to be written.
     * @throws java.io.IOException if an I/O error occurs.
     * @see java.io.FilterOutputStream#out
     */
    @Override
    public final void writeBytes(String s) throws IOException {
        int len = s.length();
        for (int i = 0; i < len; i++) {
            write((byte) s.charAt(i));
        }
    }

    @Override
    public final void writeChar(int v) throws IOException {
        if ((pos + Binary.SIZE_OF_CHAR) >= outputBuffer.length) {
            throw new EOFException("Ouput buffer full");
        }

        // CHECKSTYLE:OFF - Performs bitwise operations
        outputBuffer[pos++] = (byte) ((v >>> 8) & 0xFF);
        outputBuffer[pos++] = (byte) ((v >>> 0) & 0xFF);
        // CHECKSTYLE:ON
    }

    /**
     * Writes a string to the underlying output stream as a sequence of characters. Each
     * character is written to the data output stream as if by the <code>writeChar</code>
     * method. If no exception is thrown, the counter <code>written</code> is incremented
     * by twice the length of <code>s</code>.
     * 
     * @param s a <code>String</code> value to be written.
     * @throws java.io.IOException if an I/O error occurs.
     * @see java.io.DataOutputStream#writeChar(int)
     * @see java.io.FilterOutputStream#out
     */
    @Override
    public final void writeChars(String s) throws IOException {
        int len = s.length();
        for (int i = 0; i < len; i++) {
            int v = s.charAt(i);
            // CHECKSTYLE:OFF - Performs bitwise operations
            write((v >>> 8) & 0xFF);
            write((v >>> 0) & 0xFF);
            // CHECKSTYLE:ON
        }
    }

    /**
     * Converts the double argument to a <code>long</code> using the
     * <code>doubleToLongBits</code> method in class <code>Double</code>, and then writes
     * that <code>long</code> value to the underlying output stream as an 8-byte quantity,
     * high byte first. If no exception is thrown, the counter <code>written</code> is
     * incremented by <code>8</code>.
     * 
     * @param v a <code>double</code> value to be written.
     * @throws java.io.IOException if an I/O error occurs.
     * @see java.io.FilterOutputStream#out
     * @see java.lang.Double#doubleToLongBits(double)
     */
    @Override
    public final void writeDouble(double v) throws IOException {
        writeLong(Double.doubleToLongBits(v));
    }

    /**
     * Converts the float argument to an <code>int</code> using the
     * <code>floatToIntBits</code> method in class <code>Float</code>, and then writes
     * that <code>int</code> value to the underlying output stream as a 4-byte quantity,
     * high byte first. If no exception is thrown, the counter <code>written</code> is
     * incremented by <code>4</code>.
     * 
     * @param v a <code>float</code> value to be written.
     * @throws java.io.IOException if an I/O error occurs.
     * @see java.io.FilterOutputStream#out
     * @see java.lang.Float#floatToIntBits(float)
     */
    @Override
    public final void writeFloat(float v) throws IOException {
        writeInt(Float.floatToIntBits(v));
    }

    @Override
    public final void writeInt(int v) throws IOException {
        if ((pos + Binary.SIZE_OF_INT) >= outputBuffer.length) {
            throw new EOFException("Ouput buffer full");
        }

        // CHECKSTYLE:OFF - Performs bitwise operations
        outputBuffer[pos++] = (byte) ((v >>> 24) & 0xFF);
        outputBuffer[pos++] = (byte) ((v >>> 16) & 0xFF);
        outputBuffer[pos++] = (byte) ((v >>> 8) & 0xFF);
        outputBuffer[pos++] = (byte) ((v >>> 0) & 0xFF);
        // CHECKSTYLE:ON
    }

    @Override
    public final void writeLong(long v) throws IOException {
        final byte[] writeBuffer = new byte[Binary.SIZE_OF_LONG];

        // CHECKSTYLE:OFF - Performs bitwise operations
        writeBuffer[0] = (byte) (v >>> 56);
        writeBuffer[1] = (byte) (v >>> 48);
        writeBuffer[2] = (byte) (v >>> 40);
        writeBuffer[3] = (byte) (v >>> 32);
        writeBuffer[4] = (byte) (v >>> 24);
        writeBuffer[5] = (byte) (v >>> 16);
        writeBuffer[6] = (byte) (v >>> 8);
        writeBuffer[7] = (byte) (v >>> 0);
        write(writeBuffer, 0, 8);
        // CHECKSTYLE:ON
    }

    @Override
    public final void writeShort(int v) throws IOException {
        if ((pos + 2) >= outputBuffer.length) {
            throw new EOFException("Ouput buffer full");
        }

        // CHECKSTYLE:OFF - performs bitwise operations
        outputBuffer[pos++] = (byte) ((v >>> 8) & 0xFF);
        outputBuffer[pos++] = (byte) ((v >>> 0) & 0xFF);
        // CHECKSTYLE:ON
    }

    /**
     * Writes a string to the underlying output stream using Java modified UTF-8 encoding
     * in a machine-independent manner.
     * <p>
     * First, two bytes are written to the output stream as if by the
     * <code>writeShort</code> method giving the number of bytes to follow. This value is
     * the number of bytes actually written out, not the length of the string. Following
     * the length, each character of the string is output, in sequence, using the modified
     * UTF-8 encoding for the character. If no exception is thrown, the counter
     * <code>written</code> is incremented by the total number of bytes written to the
     * output stream. This will be at least two plus the length of <code>str</code>, and
     * at most two plus thrice the length of <code>str</code>.
     * 
     * @param str a string to be written.
     * @throws java.io.IOException if an I/O error occurs.
     */
    @Override
    public final void writeUTF(String str) throws IOException {
        writeUTF(str, this);
    }
}
