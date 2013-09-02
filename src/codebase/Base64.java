package codebase;

import java.io.IOException;

/**
 * Encodes and decodes Base64 notation.
 * <p>
 * Base64 is encoding is used to encode binary files as readable text enabling the files
 * to be sent over channels that are sensitive to binary characters like e-mail.
 * <p>
 * This class is based on Robert Harder code that found at <a
 * href="http://iharder.net/base64">http://iharder.net/base64</a>.
 */
public final class Base64 {

    /**
     * A {@link Base64.InputStream} that reads data from another
     * <tt>java.io.InputStream</tt> and encodes/decodes to/from Base64 notation on the
     * fly.
     * 
     * @see Base64
     */
    public static class InputStream extends
            java.io.FilterInputStream {

        private static final int DECODING_OUTPUT_BUFFER_LENGTH = 3;
        private static final int ENCODING_OUTPUT_BUFFER_LENGTH = 4;

        /**
         * Encoding or decoding flag.
         */
        private boolean encode;

        /**
         * Small buffer holding converted data.
         */
        private final byte[] outputBuffer;

        /**
         * Number of meaningful bytes actually in the output buffer.
         */
        private int numSigOutputBytes = 0;

        /**
         * Current position in the buffer.
         */
        private int currentPosition;

        /**
         * Current line length.
         */
        private int lineLength;

        /**
         * Break lines at less than 80 characters.
         */
        private final boolean mustBreakLines;

        /**
         * Record options used to create the stream.
         */
        private final int options;

        /**
         * Local copies to avoid extra method calls.
         */
        private final byte[] decodabet;

        /**
         * Constructs a {@link Base64.InputStream} in DECODE mode.
         * 
         * @param in the <tt>java.io.InputStream</tt> from which to read data.
         */
        public InputStream(java.io.InputStream in) {
            this(in, DECODE);
        }

        /**
         * Constructs a {@link Base64.InputStream} in either ENCODE or DECODE mode.
         * <p>
         * Valid options:
         * 
         * <pre>
         *   ENCODE or DECODE: Encode or Decode as data is read.
         *   DONT_BREAK_LINES: don't break lines at 76 characters
         *     (only meaningful when encoding)
         *     &lt;i&gt;Note: Technically, this makes your encoding non-compliant.&lt;/i&gt;
         * </pre>
         * <p>
         * Example: <code>new Base64.InputStream( in, Base64.DECODE )</code>
         * 
         * @param in the <tt>java.io.InputStream</tt> from which to read data.
         * @param options Specified options
         * @see Base64#ENCODE
         * @see Base64#DECODE
         * @see Base64#DONT_BREAK_LINES
         */
        public InputStream(java.io.InputStream in, int options) {
            super(in);
            this.mustBreakLines = (options & DONT_BREAK_LINES) != DONT_BREAK_LINES;
            this.encode = (options & ENCODE) == ENCODE;

            final int bufferLength = encode ? ENCODING_OUTPUT_BUFFER_LENGTH
                    : DECODING_OUTPUT_BUFFER_LENGTH;
            this.outputBuffer = new byte[bufferLength];

            this.currentPosition = -1;
            this.lineLength = 0;

            /*
             * Record for later, mostly to determine which
             * alphabet to use
             */
            this.options = options;
            this.decodabet = getDecodabet(options);
        }

        /**
         * Reads enough of the input stream to convert to/from Base64 and returns the next
         * byte.
         * 
         * @return next byte
         * @throws IOException if an error occurs while reading from the input stream or
         *             if the Base64 stream is malformed
         */
        public int read() throws IOException {



            /*
             * Do we need to get data?
             */
            if (currentPosition < 0) {
                if (encode) {
                    final byte[] b3 = new byte[DECODING_OUTPUT_BUFFER_LENGTH];
                    int numBinaryBytes = 0;
                    for (int i = 0; i < DECODING_OUTPUT_BUFFER_LENGTH; i++) {
                        try {
                            int b = in.read();

                            // If end of stream, b is -1.
                            if (b >= 0) {
                                b3[i] = (byte) b;
                                numBinaryBytes++;
                            }
                        } catch (IOException e) {
                            // Only a problem if we got no data at all.
                            if (i == 0)
                                throw e;

                        }
                    }

                    final boolean bytesRead = numBinaryBytes > 0;
                    if (bytesRead) {
                        encode3to4(b3, 0, numBinaryBytes, outputBuffer, 0, options);
                        currentPosition = 0;

                        assert outputBuffer.length == ENCODING_OUTPUT_BUFFER_LENGTH;
                        numSigOutputBytes = outputBuffer.length;
                    } else {
                        return -1;
                    }
                }

                /*
                 *  Else decoding
                 */
                else {
                    final byte[] b4 = new byte[ENCODING_OUTPUT_BUFFER_LENGTH];
                    int i = 0;
                    for (i = 0; i < ENCODING_OUTPUT_BUFFER_LENGTH; i++) {
                        // Read four "meaningful" bytes:
                        int b = 0;
                        do {
                            b = in.read();
                        } while (b >= 0 && decodabet[b & BASE64_BYTE_MASK] <= WHITE_SPACE_ENC);

                        // Reads a -1 if end of stream
                        if (b < 0)
                            break;

                        b4[i] = (byte) b;
                    }

                    final boolean filledUp = i == ENCODING_OUTPUT_BUFFER_LENGTH;
                    if (filledUp) {
                        numSigOutputBytes = decode4to3(b4, 0, outputBuffer, 0, options);
                        currentPosition = 0;
                    } else if (i == 0) {
                        return -1;
                    } else {
                        // Must have broken out from above.
                        throw new java.io.IOException("Improperly padded Base64 input.");
                    }

                }
            }

            /*
             * Got data?
             */
            if (currentPosition >= 0) {
                // End of relevant data?
                if (currentPosition >= numSigOutputBytes)
                    return -1;

                if (encode && mustBreakLines && lineLength >= MAX_LINE_LENGTH) {
                    lineLength = 0;
                    return '\n';
                } else {
                    /*
                     * This isn't important when decoding
                     * but throwing an extra "if" seems
                     * just as wasteful.
                     */
                    lineLength++;

                    final int b = outputBuffer[currentPosition++];

                    if (currentPosition >= outputBuffer.length)
                        currentPosition = -1;

                    /* 
                     * This is how you "cast" a byte that's
                     * intended to be unsigned.
                     */
                    return b & UNSIGNED_BYTE_MASK;
                }
            }

            // Else error
            else {
                throw new java.io.IOException("Error in Base64 code reading stream.");
            }
        }

        /**
         * Calls {@link #read()} repeatedly until the end of stream is reached or
         * <tt>len</tt> bytes are read.
         * 
         * @param dest array to hold values
         * @param off offset for array
         * @param len max number of bytes to read into array
         * @return bytes read into array or -1 if end of stream is encountered.
         * @throws IOException if an error occurs while reading from the input stream.
         */
        public int read(byte[] dest, int off, int len) throws IOException {
            int i;
            int b;
            for (i = 0; i < len; i++) {
                b = read();
                if (b >= 0)
                    dest[off + i] = (byte) b;
                else if (i == 0)
                    return -1;
                else
                    /* 
                     * Break out of 'for' loop
                     */
                    break;
            }
            return i;
        }
    }

