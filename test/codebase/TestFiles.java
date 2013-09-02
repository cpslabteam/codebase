/*
 * Created on 3/Jun/2005
 */
package codebase;

import java.io.File;
import java.io.IOException;

import codebase.junit.FileBasedTestCase;

/**
 * Test the {@link Files} utilities class.
 */
public class TestFiles extends
        FileBasedTestCase {

    private static final String SUCCESSFULL_FILE_NAME = Filenames.concat(getTestDirectory()
            .getAbsolutePath(), "successfulfile.dat");
    private static final File ABSOLUTE_FILE = new File(SUCCESSFULL_FILE_NAME);
    private static final File BASE_FILE = new File(ABSOLUTE_FILE.getParent());
    private static final String ABSOLUTE_PATH = Filenames.getFullPath(ABSOLUTE_FILE
            .getAbsolutePath());

    @Override
    public void setUp() throws IOException {
        Files.deleteDirectory(BASE_FILE);
        assertTrue(BASE_FILE.mkdirs());
    }

    @Override
    public void tearDown() throws IOException {
        Files.deleteDirectory(BASE_FILE);
    }

    /**
     * Tests the file creation in invalid and valid places.
     */
    public void testVerifyCanCreateFile() {
        // Illegal file name
        assertTrue(!Files.verifyCanCreateFile("C:", 10));
        
        // Regular file
        assertTrue(Files.verifyCanCreateFile(SUCCESSFULL_FILE_NAME, 10));
    }

    public void testCreatePath() {
        // Null cases
        assertEquals(Files.getAbsolutePath(ABSOLUTE_PATH, null), null);
        assertEquals(Files.getAbsolutePath(null, ABSOLUTE_PATH), null);
        assertEquals(Files.getAbsolutePath(ABSOLUTE_PATH, ""), null);
        assertEquals(Files.getAbsolutePath("", ABSOLUTE_PATH), null);

        // Normal case
        assertEquals(Files.getAbsolutePath(ABSOLUTE_PATH, "test1"),
                Filenames.concat(ABSOLUTE_PATH, "test1"));

        // Normal case with the filename included
        assertEquals(Files.getAbsolutePath(ABSOLUTE_FILE.getAbsolutePath(), "test1"),
                Filenames.concat(ABSOLUTE_PATH, "test1"));
    }


}
