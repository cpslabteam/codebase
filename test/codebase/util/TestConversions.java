/*
 * Created on 9/Jun/2005
 *  
 */
package codebase.util;

import codebase.util.Conversions;
import codebase.util.Format;
import junit.framework.TestCase;

/**
 * 
 * 
 *  
 */
public class TestConversions
        extends TestCase {

    public void testFormatBytes() {
        assertEquals("0B", Format.formatBytes(0, 0));
        assertEquals("0B", Format.formatBytes(0, 1));
        assertEquals("50B", Format.formatBytes(50, 0));
        assertEquals("1.0KB", Format.formatBytes(Conversions.KB, 0));
        assertEquals("1.22KB", Format.formatBytes(1250, 2));
        assertEquals("3.0KB", Format.formatBytes(3 * Conversions.KB, 0));
        assertEquals("1.0MB", Format.formatBytes(Conversions.MB, 0));
        assertEquals("3.0MB", Format.formatBytes(3 * Conversions.MB, 0));
        assertEquals("1.88MB", Format.formatBytes(1971322, 2));
        assertEquals("1.0GB", Format.formatBytes(Conversions.GB, 0));
        assertEquals("3.0GB", Format.formatBytes(3 * Conversions.GB, 0));
    }

    public void testParseBytes() {
        assertEquals(0, Format.parseBytes("0B"));
        assertEquals(0, Format.parseBytes("0.0B"));
        assertEquals(50, Format.parseBytes("50B"));
        assertEquals(Conversions.KB, Format.parseBytes("1.0KB"));
        assertEquals(1250, Format.parseBytes("1.22KB"));
        assertEquals(3 * Conversions.KB, Format.parseBytes("3.0KB"));
        assertEquals(Conversions.MB, Format.parseBytes("1.0MB"));
        assertEquals(3 * Conversions.MB, Format.parseBytes("3.0MB"));
        assertEquals(1971323, Format.parseBytes("1.88MB"));
        assertEquals(Conversions.GB, Format.parseBytes("1.0GB"));
        assertEquals(3 * Conversions.GB, Format.parseBytes("3.0GB"));
    }
}