    /**
     * A {@link Base64.OutputStream} will write data to another
     * <tt>java.io.OutputStream</tt> and encode/decode to/from Base64 notation on the fly.
     * 
     * @see Base64
     */
    public static class OutputStream extends
            java.io.FilterOutputStream {
        private static final int OUTPUT_BUFFER_SIZE = 4;
        private static final int DECODING_INPUT_BUFFER_LENGTH = 4;
        private static final int ENCODING_INPUT_BUFFER_LENGTH = 3;

        /**
         * Encoding/decoding options.
         */
        private final int options;

        /**
         * Encode/decode flag.
         */
        private final boolean encode;

        /**
         * Break lines at less than 80 characters.
         */
        private final boolean mustBreakLines;

        /**
         * Current position in the input buffer.
         */
        private int currentPosition;

        /**
         * Current input buffer contents.
         */
        private final byte[] inputBuffer;

        /**
         * Current line length.
         */
        private int lineLength;

        /**
         * Output buffer.
         */
        private final byte[] outputBuffer;

        /**
         * Flag indicating whether encoding has been suspended.
         */
        private boolean suspendEncoding;

        /**
         * Local copy to avoid extra method calls.
         */
        private final byte[] decodabet;

        /**
         * Constructs a {@link Base64.OutputStream} in ENCODE mode.
         * 
         * @param out the <tt>java.io.OutputStream</tt> to which data will be written.
         */
        public OutputStream(java.io.OutputStream out) {
            this(out, ENCODE);
        }

        /**
         * Constructs a {@link Base64.OutputStream} in either ENCODE or DECODE mode.
         * <p>
         * Valid options:
         * 
         * <pre>
         *   ENCODE or DECODE: Encode or Decode as data is read.
         *   DONT_BREAK_LINES: don't break lines at 76 characters
         *     (only meaningful when encoding)
         *     &lt;i&gt;Note: Technically, this makes your encoding non-compliant.&lt;/i&gt;
         * </pre>
         * <p>
         * Example: <code>new Base64.OutputStream( out, Base64.ENCODE )</code>
         * 
         * @param out the <tt>java.io.OutputStream</tt> to which data will be written.
         * @param options Specified options.
         * @see Base64#ENCODE
         * @see Base64#DECODE
         * @see Base64#DONT_BREAK_LINES
         */
        public OutputStream(java.io.OutputStream out, int options) {
            super(out);
            this.mustBreakLines = (options & DONT_BREAK_LINES) != DONT_BREAK_LINES;
            this.encode = (options & ENCODE) == ENCODE;

            final int bufferLength = encode ? ENCODING_INPUT_BUFFER_LENGTH
                    : DECODING_INPUT_BUFFER_LENGTH;
            this.inputBuffer = new byte[bufferLength];

            this.currentPosition = 0;
            this.lineLength = 0;
            this.suspendEncoding = false;
            this.outputBuffer = new byte[OUTPUT_BUFFER_SIZE];
            this.options = options;
            this.decodabet = getDecodabet(options);
        }

        /**
         * Pads the buffer without closing the stream.
         * 
         * @throws IOException if the output stream throws an exception while writing the
         *             pad bytes.
         */
        private void flushBase64() throws IOException {
            if (currentPosition > 0) {
                if (encode) {
                    out.write(encode3to4(inputBuffer, outputBuffer, currentPosition, options));
                    currentPosition = 0;
                } else {
                    throw new java.io.IOException("Base64 input not properly padded.");
                }
            }
        }

        /**
         * Flushes the base64 output buffer of the output stream.
         * 
         * @throws IOException if the output stream throws an exception while flushing or
         *             closing
         */
        @Override
        public void flush() throws IOException {
            flushBase64();
            super.flush();
        }

        /**
         * Resumes encoding of the stream.
         * <p>
         * May be helpful if you need to embed a piece of base64-encoded data in a stream.
         */
        public void resumeEncoding() {
            this.suspendEncoding = false;
        }

        /**
         * Suspends encoding of the stream.
         * <p>
         * May be helpful if you need to embed a piece of base640-encoded data in a
         * stream.
         * 
         * @throws IOException if the output stream throws an exception while flushing the
         *             stream.
         */
        public void suspendEncoding() throws IOException {
            flushBase64();
            this.suspendEncoding = true;
        }

        /**
         * Calls {@link #write(int)} repeatedly until <tt>len</tt> bytes are written.
         * 
         * @param theBytes array from which to read bytes
         * @param off offset for array
         * @param len max number of bytes to read into array
         * @throws IOException if the output stream throws an exception while writing to
         *             the stream.
         */
        public void write(byte[] theBytes, int off, int len) throws IOException {
            // Encoding suspended?
            if (suspendEncoding) {
                super.out.write(theBytes, off, len);
                return;
            }

            for (int i = 0; i < len; i++) {
                write(theBytes[off + i]);
            }
        }

        /**
         * Writes the byte to the output stream after converting to/from Base64 notation.
         * <p>
         * When encoding, bytes are buffered three at a time before the output stream
         * actually gets a write() call. When decoding, bytes are buffered four at a time.
         * 
         * @param b the byte to write
         * @throws IOException if the output stream throws an exception while writing to
         *             the stream.
         */
        public void write(int b) throws IOException {
            // Encoding suspended?
            if (suspendEncoding) {
                super.out.write(b);
                return;
            }

            // Encode?
            if (encode) {
                inputBuffer[currentPosition++] = (byte) b;
                final boolean enoughToEncode = currentPosition >= inputBuffer.length;
                if (enoughToEncode) {
                    out.write(encode3to4(inputBuffer, outputBuffer, inputBuffer.length, options));

                    lineLength += OUTPUT_BUFFER_SIZE;
                    if (mustBreakLines && lineLength >= MAX_LINE_LENGTH) {
                        out.write(NEW_LINE);
                        lineLength = 0;
                    }

                    currentPosition = 0;
                }
            }
            // Else, we are decoding
            else {
                // Meaningful Base64 character?
                if (decodabet[b & UNSIGNED_BYTE_MASK] > WHITE_SPACE_ENC) {
                    inputBuffer[currentPosition++] = (byte) b;
                    final boolean enoughToOutput = currentPosition >= inputBuffer.length;
                    if (enoughToOutput) {
                        int len = Base64.decode4to3(inputBuffer, 0, outputBuffer, 0, options);
                        out.write(outputBuffer, 0, len);
                        currentPosition = 0;
                    }
                } else if (decodabet[b & UNSIGNED_BYTE_MASK] != WHITE_SPACE_ENC) {
                    throw new java.io.IOException("Invalid character in Base64 data.");
                }
            }
        }

    }


