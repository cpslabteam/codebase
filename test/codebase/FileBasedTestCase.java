package codebase;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Arrays;

import junit.framework.TestCase;

/**
 * Base class for test cases doing tests with files.
 */
public abstract class FileBasedTestCase extends
        TestCase {

    private static File testDir;

    public static File getTestDirectory() {
        if (testDir == null) {
            testDir = (new File("test/io/")).getAbsoluteFile();
        }
        return testDir;
    }


    /**
     * Assert that the content of two files is the same.
     * 
     * @param f0 a file to compare
     * @param f1 another file to compare
     */
    protected void assertEqualContent(File f0, File f1) throws IOException {
        /*
         * This doesn't work because the filesize isn't updated until the file
         * is closed. assertTrue( "The files " + f0 + " and " + f1 + " have
         * differing file sizes (" + f0.length() + " vs " + f1.length() + ")", (
         * f0.length() == f1.length() ) );
         */
        final InputStream is0 = new java.io.FileInputStream(f0);
        try {
            final InputStream is1 = new java.io.FileInputStream(f1);
            try {
                final byte[] buf0 = new byte[1024];
                final byte[] buf1 = new byte[1024];
                int n0 = 0;
                int n1 = 0;

                while (-1 != n0) {
                    n0 = is0.read(buf0);
                    n1 = is1.read(buf1);
                    assertTrue("The files " + f0 + " and " + f1
                            + " have differing number of bytes available (" + n0 + " vs " + n1
                            + ")", (n0 == n1));

                    assertTrue("The files " + f0 + " and " + f1 + " have different content",
                            Arrays.equals(buf0, buf1));
                }
            } finally {
                is1.close();
            }
        } finally {
            is0.close();
        }
    }

    /**
     * Assert that the content of a file is equal to that in a byte[].
     * 
     * @param file the file to verify
     * @param b0 a byte array to check for
     */
    protected void assertEqualContent(File file, byte[] b0) throws IOException {
        InputStream is = new java.io.FileInputStream(file);
        try {
            final byte[] b1 = new byte[b0.length];
            final int numRead = is.read(b1);
            assertTrue("Different number of bytes", numRead == b0.length && is.available() == 0);
            for (int i = 0; i < numRead; i++) {
                assertTrue("Byte " + i + " differs (" + b0[i] + " != " + b1[i] + ")",
                        b0[i] == b1[i]);
            }
        } finally {
            is.close();
        }
    }

    /**
     * Assert that the content of a file is equal to that in a char[].
     * 
     * @param c0 a char array to check for
     * @param file the file to verify
     */
    protected void assertEqualContent(char[] c0, File file) throws IOException {
        Reader ir = new java.io.FileReader(file);
        try {
            char[] c1 = new char[c0.length];
            int numRead = ir.read(c1);
            assertTrue("Different number of bytes", numRead == c0.length);
            for (int i = 0; i < numRead; i++) {
                assertTrue("Byte " + i + " differs (" + c0[i] + " != " + c1[i] + ")",
                        c0[i] == c1[i]);
            }
        } finally {
            ir.close();
        }
    }


    /**
     * Verifies if a file can be deleted.
     * 
     * @param file the file to be checked and deleted
     * @throws Exception if the file cannot be deleted
     */
    protected void checkDelete(final File file) throws Exception {
        if (file.exists()) {
            assertTrue("Couldn't delete file: " + file, file.delete());
        }
    }
}
