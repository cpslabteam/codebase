package codebase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import codebase.junit.EnhancedTestCase;
import codebase.streams.StringInputStream;

/**
 * Tests the {@link Base64} class.
 * <p>
 * This class is tested by encoding and then decoding different strings and byte arrays to
 * base-64.
 */
public class TestBase64 extends
        EnhancedTestCase {

    private static final String TEXT = "At vero eos et accusamus et iusto odio dignissimos ducimus qui "
            + "blanditiis praesentium voluptatum deleniti atque corrupti quos "
            + "dolores et quas molestias excepturi sint occaecati cupiditate non "
            + "provident, similique sunt in culpa qui officia deserunt mollitia animi,"
            + " id est laborum et dolorum fuga. Et harum quidem rerum facilis est et "
            + "expedita distinctio. Nam libero tempore, cum soluta nobis est eligendi "
            + "optio cumque nihil impedit quo minus id quod maxime placeat facere "
            + "possimus, omnis voluptas assumenda est, omnis dolor repellendus. "
            + "Temporibus autem quibusdam et aut officiis debitis aut rerum "
            + "necessitatibus saepe eveniet ut et voluptates repudiandae sint et molestiae"
            + " non recusandae. Itaque earum rerum hic tenetur a sapiente delectus, ut aut"
            + " reiciendis voluptatibus maiores alias consequatur aut perferendis doloribus "
            + "asperiores repellat";

    private static final String HELLO_WORLD = "Hello World!";

    private static final byte[] BINARY_ZEROS = new byte[131];
    private static final byte[] BINARY_ONES = new byte[131];
    private static final byte[] BINARY_FFS = new byte[131];

    private static final byte[] BINARY_SEQUENCE = new byte[2131];

    static {
        java.util.Arrays.fill(BINARY_ZEROS, (byte) 0);
        java.util.Arrays.fill(BINARY_ONES, (byte) 1);
        java.util.Arrays.fill(BINARY_FFS, (byte) 0xFF);

        for (int i = 0; i < BINARY_SEQUENCE.length; i++) {
            BINARY_SEQUENCE[i] = (byte) (i & 0xFF);
        }
    }

    private static final byte[] FLUSH_TEST_BINARY_BUFFER = new byte[] { 1, 2 };

    /**
     * Tests encoding and then decoding a very simple text message.
     */
    public void testSimple() throws UnsupportedEncodingException {
        String encoded = Base64.encode(HELLO_WORLD.getBytes("UTF-8"));
        String decoded = new String(Base64.decode(encoded), "UTF-8");
        assertEquals(HELLO_WORLD, decoded);
    }

    /**
     * Tests encoding and then decoding a an empty string.
     */
    public void testEmpty() throws UnsupportedEncodingException {
        String encoded = Base64.encode("".getBytes("UTF-8"));
        String decoded = new String(Base64.decode(encoded), "UTF-8");
        assertEquals("", decoded);
    }

    /**
     * Tests encoding and then decoding a large text.
     */
    public void testText() throws UnsupportedEncodingException {
        String encoded = Base64.encode(TEXT.getBytes("UTF-8"));
        String decoded = new String(Base64.decode(encoded), "UTF-8");
        assertEquals(TEXT, decoded);
    }

    /**
     * Tests encoding and then decoding simple binary buffers.
     */
    public void testBinaryBasic() {
        final String encodedZeros = Base64.encode(BINARY_ZEROS);
        final byte[] decodedZeros = Base64.decode(encodedZeros);
        assertEquals(BINARY_ZEROS, decodedZeros);

        final String encodedOnes = Base64.encode(BINARY_ONES);
        final byte[] decodedOnes = Base64.decode(encodedOnes);
        assertEquals(BINARY_ONES, decodedOnes);

        final String encodedFFs = Base64.encode(BINARY_FFS);
        final byte[] decodedFFs = Base64.decode(encodedFFs);
        assertEquals(BINARY_FFS, decodedFFs);
    }

    /**
     * Tests encoding and then decoding a binary buffer with all 256 characters.
     */
    public void testBinarySequences() {
        final String encodedSequence = Base64.encode(BINARY_SEQUENCE);
        final byte[] decodedSequence = Base64.decode(encodedSequence);
        assertEquals(BINARY_SEQUENCE, decodedSequence);
    }

    /**
     * Tests input stream on the fly encoding/decoding of text.
     */
    public void testInputStreamString() throws IOException {
        final java.io.InputStream in = new StringInputStream(TEXT);
        final Base64.InputStream encodeInputStream = new Base64.InputStream(in, Base64.ENCODE);
        final Base64.InputStream decodeInputStream = new Base64.InputStream(encodeInputStream,
                Base64.DECODE);

        final byte[] inputBytes = TEXT.getBytes("UTF-8");
        final byte[] outputBytes = new byte[inputBytes.length];

        assertEquals(inputBytes.length, decodeInputStream.read(outputBytes));
        assertEquals(inputBytes, outputBytes);

        decodeInputStream.close();
        encodeInputStream.close();
    }

    /**
     * Tests input stream on the fly encoding/decoding of a binary sequence.
     */
    public void testInputStreamBinary() throws IOException {
        final java.io.InputStream in = new ByteArrayInputStream(BINARY_SEQUENCE);
        final Base64.InputStream encodeInputStream = new Base64.InputStream(in, Base64.ENCODE);

        final Base64.InputStream decodeInputStream = new Base64.InputStream(encodeInputStream,
                Base64.DECODE);

        final byte[] outputBytes = new byte[BINARY_SEQUENCE.length];

        assertEquals(BINARY_SEQUENCE.length, decodeInputStream.read(outputBytes));
        assertEquals(BINARY_SEQUENCE, outputBytes);

        decodeInputStream.close();
        encodeInputStream.close();
    }

    /**
     * Tests output stream on the fly encoding/decoding of text.
     */
    public void testOutputStreamString() throws IOException {
        final java.io.ByteArrayOutputStream out = new ByteArrayOutputStream();
        final Base64.OutputStream decodeOutputStream = new Base64.OutputStream(out, Base64.DECODE);
        final Base64.OutputStream encodeOutputStream = new Base64.OutputStream(decodeOutputStream,
                Base64.ENCODE);

        final byte[] outputBytes = TEXT.getBytes("UTF-8");
        encodeOutputStream.write(outputBytes);


        assertEquals(outputBytes, out.toByteArray());

        decodeOutputStream.close();
        encodeOutputStream.close();
    }

    /**
     * Tests output stream on the fly encoding/decoding of a binary sequence.
     */
    public void testOutputBinaryString() throws IOException {
        final java.io.ByteArrayOutputStream out = new ByteArrayOutputStream();
        final Base64.OutputStream decodeOutputStream = new Base64.OutputStream(out, Base64.DECODE);
        final Base64.OutputStream encodeOutputStream = new Base64.OutputStream(decodeOutputStream,
                Base64.ENCODE);

        encodeOutputStream.write(BINARY_SEQUENCE);
        encodeOutputStream.flush();

        assertEquals(BINARY_SEQUENCE, out.toByteArray());

        decodeOutputStream.close();
        encodeOutputStream.close();
    }

    /**
     * Tests the flush behavior of the output stream.
     * <p>
     * Tries to write a buffer whose number of bytes is very small (a couple of bytes
     * only) and then checks that flushing actually works produces the desired output.
     */
    public void testFlushBehaviorBasic() throws IOException {
        final java.io.ByteArrayOutputStream out = new ByteArrayOutputStream();
        final Base64.OutputStream decodeOutputStream = new Base64.OutputStream(out, Base64.DECODE);
        final Base64.OutputStream encodeOutputStream = new Base64.OutputStream(decodeOutputStream,
                Base64.ENCODE);

        encodeOutputStream.write(FLUSH_TEST_BINARY_BUFFER);
        // Check that no bytes were passed on to the output
        assertEquals(0, out.toByteArray().length);

        // Flush
        encodeOutputStream.flush();
        // Check that the output is correct
        assertEquals(FLUSH_TEST_BINARY_BUFFER, out.toByteArray());

        decodeOutputStream.close();
        encodeOutputStream.close();
    }

    /**
     * Tests that close produces a flush behavior on the output stream.
     * <p>
     * Tries to write a buffer whose number of bytes is very small (a couple of bytes
     * only) and then checks that closing the buffer actually works produces the desired
     * output.
     */
    public void testCloseFlushBehaviorBasic() throws IOException {
        final java.io.ByteArrayOutputStream out = new ByteArrayOutputStream();
        final Base64.OutputStream decodeOutputStream = new Base64.OutputStream(out, Base64.DECODE);
        final Base64.OutputStream encodeOutputStream = new Base64.OutputStream(decodeOutputStream,
                Base64.ENCODE);

        encodeOutputStream.write(FLUSH_TEST_BINARY_BUFFER);

        // Close
        decodeOutputStream.close();
        encodeOutputStream.close();

        // Check that the output is correct
        assertEquals(FLUSH_TEST_BINARY_BUFFER, out.toByteArray());

    }
}