    private static final int UNSIGNED_BYTE_MASK = 0xFF;

    /**
     * Only the low 6 bits.
     */
    private static final int BASE64_BYTE_MASK = 0x7f;

    /*
     * Encoding/Decoding options.
     */

    /**
     * No options specified. Value is zero.
     */
    public static final int NO_OPTIONS = 0;


    /**
     * Specify encoding option.
     */
    public static final int ENCODE = 1;

    /**
     * Specify decoding option.
     */
    public static final int DECODE = 0;


    /**
     * Specify don't break lines option when encoding. (<b>n.b.:</b>This violates strict
     * Base64 specification).
     */
    public static final int DONT_BREAK_LINES = 8;


    /**
     * Specify encoding using Base64-like encoding that is URL- and Filename-safe. As
     * described in Section 4 of RFC3548: <a
     * href="http://www.faqs.org/rfcs/rfc3548.html">http://www.faqs
     * .org/rfcs/rfc3548.html</a>.
     * <p>
     * It is important to note that data encoded this way is <em>not</em> officially valid
     * Base64, or at the very least should not be called Base64 without also specifying
     * that is was encoded using the URL- and Filename-safe dialect.
     */
    public static final int URL_SAFE = 16;


    /**
     * Specify encode using the special "ordered" dialect of Base64. Described on <a
     * href="http://www.faqs.org/qa/rfcc-1940.html"
     * >http://www.faqs.org/qa/rfcc-1940.html</a>.
     */
    public static final int ORDERED = 32;

