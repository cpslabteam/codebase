/*
 * Created on 3/Jun/2005
 *  
 */
package codebase.util;

import codebase.util.Files;
import junit.framework.TestCase;

/**
 * Test the file utilities
 */
public class TestFiles
        extends TestCase {
    
    final String sucessfullFileName = "./sucessfullFile.dat";
    
    /**
     * Tests the file creation in invalid and valid places
     */
    public void testVerifyCanCreateFile() {
        assertTrue(!Files.verifyCanCreateFile("C:", 10));
        assertTrue(Files.verifyCanCreateFile(sucessfullFileName, 10));
    }
   
}
