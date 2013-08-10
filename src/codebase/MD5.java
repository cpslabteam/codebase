/*
 * Created on 4/Mai/2005
 */
package codebase;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import codebase.binary.Binary;

/**
 * Implementation of the MD5 message digest algorithm.
 * <p>
 * The MD5 algorithm (RFC1321) developed by Professor Ronald L Rivest of MIT.
 * <p>
 * In essence, MD5 is a way to verify data integrity, and is much more
 * reliable than checksum and many other commonly used methods.
 * <p>
 * Note that for security critical applications MD5 should no be used. Instead
 * use SHA1.
 * <p>
 * Licensing: Implements RSA Data Security, Inc. MD5 Message-Digest Algorithm
 * and/or its reference implementation.
 */
public final class MD5 {

    /**
     * The size in bytes of the message digest output
     */
    private static final int DIGEST_SIZE = 16;

    /**
     * Size in bytes of the treatment unit. We perform the treatment in block
     * of 64 bytes.
     */
    private static final int BLOCK_TREATMENT_SIZE = DIGEST_SIZE
            * Binary.INT_LENGTH_BYTES;

    /**
     * Size of the blocks to be read from the stream
     */
    private static final int STREAM_BLOCK_READ_SIZE = 2048;

    /**
     * Encapsulates the math details and constants used
     */
    private static final class Funcs {
        private static final int S11 = 7;

        private static final int S12 = 12;

        private static final int S13 = 17;

        private static final int S14 = 22;

        private static final int S21 = 5;

        private static final int S22 = 9;

        private static final int S23 = 14;

        private static final int S24 = 20;

        private static final int S31 = 4;

        private static final int S32 = 11;

        private static final int S33 = 16;

        private static final int S34 = 23;

        private static final int S41 = 6;

        private static final int S42 = 10;

        private static final int S43 = 15;

        private static final int S44 = 21;

