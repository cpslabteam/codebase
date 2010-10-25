/*
 * Created on 3/Jun/2005
 *  
 */
package codebase;

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
    
    /**
     * Runs the checksum on a file of the os installation.
     */
    public void testMd5File() {
        final String windowsFilePath = "C:\\ntldr";
        final String filePath = windowsFilePath;
        try {
            final String md5 = Files.md5(windowsFilePath);
            // System.out.println(md5);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
}
