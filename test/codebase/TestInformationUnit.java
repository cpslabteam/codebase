/*
 * Created on 9/Jun/2005
 */
package codebase;

import junit.framework.TestCase;

/**
 * Tests the {@link InformationUnit} class.
 */
public class TestInformationUnit extends
        TestCase {

    public void testFormatBytes() {
        assertEquals("0B", InformationUnit.formatBytes(0, 0));
        assertEquals("0B", InformationUnit.formatBytes(0, 1));
        assertEquals("50B", InformationUnit.formatBytes(50, 0));
        assertEquals("1.0KB", InformationUnit.formatBytes(InformationUnit.KB, 0));
        assertEquals("1.22KB", InformationUnit.formatBytes(1250, 2));
        assertEquals("3.0KB", InformationUnit.formatBytes(3 * InformationUnit.KB, 0));
        assertEquals("1.0MB", InformationUnit.formatBytes(InformationUnit.MB, 0));
        assertEquals("3.0MB", InformationUnit.formatBytes(3 * InformationUnit.MB, 0));
        assertEquals("1.88MB", InformationUnit.formatBytes(1971322, 2));
        assertEquals("1.0GB", InformationUnit.formatBytes(InformationUnit.GB, 0));
        assertEquals("3.0GB", InformationUnit.formatBytes(3 * InformationUnit.GB, 0));
    }

    public void testParseBytes() {
        assertEquals(0, InformationUnit.parseBytes("0B"));
        assertEquals(0, InformationUnit.parseBytes("0.0B"));
        assertEquals(50, InformationUnit.parseBytes("50B"));
        assertEquals(InformationUnit.KB, InformationUnit.parseBytes("1.0KB"));
        assertEquals(1250, InformationUnit.parseBytes("1.22KB"));
        assertEquals(3 * InformationUnit.KB, InformationUnit.parseBytes("3.0KB"));
        assertEquals(InformationUnit.MB, InformationUnit.parseBytes("1.0MB"));
        assertEquals(3 * InformationUnit.MB, InformationUnit.parseBytes("3.0MB"));
        assertEquals(1971323, InformationUnit.parseBytes("1.88MB"));
        assertEquals(InformationUnit.GB, InformationUnit.parseBytes("1.0GB"));
        assertEquals(3 * InformationUnit.GB, InformationUnit.parseBytes("3.0GB"));
    }
}
