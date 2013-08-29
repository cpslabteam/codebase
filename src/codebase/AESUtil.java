package codebase;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


/**
 * Utility class that simplifies AES cyphering and decyphering operations.
 * <p>
 * The first step is to create the cypher key and the IV parameter. This can be created
 * from a byte[] (with correct size for an AES key in a multiple on 16 bytes) and then
 * call {@link #getKey(byte[])} and {@link #getIv(byte[])}.
 * <p>
 * The main methods are to be called for cyphering and decyphering are, respectively,
 * {@link #cypherText(Key, IvParameterSpec, String)} and
 * {@link #decypherText(Key, IvParameterSpec, String)}.
 * <p>
 * The number of bytes in the message must be a multiple of 16 bytes. Therefore, the
 * message will be padded if the message size does not en on a 16-byte boundary.
 * 
 * @see javax.crypto.Cipher
 */
public final class AESUtil {

    /**
     * Default padding for small messages.
     */
    private static final int AES_DEFAULT_PADDING = 16;

    /**
     * Encoding used for text.
     */
    private static final String DEFAULT_STRING_ENCODING = "UTF-8";

    /**
     * Encrypts text with the given key.
     * <p>
     * The text is converted to UTF-8 and padded with zeroes to meet the AES block
     * standard before being encrypted. The result is returned in Base64 encoding.
     * 
     * @param key the AES key
     * @param iv the initialization vector
     * @param plaintext The text to encrypt.
     * @return encrypted data in Base64 encoding.
     * @throws GeneralSecurityException the general security exception
     */
    public static String cypherText(Key key, IvParameterSpec iv, String plaintext) throws GeneralSecurityException {
        /*
         * AES ( == AES/ECB/PKCS5Padding) AES/CBC/NoPadding AES/CBC/PKCS5Padding
         * AES/CBC/ISO10126Padding
         */
        final Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);

        try {
            final byte[] inputBytes = plaintext.getBytes(DEFAULT_STRING_ENCODING);
            final byte[] outputBytes = cipher.doFinal(padBuffer(inputBytes));

            return Base64.encode(outputBytes);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Decyphers text with the given key. After decyphering, the text will be in UTF-8.
     * 
     * @param key AES key
     * @param iv the iv
     * @param cyphertext encrypted data in Base64 encoding.
     * @return the decyphered text
     * @throws GeneralSecurityException the general security exception
     */
    public static String decypherText(Key key, IvParameterSpec iv, String cyphertext) throws GeneralSecurityException {
        final Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, key, iv);

        final byte[] inputBytes = Base64.decode(cyphertext);
        final byte[] outputBytes = cipher.doFinal(inputBytes);

        final ByteBuffer bb = ByteBuffer.wrap(outputBytes);
        return Charset.forName(DEFAULT_STRING_ENCODING).decode(bb).toString();
    }


    /**
     * Instantiates an initialization vector (IV).
     * 
     * @param ivData the initialization vector bytes
     * @return an initialization vector
     */
    public static IvParameterSpec getIv(byte[] ivData) {
        return new IvParameterSpec(ivData);
    }

    /**
     * Instantiates an AES key.
     * 
     * @param keyData The key bytes
     * @return An AES key
     */
    public static Key getKey(byte[] keyData) {
        return new SecretKeySpec(keyData, "AES");
    }


    /**
     * Pads a byte array with nulls so that its length is a multiple of the AES block.
     * 
     * @param bytes the bytes to be padded
     * @return the byte[] padded in null bytes
     */
    public static byte[] padBuffer(byte[] bytes) {
        int r = bytes.length % AES_DEFAULT_PADDING;
        if (r == 0) {
            return bytes;
        } else {
            int newLength = bytes.length + (AES_DEFAULT_PADDING - r);
            return Arrays.copyOf(bytes, newLength);
        }
    }

    /**
     * Prevent the instantiation of this utility class.
     */
    private AESUtil() {
    }

}
