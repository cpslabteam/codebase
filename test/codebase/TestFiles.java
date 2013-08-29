/*
 * Created on 3/Jun/2005
 */
package codebase;

import java.io.File;

import junit.framework.TestCase;

/**
 * Test the {@link Files} utilities class.
 */
public class TestFiles extends
        TestCase {

    private static final String SUCCESSFULL_FILE_NAME = "./test/resources/sucessfullFile.dat";
    private static final File ABSOLUTE = new File(SUCCESSFULL_FILE_NAME);
    private static final String ABSOLUTE_PATH = Filenames.getFullPath(ABSOLUTE.getAbsolutePath());

    /**
     * Tests the file creation in invalid and valid places.
     */
    public void testVerifyCanCreateFile() {
        assertTrue(!Files.verifyCanCreateFile("C:", 10));
        assertTrue(Files.verifyCanCreateFile(SUCCESSFULL_FILE_NAME, 10));
    }

    public void testCreatePath() {
        // null cases
        assertEquals(Files.getAbsolutePath(ABSOLUTE_PATH, null), null);
        assertEquals(Files.getAbsolutePath(null, ABSOLUTE_PATH), null);
        assertEquals(Files.getAbsolutePath(ABSOLUTE_PATH, ""), null);
        assertEquals(Files.getAbsolutePath("", ABSOLUTE_PATH), null);

        // Normal case
        assertEquals(Files.getAbsolutePath(ABSOLUTE_PATH, "test1"),
                Filenames.concat(ABSOLUTE_PATH, "test1"));

        // Normal case with the filename included
        assertEquals(Files.getAbsolutePath(ABSOLUTE.getAbsolutePath(), "test1"),
                Filenames.concat(ABSOLUTE_PATH, "test1"));
    }


}
