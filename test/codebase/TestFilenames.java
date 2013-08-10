package codebase;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import codebase.Filenames;


/**
 *
 */
public class TestFilenames
        extends FileBasedTestCase {
    
    private static final String SEP = "" + File.separatorChar;
    
    private static final boolean WINDOWS = (File.separatorChar == '\\');
    
   /** @see junit.framework.TestCase#setUp() */
    protected void setUp() throws Exception {
    }
    
    /** @see junit.framework.TestCase#tearDown() */
    protected void tearDown() throws Exception {
        //FileUtils.deleteDirectory(getTestDirectory());
    }
    
    // -----------------------------------------------------------------------
    public void testNormalize() throws Exception {
        assertEquals(null, Filenames.normalize(null));
        assertEquals(null, Filenames.normalize(":"));
        assertEquals(null, Filenames.normalize("1:\\a\\b\\c.txt"));
        assertEquals(null, Filenames.normalize("1:"));
        assertEquals(null, Filenames.normalize("1:a"));
        assertEquals(null, Filenames.normalize("\\\\\\a\\b\\c.txt"));
        assertEquals(null, Filenames.normalize("\\\\a"));
        
        assertEquals("a" + SEP + "b" + SEP + "c.txt", Filenames
            .normalize("a\\b/c.txt"));
        assertEquals("" + SEP + "a" + SEP + "b" + SEP + "c.txt", Filenames
            .normalize("\\a\\b/c.txt"));
        assertEquals("C:" + SEP + "a" + SEP + "b" + SEP + "c.txt",
            Filenames.normalize("C:\\a\\b/c.txt"));
        assertEquals("" + SEP + "" + SEP + "server" + SEP + "a" + SEP + "b"
                + SEP + "c.txt", Filenames
            .normalize("\\\\server\\a\\b/c.txt"));
        assertEquals("~" + SEP + "a" + SEP + "b" + SEP + "c.txt", Filenames
            .normalize("~\\a\\b/c.txt"));
        assertEquals("~user" + SEP + "a" + SEP + "b" + SEP + "c.txt",
            Filenames.normalize("~user\\a\\b/c.txt"));
        
        assertEquals("a" + SEP + "c", Filenames.normalize("a/b/../c"));
        assertEquals("c", Filenames.normalize("a/b/../../c"));
        assertEquals("c" + SEP, Filenames.normalize("a/b/../../c/"));
        assertEquals(null, Filenames.normalize("a/b/../../../c"));
        assertEquals("a" + SEP, Filenames.normalize("a/b/.."));
        assertEquals("a" + SEP, Filenames.normalize("a/b/../"));
        assertEquals("", Filenames.normalize("a/b/../.."));
        assertEquals("", Filenames.normalize("a/b/../../"));
        assertEquals(null, Filenames.normalize("a/b/../../.."));
        assertEquals("a" + SEP + "d", Filenames.normalize("a/b/../c/../d"));
        assertEquals("a" + SEP + "d" + SEP, Filenames
            .normalize("a/b/../c/../d/"));
        assertEquals("a" + SEP + "b" + SEP + "d", Filenames
            .normalize("a/b//d"));
        assertEquals("a" + SEP + "b" + SEP, Filenames
            .normalize("a/b/././."));
        assertEquals("a" + SEP + "b" + SEP, Filenames
            .normalize("a/b/./././"));
        assertEquals("a" + SEP, Filenames.normalize("./a/"));
        assertEquals("a", Filenames.normalize("./a"));
        assertEquals("", Filenames.normalize("./"));
        assertEquals("", Filenames.normalize("."));
        assertEquals(null, Filenames.normalize("../a"));
        assertEquals(null, Filenames.normalize(".."));
        assertEquals("", Filenames.normalize(""));
        
        assertEquals(SEP + "a", Filenames.normalize("/a"));
        assertEquals(SEP + "a" + SEP, Filenames.normalize("/a/"));
        assertEquals(SEP + "a" + SEP + "c", Filenames
            .normalize("/a/b/../c"));
        assertEquals(SEP + "c", Filenames.normalize("/a/b/../../c"));
        assertEquals(null, Filenames.normalize("/a/b/../../../c"));
        assertEquals(SEP + "a" + SEP, Filenames.normalize("/a/b/.."));
        assertEquals(SEP + "", Filenames.normalize("/a/b/../.."));
        assertEquals(null, Filenames.normalize("/a/b/../../.."));
        assertEquals(SEP + "a" + SEP + "d", Filenames
            .normalize("/a/b/../c/../d"));
        assertEquals(SEP + "a" + SEP + "b" + SEP + "d", Filenames
            .normalize("/a/b//d"));
        assertEquals(SEP + "a" + SEP + "b" + SEP, Filenames
            .normalize("/a/b/././."));
        assertEquals(SEP + "a", Filenames.normalize("/./a"));
        assertEquals(SEP + "", Filenames.normalize("/./"));
        assertEquals(SEP + "", Filenames.normalize("/."));
        assertEquals(null, Filenames.normalize("/../a"));
        assertEquals(null, Filenames.normalize("/.."));
        assertEquals(SEP + "", Filenames.normalize("/"));
        
        assertEquals("~" + SEP + "a", Filenames.normalize("~/a"));
        assertEquals("~" + SEP + "a" + SEP, Filenames.normalize("~/a/"));
        assertEquals("~" + SEP + "a" + SEP + "c", Filenames
            .normalize("~/a/b/../c"));
        assertEquals("~" + SEP + "c", Filenames.normalize("~/a/b/../../c"));
        assertEquals(null, Filenames.normalize("~/a/b/../../../c"));
        assertEquals("~" + SEP + "a" + SEP, Filenames.normalize("~/a/b/.."));
        assertEquals("~" + SEP + "", Filenames.normalize("~/a/b/../.."));
        assertEquals(null, Filenames.normalize("~/a/b/../../.."));
        assertEquals("~" + SEP + "a" + SEP + "d", Filenames
            .normalize("~/a/b/../c/../d"));
        assertEquals("~" + SEP + "a" + SEP + "b" + SEP + "d", Filenames
            .normalize("~/a/b//d"));
        assertEquals("~" + SEP + "a" + SEP + "b" + SEP, Filenames
            .normalize("~/a/b/././."));
        assertEquals("~" + SEP + "a", Filenames.normalize("~/./a"));
        assertEquals("~" + SEP, Filenames.normalize("~/./"));
        assertEquals("~" + SEP, Filenames.normalize("~/."));
        assertEquals(null, Filenames.normalize("~/../a"));
        assertEquals(null, Filenames.normalize("~/.."));
        assertEquals("~" + SEP, Filenames.normalize("~/"));
        assertEquals("~" + SEP, Filenames.normalize("~"));
        
        assertEquals("~user" + SEP + "a", Filenames.normalize("~user/a"));
        assertEquals("~user" + SEP + "a" + SEP, Filenames
            .normalize("~user/a/"));
        assertEquals("~user" + SEP + "a" + SEP + "c", Filenames
            .normalize("~user/a/b/../c"));
        assertEquals("~user" + SEP + "c", Filenames
            .normalize("~user/a/b/../../c"));
        assertEquals(null, Filenames.normalize("~user/a/b/../../../c"));
        assertEquals("~user" + SEP + "a" + SEP, Filenames
            .normalize("~user/a/b/.."));
        assertEquals("~user" + SEP + "", Filenames
            .normalize("~user/a/b/../.."));
        assertEquals(null, Filenames.normalize("~user/a/b/../../.."));
        assertEquals("~user" + SEP + "a" + SEP + "d", Filenames
            .normalize("~user/a/b/../c/../d"));
        assertEquals("~user" + SEP + "a" + SEP + "b" + SEP + "d", Filenames
            .normalize("~user/a/b//d"));
        assertEquals("~user" + SEP + "a" + SEP + "b" + SEP, Filenames
            .normalize("~user/a/b/././."));
        assertEquals("~user" + SEP + "a", Filenames.normalize("~user/./a"));
        assertEquals("~user" + SEP + "", Filenames.normalize("~user/./"));
        assertEquals("~user" + SEP + "", Filenames.normalize("~user/."));
        assertEquals(null, Filenames.normalize("~user/../a"));
        assertEquals(null, Filenames.normalize("~user/.."));
        assertEquals("~user" + SEP, Filenames.normalize("~user/"));
        assertEquals("~user" + SEP, Filenames.normalize("~user"));
        
        assertEquals("C:" + SEP + "a", Filenames.normalize("C:/a"));
        assertEquals("C:" + SEP + "a" + SEP, Filenames.normalize("C:/a/"));
        assertEquals("C:" + SEP + "a" + SEP + "c", Filenames
            .normalize("C:/a/b/../c"));
        assertEquals("C:" + SEP + "c", Filenames
            .normalize("C:/a/b/../../c"));
        assertEquals(null, Filenames.normalize("C:/a/b/../../../c"));
        assertEquals("C:" + SEP + "a" + SEP, Filenames
            .normalize("C:/a/b/.."));
        assertEquals("C:" + SEP + "", Filenames.normalize("C:/a/b/../.."));
        assertEquals(null, Filenames.normalize("C:/a/b/../../.."));
        assertEquals("C:" + SEP + "a" + SEP + "d", Filenames
            .normalize("C:/a/b/../c/../d"));
        assertEquals("C:" + SEP + "a" + SEP + "b" + SEP + "d", Filenames
            .normalize("C:/a/b//d"));
        assertEquals("C:" + SEP + "a" + SEP + "b" + SEP, Filenames
            .normalize("C:/a/b/././."));
        assertEquals("C:" + SEP + "a", Filenames.normalize("C:/./a"));
        assertEquals("C:" + SEP + "", Filenames.normalize("C:/./"));
        assertEquals("C:" + SEP + "", Filenames.normalize("C:/."));
        assertEquals(null, Filenames.normalize("C:/../a"));
        assertEquals(null, Filenames.normalize("C:/.."));
        assertEquals("C:" + SEP + "", Filenames.normalize("C:/"));
        
        assertEquals("C:" + "a", Filenames.normalize("C:a"));
        assertEquals("C:" + "a" + SEP, Filenames.normalize("C:a/"));
        assertEquals("C:" + "a" + SEP + "c", Filenames
            .normalize("C:a/b/../c"));
        assertEquals("C:" + "c", Filenames.normalize("C:a/b/../../c"));
        assertEquals(null, Filenames.normalize("C:a/b/../../../c"));
        assertEquals("C:" + "a" + SEP, Filenames.normalize("C:a/b/.."));
        assertEquals("C:" + "", Filenames.normalize("C:a/b/../.."));
        assertEquals(null, Filenames.normalize("C:a/b/../../.."));
        assertEquals("C:" + "a" + SEP + "d", Filenames
            .normalize("C:a/b/../c/../d"));
        assertEquals("C:" + "a" + SEP + "b" + SEP + "d", Filenames
            .normalize("C:a/b//d"));
        assertEquals("C:" + "a" + SEP + "b" + SEP, Filenames
            .normalize("C:a/b/././."));
        assertEquals("C:" + "a", Filenames.normalize("C:./a"));
        assertEquals("C:" + "", Filenames.normalize("C:./"));
        assertEquals("C:" + "", Filenames.normalize("C:."));
        assertEquals(null, Filenames.normalize("C:../a"));
        assertEquals(null, Filenames.normalize("C:.."));
        assertEquals("C:" + "", Filenames.normalize("C:"));
        
        assertEquals(SEP + SEP + "server" + SEP + "a", Filenames
            .normalize("//server/a"));
        assertEquals(SEP + SEP + "server" + SEP + "a" + SEP, Filenames
            .normalize("//server/a/"));
        assertEquals(SEP + SEP + "server" + SEP + "a" + SEP + "c",
            Filenames.normalize("//server/a/b/../c"));
        assertEquals(SEP + SEP + "server" + SEP + "c", Filenames
            .normalize("//server/a/b/../../c"));
        assertEquals(null, Filenames.normalize("//server/a/b/../../../c"));
        assertEquals(SEP + SEP + "server" + SEP + "a" + SEP, Filenames
            .normalize("//server/a/b/.."));
        assertEquals(SEP + SEP + "server" + SEP + "", Filenames
            .normalize("//server/a/b/../.."));
        assertEquals(null, Filenames.normalize("//server/a/b/../../.."));
        assertEquals(SEP + SEP + "server" + SEP + "a" + SEP + "d",
            Filenames.normalize("//server/a/b/../c/../d"));
        assertEquals(SEP + SEP + "server" + SEP + "a" + SEP + "b" + SEP + "d",
            Filenames.normalize("//server/a/b//d"));
        assertEquals(SEP + SEP + "server" + SEP + "a" + SEP + "b" + SEP,
            Filenames.normalize("//server/a/b/././."));
        assertEquals(SEP + SEP + "server" + SEP + "a", Filenames
            .normalize("//server/./a"));
        assertEquals(SEP + SEP + "server" + SEP + "", Filenames
            .normalize("//server/./"));
        assertEquals(SEP + SEP + "server" + SEP + "", Filenames
            .normalize("//server/."));
        assertEquals(null, Filenames.normalize("//server/../a"));
        assertEquals(null, Filenames.normalize("//server/.."));
        assertEquals(SEP + SEP + "server" + SEP + "", Filenames
            .normalize("//server/"));
    }
    
    // -----------------------------------------------------------------------
    public void testNormalizeNoEndSeparator() throws Exception {
        assertEquals(null, Filenames.normalizeNoEndSeparator(null));
        assertEquals(null, Filenames.normalizeNoEndSeparator(":"));
        assertEquals(null, Filenames
            .normalizeNoEndSeparator("1:\\a\\b\\c.txt"));
        assertEquals(null, Filenames.normalizeNoEndSeparator("1:"));
        assertEquals(null, Filenames.normalizeNoEndSeparator("1:a"));
        assertEquals(null, Filenames
            .normalizeNoEndSeparator("\\\\\\a\\b\\c.txt"));
        assertEquals(null, Filenames.normalizeNoEndSeparator("\\\\a"));
        
        assertEquals("a" + SEP + "b" + SEP + "c.txt", Filenames
            .normalizeNoEndSeparator("a\\b/c.txt"));
        assertEquals("" + SEP + "a" + SEP + "b" + SEP + "c.txt", Filenames
            .normalizeNoEndSeparator("\\a\\b/c.txt"));
        assertEquals("C:" + SEP + "a" + SEP + "b" + SEP + "c.txt",
            Filenames.normalizeNoEndSeparator("C:\\a\\b/c.txt"));
        assertEquals("" + SEP + "" + SEP + "server" + SEP + "a" + SEP + "b"
                + SEP + "c.txt", Filenames
            .normalizeNoEndSeparator("\\\\server\\a\\b/c.txt"));
        assertEquals("~" + SEP + "a" + SEP + "b" + SEP + "c.txt", Filenames
            .normalizeNoEndSeparator("~\\a\\b/c.txt"));
        assertEquals("~user" + SEP + "a" + SEP + "b" + SEP + "c.txt",
            Filenames.normalizeNoEndSeparator("~user\\a\\b/c.txt"));
        
        assertEquals("a" + SEP + "c", Filenames
            .normalizeNoEndSeparator("a/b/../c"));
        assertEquals("c", Filenames.normalizeNoEndSeparator("a/b/../../c"));
        assertEquals("c", Filenames.normalizeNoEndSeparator("a/b/../../c/"));
        assertEquals(null, Filenames
            .normalizeNoEndSeparator("a/b/../../../c"));
        assertEquals("a", Filenames.normalizeNoEndSeparator("a/b/.."));
        assertEquals("a", Filenames.normalizeNoEndSeparator("a/b/../"));
        assertEquals("", Filenames.normalizeNoEndSeparator("a/b/../.."));
        assertEquals("", Filenames.normalizeNoEndSeparator("a/b/../../"));
        assertEquals(null, Filenames
            .normalizeNoEndSeparator("a/b/../../.."));
        assertEquals("a" + SEP + "d", Filenames
            .normalizeNoEndSeparator("a/b/../c/../d"));
        assertEquals("a" + SEP + "d", Filenames
            .normalizeNoEndSeparator("a/b/../c/../d/"));
        assertEquals("a" + SEP + "b" + SEP + "d", Filenames
            .normalizeNoEndSeparator("a/b//d"));
        assertEquals("a" + SEP + "b", Filenames
            .normalizeNoEndSeparator("a/b/././."));
        assertEquals("a" + SEP + "b", Filenames
            .normalizeNoEndSeparator("a/b/./././"));
        assertEquals("a", Filenames.normalizeNoEndSeparator("./a/"));
        assertEquals("a", Filenames.normalizeNoEndSeparator("./a"));
        assertEquals("", Filenames.normalizeNoEndSeparator("./"));
        assertEquals("", Filenames.normalizeNoEndSeparator("."));
        assertEquals(null, Filenames.normalizeNoEndSeparator("../a"));
        assertEquals(null, Filenames.normalizeNoEndSeparator(".."));
        assertEquals("", Filenames.normalizeNoEndSeparator(""));
        
        assertEquals(SEP + "a", Filenames.normalizeNoEndSeparator("/a"));
        assertEquals(SEP + "a", Filenames.normalizeNoEndSeparator("/a/"));
        assertEquals(SEP + "a" + SEP + "c", Filenames
            .normalizeNoEndSeparator("/a/b/../c"));
        assertEquals(SEP + "c", Filenames
            .normalizeNoEndSeparator("/a/b/../../c"));
        assertEquals(null, Filenames
            .normalizeNoEndSeparator("/a/b/../../../c"));
        assertEquals(SEP + "a", Filenames
            .normalizeNoEndSeparator("/a/b/.."));
        assertEquals(SEP + "", Filenames
            .normalizeNoEndSeparator("/a/b/../.."));
        assertEquals(null, Filenames
            .normalizeNoEndSeparator("/a/b/../../.."));
        assertEquals(SEP + "a" + SEP + "d", Filenames
            .normalizeNoEndSeparator("/a/b/../c/../d"));
        assertEquals(SEP + "a" + SEP + "b" + SEP + "d", Filenames
            .normalizeNoEndSeparator("/a/b//d"));
        assertEquals(SEP + "a" + SEP + "b", Filenames
            .normalizeNoEndSeparator("/a/b/././."));
        assertEquals(SEP + "a", Filenames.normalizeNoEndSeparator("/./a"));
        assertEquals(SEP + "", Filenames.normalizeNoEndSeparator("/./"));
        assertEquals(SEP + "", Filenames.normalizeNoEndSeparator("/."));
        assertEquals(null, Filenames.normalizeNoEndSeparator("/../a"));
        assertEquals(null, Filenames.normalizeNoEndSeparator("/.."));
        assertEquals(SEP + "", Filenames.normalizeNoEndSeparator("/"));
        
        assertEquals("~" + SEP + "a", Filenames
            .normalizeNoEndSeparator("~/a"));
        assertEquals("~" + SEP + "a", Filenames
            .normalizeNoEndSeparator("~/a/"));
        assertEquals("~" + SEP + "a" + SEP + "c", Filenames
            .normalizeNoEndSeparator("~/a/b/../c"));
        assertEquals("~" + SEP + "c", Filenames
            .normalizeNoEndSeparator("~/a/b/../../c"));
        assertEquals(null, Filenames
            .normalizeNoEndSeparator("~/a/b/../../../c"));
        assertEquals("~" + SEP + "a", Filenames
            .normalizeNoEndSeparator("~/a/b/.."));
        assertEquals("~" + SEP + "", Filenames
            .normalizeNoEndSeparator("~/a/b/../.."));
        assertEquals(null, Filenames
            .normalizeNoEndSeparator("~/a/b/../../.."));
        assertEquals("~" + SEP + "a" + SEP + "d", Filenames
            .normalizeNoEndSeparator("~/a/b/../c/../d"));
        assertEquals("~" + SEP + "a" + SEP + "b" + SEP + "d", Filenames
            .normalizeNoEndSeparator("~/a/b//d"));
        assertEquals("~" + SEP + "a" + SEP + "b", Filenames
            .normalizeNoEndSeparator("~/a/b/././."));
        assertEquals("~" + SEP + "a", Filenames
            .normalizeNoEndSeparator("~/./a"));
        assertEquals("~" + SEP, Filenames.normalizeNoEndSeparator("~/./"));
        assertEquals("~" + SEP, Filenames.normalizeNoEndSeparator("~/."));
        assertEquals(null, Filenames.normalizeNoEndSeparator("~/../a"));
        assertEquals(null, Filenames.normalizeNoEndSeparator("~/.."));
        assertEquals("~" + SEP, Filenames.normalizeNoEndSeparator("~/"));
        assertEquals("~" + SEP, Filenames.normalizeNoEndSeparator("~"));
        
        assertEquals("~user" + SEP + "a", Filenames
            .normalizeNoEndSeparator("~user/a"));
        assertEquals("~user" + SEP + "a", Filenames
            .normalizeNoEndSeparator("~user/a/"));
        assertEquals("~user" + SEP + "a" + SEP + "c", Filenames
            .normalizeNoEndSeparator("~user/a/b/../c"));
        assertEquals("~user" + SEP + "c", Filenames
            .normalizeNoEndSeparator("~user/a/b/../../c"));
        assertEquals(null, Filenames
            .normalizeNoEndSeparator("~user/a/b/../../../c"));
        assertEquals("~user" + SEP + "a", Filenames
            .normalizeNoEndSeparator("~user/a/b/.."));
        assertEquals("~user" + SEP + "", Filenames
            .normalizeNoEndSeparator("~user/a/b/../.."));
        assertEquals(null, Filenames
            .normalizeNoEndSeparator("~user/a/b/../../.."));
        assertEquals("~user" + SEP + "a" + SEP + "d", Filenames
            .normalizeNoEndSeparator("~user/a/b/../c/../d"));
        assertEquals("~user" + SEP + "a" + SEP + "b" + SEP + "d", Filenames
            .normalizeNoEndSeparator("~user/a/b//d"));
        assertEquals("~user" + SEP + "a" + SEP + "b", Filenames
            .normalizeNoEndSeparator("~user/a/b/././."));
        assertEquals("~user" + SEP + "a", Filenames
            .normalizeNoEndSeparator("~user/./a"));
        assertEquals("~user" + SEP + "", Filenames
            .normalizeNoEndSeparator("~user/./"));
        assertEquals("~user" + SEP + "", Filenames
            .normalizeNoEndSeparator("~user/."));
        assertEquals(null, Filenames.normalizeNoEndSeparator("~user/../a"));
        assertEquals(null, Filenames.normalizeNoEndSeparator("~user/.."));
        assertEquals("~user" + SEP, Filenames
            .normalizeNoEndSeparator("~user/"));
        assertEquals("~user" + SEP, Filenames
            .normalizeNoEndSeparator("~user"));
        
        assertEquals("C:" + SEP + "a", Filenames
            .normalizeNoEndSeparator("C:/a"));
        assertEquals("C:" + SEP + "a", Filenames
            .normalizeNoEndSeparator("C:/a/"));
        assertEquals("C:" + SEP + "a" + SEP + "c", Filenames
            .normalizeNoEndSeparator("C:/a/b/../c"));
        assertEquals("C:" + SEP + "c", Filenames
            .normalizeNoEndSeparator("C:/a/b/../../c"));
        assertEquals(null, Filenames
            .normalizeNoEndSeparator("C:/a/b/../../../c"));
        assertEquals("C:" + SEP + "a", Filenames
            .normalizeNoEndSeparator("C:/a/b/.."));
        assertEquals("C:" + SEP + "", Filenames
            .normalizeNoEndSeparator("C:/a/b/../.."));
        assertEquals(null, Filenames
            .normalizeNoEndSeparator("C:/a/b/../../.."));
        assertEquals("C:" + SEP + "a" + SEP + "d", Filenames
            .normalizeNoEndSeparator("C:/a/b/../c/../d"));
        assertEquals("C:" + SEP + "a" + SEP + "b" + SEP + "d", Filenames
            .normalizeNoEndSeparator("C:/a/b//d"));
        assertEquals("C:" + SEP + "a" + SEP + "b", Filenames
            .normalizeNoEndSeparator("C:/a/b/././."));
        assertEquals("C:" + SEP + "a", Filenames
            .normalizeNoEndSeparator("C:/./a"));
        assertEquals("C:" + SEP + "", Filenames
            .normalizeNoEndSeparator("C:/./"));
        assertEquals("C:" + SEP + "", Filenames
            .normalizeNoEndSeparator("C:/."));
        assertEquals(null, Filenames.normalizeNoEndSeparator("C:/../a"));
        assertEquals(null, Filenames.normalizeNoEndSeparator("C:/.."));
        assertEquals("C:" + SEP + "", Filenames
            .normalizeNoEndSeparator("C:/"));
        
        assertEquals("C:" + "a", Filenames.normalizeNoEndSeparator("C:a"));
        assertEquals("C:" + "a", Filenames.normalizeNoEndSeparator("C:a/"));
        assertEquals("C:" + "a" + SEP + "c", Filenames
            .normalizeNoEndSeparator("C:a/b/../c"));
        assertEquals("C:" + "c", Filenames
            .normalizeNoEndSeparator("C:a/b/../../c"));
        assertEquals(null, Filenames
            .normalizeNoEndSeparator("C:a/b/../../../c"));
        assertEquals("C:" + "a", Filenames
            .normalizeNoEndSeparator("C:a/b/.."));
        assertEquals("C:" + "", Filenames
            .normalizeNoEndSeparator("C:a/b/../.."));
        assertEquals(null, Filenames
            .normalizeNoEndSeparator("C:a/b/../../.."));
        assertEquals("C:" + "a" + SEP + "d", Filenames
            .normalizeNoEndSeparator("C:a/b/../c/../d"));
        assertEquals("C:" + "a" + SEP + "b" + SEP + "d", Filenames
            .normalizeNoEndSeparator("C:a/b//d"));
        assertEquals("C:" + "a" + SEP + "b", Filenames
            .normalizeNoEndSeparator("C:a/b/././."));
        assertEquals("C:" + "a", Filenames.normalizeNoEndSeparator("C:./a"));
        assertEquals("C:" + "", Filenames.normalizeNoEndSeparator("C:./"));
        assertEquals("C:" + "", Filenames.normalizeNoEndSeparator("C:."));
        assertEquals(null, Filenames.normalizeNoEndSeparator("C:../a"));
        assertEquals(null, Filenames.normalizeNoEndSeparator("C:.."));
        assertEquals("C:" + "", Filenames.normalizeNoEndSeparator("C:"));
        
        assertEquals(SEP + SEP + "server" + SEP + "a", Filenames
            .normalizeNoEndSeparator("//server/a"));
        assertEquals(SEP + SEP + "server" + SEP + "a", Filenames
            .normalizeNoEndSeparator("//server/a/"));
        assertEquals(SEP + SEP + "server" + SEP + "a" + SEP + "c",
            Filenames.normalizeNoEndSeparator("//server/a/b/../c"));
        assertEquals(SEP + SEP + "server" + SEP + "c", Filenames
            .normalizeNoEndSeparator("//server/a/b/../../c"));
        assertEquals(null, Filenames
            .normalizeNoEndSeparator("//server/a/b/../../../c"));
        assertEquals(SEP + SEP + "server" + SEP + "a", Filenames
            .normalizeNoEndSeparator("//server/a/b/.."));
        assertEquals(SEP + SEP + "server" + SEP + "", Filenames
            .normalizeNoEndSeparator("//server/a/b/../.."));
        assertEquals(null, Filenames
            .normalizeNoEndSeparator("//server/a/b/../../.."));
        assertEquals(SEP + SEP + "server" + SEP + "a" + SEP + "d",
            Filenames.normalizeNoEndSeparator("//server/a/b/../c/../d"));
        assertEquals(SEP + SEP + "server" + SEP + "a" + SEP + "b" + SEP + "d",
            Filenames.normalizeNoEndSeparator("//server/a/b//d"));
        assertEquals(SEP + SEP + "server" + SEP + "a" + SEP + "b",
            Filenames.normalizeNoEndSeparator("//server/a/b/././."));
        assertEquals(SEP + SEP + "server" + SEP + "a", Filenames
            .normalizeNoEndSeparator("//server/./a"));
        assertEquals(SEP + SEP + "server" + SEP + "", Filenames
            .normalizeNoEndSeparator("//server/./"));
        assertEquals(SEP + SEP + "server" + SEP + "", Filenames
            .normalizeNoEndSeparator("//server/."));
        assertEquals(null, Filenames
            .normalizeNoEndSeparator("//server/../a"));
        assertEquals(null, Filenames.normalizeNoEndSeparator("//server/.."));
        assertEquals(SEP + SEP + "server" + SEP + "", Filenames
            .normalizeNoEndSeparator("//server/"));
    }
    
    // -----------------------------------------------------------------------
    public void testConcat() {
        assertEquals(null, Filenames.concat("", null));
        assertEquals(null, Filenames.concat(null, null));
        assertEquals(null, Filenames.concat(null, ""));
        assertEquals(null, Filenames.concat(null, "a"));
        assertEquals(SEP + "a", Filenames.concat(null, "/a"));
        
        assertEquals(null, Filenames.concat("", ":")); // invalid prefix
        assertEquals(null, Filenames.concat(":", "")); // invalid prefix
        
        assertEquals("f" + SEP, Filenames.concat("", "f/"));
        assertEquals("f", Filenames.concat("", "f"));
        assertEquals("a" + SEP + "f" + SEP, Filenames.concat("a/", "f/"));
        assertEquals("a" + SEP + "f", Filenames.concat("a", "f"));
        assertEquals("a" + SEP + "b" + SEP + "f" + SEP, Filenames.concat(
            "a/b/", "f/"));
        assertEquals("a" + SEP + "b" + SEP + "f", Filenames.concat("a/b",
            "f"));
        
        assertEquals("a" + SEP + "f" + SEP, Filenames.concat("a/b/",
            "../f/"));
        assertEquals("a" + SEP + "f", Filenames.concat("a/b", "../f"));
        assertEquals("a" + SEP + "c" + SEP + "g" + SEP, Filenames.concat(
            "a/b/../c/", "f/../g/"));
        assertEquals("a" + SEP + "c" + SEP + "g", Filenames.concat(
            "a/b/../c", "f/../g"));
        
        assertEquals("a" + SEP + "c.txt" + SEP + "f", Filenames.concat(
            "a/c.txt", "f"));
        
        assertEquals(SEP + "f" + SEP, Filenames.concat("", "/f/"));
        assertEquals(SEP + "f", Filenames.concat("", "/f"));
        assertEquals(SEP + "f" + SEP, Filenames.concat("a/", "/f/"));
        assertEquals(SEP + "f", Filenames.concat("a", "/f"));
        
        assertEquals(SEP + "c" + SEP + "d", Filenames
            .concat("a/b/", "/c/d"));
        assertEquals("C:c" + SEP + "d", Filenames.concat("a/b/", "C:c/d"));
        assertEquals("C:" + SEP + "c" + SEP + "d", Filenames.concat("a/b/",
            "C:/c/d"));
        assertEquals("~" + SEP + "c" + SEP + "d", Filenames.concat("a/b/",
            "~/c/d"));
        assertEquals("~user" + SEP + "c" + SEP + "d", Filenames.concat(
            "a/b/", "~user/c/d"));
        assertEquals("~" + SEP, Filenames.concat("a/b/", "~"));
        assertEquals("~user" + SEP, Filenames.concat("a/b/", "~user"));
    }
    
    // -----------------------------------------------------------------------
    public void testSeparatorsToUnix() {
        assertEquals(null, Filenames.separatorsToUnix(null));
        assertEquals("/a/b/c", Filenames.separatorsToUnix("/a/b/c"));
        assertEquals("/a/b/c.txt", Filenames.separatorsToUnix("/a/b/c.txt"));
        assertEquals("/a/b/c", Filenames.separatorsToUnix("/a/b\\c"));
        assertEquals("/a/b/c", Filenames.separatorsToUnix("\\a\\b\\c"));
        assertEquals("D:/a/b/c", Filenames.separatorsToUnix("D:\\a\\b\\c"));
    }
    
    public void testSeparatorsToWindows() {
        assertEquals(null, Filenames.separatorsToWindows(null));
        assertEquals("\\a\\b\\c", Filenames
            .separatorsToWindows("\\a\\b\\c"));
        assertEquals("\\a\\b\\c.txt", Filenames
            .separatorsToWindows("\\a\\b\\c.txt"));
        assertEquals("\\a\\b\\c", Filenames.separatorsToWindows("\\a\\b/c"));
        assertEquals("\\a\\b\\c", Filenames.separatorsToWindows("/a/b/c"));
        assertEquals("D:\\a\\b\\c", Filenames
            .separatorsToWindows("D:/a/b/c"));
    }
    
    public void testSeparatorsToSystem() {
        if (WINDOWS) {
            assertEquals(null, Filenames.separatorsToSystem(null));
            assertEquals("\\a\\b\\c", Filenames
                .separatorsToSystem("\\a\\b\\c"));
            assertEquals("\\a\\b\\c.txt", Filenames
                .separatorsToSystem("\\a\\b\\c.txt"));
            assertEquals("\\a\\b\\c", Filenames
                .separatorsToSystem("\\a\\b/c"));
            assertEquals("\\a\\b\\c", Filenames
                .separatorsToSystem("/a/b/c"));
            assertEquals("D:\\a\\b\\c", Filenames
                .separatorsToSystem("D:/a/b/c"));
        } else {
            assertEquals(null, Filenames.separatorsToSystem(null));
            assertEquals("/a/b/c", Filenames.separatorsToSystem("/a/b/c"));
            assertEquals("/a/b/c.txt", Filenames
                .separatorsToSystem("/a/b/c.txt"));
            assertEquals("/a/b/c", Filenames.separatorsToSystem("/a/b\\c"));
            assertEquals("/a/b/c", Filenames
                .separatorsToSystem("\\a\\b\\c"));
            assertEquals("D:/a/b/c", Filenames
                .separatorsToSystem("D:\\a\\b\\c"));
        }
    }
    
    // -----------------------------------------------------------------------
    public void testGetPrefixLength() {
        assertEquals(-1, Filenames.getPrefixLength(null));
        assertEquals(-1, Filenames.getPrefixLength(":"));
        assertEquals(-1, Filenames.getPrefixLength("1:\\a\\b\\c.txt"));
        assertEquals(-1, Filenames.getPrefixLength("1:"));
        assertEquals(-1, Filenames.getPrefixLength("1:a"));
        assertEquals(-1, Filenames.getPrefixLength("\\\\\\a\\b\\c.txt"));
        assertEquals(-1, Filenames.getPrefixLength("\\\\a"));
        
        assertEquals(0, Filenames.getPrefixLength(""));
        assertEquals(1, Filenames.getPrefixLength("\\"));
        assertEquals(2, Filenames.getPrefixLength("C:"));
        assertEquals(3, Filenames.getPrefixLength("C:\\"));
        assertEquals(9, Filenames.getPrefixLength("//server/"));
        assertEquals(2, Filenames.getPrefixLength("~"));
        assertEquals(2, Filenames.getPrefixLength("~/"));
        assertEquals(6, Filenames.getPrefixLength("~user"));
        assertEquals(6, Filenames.getPrefixLength("~user/"));
        
        assertEquals(0, Filenames.getPrefixLength("a\\b\\c.txt"));
        assertEquals(1, Filenames.getPrefixLength("\\a\\b\\c.txt"));
        assertEquals(2, Filenames.getPrefixLength("C:a\\b\\c.txt"));
        assertEquals(3, Filenames.getPrefixLength("C:\\a\\b\\c.txt"));
        assertEquals(9, Filenames
            .getPrefixLength("\\\\server\\a\\b\\c.txt"));
        
        assertEquals(0, Filenames.getPrefixLength("a/b/c.txt"));
        assertEquals(1, Filenames.getPrefixLength("/a/b/c.txt"));
        assertEquals(3, Filenames.getPrefixLength("C:/a/b/c.txt"));
        assertEquals(9, Filenames.getPrefixLength("//server/a/b/c.txt"));
        assertEquals(2, Filenames.getPrefixLength("~/a/b/c.txt"));
        assertEquals(6, Filenames.getPrefixLength("~user/a/b/c.txt"));
        
        assertEquals(0, Filenames.getPrefixLength("a\\b\\c.txt"));
        assertEquals(1, Filenames.getPrefixLength("\\a\\b\\c.txt"));
        assertEquals(2, Filenames.getPrefixLength("~\\a\\b\\c.txt"));
        assertEquals(6, Filenames.getPrefixLength("~user\\a\\b\\c.txt"));
    }
    
    public void testIndexOfLastSeparator() {
        assertEquals(-1, Filenames.indexOfLastSeparator(null));
        assertEquals(-1, Filenames
            .indexOfLastSeparator("noseperator.inthispath"));
        assertEquals(3, Filenames.indexOfLastSeparator("a/b/c"));
        assertEquals(3, Filenames.indexOfLastSeparator("a\\b\\c"));
    }
    
    public void testIndexOfExtension() {
        assertEquals(-1, Filenames.indexOfExtension(null));
        assertEquals(-1, Filenames.indexOfExtension("file"));
        assertEquals(4, Filenames.indexOfExtension("file.txt"));
        assertEquals(13, Filenames.indexOfExtension("a.txt/b.txt/c.txt"));
        assertEquals(-1, Filenames.indexOfExtension("a/b/c"));
        assertEquals(-1, Filenames.indexOfExtension("a\\b\\c"));
        assertEquals(-1, Filenames.indexOfExtension("a/b.notextension/c"));
        assertEquals(-1, Filenames.indexOfExtension("a\\b.notextension\\c"));
    }
    
    // -----------------------------------------------------------------------
    public void testGetPrefix() {
        assertEquals(null, Filenames.getPrefix(null));
        assertEquals(null, Filenames.getPrefix(":"));
        assertEquals(null, Filenames.getPrefix("1:\\a\\b\\c.txt"));
        assertEquals(null, Filenames.getPrefix("1:"));
        assertEquals(null, Filenames.getPrefix("1:a"));
        assertEquals(null, Filenames.getPrefix("\\\\\\a\\b\\c.txt"));
        assertEquals(null, Filenames.getPrefix("\\\\a"));
        
        assertEquals("", Filenames.getPrefix(""));
        assertEquals("\\", Filenames.getPrefix("\\"));
        assertEquals("C:", Filenames.getPrefix("C:"));
        assertEquals("C:\\", Filenames.getPrefix("C:\\"));
        assertEquals("//server/", Filenames.getPrefix("//server/"));
        assertEquals("~/", Filenames.getPrefix("~"));
        assertEquals("~/", Filenames.getPrefix("~/"));
        assertEquals("~user/", Filenames.getPrefix("~user"));
        assertEquals("~user/", Filenames.getPrefix("~user/"));
        
        assertEquals("", Filenames.getPrefix("a\\b\\c.txt"));
        assertEquals("\\", Filenames.getPrefix("\\a\\b\\c.txt"));
        assertEquals("C:\\", Filenames.getPrefix("C:\\a\\b\\c.txt"));
        assertEquals("\\\\server\\", Filenames
            .getPrefix("\\\\server\\a\\b\\c.txt"));
        
        assertEquals("", Filenames.getPrefix("a/b/c.txt"));
        assertEquals("/", Filenames.getPrefix("/a/b/c.txt"));
        assertEquals("C:/", Filenames.getPrefix("C:/a/b/c.txt"));
        assertEquals("//server/", Filenames.getPrefix("//server/a/b/c.txt"));
        assertEquals("~/", Filenames.getPrefix("~/a/b/c.txt"));
        assertEquals("~user/", Filenames.getPrefix("~user/a/b/c.txt"));
        
        assertEquals("", Filenames.getPrefix("a\\b\\c.txt"));
        assertEquals("\\", Filenames.getPrefix("\\a\\b\\c.txt"));
        assertEquals("~\\", Filenames.getPrefix("~\\a\\b\\c.txt"));
        assertEquals("~user\\", Filenames.getPrefix("~user\\a\\b\\c.txt"));
    }
    
    public void testGetPath() {
        assertEquals(null, Filenames.getPath(null));
        assertEquals("", Filenames.getPath("noseperator.inthispath"));
        assertEquals("a/b/", Filenames.getPath("a/b/c.txt"));
        assertEquals("a/b/", Filenames.getPath("a/b/c"));
        assertEquals("a/b/c/", Filenames.getPath("a/b/c/"));
        assertEquals("a\\b\\", Filenames.getPath("a\\b\\c"));
        
        assertEquals(null, Filenames.getPath(":"));
        assertEquals(null, Filenames.getPath("1:/a/b/c.txt"));
        assertEquals(null, Filenames.getPath("1:"));
        assertEquals(null, Filenames.getPath("1:a"));
        assertEquals(null, Filenames.getPath("///a/b/c.txt"));
        assertEquals(null, Filenames.getPath("//a"));
        
        assertEquals("", Filenames.getPath(""));
        assertEquals("", Filenames.getPath("C:"));
        assertEquals("", Filenames.getPath("C:/"));
        assertEquals("", Filenames.getPath("//server/"));
        assertEquals("", Filenames.getPath("~"));
        assertEquals("", Filenames.getPath("~/"));
        assertEquals("", Filenames.getPath("~user"));
        assertEquals("", Filenames.getPath("~user/"));
        
        assertEquals("a/b/", Filenames.getPath("a/b/c.txt"));
        assertEquals("a/b/", Filenames.getPath("/a/b/c.txt"));
        assertEquals("", Filenames.getPath("C:a"));
        assertEquals("a/b/", Filenames.getPath("C:a/b/c.txt"));
        assertEquals("a/b/", Filenames.getPath("C:/a/b/c.txt"));
        assertEquals("a/b/", Filenames.getPath("//server/a/b/c.txt"));
        assertEquals("a/b/", Filenames.getPath("~/a/b/c.txt"));
        assertEquals("a/b/", Filenames.getPath("~user/a/b/c.txt"));
    }
    
    public void testGetPathNoEndSeparator() {
        assertEquals(null, Filenames.getPath(null));
        assertEquals("", Filenames.getPath("noseperator.inthispath"));
        assertEquals("a/b", Filenames.getPathNoEndSeparator("a/b/c.txt"));
        assertEquals("a/b", Filenames.getPathNoEndSeparator("a/b/c"));
        assertEquals("a/b/c", Filenames.getPathNoEndSeparator("a/b/c/"));
        assertEquals("a\\b", Filenames.getPathNoEndSeparator("a\\b\\c"));
        
        assertEquals(null, Filenames.getPathNoEndSeparator(":"));
        assertEquals(null, Filenames.getPathNoEndSeparator("1:/a/b/c.txt"));
        assertEquals(null, Filenames.getPathNoEndSeparator("1:"));
        assertEquals(null, Filenames.getPathNoEndSeparator("1:a"));
        assertEquals(null, Filenames.getPathNoEndSeparator("///a/b/c.txt"));
        assertEquals(null, Filenames.getPathNoEndSeparator("//a"));
        
        assertEquals("", Filenames.getPathNoEndSeparator(""));
        assertEquals("", Filenames.getPathNoEndSeparator("C:"));
        assertEquals("", Filenames.getPathNoEndSeparator("C:/"));
        assertEquals("", Filenames.getPathNoEndSeparator("//server/"));
        assertEquals("", Filenames.getPathNoEndSeparator("~"));
        assertEquals("", Filenames.getPathNoEndSeparator("~/"));
        assertEquals("", Filenames.getPathNoEndSeparator("~user"));
        assertEquals("", Filenames.getPathNoEndSeparator("~user/"));
        
        assertEquals("a/b", Filenames.getPathNoEndSeparator("a/b/c.txt"));
        assertEquals("a/b", Filenames.getPathNoEndSeparator("/a/b/c.txt"));
        assertEquals("", Filenames.getPathNoEndSeparator("C:a"));
        assertEquals("a/b", Filenames.getPathNoEndSeparator("C:a/b/c.txt"));
        assertEquals("a/b", Filenames.getPathNoEndSeparator("C:/a/b/c.txt"));
        assertEquals("a/b", Filenames
            .getPathNoEndSeparator("//server/a/b/c.txt"));
        assertEquals("a/b", Filenames.getPathNoEndSeparator("~/a/b/c.txt"));
        assertEquals("a/b", Filenames
            .getPathNoEndSeparator("~user/a/b/c.txt"));
    }
    
    public void testGetFullPath() {
        assertEquals(null, Filenames.getFullPath(null));
        assertEquals("", Filenames.getFullPath("noseperator.inthispath"));
        assertEquals("a/b/", Filenames.getFullPath("a/b/c.txt"));
        assertEquals("a/b/", Filenames.getFullPath("a/b/c"));
        assertEquals("a/b/c/", Filenames.getFullPath("a/b/c/"));
        assertEquals("a\\b\\", Filenames.getFullPath("a\\b\\c"));
        
        assertEquals(null, Filenames.getFullPath(":"));
        assertEquals(null, Filenames.getFullPath("1:/a/b/c.txt"));
        assertEquals(null, Filenames.getFullPath("1:"));
        assertEquals(null, Filenames.getFullPath("1:a"));
        assertEquals(null, Filenames.getFullPath("///a/b/c.txt"));
        assertEquals(null, Filenames.getFullPath("//a"));
        
        assertEquals("", Filenames.getFullPath(""));
        assertEquals("C:", Filenames.getFullPath("C:"));
        assertEquals("C:/", Filenames.getFullPath("C:/"));
        assertEquals("//server/", Filenames.getFullPath("//server/"));
        assertEquals("~/", Filenames.getFullPath("~"));
        assertEquals("~/", Filenames.getFullPath("~/"));
        assertEquals("~user/", Filenames.getFullPath("~user"));
        assertEquals("~user/", Filenames.getFullPath("~user/"));
        
        assertEquals("a/b/", Filenames.getFullPath("a/b/c.txt"));
        assertEquals("/a/b/", Filenames.getFullPath("/a/b/c.txt"));
        assertEquals("C:", Filenames.getFullPath("C:a"));
        assertEquals("C:a/b/", Filenames.getFullPath("C:a/b/c.txt"));
        assertEquals("C:/a/b/", Filenames.getFullPath("C:/a/b/c.txt"));
        assertEquals("//server/a/b/", Filenames
            .getFullPath("//server/a/b/c.txt"));
        assertEquals("~/a/b/", Filenames.getFullPath("~/a/b/c.txt"));
        assertEquals("~user/a/b/", Filenames.getFullPath("~user/a/b/c.txt"));
    }
    
    public void testGetFullPathNoEndSeparator() {
        assertEquals(null, Filenames.getFullPathNoEndSeparator(null));
        assertEquals("", Filenames
            .getFullPathNoEndSeparator("noseperator.inthispath"));
        assertEquals("a/b", Filenames
            .getFullPathNoEndSeparator("a/b/c.txt"));
        assertEquals("a/b", Filenames.getFullPathNoEndSeparator("a/b/c"));
        assertEquals("a/b/c", Filenames.getFullPathNoEndSeparator("a/b/c/"));
        assertEquals("a\\b", Filenames.getFullPathNoEndSeparator("a\\b\\c"));
        
        assertEquals(null, Filenames.getFullPathNoEndSeparator(":"));
        assertEquals(null, Filenames
            .getFullPathNoEndSeparator("1:/a/b/c.txt"));
        assertEquals(null, Filenames.getFullPathNoEndSeparator("1:"));
        assertEquals(null, Filenames.getFullPathNoEndSeparator("1:a"));
        assertEquals(null, Filenames
            .getFullPathNoEndSeparator("///a/b/c.txt"));
        assertEquals(null, Filenames.getFullPathNoEndSeparator("//a"));
        
        assertEquals("", Filenames.getFullPathNoEndSeparator(""));
        assertEquals("C:", Filenames.getFullPathNoEndSeparator("C:"));
        assertEquals("C:/", Filenames.getFullPathNoEndSeparator("C:/"));
        assertEquals("//server/", Filenames
            .getFullPathNoEndSeparator("//server/"));
        assertEquals("~", Filenames.getFullPathNoEndSeparator("~"));
        assertEquals("~/", Filenames.getFullPathNoEndSeparator("~/"));
        assertEquals("~user", Filenames.getFullPathNoEndSeparator("~user"));
        assertEquals("~user/", Filenames
            .getFullPathNoEndSeparator("~user/"));
        
        assertEquals("a/b", Filenames
            .getFullPathNoEndSeparator("a/b/c.txt"));
        assertEquals("/a/b", Filenames
            .getFullPathNoEndSeparator("/a/b/c.txt"));
        assertEquals("C:", Filenames.getFullPathNoEndSeparator("C:a"));
        assertEquals("C:a/b", Filenames
            .getFullPathNoEndSeparator("C:a/b/c.txt"));
        assertEquals("C:/a/b", Filenames
            .getFullPathNoEndSeparator("C:/a/b/c.txt"));
        assertEquals("//server/a/b", Filenames
            .getFullPathNoEndSeparator("//server/a/b/c.txt"));
        assertEquals("~/a/b", Filenames
            .getFullPathNoEndSeparator("~/a/b/c.txt"));
        assertEquals("~user/a/b", Filenames
            .getFullPathNoEndSeparator("~user/a/b/c.txt"));
    }
    
    public void testGetName() {
        assertEquals(null, Filenames.getName(null));
        assertEquals("noseperator.inthispath", Filenames
            .getName("noseperator.inthispath"));
        assertEquals("c.txt", Filenames.getName("a/b/c.txt"));
        assertEquals("c", Filenames.getName("a/b/c"));
        assertEquals("", Filenames.getName("a/b/c/"));
        assertEquals("c", Filenames.getName("a\\b\\c"));
    }
    
    public void testGetBaseName() {
        assertEquals(null, Filenames.getBaseName(null));
        assertEquals("noseperator", Filenames
            .getBaseName("noseperator.inthispath"));
        assertEquals("c", Filenames.getBaseName("a/b/c.txt"));
        assertEquals("c", Filenames.getBaseName("a/b/c"));
        assertEquals("", Filenames.getBaseName("a/b/c/"));
        assertEquals("c", Filenames.getBaseName("a\\b\\c"));
        assertEquals("file.txt", Filenames.getBaseName("file.txt.bak"));
    }
    
    public void testGetExtension() {
        assertEquals(null, Filenames.getExtension(null));
        assertEquals("ext", Filenames.getExtension("file.ext"));
        assertEquals("", Filenames.getExtension("README"));
        assertEquals("com", Filenames.getExtension("domain.dot.com"));
        assertEquals("jpeg", Filenames.getExtension("image.jpeg"));
        assertEquals("", Filenames.getExtension("a.b/c"));
        assertEquals("txt", Filenames.getExtension("a.b/c.txt"));
        assertEquals("", Filenames.getExtension("a/b/c"));
        assertEquals("", Filenames.getExtension("a.b\\c"));
        assertEquals("txt", Filenames.getExtension("a.b\\c.txt"));
        assertEquals("", Filenames.getExtension("a\\b\\c"));
        assertEquals("", Filenames
            .getExtension("C:\\temp\\foo.bar\\README"));
        assertEquals("ext", Filenames.getExtension("../filename.ext"));
    }
    
    public void testRemoveExtension() {
        assertEquals(null, Filenames.removeExtension(null));
        assertEquals("file", Filenames.removeExtension("file.ext"));
        assertEquals("README", Filenames.removeExtension("README"));
        assertEquals("domain.dot", Filenames
            .removeExtension("domain.dot.com"));
        assertEquals("image", Filenames.removeExtension("image.jpeg"));
        assertEquals("a.b/c", Filenames.removeExtension("a.b/c"));
        assertEquals("a.b/c", Filenames.removeExtension("a.b/c.txt"));
        assertEquals("a/b/c", Filenames.removeExtension("a/b/c"));
        assertEquals("a.b\\c", Filenames.removeExtension("a.b\\c"));
        assertEquals("a.b\\c", Filenames.removeExtension("a.b\\c.txt"));
        assertEquals("a\\b\\c", Filenames.removeExtension("a\\b\\c"));
        assertEquals("C:\\temp\\foo.bar\\README", Filenames
            .removeExtension("C:\\temp\\foo.bar\\README"));
        assertEquals("../filename", Filenames
            .removeExtension("../filename.ext"));
    }
    
    // -----------------------------------------------------------------------
    public void testEquals() {
        assertEquals(true, Filenames.equals(null, null));
        assertEquals(false, Filenames.equals(null, ""));
        assertEquals(false, Filenames.equals("", null));
        assertEquals(true, Filenames.equals("", ""));
        assertEquals(true, Filenames.equals("file.txt", "file.txt"));
        assertEquals(false, Filenames.equals("file.txt", "FILE.TXT"));
        assertEquals(false, Filenames.equals("a\\b\\file.txt",
            "a/b/file.txt"));
    }
    
    public void testEqualsOnSystem() {
        assertEquals(true, Filenames.equalsOnSystem(null, null));
        assertEquals(false, Filenames.equalsOnSystem(null, ""));
        assertEquals(false, Filenames.equalsOnSystem("", null));
        assertEquals(true, Filenames.equalsOnSystem("", ""));
        assertEquals(true, Filenames.equalsOnSystem("file.txt", "file.txt"));
        assertEquals(WINDOWS, Filenames.equalsOnSystem("file.txt",
            "FILE.TXT"));
        assertEquals(false, Filenames.equalsOnSystem("a\\b\\file.txt",
            "a/b/file.txt"));
    }
    
    // -----------------------------------------------------------------------
    public void testEqualsNormalized() {
        assertEquals(true, Filenames.equalsNormalized(null, null));
        assertEquals(false, Filenames.equalsNormalized(null, ""));
        assertEquals(false, Filenames.equalsNormalized("", null));
        assertEquals(true, Filenames.equalsNormalized("", ""));
        assertEquals(true, Filenames.equalsNormalized("file.txt",
            "file.txt"));
        assertEquals(false, Filenames.equalsNormalized("file.txt",
            "FILE.TXT"));
        assertEquals(true, Filenames.equalsNormalized("a\\b\\file.txt",
            "a/b/file.txt"));
        assertEquals(false, Filenames.equalsNormalized("a/b/", "a/b"));
    }
    
    public void testEqualsNormalizedOnSystem() {
        assertEquals(true, Filenames.equalsNormalizedOnSystem(null, null));
        assertEquals(false, Filenames.equalsNormalizedOnSystem(null, ""));
        assertEquals(false, Filenames.equalsNormalizedOnSystem("", null));
        assertEquals(true, Filenames.equalsNormalizedOnSystem("", ""));
        assertEquals(true, Filenames.equalsNormalizedOnSystem("file.txt",
            "file.txt"));
        assertEquals(WINDOWS, Filenames.equalsNormalizedOnSystem(
            "file.txt", "FILE.TXT"));
        assertEquals(true, Filenames.equalsNormalizedOnSystem(
            "a\\b\\file.txt", "a/b/file.txt"));
        assertEquals(false, Filenames.equalsNormalizedOnSystem("a/b/",
            "a/b"));
    }
    
    // -----------------------------------------------------------------------
    public void testIsExtension() {
        assertEquals(false, Filenames.isExtension(null, (String) null));
        assertEquals(false, Filenames
            .isExtension("file.txt", (String) null));
        assertEquals(true, Filenames.isExtension("file", (String) null));
        assertEquals(false, Filenames.isExtension("file.txt", ""));
        assertEquals(true, Filenames.isExtension("file", ""));
        assertEquals(true, Filenames.isExtension("file.txt", "txt"));
        assertEquals(false, Filenames.isExtension("file.txt", "rtf"));
        
        assertEquals(false, Filenames.isExtension("a/b/file.txt",
            (String) null));
        assertEquals(false, Filenames.isExtension("a/b/file.txt", ""));
        assertEquals(true, Filenames.isExtension("a/b/file.txt", "txt"));
        assertEquals(false, Filenames.isExtension("a/b/file.txt", "rtf"));
        
        assertEquals(false, Filenames.isExtension("a.b/file.txt",
            (String) null));
        assertEquals(false, Filenames.isExtension("a.b/file.txt", ""));
        assertEquals(true, Filenames.isExtension("a.b/file.txt", "txt"));
        assertEquals(false, Filenames.isExtension("a.b/file.txt", "rtf"));
        
        assertEquals(false, Filenames.isExtension("a\\b\\file.txt",
            (String) null));
        assertEquals(false, Filenames.isExtension("a\\b\\file.txt", ""));
        assertEquals(true, Filenames.isExtension("a\\b\\file.txt", "txt"));
        assertEquals(false, Filenames.isExtension("a\\b\\file.txt", "rtf"));
        
        assertEquals(false, Filenames.isExtension("a.b\\file.txt",
            (String) null));
        assertEquals(false, Filenames.isExtension("a.b\\file.txt", ""));
        assertEquals(true, Filenames.isExtension("a.b\\file.txt", "txt"));
        assertEquals(false, Filenames.isExtension("a.b\\file.txt", "rtf"));
        
        assertEquals(false, Filenames.isExtension("a.b\\file.txt", "TXT"));
    }
    
    public void testIsExtensionArray() {
        assertEquals(false, Filenames.isExtension(null, (String[]) null));
        assertEquals(false, Filenames.isExtension("file.txt",
            (String[]) null));
        assertEquals(true, Filenames.isExtension("file", (String[]) null));
        assertEquals(false, Filenames
            .isExtension("file.txt", new String[0]));
        assertEquals(true, Filenames.isExtension("file.txt",
            new String[] { "txt" }));
        assertEquals(false, Filenames.isExtension("file.txt",
            new String[] { "rtf" }));
        assertEquals(true, Filenames.isExtension("file", new String[] {
                "rtf", "" }));
        assertEquals(true, Filenames.isExtension("file.txt", new String[] {
                "rtf", "txt" }));
        
        assertEquals(false, Filenames.isExtension("a/b/file.txt",
            (String[]) null));
        assertEquals(false, Filenames.isExtension("a/b/file.txt",
            new String[0]));
        assertEquals(true, Filenames.isExtension("a/b/file.txt",
            new String[] { "txt" }));
        assertEquals(false, Filenames.isExtension("a/b/file.txt",
            new String[] { "rtf" }));
        assertEquals(true, Filenames.isExtension("a/b/file.txt",
            new String[] { "rtf", "txt" }));
        
        assertEquals(false, Filenames.isExtension("a.b/file.txt",
            (String[]) null));
        assertEquals(false, Filenames.isExtension("a.b/file.txt",
            new String[0]));
        assertEquals(true, Filenames.isExtension("a.b/file.txt",
            new String[] { "txt" }));
        assertEquals(false, Filenames.isExtension("a.b/file.txt",
            new String[] { "rtf" }));
        assertEquals(true, Filenames.isExtension("a.b/file.txt",
            new String[] { "rtf", "txt" }));
        
        assertEquals(false, Filenames.isExtension("a\\b\\file.txt",
            (String[]) null));
        assertEquals(false, Filenames.isExtension("a\\b\\file.txt",
            new String[0]));
        assertEquals(true, Filenames.isExtension("a\\b\\file.txt",
            new String[] { "txt" }));
        assertEquals(false, Filenames.isExtension("a\\b\\file.txt",
            new String[] { "rtf" }));
        assertEquals(true, Filenames.isExtension("a\\b\\file.txt",
            new String[] { "rtf", "txt" }));
        
        assertEquals(false, Filenames.isExtension("a.b\\file.txt",
            (String[]) null));
        assertEquals(false, Filenames.isExtension("a.b\\file.txt",
            new String[0]));
        assertEquals(true, Filenames.isExtension("a.b\\file.txt",
            new String[] { "txt" }));
        assertEquals(false, Filenames.isExtension("a.b\\file.txt",
            new String[] { "rtf" }));
        assertEquals(true, Filenames.isExtension("a.b\\file.txt",
            new String[] { "rtf", "txt" }));
        
        assertEquals(false, Filenames.isExtension("a.b\\file.txt",
            new String[] { "TXT" }));
        assertEquals(false, Filenames.isExtension("a.b\\file.txt",
            new String[] { "TXT", "RTF" }));
    }
    
    public void testIsExtensionCollection() {
        assertEquals(false, Filenames.isExtension(null, (Collection<String>) null));
        assertEquals(false, Filenames.isExtension("file.txt",
            (Collection<String>) null));
        assertEquals(true, Filenames.isExtension("file", (Collection<String>) null));
        assertEquals(false, Filenames.isExtension("file.txt",
            new ArrayList<String>()));
        assertEquals(true, Filenames.isExtension("file.txt", new ArrayList<String>(
            Arrays.asList(new String[] { "txt" }))));
        assertEquals(false, Filenames.isExtension("file.txt",
            new ArrayList<String>(Arrays.asList(new String[] { "rtf" }))));
        assertEquals(true, Filenames.isExtension("file", new ArrayList<String>(
            Arrays.asList(new String[] { "rtf", "" }))));
        assertEquals(true, Filenames.isExtension("file.txt", new ArrayList<String>(
            Arrays.asList(new String[] { "rtf", "txt" }))));
        
        assertEquals(false, Filenames.isExtension("a/b/file.txt",
            (Collection<String>) null));
        assertEquals(false, Filenames.isExtension("a/b/file.txt",
            new ArrayList<String>()));
        assertEquals(true, Filenames.isExtension("a/b/file.txt",
            new ArrayList<String>(Arrays.asList(new String[] { "txt" }))));
        assertEquals(false, Filenames.isExtension("a/b/file.txt",
            new ArrayList<String>(Arrays.asList(new String[] { "rtf" }))));
        assertEquals(true, Filenames.isExtension("a/b/file.txt",
            new ArrayList<String>(Arrays.asList(new String[] { "rtf", "txt" }))));
        
        assertEquals(false, Filenames.isExtension("a.b/file.txt",
            (Collection<String>) null));
        assertEquals(false, Filenames.isExtension("a.b/file.txt",
            new ArrayList<String>()));
        assertEquals(true, Filenames.isExtension("a.b/file.txt",
            new ArrayList<String>(Arrays.asList(new String[] { "txt" }))));
        assertEquals(false, Filenames.isExtension("a.b/file.txt",
            new ArrayList<String>(Arrays.asList(new String[] { "rtf" }))));
        assertEquals(true, Filenames.isExtension("a.b/file.txt",
            new ArrayList<String>(Arrays.asList(new String[] { "rtf", "txt" }))));
        
        assertEquals(false, Filenames.isExtension("a\\b\\file.txt",
            (Collection<String>) null));
        assertEquals(false, Filenames.isExtension("a\\b\\file.txt",
            new ArrayList<String>()));
        assertEquals(true, Filenames.isExtension("a\\b\\file.txt",
            new ArrayList<String>(Arrays.asList(new String[] { "txt" }))));
        assertEquals(false, Filenames.isExtension("a\\b\\file.txt",
            new ArrayList<String>(Arrays.asList(new String[] { "rtf" }))));
        assertEquals(true, Filenames.isExtension("a\\b\\file.txt",
            new ArrayList<String>(Arrays.asList(new String[] { "rtf", "txt" }))));
        
        assertEquals(false, Filenames.isExtension("a.b\\file.txt",
            (Collection<String>) null));
        assertEquals(false, Filenames.isExtension("a.b\\file.txt",
            new ArrayList<String>()));
        assertEquals(true, Filenames.isExtension("a.b\\file.txt",
            new ArrayList<String>(Arrays.asList(new String[] { "txt" }))));
        assertEquals(false, Filenames.isExtension("a.b\\file.txt",
            new ArrayList<String>(Arrays.asList(new String[] { "rtf" }))));
        assertEquals(true, Filenames.isExtension("a.b\\file.txt",
            new ArrayList<String>(Arrays.asList(new String[] { "rtf", "txt" }))));
        
        assertEquals(false, Filenames.isExtension("a.b\\file.txt",
            new ArrayList<String>(Arrays.asList(new String[] { "TXT" }))));
        assertEquals(false, Filenames.isExtension("a.b\\file.txt",
            new ArrayList<String>(Arrays.asList(new String[] { "TXT", "RTF" }))));
    }
    
}
