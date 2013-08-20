package codebase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import codebase.Base64.InputStream;
import codebase.junit.EnhancedTestCase;
import codebase.streams.StringInputStream;

import junit.framework.TestCase;

/**
 * Tests the {@link Base64} class.
 * <p>
 * This class is tested by encoding and then decoding different strings to base-64.
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

    private static final byte[] BINARY_SEQUENCE = new byte[255];

    static {
        java.util.Arrays.fill(BINARY_ZEROS, (byte) 0);
        java.util.Arrays.fill(BINARY_ONES, (byte) 1);
        java.util.Arrays.fill(BINARY_FFS, (byte) 0xFF);

        for (int i = 0; i < 255; i++) {
            BINARY_SEQUENCE[i] = (byte) (i & 0xFF);
        }
    }

    public void testSimple() throws UnsupportedEncodingException {
        String encoded = Base64.encode(HELLO_WORLD.getBytes("UTF-8"));
        String decoded = new String(Base64.decode(encoded));
        assertEquals(HELLO_WORLD, decoded);
    }

    public void testEmpty() throws UnsupportedEncodingException {
        String encoded = Base64.encode("".getBytes("UTF-8"));
        String decoded = new String(Base64.decode(encoded));
        assertEquals("", decoded);
    }

    public void testText() throws UnsupportedEncodingException {
        String encoded = Base64.encode(TEXT.getBytes("UTF-8"));
        String decoded = new String(Base64.decode(encoded));
        assertEquals(TEXT, decoded);
    }

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

    public void testBinarySequences() {
        final String encodedSequence = Base64.encode(BINARY_SEQUENCE);
        final byte[] decodedSequence = Base64.decode(encodedSequence);
        assertEquals(BINARY_SEQUENCE, decodedSequence);
    }

    public void testInputStreamString() throws IOException {
        final java.io.InputStream in = new StringInputStream(TEXT);
        final Base64.InputStream encodeInputStream = new Base64.InputStream(in, Base64.ENCODE);
        final Base64.InputStream decodeInputStream = new Base64.InputStream(encodeInputStream,
                Base64.DECODE);

        final byte[] inputBytes = TEXT.getBytes("UTF-8");
        final byte[] outputBytes = new byte[inputBytes.length];
        decodeInputStream.read(outputBytes);

        assertEquals(inputBytes, outputBytes);

        decodeInputStream.close();
        encodeInputStream.close();
    }

    public void testInputStreamBinary() throws IOException {
        final java.io.InputStream in = new ByteArrayInputStream(BINARY_SEQUENCE);
        final Base64.InputStream encodeInputStream = new Base64.InputStream(in, Base64.ENCODE);

        final Base64.InputStream decodeInputStream = new Base64.InputStream(encodeInputStream,
                Base64.DECODE);

        final byte[] outputBytes = new byte[BINARY_SEQUENCE.length];
        decodeInputStream.read(outputBytes);

        assertEquals(BINARY_SEQUENCE, outputBytes);

        decodeInputStream.close();
        encodeInputStream.close();
    }

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

    public void testOutputBinaryString() throws IOException {
        final java.io.ByteArrayOutputStream out = new ByteArrayOutputStream();
        final Base64.OutputStream decodeOutputStream = new Base64.OutputStream(out, Base64.DECODE);
        final Base64.OutputStream encodeOutputStream = new Base64.OutputStream(decodeOutputStream,
                Base64.ENCODE);

        encodeOutputStream.write(BINARY_SEQUENCE);

        assertEquals(BINARY_SEQUENCE, out.toByteArray());

        decodeOutputStream.close();
        encodeOutputStream.close();
    }
}