    /*
     * Fields
     */

    /**
     * Maximum line length (76) of Base64 output.
     */
    private static final int MAX_LINE_LENGTH = 76;


    /**
     * The equals sign (=) as a byte.
     */
    private static final byte EQUALS_SIGN = (byte) '=';


    /**
     * The new line character (\n) as a byte.
     */
    private static final byte NEW_LINE = (byte) '\n';

    /**
     * Preferred text encoding.
     */
    private static final String PREFERRED_TEXT_ENCODING = "UTF-8";


    /* 
     * Base 64 Alphabet constants
     */

    /**
     * Indicates white space in encoding.
     */
    private static final byte WHITE_SPACE_ENC = -5;


    /**
     * Indicates equals sign in encoding.
     */
    private static final byte EQUALS_SIGN_ENC = -1;



    /**
     * The 64 valid Base64 values. Since the host platform me be something funny like
     * EBCDIC, we hardcode these values.
     */
    private static final byte[] STANDARD_ALPHABET = { (byte) 'A', (byte) 'B', (byte) 'C',
            (byte) 'D', (byte) 'E', (byte) 'F', (byte) 'G', (byte) 'H', (byte) 'I', (byte) 'J',
            (byte) 'K', (byte) 'L', (byte) 'M', (byte) 'N', (byte) 'O', (byte) 'P', (byte) 'Q',
            (byte) 'R', (byte) 'S', (byte) 'T', (byte) 'U', (byte) 'V', (byte) 'W', (byte) 'X',
            (byte) 'Y', (byte) 'Z', (byte) 'a', (byte) 'b', (byte) 'c', (byte) 'd', (byte) 'e',
            (byte) 'f', (byte) 'g', (byte) 'h', (byte) 'i', (byte) 'j', (byte) 'k', (byte) 'l',
            (byte) 'm', (byte) 'n', (byte) 'o', (byte) 'p', (byte) 'q', (byte) 'r', (byte) 's',
            (byte) 't', (byte) 'u', (byte) 'v', (byte) 'w', (byte) 'x', (byte) 'y', (byte) 'z',
            (byte) '0', (byte) '1', (byte) '2', (byte) '3', (byte) '4', (byte) '5', (byte) '6',
            (byte) '7', (byte) '8', (byte) '9', (byte) '+', (byte) '/' };

    /**
     * Translates a Base64 value to either its 6-bit reconstruction value or a negative
     * number indicating some other meaning.
     **/
    private static final byte[] STANDARD_DECODABET = { -9, -9, -9, -9, -9, -9, -9, -9, -9, // Decimal 0 - 8
            -5, -5, // Whitespace: Tab and Linefeed
            -9, -9, // Decimal 11 - 12
            -5, // Whitespace: Carriage Return
            -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, // Decimal 14 - 26
            -9, -9, -9, -9, -9, // Decimal 27 - 31
            -5, // Whitespace: Space
            -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, // Decimal 33 - 42
            62, // Plus sign at decimal 43
            -9, -9, -9, // Decimal 44 - 46
            63, // Slash at decimal 47
            52, 53, 54, 55, 56, 57, 58, 59, 60, 61, // Numbers zero through nine
            -9, -9, -9, // Decimal 58 - 60
            -1, // Equals sign at decimal 61
            -9, -9, -9, // Decimal 62 - 64
            0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, // Letters 'A' through 'N'
            14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, // Letters 'O' through 'Z'
            -9, -9, -9, -9, -9, -9, // Decimal 91 - 96
            26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, // Letters 'a' through 'm'
            39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, // Letters 'n' through 'z'
            -9, -9, -9, -9 // Decimal 123 - 126
    };


    /* 
     * URL safe Base 64 alphabet 
     */


