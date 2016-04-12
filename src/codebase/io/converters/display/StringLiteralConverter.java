package codebase.io.converters.display;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.EOFException;
import java.io.IOException;

import codebase.io.converters.Converter;

/**
 * A converter for strings with variable size.
 * <p>
 * Strings are encoded using double commas.
 */
public class StringLiteralConverter
        implements Converter {
    /**
     * Maximum read length to avoid reading forever.
     */
    private static final int DEFAULT_MAX_READ_LENGTH = 16 * 1024;

    /**
     * Parses a string object.
     * <p>
     * Skips all bytes until the first commas and then reads the a string between commas.
     * A corner occurs when an {@link EOFException} occurs before a second comma is found.
     * In this case the string is read until EOF is returned.
     * <p>
     * The maximum size of a string is 16K.
     * 
     * @param dataInput the data input to read the data input from
     * @throws IOException if an exception occurs while reading the string
     * @return a string read from the data input
     */
    @Override
    public Object read(DataInput dataInput) throws IOException {
        assert dataInput != null;
        int len = 0;

        /*
         * Fast forward to the first " character
         */
        int ch;
        do {
            ch = dataInput.readByte();
            len++;
        } while (ch != '\"' && len <= DEFAULT_MAX_READ_LENGTH);

        if (len >= DEFAULT_MAX_READ_LENGTH) {
            throw new IOException(
                    "Max read length for a string literal exceeded");
        }

        assert ch == '\"' : "String litteral initiator found";

        /*
         * Add the string characters until we see " again. 
         */
        final StringBuilder sb = new StringBuilder();
        sb.append((char) ch);

        boolean sawBackslash = false;
        do {
            try {
                ch = dataInput.readByte();
            } catch (EOFException e) {
                /*
                 * We treat EOF as the end of buffer
                 */
                break;
            } catch (IOException e) {
                throw e;
            }

            if (sawBackslash) {
                if (ch == '\"') {
                    sb.append((char) ch);
                } else {
                    sb.append('\\');
                    sb.append((char) ch);
                }
                sawBackslash = false;
            } else {
                if (ch == '\\') {
                    sawBackslash = true;
                } else if (ch == '\"') {
                    sb.append((char) ch);
                    break;
                } else {
                    sb.append((char) ch);
                }
            }

            len++;
        } while (len <= DEFAULT_MAX_READ_LENGTH);

        if (len >= DEFAULT_MAX_READ_LENGTH) {
            throw new IOException(
                    "Max read length for a string literal exceeded.");
        }

        return codebase.StringUtil.unstringify(sb.toString());
    }

    /**
     * Writes a string to the output surrounded by double commas.
     * 
     * @param dataOutput the data output object to write to
     * @param object the string object to write
     * @throws IOException if an exception occurs while reading the string
     */
    @Override
    public void write(DataOutput dataOutput, Object object) throws IOException {
        assert dataOutput != null;
        assert object != null;

        final String s = codebase.StringUtil.stringify(object.toString());
        dataOutput.write(s.getBytes());
    }
}