        private static byte[] padding = {
                (byte) 0x80, (byte) 0, (byte) 0, (byte) 0, (byte) 0,
                (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0,
                (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0,
                (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0,
                (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0,
                (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0,
                (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0,
                (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0,
                (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0,
                (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0,
                (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0};

        private static final int F(int x, int y, int z) {
            return ((x & y) | ((~x) & z));
        }

        private static final int G(int x, int y, int z) {
            return ((x & z) | (y & (~z)));
        }

        private static final int H(int x, int y, int z) {
            return (x ^ y ^ z);
        }

        private static final int I(int x, int y, int z) {
            return (y ^ (x | (~z)));
        }

        private static final int rotateLeft(int x, int n) {
            return ((x << n) | (x >>> (32 - n)));
        }

        private static final int FF(int a, int b, int c, int d, int x, int s,
                int ac) {
            a += (F(b, c, d) + x + ac);
            a = rotateLeft(a, s);
            a += b;
            return a;
        }

        private static final int GG(int a, int b, int c, int d, int x, int s,
                int ac) {
            a += (G(b, c, d) + x + ac);
            a = rotateLeft(a, s);
            a += b;
            return a;
        }

        private static final int HH(int a, int b, int c, int d, int x, int s,
                int ac) {
            a += (H(b, c, d) + x + ac);
            a = rotateLeft(a, s);
            a += b;
            return a;
        }

        private static final int II(int a, int b, int c, int d, int x, int s,
                int ac) {
            a += (I(b, c, d) + x + ac);
            a = rotateLeft(a, s);
            a += b;
            return a;
        }

        /**
         * Builds a new digest from a previous digest, with a block of 64
         * bytes (four 16 bytes)
         *
         * @param block the block to be added
         * @param offset the the
         */
        private static final void transform(byte[] block, int offset,
                final int[] state) {
            int a = state[0];
            int b = state[1];
            int c = state[2];
            int d = state[3];

            int[] x = Binary.encodeBytesToIntegers(block, offset,
                BLOCK_TREATMENT_SIZE);

            /* Round 1 */
            a = FF(a, b, c, d, x[0], S11, 0xd76aa478); /* 1 */
            d = FF(d, a, b, c, x[1], S12, 0xe8c7b756); /* 2 */
            c = FF(c, d, a, b, x[2], S13, 0x242070db); /* 3 */
            b = FF(b, c, d, a, x[3], S14, 0xc1bdceee); /* 4 */
            a = FF(a, b, c, d, x[4], S11, 0xf57c0faf); /* 5 */
            d = FF(d, a, b, c, x[5], S12, 0x4787c62a); /* 6 */
            c = FF(c, d, a, b, x[6], S13, 0xa8304613); /* 7 */
            b = FF(b, c, d, a, x[7], S14, 0xfd469501); /* 8 */
            a = FF(a, b, c, d, x[8], S11, 0x698098d8); /* 9 */
            d = FF(d, a, b, c, x[9], S12, 0x8b44f7af); /* 10 */
            c = FF(c, d, a, b, x[10], S13, 0xffff5bb1); /* 11 */
            b = FF(b, c, d, a, x[11], S14, 0x895cd7be); /* 12 */
            a = FF(a, b, c, d, x[12], S11, 0x6b901122); /* 13 */
            d = FF(d, a, b, c, x[13], S12, 0xfd987193); /* 14 */
            c = FF(c, d, a, b, x[14], S13, 0xa679438e); /* 15 */
            b = FF(b, c, d, a, x[15], S14, 0x49b40821); /* 16 */
            /* Round 2 */
            a = GG(a, b, c, d, x[1], S21, 0xf61e2562); /* 17 */
            d = GG(d, a, b, c, x[6], S22, 0xc040b340); /* 18 */
            c = GG(c, d, a, b, x[11], S23, 0x265e5a51); /* 19 */
            b = GG(b, c, d, a, x[0], S24, 0xe9b6c7aa); /* 20 */
            a = GG(a, b, c, d, x[5], S21, 0xd62f105d); /* 21 */
            d = GG(d, a, b, c, x[10], S22, 0x2441453); /* 22 */
            c = GG(c, d, a, b, x[15], S23, 0xd8a1e681); /* 23 */
            b = GG(b, c, d, a, x[4], S24, 0xe7d3fbc8); /* 24 */
            a = GG(a, b, c, d, x[9], S21, 0x21e1cde6); /* 25 */
            d = GG(d, a, b, c, x[14], S22, 0xc33707d6); /* 26 */
            c = GG(c, d, a, b, x[3], S23, 0xf4d50d87); /* 27 */
            b = GG(b, c, d, a, x[8], S24, 0x455a14ed); /* 28 */
            a = GG(a, b, c, d, x[13], S21, 0xa9e3e905); /* 29 */
            d = GG(d, a, b, c, x[2], S22, 0xfcefa3f8); /* 30 */
            c = GG(c, d, a, b, x[7], S23, 0x676f02d9); /* 31 */
            b = GG(b, c, d, a, x[12], S24, 0x8d2a4c8a); /* 32 */

            /* Round 3 */
            a = HH(a, b, c, d, x[5], S31, 0xfffa3942); /* 33 */
            d = HH(d, a, b, c, x[8], S32, 0x8771f681); /* 34 */
            c = HH(c, d, a, b, x[11], S33, 0x6d9d6122); /* 35 */
            b = HH(b, c, d, a, x[14], S34, 0xfde5380c); /* 36 */
            a = HH(a, b, c, d, x[1], S31, 0xa4beea44); /* 37 */
            d = HH(d, a, b, c, x[4], S32, 0x4bdecfa9); /* 38 */
            c = HH(c, d, a, b, x[7], S33, 0xf6bb4b60); /* 39 */
            b = HH(b, c, d, a, x[10], S34, 0xbebfbc70); /* 40 */
            a = HH(a, b, c, d, x[13], S31, 0x289b7ec6); /* 41 */
            d = HH(d, a, b, c, x[0], S32, 0xeaa127fa); /* 42 */
            c = HH(c, d, a, b, x[3], S33, 0xd4ef3085); /* 43 */
            b = HH(b, c, d, a, x[6], S34, 0x4881d05); /* 44 */
            a = HH(a, b, c, d, x[9], S31, 0xd9d4d039); /* 45 */
            d = HH(d, a, b, c, x[12], S32, 0xe6db99e5); /* 46 */
            c = HH(c, d, a, b, x[15], S33, 0x1fa27cf8); /* 47 */
            b = HH(b, c, d, a, x[2], S34, 0xc4ac5665); /* 48 */

            /* Round 4 */
            a = II(a, b, c, d, x[0], S41, 0xf4292244); /* 49 */
            d = II(d, a, b, c, x[7], S42, 0x432aff97); /* 50 */
            c = II(c, d, a, b, x[14], S43, 0xab9423a7); /* 51 */
            b = II(b, c, d, a, x[5], S44, 0xfc93a039); /* 52 */
            a = II(a, b, c, d, x[12], S41, 0x655b59c3); /* 53 */
            d = II(d, a, b, c, x[3], S42, 0x8f0ccc92); /* 54 */
            c = II(c, d, a, b, x[10], S43, 0xffeff47d); /* 55 */
            b = II(b, c, d, a, x[1], S44, 0x85845dd1); /* 56 */
            a = II(a, b, c, d, x[8], S41, 0x6fa87e4f); /* 57 */
            d = II(d, a, b, c, x[15], S42, 0xfe2ce6e0); /* 58 */
            c = II(c, d, a, b, x[6], S43, 0xa3014314); /* 59 */
            b = II(b, c, d, a, x[13], S44, 0x4e0811a1); /* 60 */
            a = II(a, b, c, d, x[4], S41, 0xf7537e82); /* 61 */
            d = II(d, a, b, c, x[11], S42, 0xbd3af235); /* 62 */
            c = II(c, d, a, b, x[2], S43, 0x2ad7d2bb); /* 63 */
            b = II(b, c, d, a, x[9], S44, 0xeb86d391); /* 64 */

            state[0] += a;
            state[1] += b;
            state[2] += c;
            state[3] += d;
        }
    }

    /**
     * State of the algorithm comprising the 16-bytes. The state can be used
     * to digest very long streams, by calling update successively
     */
    private static class State {
        /**
         * Number of positions in the array of integers that correspond to the
         * size of 16 bytes
         */
        private static final int NUM_POSITIONS = 16 / Binary.INT_LENGTH_BYTES;

        /**
         * The 4 integer array used to encode that status.
         */
        private final int[] digest = new int[NUM_POSITIONS];

        /**
         * Number of bytes treated from the input buffer.
         */
        private long count = 0;

        /**
         * 64-byte tretment buffer.
         */
        private byte[] buffer = null;

        /**
         * Constructs a new digest.
         */
        State() {
            init();
        }

        /**
         * Initializes the digest array This method can be called to start
         * before digesting another string
         */
        public final void init() {
            digest[0] = 0x67452301;
            digest[1] = 0xefcdab89;
            digest[2] = 0x98badcfe;
            digest[3] = 0x10325476;
            count = 0;
            buffer = new byte[BLOCK_TREATMENT_SIZE];
        }

        /**
         * Updates the digest with length bytes takes from the input buffer
         *
         * @param input the input buffer
         * @param length the number of bytes to be consumed
         */
        private final void update(byte[] input, int length) {
            int index = ((int) (count >> 3)) & 0x3f;
            count += (length << 3);
            int remainingBytes = BLOCK_TREATMENT_SIZE - index;
            int i = 0;
            if (length >= remainingBytes) {
                System.arraycopy(input, 0, buffer, index, remainingBytes);
                Funcs.transform(buffer, 0, digest);
                for (i = remainingBytes; i + 63 < length; i += 64)
                    Funcs.transform(input, i, digest);
                index = 0;
            } else {
                i = 0;
            }
            System.arraycopy(input, i, buffer, index, length - i);
        }

        /**
         * @return
         */
        private byte[] end() {
            byte[] bits = new byte[Binary.BITS_PER_BYTE];
            for (int i = 0; i < Binary.BITS_PER_BYTE; i++) {
                final int displacement = (i * Binary.BITS_PER_BYTE);
                bits[i] = (byte) ((count >>> displacement) & Binary.INT_LOW_BYTE_MASK);
            }
            int index = ((int) (count >> 3)) & 0x3f;
            int padlen = (index < 56) ? (56 - index) : (120 - index);
            update(Funcs.padding, padlen);
            update(bits, Binary.BITS_PER_BYTE);
            return Binary.decodeIntegersToBytes(digest,
                2 * Binary.BITS_PER_BYTE);
        }

    };

    /**
     * Get the digestt of a string. This method constructs the digest of a
     * string following the MD5 (RFC1321) algorithm, and returns it as an hex
     * string assuming a UTF-8 as default encoding
     *
     * @param s the string to be digested
     * @return An instance of String, with the MD5 result of a message.
     */
    public static String getDigest(final String s) {
        final String hexDigest = Binary.toHexString(getDigestRaw(s,
            Strings.UTF8_ENCODING));
        return hexDigest;
    }

    /**
     * Get the digest of a string. This method constructs the digest of a
     * string of a given encoding following the MD5 (RFC1321) algorithm, and
     * returns it as an hex string. This method is encoding aware. This is
     * very important because the number of bytes (not chars) may be
     * different. We need the encoding to obtain the bytes of the String
     * object.
     *
     * @param s the string to be digested
     * @param encoding the encoding to be used
     * @return An instance of String, with the MD5 result of a message.
     */
    public static String getDigest(final String s, final String encoding) {
        final String hexDigest = Binary
            .toHexString(getDigestRaw(s, encoding));
        return hexDigest;
    }

    /**
     * Computes the digest of a stream of bytes
     *
     * @param input the input stream
     * @return an hex string with the message digest
     * @throws IOException if a problem occurs while reading the stream
     */
    public static String getDigest(final InputStream input)
            throws IOException {
        final String hexDigest = Binary.toHexString(getDigestRaw(input));
        return hexDigest;
    }

    /**
     * Returns a message digest from a string and an encoding
     *
     * @param input the input stream to be treated
     * @return the 16-byte array containing the message digest
     * @throws RuntimeException if the encoding is not supported
     */
    public static final byte[] getDigestRaw(final InputStream input)
            throws IOException {
        byte[] bytes = new byte[STREAM_BLOCK_READ_SIZE];

        int size = -1;
        final State state = new State();
        do {
            size = input.read(bytes);
            if (size > 0) {
                state.update(bytes, size);
            }
        } while (size > 0);
        final byte[] digest = state.end();
        return digest;
    }

    /**
     * Returns a message digest from a string and an encoding
     *
     * @param s the string to be treated
     * @param encoding the encoding to be used
     * @return the 16-byte array containg the message digest
     * @throws RuntimeException if the encoding is not supported
     */
    public static final byte[] getDigestRaw(final String s,
            final String encoding) {
        byte[] bytes = null;

        try {
            bytes = s.getBytes(encoding);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("no " + encoding + " encoding");
        }

        State state = new State();
        state.update(bytes, bytes.length);
        final byte[] digest = state.end();
        return digest;
    }
}