    /**
     * Used in the URL- and Filename-safe dialect described in Section 4 of RFC3548: <a
     * href
     * ="http://www.faqs.org/rfcs/rfc3548.html">http://www.faqs.org/rfcs/rfc3548.html</a>.
     * Notice that the last two bytes become "hyphen" and "underscore" instead of "plus"
     * and "slash."
     */
    private static final byte[] URL_SAFE_ALPHABET = { (byte) 'A', (byte) 'B', (byte) 'C',
            (byte) 'D', (byte) 'E', (byte) 'F', (byte) 'G', (byte) 'H', (byte) 'I', (byte) 'J',
            (byte) 'K', (byte) 'L', (byte) 'M', (byte) 'N', (byte) 'O', (byte) 'P', (byte) 'Q',
            (byte) 'R', (byte) 'S', (byte) 'T', (byte) 'U', (byte) 'V', (byte) 'W', (byte) 'X',
            (byte) 'Y', (byte) 'Z', (byte) 'a', (byte) 'b', (byte) 'c', (byte) 'd', (byte) 'e',
            (byte) 'f', (byte) 'g', (byte) 'h', (byte) 'i', (byte) 'j', (byte) 'k', (byte) 'l',
            (byte) 'm', (byte) 'n', (byte) 'o', (byte) 'p', (byte) 'q', (byte) 'r', (byte) 's',
            (byte) 't', (byte) 'u', (byte) 'v', (byte) 'w', (byte) 'x', (byte) 'y', (byte) 'z',
            (byte) '0', (byte) '1', (byte) '2', (byte) '3', (byte) '4', (byte) '5', (byte) '6',
            (byte) '7', (byte) '8', (byte) '9', (byte) '-', (byte) '_' };

    /**
     * Used in decoding URL- and Filename-safe dialects of Base64.
     */
    private static final byte[] URL_SAFE_DECODABET = { -9, -9, -9, -9, -9, -9, -9, -9, -9, // Decimal 0 - 8
            -5, -5, // Whitespace: Tab and Linefeed
            -9, -9, // Decimal 11 - 12
            -5, // Whitespace: Carriage Return
            -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, // Decimal 14 - 26
            -9, -9, -9, -9, -9, // Decimal 27 - 31
            -5, // Whitespace: Space
            -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, // Decimal 33 - 42
            -9, // Plus sign at decimal 43
            -9, // Decimal 44
            62, // Minus sign at decimal 45
            -9, // Decimal 46
            -9, // Slash at decimal 47
            52, 53, 54, 55, 56, 57, 58, 59, 60, 61, // Numbers zero through nine
            -9, -9, -9, // Decimal 58 - 60
            -1, // Equals sign at decimal 61
            -9, -9, -9, // Decimal 62 - 64
            0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, // Letters 'A' through 'N'
            14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, // Letters 'O' through 'Z'
            -9, -9, -9, -9, // Decimal 91 - 94
            63, // Underscore at decimal 95
            -9, // Decimal 96
            26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, // Letters 'a' through 'm'
            39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, // Letters 'n' through 'z'
            -9, -9, -9, -9 // Decimal 123 - 126
    };


    /* 
     * Ordered Base64 Alphabet 
     */

    /**
     * Ordered alphabet as described here: <a href="http://www.faqs.org/qa/rfcc-1940.html"
     * >http://www.faqs.org/qa/rfcc-1940.html</a>.
     */
    private static final byte[] ORDERED_ALPHABET = { (byte) '-', (byte) '0', (byte) '1',
            (byte) '2', (byte) '3', (byte) '4', (byte) '5', (byte) '6', (byte) '7', (byte) '8',
            (byte) '9', (byte) 'A', (byte) 'B', (byte) 'C', (byte) 'D', (byte) 'E', (byte) 'F',
            (byte) 'G', (byte) 'H', (byte) 'I', (byte) 'J', (byte) 'K', (byte) 'L', (byte) 'M',
            (byte) 'N', (byte) 'O', (byte) 'P', (byte) 'Q', (byte) 'R', (byte) 'S', (byte) 'T',
            (byte) 'U', (byte) 'V', (byte) 'W', (byte) 'X', (byte) 'Y', (byte) 'Z', (byte) '_',
            (byte) 'a', (byte) 'b', (byte) 'c', (byte) 'd', (byte) 'e', (byte) 'f', (byte) 'g',
            (byte) 'h', (byte) 'i', (byte) 'j', (byte) 'k', (byte) 'l', (byte) 'm', (byte) 'n',
            (byte) 'o', (byte) 'p', (byte) 'q', (byte) 'r', (byte) 's', (byte) 't', (byte) 'u',
            (byte) 'v', (byte) 'w', (byte) 'x', (byte) 'y', (byte) 'z' };

    /**
     * Used in decoding the "ordered" dialect of Base64.
     */
    private static final byte[] ORDERED_DECODABET = { -9, -9, -9, -9, -9, -9, -9, -9, -9, // Decimal 0 - 8
            -5, -5, // Whitespace: Tab and Linefeed
            -9, -9, // Decimal 11 - 12
            -5, // Whitespace: Carriage Return
            -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, // Decimal 14 - 26
            -9, -9, -9, -9, -9, // Decimal 27 - 31
            -5, // Whitespace: Space
            -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, // Decimal 33 - 42
            -9, // Plus sign at decimal 43
            -9, // Decimal 44
            0, // Minus sign at decimal 45
            -9, // Decimal 46
            -9, // Slash at decimal 47
            1, 2, 3, 4, 5, 6, 7, 8, 9, 10, // Numbers zero through nine
            -9, -9, -9, // Decimal 58 - 60
            -1, // Equals sign at decimal 61
            -9, -9, -9, // Decimal 62 - 64
            11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, // Letters 'A' through 'M'
            24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, // Letters 'N' through 'Z'
            -9, -9, -9, -9, // Decimal 91 - 94
            37, // Underscore at decimal 95
            -9, // Decimal 96
            38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, // Letters 'a' through 'm'
            51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, // Letters 'n' through 'z'
            -9, -9, -9, -9 // Decimal 123 - 126
    };

