package codebase.io.converters.display;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.EOFException;
import java.io.IOException;

import codebase.Binary;
import codebase.io.converters.Converter;

/**
 * A converter for integers in display format.
 * <p>
 * This converter consumes the input until the first character that is not a sign or
 * digit. This last character will be used as delimiter and is consumed. Note that this is
 * a technical limitation. Since we do not know the number of digits up front, any
 * character that is not a digit will serve as delimiter.
 */
public class IntegerDisplayConverter
        implements Converter {

    /**
     * The maximum amount of digits that held by a Integer.
     */
    public static final int INTEGER_MAX_DIGITS = 10;

    /**
     * Parses an integer object.
     * <p>
     * The first character must a sign or a digit until a character that is not a digit or
     * {@link EOFException} is found.
     * 
     * @param dataInput the data input to read the data input from
     * @throws IOException if an exception occurs while reading the string
     * @return a string read from the data input
     */
    @Override
    public Object read(DataInput dataInput) throws IOException {
        assert dataInput != null;

        int ch;
        do {
            try {
                ch = (dataInput.readByte() & Binary.INT_LOW_BYTE_MASK);
            } catch (EOFException e) {
                /*
                 * This means that no valid integer 
                 * digits where found 
                 */
                throw e;
            }
        } while (!((ch == '-' || ch == '+') || (ch >= '0' && ch <= '9')));

        final boolean hasLeadingSign = ch == '-' || ch == '+';

        final byte[] data;

        int pos = 0;
        if (hasLeadingSign) {
            data = new byte[INTEGER_MAX_DIGITS + 1];
            data[pos] = (byte) ch;
        } else if (ch >= '0' && ch <= '9') {
            data = new byte[INTEGER_MAX_DIGITS];
            data[pos] = (byte) ch;
        } else
            throw new NumberFormatException("Expecting digits but found '"
                                            + (char) ch + "'");

        pos++;

        do {
            try {
                ch = (dataInput.readByte() & Binary.INT_LOW_BYTE_MASK);
                final boolean isValidDigit = ch >= '0' && ch <= '9';

                if (!isValidDigit)
                    break;

                data[pos] = (byte) ch;
                pos++;
            } catch (EOFException e) {
                break;
            }
        } while (pos < data.length);

        if (pos == 1 && hasLeadingSign) {
            throw new NumberFormatException(
                    "Found sign but number digits missing");
        }

        return Integer.parseInt(new String(data, 0, pos));
    }


    /**
     * Writes a integer to the output in display format.
     * 
     * @param dataOutput the data output object to write to
     * @param object the string object to write
     * @throws IOException if an exception occurs while reading the string
     */
    @Override
    public void write(DataOutput dataOutput, Object object) throws IOException {
        assert dataOutput != null;
        assert object != null;

        dataOutput.write(object.toString().getBytes());
    }

}
