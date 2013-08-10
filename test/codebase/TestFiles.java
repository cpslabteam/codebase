/*
 * Created on 3/Jun/2005
 *  
 */
package codebase;

import junit.framework.TestCase;

/**
 * Test the file utilities.
 */
public class TestFiles
        extends TestCase {
    
    private static final String SUCCESSFULL_FILE_NAME = "./sucessfullFile.dat";
    
    /**
     * Tests the file creation in invalid and valid places.
     */
    public void testVerifyCanCreateFile() {
        assertTrue(!Files.verifyCanCreateFile("C:", 10));
        assertTrue(Files.verifyCanCreateFile(SUCCESSFULL_FILE_NAME, 10));
    }
   
}
