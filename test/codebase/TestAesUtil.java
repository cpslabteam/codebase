package codebase;

import java.security.GeneralSecurityException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import junit.framework.TestCase;

public class TestAesUtil extends
        TestCase {

    private static final String[] TEST_STRINGS = new String[] {
            "",
            "1",
            "Hello World",
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

    public void testCryptDecrypt() throws GeneralSecurityException {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);
        SecretKey aesKey = kgen.generateKey();

        for (String s : TEST_STRINGS) {
            // Cypher
            final String cypheredText = AesUtil.cypherText(
                    AesUtil.getKey(aesKey.toString().getBytes()),
                    AesUtil.getIv(aesKey.getEncoded()), s);

            // Decypher
            final String decypheredText = AesUtil.decypherText(
                    AesUtil.getKey(aesKey.toString().getBytes()),
                    AesUtil.getIv(aesKey.getEncoded()), cypheredText);
            
            // Check that decyphering the cyphered text is the original
            assertEquals(s, decypheredText);
        }
    }
}