    /**
     * Decodes four bytes from array <tt>source</tt> and writes the resulting bytes (up to
     * three of them) to <tt>destination</tt>.
     * <p>
     * The source and destination arrays can be manipulated anywhere along their length by
     * specifying <tt>srcOffset</tt> and <tt>destOffset</tt>. This method does not check
     * to make sure your arrays are large enough to accomodate <tt>srcOffset</tt> + 4 for
     * the <tt>source</tt> array or <tt>destOffset</tt> + 3 for the <tt>destination</tt>
     * array. This method returns the actual number of bytes that were converted from the
     * Base64 encoding.
     * <p>
     * This is the lowest level of the decoding methods with all possible parameters.
     * </p>
     * 
     * @param source the array to convert
     * @param srcOffset the index where conversion begins
     * @param destination the array to hold the conversion
     * @param destOffset the index where output will be put
     * @param options alphabet type is pulled from this (standard, url-safe, ordered)
     * @return the number of decoded bytes converted or <tt>-1</tt> if conversion error
     *         occurred
     */
    private static int decode4to3(byte[] source,
                                  int srcOffset,
                                  byte[] destination,
                                  int destOffset,
                                  int options) {

        // CHECKSTYLE:OFF - bit shift constants are local
        final byte[] decodabet = getDecodabet(options);

        // Example: Dk==
        if (source[srcOffset + 2] == EQUALS_SIGN) {
            final int outBuff = ((decodabet[source[srcOffset]] & 0xFF) << 18)
                    | ((decodabet[source[srcOffset + 1]] & 0xFF) << 12);

            destination[destOffset] = (byte) (outBuff >>> 16);
            return 1;
        }

        // Example: DkL=
        else if (source[srcOffset + 3] == EQUALS_SIGN) {
            final int outBuff = ((decodabet[source[srcOffset]] & 0xFF) << 18)
                    | ((decodabet[source[srcOffset + 1]] & 0xFF) << 12)
                    | ((decodabet[source[srcOffset + 2]] & 0xFF) << 6);

            destination[destOffset] = (byte) (outBuff >>> 16);
            destination[destOffset + 1] = (byte) (outBuff >>> 8);
            return 2;
        }

        // Example: DkLE
        else {
            try {
                final int outBuff = ((decodabet[source[srcOffset]] & 0xFF) << 18)
                        | ((decodabet[source[srcOffset + 1]] & 0xFF) << 12)
                        | ((decodabet[source[srcOffset + 2]] & 0xFF) << 6)
                        | ((decodabet[source[srcOffset + 3]] & 0xFF));

                destination[destOffset] = (byte) (outBuff >> 16);
                destination[destOffset + 1] = (byte) (outBuff >> 8);
                destination[destOffset + 2] = (byte) (outBuff);

                return 3;
            } catch (IndexOutOfBoundsException e) {
                /*
                 * If a array access problem occurred just ignore and report that the buffer is malformed
                 */
                return -1;
            }
        }

        //CHECKSTYLE:ON
    }


    /**
     * Encodes up to the first three bytes of an array and returns a four-byte array in
     * Base64 notation.
     * <p>
     * The array <tt>b3</tt> needs only be as big as <tt>numSigBytes</tt>. The actual
     * number of significant bytes in your array is given by <tt>numSigBytes</tt>. Code
     * can reuse a byte array by passing a four-byte array as <tt>b4</tt>.
     * 
     * @param b3 the array to convert
     * @param b4 a reusable four byte array to reduce array instantiation
     * @param numSigBytes the number of significant bytes in your array
     * @return four byte array in Base64 notation.
     */
    private static byte[] encode3to4(byte[] b3, byte[] b4, int numSigBytes, int options) {
        encode3to4(b3, 0, numSigBytes, b4, 0, options);
        return b4;
    }


