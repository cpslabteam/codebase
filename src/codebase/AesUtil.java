package codebase;

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
 */
public final class AesUtil {

    /**
     * Default padding for small messages.
     */
    private static final int AES_DEFAULT_PADDING = 16;

    /**
     * Encoding used for text.
     */
    private static final String DEFAULT_STRING_ENCODING = "UTF-8";

    /**
     * Prevent the instantiation of this utility class.
     */
    private AesUtil() {
    }

    /**
     * Pads a byte array with nulls so that its length is a multiple of a given length.
     * 
     * @param bytes the bytes to be padded
     * @param lengthMultiple the length multiple
     * @return the byte[] padded in null bytes
     */
    private static byte[] padWithNulls(byte[] bytes, int lengthMultiple) {
        int r = bytes.length % lengthMultiple;
        if (r == 0) {
            return bytes;
        } else {
            int newLength = bytes.length + (lengthMultiple - r);
            return Arrays.copyOf(bytes, newLength);
        }
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
     * Instantiates an initialization vector (IV).
     * 
     * @param ivData the initialization vector bytes
     * @return an initialization vector
     */
    public static IvParameterSpec getIv(byte[] ivData) {
        return new IvParameterSpec(ivData);
    }


    /**
     * Encrypts text with the given key.
     * <p>
     * The text is converted to UTF-8 before being encrypted. The result is returned in
     * Base64 encoding.
     * 
     * @param key AES key
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

        final byte[] inputBytes = Charset.forName(DEFAULT_STRING_ENCODING).encode(plaintext)
                .array();
        final byte[] outputBytes = cipher.doFinal(padWithNulls(inputBytes, AES_DEFAULT_PADDING));

        return Base64.encode(outputBytes);
    }

    /**
     * Decyphers text with the given key. After decyphering, the text will be in UTF-8.
     * 
     * @param key AES key
     * @param iv the iv
     * @param cyphertext encrypted data in Base64 encoding.
     * @return The decyphered text
     * @throws GeneralSecurityException the general security exception
     */
    public static String decypherText(Key key, IvParameterSpec iv, String cyphertext) throws GeneralSecurityException {
        final Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, key, iv);

        byte[] inputBytes = Base64.decode(cyphertext);
        byte[] outputBytes = cipher.doFinal(inputBytes);

        ByteBuffer bb = ByteBuffer.wrap(outputBytes);
        return Charset.forName(DEFAULT_STRING_ENCODING).decode(bb).toString();
    }

}
