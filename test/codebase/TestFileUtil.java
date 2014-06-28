/*
 * Created on 3/Jun/2005
 */
package codebase;

import java.io.File;
import java.io.IOException;

import codebase.junit.FileBasedTestCase;

/**
 * Test the {@link FileUtil} utilities class.
 */
public class TestFileUtil extends
        FileBasedTestCase {

    private static final String SUCCESSFULL_FILE_NAME = FilenameUtil.concat(getTestDirectory()
            .getAbsolutePath(), "successfulfile.dat");
    private static final File ABSOLUTE_FILE = new File(SUCCESSFULL_FILE_NAME);
    private static final File BASE_FILE = new File(ABSOLUTE_FILE.getParent());
    private static final String ABSOLUTE_PATH = FilenameUtil.getFullPath(ABSOLUTE_FILE
            .getAbsolutePath());

    @Override
    public void setUp() throws IOException {
        FileUtil.deleteDirectory(BASE_FILE);
        assertTrue(BASE_FILE.mkdirs());
    }

    @Override
    public void tearDown() throws IOException {
        FileUtil.deleteDirectory(BASE_FILE);
    }

    /**
     * Tests the file creation in invalid and valid places.
     */
    public void testVerifyCanCreateFile() {
        // Illegal file name
    	final String osName = System.getProperty("os.name").toLowerCase();
    	//This tests won't work if they are run as admin/root
    	if(osName.contains("win")){
    		assertTrue(!FileUtil.verifyCanCreateFile("C:", 10));
    	} else {
    		assertTrue(!FileUtil.verifyCanCreateFile("/", 10));
    	}
        // Regular file
        assertTrue(FileUtil.verifyCanCreateFile(SUCCESSFULL_FILE_NAME, 10));
    }

    public void testCreatePath() {
        // Null cases
        assertEquals(FileUtil.getAbsolutePath(ABSOLUTE_PATH, null), null);
        assertEquals(FileUtil.getAbsolutePath(null, ABSOLUTE_PATH), null);
        assertEquals(FileUtil.getAbsolutePath(ABSOLUTE_PATH, ""), null);
        assertEquals(FileUtil.getAbsolutePath("", ABSOLUTE_PATH), null);

        // Normal case
        assertEquals(FileUtil.getAbsolutePath(ABSOLUTE_PATH, "test1"),
                FilenameUtil.concat(ABSOLUTE_PATH, "test1"));

        // Normal case with the filename included
        assertEquals(FileUtil.getAbsolutePath(ABSOLUTE_FILE.getAbsolutePath(), "test1"),
                FilenameUtil.concat(ABSOLUTE_PATH, "test1"));
    }


}