    /**
     * Encodes up to three bytes of a byte array and writes the resulting four Base64
     * bytes to a destination array.
     * <p>
     * The source and destination arrays can be manipulated anywhere along their length by
     * specifying <tt>srcOffset</tt> and <tt>destOffset</tt>. This method does not check
     * to make sure your arrays are large enough to accomodate <tt>srcOffset</tt> + 3 for
     * the <tt>source</tt> array or <tt>destOffset</tt> + 4 for the <tt>destination</tt>
     * array. The actual number of significant bytes in your array is given by
     * <tt>numSigBytes</tt>.
     * </p>
     * <p>
     * This is the lowest level of the encoding methods with all possible parameters.
     * </p>
     * 
     * @param source the array to convert
     * @param srcOffset the index where conversion begins
     * @param numSigBytes the number of significant bytes in your array
     * @param destination the array to hold the conversion
     * @param destOffset the index where output will be put
     * @return the <tt>destination</tt> array
     */
    private static byte[] encode3to4(byte[] source,
                                     int srcOffset,
                                     int numSigBytes,
                                     byte[] destination,
                                     int destOffset,
                                     int options) {
        final byte[] alphabet = getAlphabet(options);

        /* 1 2 3
         * 01234567890123456789012345678901 Bit position
         * --------000000001111111122222222 Array position from threeBytes
         * --------| || || || | Six bit groups to index ALPHABET
         * >>18 >>12 >> 6 >> 0 Right shift necessary
         * 0x3f 0x3f 0x3f Additional AND

        /*
         * Create buffer with zero-padding if there are only one or two
         * significant bytes passed in the array.
         *
         * We have to shift left 24 in order to flush out the 1's that appear
         * when Java treats a value as negative that is cast from a byte to an int.
         */

        // CHECKSTYLE:OFF - this is a low level decoding method 
        int inBuff = (numSigBytes > 0 ? ((source[srcOffset] << 24) >>> 8) : 0)
                | (numSigBytes > 1 ? ((source[srcOffset + 1] << 24) >>> 16) : 0)
                | (numSigBytes > 2 ? ((source[srcOffset + 2] << 24) >>> 24) : 0);

        switch (numSigBytes) {
            case 3:
                destination[destOffset] = alphabet[(inBuff >>> 18)];
                destination[destOffset + 1] = alphabet[(inBuff >>> 12) & 0x3f];
                destination[destOffset + 2] = alphabet[(inBuff >>> 6) & 0x3f];
                destination[destOffset + 3] = alphabet[(inBuff) & 0x3f];
                return destination;

            case 2:
                destination[destOffset] = alphabet[(inBuff >>> 18)];
                destination[destOffset + 1] = alphabet[(inBuff >>> 12) & 0x3f];
                destination[destOffset + 2] = alphabet[(inBuff >>> 6) & 0x3f];
                destination[destOffset + 3] = EQUALS_SIGN;
                return destination;

            case 1:
                destination[destOffset] = alphabet[(inBuff >>> 18)];
                destination[destOffset + 1] = alphabet[(inBuff >>> 12) & 0x3f];
                destination[destOffset + 2] = EQUALS_SIGN;
                destination[destOffset + 3] = EQUALS_SIGN;
                return destination;

            default:
                return destination;
        }
        //CHECKSTYLE:ON
    }

    /**
     * Returns one of the <tt>ALPHABET</tt> byte arrays depending on the options
     * specified.
     * <p>
     * It's possible, though silly, to specify ORDERED and URLSAFE in which case one of
     * them will be picked, though there is no guarantee as to which one will be picked.
     */
    private static byte[] getAlphabet(int options) {
        if ((options & URL_SAFE) == URL_SAFE)
            return URL_SAFE_ALPHABET;
        else if ((options & ORDERED) == ORDERED)
            return ORDERED_ALPHABET;
        else
            return STANDARD_ALPHABET;
    }


    /*
     * Encoding methods
     */


    /**
     * Returns one of the <tt>DECODABET</tt> byte arrays depending on the options
     * specified.
     * <p>
     * It's possible, though silly, to specify ORDERED and URL_SAFE in which case one of
     * them will be picked, though there is no guarantee as to which one will be picked.
     */
    private static byte[] getDecodabet(int options) {
        if ((options & URL_SAFE) == URL_SAFE)
            return URL_SAFE_DECODABET;
        else if ((options & ORDERED) == ORDERED)
            return ORDERED_DECODABET;
        else
            return STANDARD_DECODABET;
    }

    /**
     * Decodes Base-64 ASCII characters in the form of a byte array.
     * <p>
     * Very low-level access. Does not support automatically gunzipping or any other
     * 'fancy' features.
     * 
     * @param source the Base64 encoded data
     * @param off The offset of where to begin decoding
     * @param len The length of characters to decode
     * @param options the decoding options return decoded data
     * @return the decoded binary data
     */
    public static byte[] decode(byte[] source, int off, int len, int options) {
        final byte[] decodabet = getDecodabet(options);

        // CHECKSTYLE:OFF - array access indexes 
        final int maxBufferSize = len * 3 / 4;
        final byte[] outBuff = new byte[maxBufferSize]; // Upper limit on size of output
        int outBuffPos = 0;

        final byte[] b4 = new byte[4];

        int b4Posn = 0;
        int i = 0;
        byte sbiCrop = 0;
        byte sbiDecode = 0;
        for (i = off; i < off + len; i++) {
            sbiCrop = (byte) (source[i] & UNSIGNED_BYTE_MASK);
            sbiDecode = decodabet[sbiCrop];

            /*
             * White space, Equals sign or better
             */
            if (sbiDecode >= WHITE_SPACE_ENC) {
                if (sbiDecode >= EQUALS_SIGN_ENC) {
                    b4[b4Posn++] = sbiCrop;
                    if (b4Posn > 3) {
                        outBuffPos += decode4to3(b4, 0, outBuff, outBuffPos, options);
                        b4Posn = 0;

                        // If that was the equals sign, break out of 'for' loop
                        if (sbiCrop == EQUALS_SIGN)
                            break;
                    }
                }
            } else {
                throw new IllegalArgumentException("Bad Base64 input character at " + i + ": "
                        + source[i] + "(decimal)");
            }
        }
        // CHECKSTYLE:ON 

        final byte[] out = new byte[outBuffPos];
        System.arraycopy(outBuff, 0, out, 0, outBuffPos);
        return out;
    }


