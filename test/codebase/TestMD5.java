/*
 * Created on 4/Mai/2005
 */
package codebase;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import junit.framework.TestCase;

/**
 * Implements the {@link MD5} test suite as defined in RFC1321.
 */
public class TestMD5 extends
        TestCase {

    /**
     * Runs RFC1321 test suite.
     */
    public void testMD5() {
        assertEquals("D41D8CD98F00B204E9800998ECF8427E", MD5.getDigest("").toUpperCase());
        assertEquals("0CC175B9C0F1B6A831C399E269772661", MD5.getDigest("a").toUpperCase());
        assertEquals("900150983CD24FB0D6963F7D28E17F72", MD5.getDigest("abc").toUpperCase());
        assertEquals("F96B697D7CB7938D525A2F31AAF161D0", MD5.getDigest("message digest")
                .toUpperCase());
        assertEquals("C3FCD3D76192E4007DFB496CCA67E13B", MD5
                .getDigest("abcdefghijklmnopqrstuvwxyz").toUpperCase());
        assertEquals("D174AB98D277D9F5A5611C2C9F419D9F", MD5.getDigest(
                "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "abcdefghijklmnopqrstuvwxyz" + "0123456789")
                .toUpperCase());
        assertEquals("57EDF4A22BE3C955AC49DA2E2107B67A", MD5.getDigest(
                "12345678901234567890" + "1234567890" + "1234567890" + "1234567890" + "1234567890"
                        + "1234567890" + "1234567890").toUpperCase());
    }

    /**
     * Tests the input stream digest reader with a large string.
     */
    public void testDigestStream() throws UnsupportedEncodingException {
        final String str = "12345678901234567890" + "1234567890" + "1234567890" + "1234567890"
                + "1234567890" + "1234567890" + "1234567890";

        final InputStream input = new ByteArrayInputStream(str.getBytes("UTF-8"));
        final String md5;
        try {
            md5 = MD5.getDigest(input);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        assertEquals("57EDF4A22BE3C955AC49DA2E2107B67A", md5.toUpperCase());
    }
}
