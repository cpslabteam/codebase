package codebase.io.converters.display;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.EOFException;
import java.io.IOException;

import codebase.io.converters.VariableSizeConverter;

public class StringLiteralConverter extends
        VariableSizeConverter {
    private static final int DEFAULT_MAX_READ_LENGTH = 1024;

    private final int maxReadSize = DEFAULT_MAX_READ_LENGTH;

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
        } while (ch != '\"' && len <= maxReadSize);

        if (len >= maxReadSize) {
            throw new IOException("Max read length for a string literal exceeded");
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
            } catch(EOFException e) {
                /*
                 * We treat EOF as the end of buffer
                 */
                break;
            } catch(IOException e) {
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
        } while (len <= maxReadSize);

        if (len >= maxReadSize) {
            throw new IOException("Max read length for a string literal exceeded.");
        }

        return codebase.Strings.unstringify(sb.toString());
    }

    @Override
    public void write(DataOutput dataOutput, Object object) throws IOException {
        assert dataOutput != null;
        assert object != null;

        final String s = codebase.Strings.stringify(object.toString());
        dataOutput.write(s.getBytes());
    }
}