    /**
     * Decodes data from Base64 notation.
     * 
     * @param s the string to decode
     * @return the decoded data
     */
    public static byte[] decode(String s) {
        return decode(s, NO_OPTIONS);
    }


    /**
     * Decodes data from Base64 notation.
     * 
     * @param s the string to decode
     * @param options encode options such as URL_SAFE
     * @return the decoded data
     */
    public static byte[] decode(String s, int options) {
        try {
            final byte[] bytes = s.getBytes(PREFERRED_TEXT_ENCODING);
            return decode(bytes, 0, bytes.length, options);
        } catch (java.io.UnsupportedEncodingException e) {
            // this is an coding error situation - should never happen
            throw new RuntimeException(e);
        }
    }


    /**
     * Encodes a byte array into Base64 notation.
     * 
     * @param source The data to convert
     * @return the Base64 encoded string
     */
    public static String encode(byte[] source) {
        return encode(source, 0, source.length, NO_OPTIONS);
    }

    /**
     * Encodes a byte array into Base64 notation.
     * <p>
     * Valid options:
     * 
     * <pre>
     *   DONT_BREAK_LINES: don't break lines at 76 characters
     *     &lt;i&gt;Note: Technically, this makes your encoding non-compliant.&lt;/i&gt;
     * </pre>
     * <p>
     * Example: <code>encodeBytes(myData, Base64.DONT_BREAK_LINES)</code>
     * 
     * @param source The data to convert
     * @param options Specified options
     * @return the Base64 encoded string
     * @see Base64#DONT_BREAK_LINES
     */
    public static String encode(byte[] source, int options) {
        return encode(source, 0, source.length, options);
    }


    /*
     * Decoding methods
     */


    /**
     * Encodes a byte array into Base64 notation.
     * 
     * @param source The data to convert
     * @param off Offset in array where conversion should begin
     * @param len Length of data to convert
     * @return the Base64 encoded string
     */
    public static String encode(byte[] source, int off, int len) {
        return encode(source, off, len, NO_OPTIONS);
    }


    /**
     * Encodes a byte array into Base64 notation.
     * <p>
     * Valid options:
     * 
     * <pre>
     *   DONT_BREAK_LINES: don't break lines at 76 characters
     *     &lt;i&gt;Note: Technically, this makes your encoding non-compliant.&lt;/i&gt;
     * </pre>
     * <p>
     * Example: <code>encodeBytes(myData, Base64.DONT_BREAK_LINES)</code>
     * 
     * @param source The data to convert
     * @param off Offset in array where conversion should begin
     * @param len Length of data to convert
     * @param options Specified options alphabet type is pulled from this (standard,
     *            url-safe, ordered)
     * @return the Base64 encoded string
     * @see Base64#DONT_BREAK_LINES
     */
    public static String encode(byte[] source, int off, int len, int options) {
        // Isolate options
        final int dontBreakLines = (options & DONT_BREAK_LINES);
        final boolean breakLines = dontBreakLines == 0;

        // CHECKSTYLE:OFF - this is a low level decoding method with array index access
        final int len43 = len * 4 / 3;
        final int outBufferSize = (len43) // Main 4:3
                + ((len % 3) > 0 ? 4 : 0) // Account for padding
                + (breakLines ? (len43 / MAX_LINE_LENGTH) : 0); // New lines

        final byte[] outBuffer = new byte[outBufferSize];

        int a = 0;
        int b = 0;
        int lineLength = 0;
        for (; a < (len - 2); a += 3, b += 4) {
            encode3to4(source, a + off, 3, outBuffer, b, options);

            lineLength += 4;
            if (breakLines && lineLength == MAX_LINE_LENGTH) {
                outBuffer[b + 4] = NEW_LINE;
                b++;
                lineLength = 0;
            }
        }

        if (a < len) {
            encode3to4(source, a + off, len - a, outBuffer, b, options);
            b += 4;
        }
        // CHECKSTYLE:ON

        // Return value according to relevant encoding.
        try {
            return new String(outBuffer, 0, b, PREFERRED_TEXT_ENCODING);
        } catch (java.io.UnsupportedEncodingException e) {
            // this is an coding error situation - should never happen
            throw new RuntimeException(e);
        }
    }

    /**
     * Prevents instantiation.
     */
    private Base64() {
    }
}
