package codebase;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

import codebase.junit.EnhancedTestCase;

import junit.framework.TestCase;

/**
 * Tests the {@link AESUtil} utility class.
 */
public class TestAesUtil extends
        TestCase {

    /**
     * A key with the correct size.
     */
    private static final byte[] KEY_VALUE = new byte[] { 'T', 'h', 'i', 's', 'I', 's', 'A', 'S',
            'e', 'c', 'r', 'e', 't', 'K', 'e', 'y' };

    /**
     * An initialization vector with the correct size.
     */
    private static final byte[] IV_VALUE = new byte[] { '1', '2', '3', '4', '5', '6', '7', '8',
            '9', '0', '1', '2', '3', '4', '5', '6' };

    /**
     * Test strings.
     */
    private static final String[] TEST_STRINGS = new String[] {
            "",
            "1",
            "1234567890",
            "Hello World!",
            "At vero eos et accusamus et iusto odio dignissimos ducimus qui "
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
                    + "asperiores repellat" };

    /**
     * Test that cyphered messages are correctly decyphered.
     * <p>
     * Cyphers a messages, then decyphers it and compares it padded because the AES
     * algorithm forces padding to a certaing block size.
     */
    public void testCryptDecrypt() throws GeneralSecurityException, UnsupportedEncodingException {
        for (String s : TEST_STRINGS) {
            // Cypher
            final String cypheredText = AESUtil.cypherText(AESUtil.getKey(KEY_VALUE),
                    AESUtil.getIv(IV_VALUE), s);

            // Decypher
            final String decypheredText = AESUtil.decypherText(AESUtil.getKey(KEY_VALUE),
                    AESUtil.getIv(IV_VALUE), cypheredText);

            // Check that decyphering the cyphered text is the original after padding
            EnhancedTestCase.assertEquals(AESUtil.padBuffer(s.getBytes("UTF-8")),
                    decypheredText.getBytes("UTF-8"));
        }
    }
}
