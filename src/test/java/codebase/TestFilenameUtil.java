package codebase;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;

import junit.framework.TestCase;


/**
 * Tests the {@link FilenameUtil} utility class.
 */
public class TestFilenameUtil extends TestCase {

    private static final String SEP = "" + File.separatorChar;

    private static final boolean WINDOWS = (File.separatorChar == '\\');

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
        //FileUtils.deleteDirectory(getTestDirectory());
    }

    /**
     * Tests the normalize path functionality in several corner situations including UNCs,
     * dot slash, dot dot slash, home character.
     */
    @Test
    public void testNormalize() throws Exception {
        assertEquals(null, FilenameUtil.normalize(null));
        assertEquals(null, FilenameUtil.normalize(":"));
        assertEquals(null, FilenameUtil.normalize("1:\\a\\b\\c.txt"));
        assertEquals(null, FilenameUtil.normalize("1:"));
        assertEquals(null, FilenameUtil.normalize("1:a"));
        assertEquals(null, FilenameUtil.normalize("\\\\\\a\\b\\c.txt"));
        assertEquals(null, FilenameUtil.normalize("\\\\a"));

        assertEquals("a" + SEP + "b" + SEP + "c.txt", FilenameUtil.normalize("a\\b/c.txt"));
        assertEquals("" + SEP + "a" + SEP + "b" + SEP + "c.txt",
                FilenameUtil.normalize("\\a\\b/c.txt"));
        assertEquals("C:" + SEP + "a" + SEP + "b" + SEP + "c.txt",
                FilenameUtil.normalize("C:\\a\\b/c.txt"));
        assertEquals("" + SEP + "" + SEP + "server" + SEP + "a" + SEP + "b" + SEP + "c.txt",
                FilenameUtil.normalize("\\\\server\\a\\b/c.txt"));
        assertEquals("~" + SEP + "a" + SEP + "b" + SEP + "c.txt",
                FilenameUtil.normalize("~\\a\\b/c.txt"));
        assertEquals("~user" + SEP + "a" + SEP + "b" + SEP + "c.txt",
                FilenameUtil.normalize("~user\\a\\b/c.txt"));

        assertEquals("a" + SEP + "c", FilenameUtil.normalize("a/b/../c"));
        assertEquals("c", FilenameUtil.normalize("a/b/../../c"));
        assertEquals("c" + SEP, FilenameUtil.normalize("a/b/../../c/"));
        assertEquals(null, FilenameUtil.normalize("a/b/../../../c"));
        assertEquals("a" + SEP, FilenameUtil.normalize("a/b/.."));
        assertEquals("a" + SEP, FilenameUtil.normalize("a/b/../"));
        assertEquals("", FilenameUtil.normalize("a/b/../.."));
        assertEquals("", FilenameUtil.normalize("a/b/../../"));
        assertEquals(null, FilenameUtil.normalize("a/b/../../.."));
        assertEquals("a" + SEP + "d", FilenameUtil.normalize("a/b/../c/../d"));
        assertEquals("a" + SEP + "d" + SEP, FilenameUtil.normalize("a/b/../c/../d/"));
        assertEquals("a" + SEP + "b" + SEP + "d", FilenameUtil.normalize("a/b//d"));
        assertEquals("a" + SEP + "b" + SEP, FilenameUtil.normalize("a/b/././."));
        assertEquals("a" + SEP + "b" + SEP, FilenameUtil.normalize("a/b/./././"));
        assertEquals("a" + SEP, FilenameUtil.normalize("./a/"));
        assertEquals("a", FilenameUtil.normalize("./a"));
        assertEquals("", FilenameUtil.normalize("./"));
        assertEquals("", FilenameUtil.normalize("."));
        assertEquals(null, FilenameUtil.normalize("../a"));
        assertEquals(null, FilenameUtil.normalize(".."));
        assertEquals("", FilenameUtil.normalize(""));

        assertEquals(SEP + "a", FilenameUtil.normalize("/a"));
        assertEquals(SEP + "a" + SEP, FilenameUtil.normalize("/a/"));
        assertEquals(SEP + "a" + SEP + "c", FilenameUtil.normalize("/a/b/../c"));
        assertEquals(SEP + "c", FilenameUtil.normalize("/a/b/../../c"));
        assertEquals(null, FilenameUtil.normalize("/a/b/../../../c"));
        assertEquals(SEP + "a" + SEP, FilenameUtil.normalize("/a/b/.."));
        assertEquals(SEP + "", FilenameUtil.normalize("/a/b/../.."));
        assertEquals(null, FilenameUtil.normalize("/a/b/../../.."));
        assertEquals(SEP + "a" + SEP + "d", FilenameUtil.normalize("/a/b/../c/../d"));
        assertEquals(SEP + "a" + SEP + "b" + SEP + "d", FilenameUtil.normalize("/a/b//d"));
        assertEquals(SEP + "a" + SEP + "b" + SEP, FilenameUtil.normalize("/a/b/././."));
        assertEquals(SEP + "a", FilenameUtil.normalize("/./a"));
        assertEquals(SEP + "", FilenameUtil.normalize("/./"));
        assertEquals(SEP + "", FilenameUtil.normalize("/."));
        assertEquals(null, FilenameUtil.normalize("/../a"));
        assertEquals(null, FilenameUtil.normalize("/.."));
        assertEquals(SEP + "", FilenameUtil.normalize("/"));

        assertEquals("~" + SEP + "a", FilenameUtil.normalize("~/a"));
        assertEquals("~" + SEP + "a" + SEP, FilenameUtil.normalize("~/a/"));
        assertEquals("~" + SEP + "a" + SEP + "c", FilenameUtil.normalize("~/a/b/../c"));
        assertEquals("~" + SEP + "c", FilenameUtil.normalize("~/a/b/../../c"));
        assertEquals(null, FilenameUtil.normalize("~/a/b/../../../c"));
        assertEquals("~" + SEP + "a" + SEP, FilenameUtil.normalize("~/a/b/.."));
        assertEquals("~" + SEP + "", FilenameUtil.normalize("~/a/b/../.."));
        assertEquals(null, FilenameUtil.normalize("~/a/b/../../.."));
        assertEquals("~" + SEP + "a" + SEP + "d", FilenameUtil.normalize("~/a/b/../c/../d"));
        assertEquals("~" + SEP + "a" + SEP + "b" + SEP + "d", FilenameUtil.normalize("~/a/b//d"));
        assertEquals("~" + SEP + "a" + SEP + "b" + SEP, FilenameUtil.normalize("~/a/b/././."));
        assertEquals("~" + SEP + "a", FilenameUtil.normalize("~/./a"));
        assertEquals("~" + SEP, FilenameUtil.normalize("~/./"));
        assertEquals("~" + SEP, FilenameUtil.normalize("~/."));
        assertEquals(null, FilenameUtil.normalize("~/../a"));
        assertEquals(null, FilenameUtil.normalize("~/.."));
        assertEquals("~" + SEP, FilenameUtil.normalize("~/"));
        assertEquals("~" + SEP, FilenameUtil.normalize("~"));

        assertEquals("~user" + SEP + "a", FilenameUtil.normalize("~user/a"));
        assertEquals("~user" + SEP + "a" + SEP, FilenameUtil.normalize("~user/a/"));
        assertEquals("~user" + SEP + "a" + SEP + "c", FilenameUtil.normalize("~user/a/b/../c"));
        assertEquals("~user" + SEP + "c", FilenameUtil.normalize("~user/a/b/../../c"));
        assertEquals(null, FilenameUtil.normalize("~user/a/b/../../../c"));
        assertEquals("~user" + SEP + "a" + SEP, FilenameUtil.normalize("~user/a/b/.."));
        assertEquals("~user" + SEP + "", FilenameUtil.normalize("~user/a/b/../.."));
        assertEquals(null, FilenameUtil.normalize("~user/a/b/../../.."));
        assertEquals("~user" + SEP + "a" + SEP + "d",
                FilenameUtil.normalize("~user/a/b/../c/../d"));
        assertEquals("~user" + SEP + "a" + SEP + "b" + SEP + "d",
                FilenameUtil.normalize("~user/a/b//d"));
        assertEquals("~user" + SEP + "a" + SEP + "b" + SEP,
                FilenameUtil.normalize("~user/a/b/././."));
        assertEquals("~user" + SEP + "a", FilenameUtil.normalize("~user/./a"));
        assertEquals("~user" + SEP + "", FilenameUtil.normalize("~user/./"));
        assertEquals("~user" + SEP + "", FilenameUtil.normalize("~user/."));
        assertEquals(null, FilenameUtil.normalize("~user/../a"));
        assertEquals(null, FilenameUtil.normalize("~user/.."));
        assertEquals("~user" + SEP, FilenameUtil.normalize("~user/"));
        assertEquals("~user" + SEP, FilenameUtil.normalize("~user"));

        assertEquals("C:" + SEP + "a", FilenameUtil.normalize("C:/a"));
        assertEquals("C:" + SEP + "a" + SEP, FilenameUtil.normalize("C:/a/"));
        assertEquals("C:" + SEP + "a" + SEP + "c", FilenameUtil.normalize("C:/a/b/../c"));
        assertEquals("C:" + SEP + "c", FilenameUtil.normalize("C:/a/b/../../c"));
        assertEquals(null, FilenameUtil.normalize("C:/a/b/../../../c"));
        assertEquals("C:" + SEP + "a" + SEP, FilenameUtil.normalize("C:/a/b/.."));
        assertEquals("C:" + SEP + "", FilenameUtil.normalize("C:/a/b/../.."));
        assertEquals(null, FilenameUtil.normalize("C:/a/b/../../.."));
        assertEquals("C:" + SEP + "a" + SEP + "d", FilenameUtil.normalize("C:/a/b/../c/../d"));
        assertEquals("C:" + SEP + "a" + SEP + "b" + SEP + "d", FilenameUtil.normalize("C:/a/b//d"));
        assertEquals("C:" + SEP + "a" + SEP + "b" + SEP, FilenameUtil.normalize("C:/a/b/././."));
        assertEquals("C:" + SEP + "a", FilenameUtil.normalize("C:/./a"));
        assertEquals("C:" + SEP + "", FilenameUtil.normalize("C:/./"));
        assertEquals("C:" + SEP + "", FilenameUtil.normalize("C:/."));
        assertEquals(null, FilenameUtil.normalize("C:/../a"));
        assertEquals(null, FilenameUtil.normalize("C:/.."));
        assertEquals("C:" + SEP + "", FilenameUtil.normalize("C:/"));

        assertEquals("C:" + "a", FilenameUtil.normalize("C:a"));
        assertEquals("C:" + "a" + SEP, FilenameUtil.normalize("C:a/"));
        assertEquals("C:" + "a" + SEP + "c", FilenameUtil.normalize("C:a/b/../c"));
        assertEquals("C:" + "c", FilenameUtil.normalize("C:a/b/../../c"));
        assertEquals(null, FilenameUtil.normalize("C:a/b/../../../c"));
        assertEquals("C:" + "a" + SEP, FilenameUtil.normalize("C:a/b/.."));
        assertEquals("C:" + "", FilenameUtil.normalize("C:a/b/../.."));
        assertEquals(null, FilenameUtil.normalize("C:a/b/../../.."));
        assertEquals("C:" + "a" + SEP + "d", FilenameUtil.normalize("C:a/b/../c/../d"));
        assertEquals("C:" + "a" + SEP + "b" + SEP + "d", FilenameUtil.normalize("C:a/b//d"));
        assertEquals("C:" + "a" + SEP + "b" + SEP, FilenameUtil.normalize("C:a/b/././."));
        assertEquals("C:" + "a", FilenameUtil.normalize("C:./a"));
        assertEquals("C:" + "", FilenameUtil.normalize("C:./"));
        assertEquals("C:" + "", FilenameUtil.normalize("C:."));
        assertEquals(null, FilenameUtil.normalize("C:../a"));
        assertEquals(null, FilenameUtil.normalize("C:.."));
        assertEquals("C:" + "", FilenameUtil.normalize("C:"));

        assertEquals(SEP + SEP + "server" + SEP + "a", FilenameUtil.normalize("//server/a"));
        assertEquals(SEP + SEP + "server" + SEP + "a" + SEP, FilenameUtil.normalize("//server/a/"));
        assertEquals(SEP + SEP + "server" + SEP + "a" + SEP + "c",
                FilenameUtil.normalize("//server/a/b/../c"));
        assertEquals(SEP + SEP + "server" + SEP + "c",
                FilenameUtil.normalize("//server/a/b/../../c"));
        assertEquals(null, FilenameUtil.normalize("//server/a/b/../../../c"));
        assertEquals(SEP + SEP + "server" + SEP + "a" + SEP,
                FilenameUtil.normalize("//server/a/b/.."));
        assertEquals(SEP + SEP + "server" + SEP + "", FilenameUtil.normalize("//server/a/b/../.."));
        assertEquals(null, FilenameUtil.normalize("//server/a/b/../../.."));
        assertEquals(SEP + SEP + "server" + SEP + "a" + SEP + "d",
                FilenameUtil.normalize("//server/a/b/../c/../d"));
        assertEquals(SEP + SEP + "server" + SEP + "a" + SEP + "b" + SEP + "d",
                FilenameUtil.normalize("//server/a/b//d"));
        assertEquals(SEP + SEP + "server" + SEP + "a" + SEP + "b" + SEP,
                FilenameUtil.normalize("//server/a/b/././."));
        assertEquals(SEP + SEP + "server" + SEP + "a", FilenameUtil.normalize("//server/./a"));
        assertEquals(SEP + SEP + "server" + SEP + "", FilenameUtil.normalize("//server/./"));
        assertEquals(SEP + SEP + "server" + SEP + "", FilenameUtil.normalize("//server/."));
        assertEquals(null, FilenameUtil.normalize("//server/../a"));
        assertEquals(null, FilenameUtil.normalize("//server/.."));
        assertEquals(SEP + SEP + "server" + SEP + "", FilenameUtil.normalize("//server/"));
    }

    /**
     * Tests the normalize functionality wothout the end separator.
     */
    @Test
    public void testNormalizeNoEndSeparator() throws Exception {
        assertEquals(null, FilenameUtil.normalizeNoEndSeparator(null));
        assertEquals(null, FilenameUtil.normalizeNoEndSeparator(":"));
        assertEquals(null, FilenameUtil.normalizeNoEndSeparator("1:\\a\\b\\c.txt"));
        assertEquals(null, FilenameUtil.normalizeNoEndSeparator("1:"));
        assertEquals(null, FilenameUtil.normalizeNoEndSeparator("1:a"));
        assertEquals(null, FilenameUtil.normalizeNoEndSeparator("\\\\\\a\\b\\c.txt"));
        assertEquals(null, FilenameUtil.normalizeNoEndSeparator("\\\\a"));

        assertEquals("a" + SEP + "b" + SEP + "c.txt",
                FilenameUtil.normalizeNoEndSeparator("a\\b/c.txt"));
        assertEquals("" + SEP + "a" + SEP + "b" + SEP + "c.txt",
                FilenameUtil.normalizeNoEndSeparator("\\a\\b/c.txt"));
        assertEquals("C:" + SEP + "a" + SEP + "b" + SEP + "c.txt",
                FilenameUtil.normalizeNoEndSeparator("C:\\a\\b/c.txt"));
        assertEquals("" + SEP + "" + SEP + "server" + SEP + "a" + SEP + "b" + SEP + "c.txt",
                FilenameUtil.normalizeNoEndSeparator("\\\\server\\a\\b/c.txt"));
        assertEquals("~" + SEP + "a" + SEP + "b" + SEP + "c.txt",
                FilenameUtil.normalizeNoEndSeparator("~\\a\\b/c.txt"));
        assertEquals("~user" + SEP + "a" + SEP + "b" + SEP + "c.txt",
                FilenameUtil.normalizeNoEndSeparator("~user\\a\\b/c.txt"));

        assertEquals("a" + SEP + "c", FilenameUtil.normalizeNoEndSeparator("a/b/../c"));
        assertEquals("c", FilenameUtil.normalizeNoEndSeparator("a/b/../../c"));
        assertEquals("c", FilenameUtil.normalizeNoEndSeparator("a/b/../../c/"));
        assertEquals(null, FilenameUtil.normalizeNoEndSeparator("a/b/../../../c"));
        assertEquals("a", FilenameUtil.normalizeNoEndSeparator("a/b/.."));
        assertEquals("a", FilenameUtil.normalizeNoEndSeparator("a/b/../"));
        assertEquals("", FilenameUtil.normalizeNoEndSeparator("a/b/../.."));
        assertEquals("", FilenameUtil.normalizeNoEndSeparator("a/b/../../"));
        assertEquals(null, FilenameUtil.normalizeNoEndSeparator("a/b/../../.."));
        assertEquals("a" + SEP + "d", FilenameUtil.normalizeNoEndSeparator("a/b/../c/../d"));
        assertEquals("a" + SEP + "d", FilenameUtil.normalizeNoEndSeparator("a/b/../c/../d/"));
        assertEquals("a" + SEP + "b" + SEP + "d", FilenameUtil.normalizeNoEndSeparator("a/b//d"));
        assertEquals("a" + SEP + "b", FilenameUtil.normalizeNoEndSeparator("a/b/././."));
        assertEquals("a" + SEP + "b", FilenameUtil.normalizeNoEndSeparator("a/b/./././"));
        assertEquals("a", FilenameUtil.normalizeNoEndSeparator("./a/"));
        assertEquals("a", FilenameUtil.normalizeNoEndSeparator("./a"));
        assertEquals("", FilenameUtil.normalizeNoEndSeparator("./"));
        assertEquals("", FilenameUtil.normalizeNoEndSeparator("."));
        assertEquals(null, FilenameUtil.normalizeNoEndSeparator("../a"));
        assertEquals(null, FilenameUtil.normalizeNoEndSeparator(".."));
        assertEquals("", FilenameUtil.normalizeNoEndSeparator(""));

        assertEquals(SEP + "a", FilenameUtil.normalizeNoEndSeparator("/a"));
        assertEquals(SEP + "a", FilenameUtil.normalizeNoEndSeparator("/a/"));
        assertEquals(SEP + "a" + SEP + "c", FilenameUtil.normalizeNoEndSeparator("/a/b/../c"));
        assertEquals(SEP + "c", FilenameUtil.normalizeNoEndSeparator("/a/b/../../c"));
        assertEquals(null, FilenameUtil.normalizeNoEndSeparator("/a/b/../../../c"));
        assertEquals(SEP + "a", FilenameUtil.normalizeNoEndSeparator("/a/b/.."));
        assertEquals(SEP + "", FilenameUtil.normalizeNoEndSeparator("/a/b/../.."));
        assertEquals(null, FilenameUtil.normalizeNoEndSeparator("/a/b/../../.."));
        assertEquals(SEP + "a" + SEP + "d", FilenameUtil.normalizeNoEndSeparator("/a/b/../c/../d"));
        assertEquals(SEP + "a" + SEP + "b" + SEP + "d",
                FilenameUtil.normalizeNoEndSeparator("/a/b//d"));
        assertEquals(SEP + "a" + SEP + "b", FilenameUtil.normalizeNoEndSeparator("/a/b/././."));
        assertEquals(SEP + "a", FilenameUtil.normalizeNoEndSeparator("/./a"));
        assertEquals(SEP + "", FilenameUtil.normalizeNoEndSeparator("/./"));
        assertEquals(SEP + "", FilenameUtil.normalizeNoEndSeparator("/."));
        assertEquals(null, FilenameUtil.normalizeNoEndSeparator("/../a"));
        assertEquals(null, FilenameUtil.normalizeNoEndSeparator("/.."));
        assertEquals(SEP + "", FilenameUtil.normalizeNoEndSeparator("/"));

        assertEquals("~" + SEP + "a", FilenameUtil.normalizeNoEndSeparator("~/a"));
        assertEquals("~" + SEP + "a", FilenameUtil.normalizeNoEndSeparator("~/a/"));
        assertEquals("~" + SEP + "a" + SEP + "c",
                FilenameUtil.normalizeNoEndSeparator("~/a/b/../c"));
        assertEquals("~" + SEP + "c", FilenameUtil.normalizeNoEndSeparator("~/a/b/../../c"));
        assertEquals(null, FilenameUtil.normalizeNoEndSeparator("~/a/b/../../../c"));
        assertEquals("~" + SEP + "a", FilenameUtil.normalizeNoEndSeparator("~/a/b/.."));
        assertEquals("~" + SEP + "", FilenameUtil.normalizeNoEndSeparator("~/a/b/../.."));
        assertEquals(null, FilenameUtil.normalizeNoEndSeparator("~/a/b/../../.."));
        assertEquals("~" + SEP + "a" + SEP + "d",
                FilenameUtil.normalizeNoEndSeparator("~/a/b/../c/../d"));
        assertEquals("~" + SEP + "a" + SEP + "b" + SEP + "d",
                FilenameUtil.normalizeNoEndSeparator("~/a/b//d"));
        assertEquals("~" + SEP + "a" + SEP + "b",
                FilenameUtil.normalizeNoEndSeparator("~/a/b/././."));
        assertEquals("~" + SEP + "a", FilenameUtil.normalizeNoEndSeparator("~/./a"));
        assertEquals("~" + SEP, FilenameUtil.normalizeNoEndSeparator("~/./"));
        assertEquals("~" + SEP, FilenameUtil.normalizeNoEndSeparator("~/."));
        assertEquals(null, FilenameUtil.normalizeNoEndSeparator("~/../a"));
        assertEquals(null, FilenameUtil.normalizeNoEndSeparator("~/.."));
        assertEquals("~" + SEP, FilenameUtil.normalizeNoEndSeparator("~/"));
        assertEquals("~" + SEP, FilenameUtil.normalizeNoEndSeparator("~"));

        assertEquals("~user" + SEP + "a", FilenameUtil.normalizeNoEndSeparator("~user/a"));
        assertEquals("~user" + SEP + "a", FilenameUtil.normalizeNoEndSeparator("~user/a/"));
        assertEquals("~user" + SEP + "a" + SEP + "c",
                FilenameUtil.normalizeNoEndSeparator("~user/a/b/../c"));
        assertEquals("~user" + SEP + "c",
                FilenameUtil.normalizeNoEndSeparator("~user/a/b/../../c"));
        assertEquals(null, FilenameUtil.normalizeNoEndSeparator("~user/a/b/../../../c"));
        assertEquals("~user" + SEP + "a", FilenameUtil.normalizeNoEndSeparator("~user/a/b/.."));
        assertEquals("~user" + SEP + "", FilenameUtil.normalizeNoEndSeparator("~user/a/b/../.."));
        assertEquals(null, FilenameUtil.normalizeNoEndSeparator("~user/a/b/../../.."));
        assertEquals("~user" + SEP + "a" + SEP + "d",
                FilenameUtil.normalizeNoEndSeparator("~user/a/b/../c/../d"));
        assertEquals("~user" + SEP + "a" + SEP + "b" + SEP + "d",
                FilenameUtil.normalizeNoEndSeparator("~user/a/b//d"));
        assertEquals("~user" + SEP + "a" + SEP + "b",
                FilenameUtil.normalizeNoEndSeparator("~user/a/b/././."));
        assertEquals("~user" + SEP + "a", FilenameUtil.normalizeNoEndSeparator("~user/./a"));
        assertEquals("~user" + SEP + "", FilenameUtil.normalizeNoEndSeparator("~user/./"));
        assertEquals("~user" + SEP + "", FilenameUtil.normalizeNoEndSeparator("~user/."));
        assertEquals(null, FilenameUtil.normalizeNoEndSeparator("~user/../a"));
        assertEquals(null, FilenameUtil.normalizeNoEndSeparator("~user/.."));
        assertEquals("~user" + SEP, FilenameUtil.normalizeNoEndSeparator("~user/"));
        assertEquals("~user" + SEP, FilenameUtil.normalizeNoEndSeparator("~user"));

        assertEquals("C:" + SEP + "a", FilenameUtil.normalizeNoEndSeparator("C:/a"));
        assertEquals("C:" + SEP + "a", FilenameUtil.normalizeNoEndSeparator("C:/a/"));
        assertEquals("C:" + SEP + "a" + SEP + "c",
                FilenameUtil.normalizeNoEndSeparator("C:/a/b/../c"));
        assertEquals("C:" + SEP + "c", FilenameUtil.normalizeNoEndSeparator("C:/a/b/../../c"));
        assertEquals(null, FilenameUtil.normalizeNoEndSeparator("C:/a/b/../../../c"));
        assertEquals("C:" + SEP + "a", FilenameUtil.normalizeNoEndSeparator("C:/a/b/.."));
        assertEquals("C:" + SEP + "", FilenameUtil.normalizeNoEndSeparator("C:/a/b/../.."));
        assertEquals(null, FilenameUtil.normalizeNoEndSeparator("C:/a/b/../../.."));
        assertEquals("C:" + SEP + "a" + SEP + "d",
                FilenameUtil.normalizeNoEndSeparator("C:/a/b/../c/../d"));
        assertEquals("C:" + SEP + "a" + SEP + "b" + SEP + "d",
                FilenameUtil.normalizeNoEndSeparator("C:/a/b//d"));
        assertEquals("C:" + SEP + "a" + SEP + "b",
                FilenameUtil.normalizeNoEndSeparator("C:/a/b/././."));
        assertEquals("C:" + SEP + "a", FilenameUtil.normalizeNoEndSeparator("C:/./a"));
        assertEquals("C:" + SEP + "", FilenameUtil.normalizeNoEndSeparator("C:/./"));
        assertEquals("C:" + SEP + "", FilenameUtil.normalizeNoEndSeparator("C:/."));
        assertEquals(null, FilenameUtil.normalizeNoEndSeparator("C:/../a"));
        assertEquals(null, FilenameUtil.normalizeNoEndSeparator("C:/.."));
        assertEquals("C:" + SEP + "", FilenameUtil.normalizeNoEndSeparator("C:/"));

        assertEquals("C:" + "a", FilenameUtil.normalizeNoEndSeparator("C:a"));
        assertEquals("C:" + "a", FilenameUtil.normalizeNoEndSeparator("C:a/"));
        assertEquals("C:" + "a" + SEP + "c", FilenameUtil.normalizeNoEndSeparator("C:a/b/../c"));
        assertEquals("C:" + "c", FilenameUtil.normalizeNoEndSeparator("C:a/b/../../c"));
        assertEquals(null, FilenameUtil.normalizeNoEndSeparator("C:a/b/../../../c"));
        assertEquals("C:" + "a", FilenameUtil.normalizeNoEndSeparator("C:a/b/.."));
        assertEquals("C:" + "", FilenameUtil.normalizeNoEndSeparator("C:a/b/../.."));
        assertEquals(null, FilenameUtil.normalizeNoEndSeparator("C:a/b/../../.."));
        assertEquals("C:" + "a" + SEP + "d",
                FilenameUtil.normalizeNoEndSeparator("C:a/b/../c/../d"));
        assertEquals("C:" + "a" + SEP + "b" + SEP + "d",
                FilenameUtil.normalizeNoEndSeparator("C:a/b//d"));
        assertEquals("C:" + "a" + SEP + "b", FilenameUtil.normalizeNoEndSeparator("C:a/b/././."));
        assertEquals("C:" + "a", FilenameUtil.normalizeNoEndSeparator("C:./a"));
        assertEquals("C:" + "", FilenameUtil.normalizeNoEndSeparator("C:./"));
        assertEquals("C:" + "", FilenameUtil.normalizeNoEndSeparator("C:."));
        assertEquals(null, FilenameUtil.normalizeNoEndSeparator("C:../a"));
        assertEquals(null, FilenameUtil.normalizeNoEndSeparator("C:.."));
        assertEquals("C:" + "", FilenameUtil.normalizeNoEndSeparator("C:"));

        assertEquals(SEP + SEP + "server" + SEP + "a",
                FilenameUtil.normalizeNoEndSeparator("//server/a"));
        assertEquals(SEP + SEP + "server" + SEP + "a",
                FilenameUtil.normalizeNoEndSeparator("//server/a/"));
        assertEquals(SEP + SEP + "server" + SEP + "a" + SEP + "c",
                FilenameUtil.normalizeNoEndSeparator("//server/a/b/../c"));
        assertEquals(SEP + SEP + "server" + SEP + "c",
                FilenameUtil.normalizeNoEndSeparator("//server/a/b/../../c"));
        assertEquals(null, FilenameUtil.normalizeNoEndSeparator("//server/a/b/../../../c"));
        assertEquals(SEP + SEP + "server" + SEP + "a",
                FilenameUtil.normalizeNoEndSeparator("//server/a/b/.."));
        assertEquals(SEP + SEP + "server" + SEP + "",
                FilenameUtil.normalizeNoEndSeparator("//server/a/b/../.."));
        assertEquals(null, FilenameUtil.normalizeNoEndSeparator("//server/a/b/../../.."));
        assertEquals(SEP + SEP + "server" + SEP + "a" + SEP + "d",
                FilenameUtil.normalizeNoEndSeparator("//server/a/b/../c/../d"));
        assertEquals(SEP + SEP + "server" + SEP + "a" + SEP + "b" + SEP + "d",
                FilenameUtil.normalizeNoEndSeparator("//server/a/b//d"));
        assertEquals(SEP + SEP + "server" + SEP + "a" + SEP + "b",
                FilenameUtil.normalizeNoEndSeparator("//server/a/b/././."));
        assertEquals(SEP + SEP + "server" + SEP + "a",
                FilenameUtil.normalizeNoEndSeparator("//server/./a"));
        assertEquals(SEP + SEP + "server" + SEP + "",
                FilenameUtil.normalizeNoEndSeparator("//server/./"));
        assertEquals(SEP + SEP + "server" + SEP + "",
                FilenameUtil.normalizeNoEndSeparator("//server/."));
        assertEquals(null, FilenameUtil.normalizeNoEndSeparator("//server/../a"));
        assertEquals(null, FilenameUtil.normalizeNoEndSeparator("//server/.."));
        assertEquals(SEP + SEP + "server" + SEP + "",
                FilenameUtil.normalizeNoEndSeparator("//server/"));
    }

    /**
     * Tests concatenating file paths.
     */
    @Test
    public void testConcat() {
        assertEquals(null, FilenameUtil.concat("", null));
        assertEquals(null, FilenameUtil.concat(null, null));
        assertEquals(null, FilenameUtil.concat(null, ""));
        assertEquals(null, FilenameUtil.concat(null, "a"));
        assertEquals(SEP + "a", FilenameUtil.concat(null, "/a"));

        assertEquals(null, FilenameUtil.concat("", ":")); // invalid prefix
        assertEquals(null, FilenameUtil.concat(":", "")); // invalid prefix

        assertEquals("f" + SEP, FilenameUtil.concat("", "f/"));
        assertEquals("f", FilenameUtil.concat("", "f"));
        assertEquals("a" + SEP + "f" + SEP, FilenameUtil.concat("a/", "f/"));
        assertEquals("a" + SEP + "f", FilenameUtil.concat("a", "f"));
        assertEquals("a" + SEP + "b" + SEP + "f" + SEP, FilenameUtil.concat("a/b/", "f/"));
        assertEquals("a" + SEP + "b" + SEP + "f", FilenameUtil.concat("a/b", "f"));

        assertEquals("a" + SEP + "f" + SEP, FilenameUtil.concat("a/b/", "../f/"));
        assertEquals("a" + SEP + "f", FilenameUtil.concat("a/b", "../f"));
        assertEquals("a" + SEP + "c" + SEP + "g" + SEP,
                FilenameUtil.concat("a/b/../c/", "f/../g/"));
        assertEquals("a" + SEP + "c" + SEP + "g", FilenameUtil.concat("a/b/../c", "f/../g"));

        assertEquals("a" + SEP + "c.txt" + SEP + "f", FilenameUtil.concat("a/c.txt", "f"));

        assertEquals(SEP + "f" + SEP, FilenameUtil.concat("", "/f/"));
        assertEquals(SEP + "f", FilenameUtil.concat("", "/f"));
        assertEquals(SEP + "f" + SEP, FilenameUtil.concat("a/", "/f/"));
        assertEquals(SEP + "f", FilenameUtil.concat("a", "/f"));

        assertEquals(SEP + "c" + SEP + "d", FilenameUtil.concat("a/b/", "/c/d"));
        assertEquals("C:c" + SEP + "d", FilenameUtil.concat("a/b/", "C:c/d"));
        assertEquals("C:" + SEP + "c" + SEP + "d", FilenameUtil.concat("a/b/", "C:/c/d"));
        assertEquals("~" + SEP + "c" + SEP + "d", FilenameUtil.concat("a/b/", "~/c/d"));
        assertEquals("~user" + SEP + "c" + SEP + "d", FilenameUtil.concat("a/b/", "~user/c/d"));
        assertEquals("~" + SEP, FilenameUtil.concat("a/b/", "~"));
        assertEquals("~user" + SEP, FilenameUtil.concat("a/b/", "~user"));
    }

    /**
     * Tests the conversion of file paths to Unix paths.
     */
    @Test
    public void testSeparatorsToUnix() {
        assertEquals(null, FilenameUtil.separatorsToUnix(null));
        assertEquals("/a/b/c", FilenameUtil.separatorsToUnix("/a/b/c"));
        assertEquals("/a/b/c.txt", FilenameUtil.separatorsToUnix("/a/b/c.txt"));
        assertEquals("/a/b/c", FilenameUtil.separatorsToUnix("/a/b\\c"));
        assertEquals("/a/b/c", FilenameUtil.separatorsToUnix("\\a\\b\\c"));
        assertEquals("D:/a/b/c", FilenameUtil.separatorsToUnix("D:\\a\\b\\c"));
    }

    /**
     * Tests the conversion of file paths to windows paths.
     */
    @Test
    public void testSeparatorsToWindows() {
        assertEquals(null, FilenameUtil.separatorsToWindows(null));
        assertEquals("\\a\\b\\c", FilenameUtil.separatorsToWindows("\\a\\b\\c"));
        assertEquals("\\a\\b\\c.txt", FilenameUtil.separatorsToWindows("\\a\\b\\c.txt"));
        assertEquals("\\a\\b\\c", FilenameUtil.separatorsToWindows("\\a\\b/c"));
        assertEquals("\\a\\b\\c", FilenameUtil.separatorsToWindows("/a/b/c"));
        assertEquals("D:\\a\\b\\c", FilenameUtil.separatorsToWindows("D:/a/b/c"));
    }

    @Test
    public void testSeparatorsToSystem() {
        if (WINDOWS) {
            assertEquals(null, FilenameUtil.separatorsToSystem(null));
            assertEquals("\\a\\b\\c", FilenameUtil.separatorsToSystem("\\a\\b\\c"));
            assertEquals("\\a\\b\\c.txt", FilenameUtil.separatorsToSystem("\\a\\b\\c.txt"));
            assertEquals("\\a\\b\\c", FilenameUtil.separatorsToSystem("\\a\\b/c"));
            assertEquals("\\a\\b\\c", FilenameUtil.separatorsToSystem("/a/b/c"));
            assertEquals("D:\\a\\b\\c", FilenameUtil.separatorsToSystem("D:/a/b/c"));
        } else {
            assertEquals(null, FilenameUtil.separatorsToSystem(null));
            assertEquals("/a/b/c", FilenameUtil.separatorsToSystem("/a/b/c"));
            assertEquals("/a/b/c.txt", FilenameUtil.separatorsToSystem("/a/b/c.txt"));
            assertEquals("/a/b/c", FilenameUtil.separatorsToSystem("/a/b\\c"));
            assertEquals("/a/b/c", FilenameUtil.separatorsToSystem("\\a\\b\\c"));
            assertEquals("D:/a/b/c", FilenameUtil.separatorsToSystem("D:\\a\\b\\c"));
        }
    }

    // -----------------------------------------------------------------------
    @Test
    public void testGetPrefixLength() {
        assertEquals(-1, FilenameUtil.getPrefixLength(null));
        assertEquals(-1, FilenameUtil.getPrefixLength(":"));
        assertEquals(-1, FilenameUtil.getPrefixLength("1:\\a\\b\\c.txt"));
        assertEquals(-1, FilenameUtil.getPrefixLength("1:"));
        assertEquals(-1, FilenameUtil.getPrefixLength("1:a"));
        assertEquals(-1, FilenameUtil.getPrefixLength("\\\\\\a\\b\\c.txt"));
        assertEquals(-1, FilenameUtil.getPrefixLength("\\\\a"));

        assertEquals(0, FilenameUtil.getPrefixLength(""));
        assertEquals(1, FilenameUtil.getPrefixLength("\\"));
        assertEquals(2, FilenameUtil.getPrefixLength("C:"));
        assertEquals(3, FilenameUtil.getPrefixLength("C:\\"));
        assertEquals(9, FilenameUtil.getPrefixLength("//server/"));
        assertEquals(2, FilenameUtil.getPrefixLength("~"));
        assertEquals(2, FilenameUtil.getPrefixLength("~/"));
        assertEquals(6, FilenameUtil.getPrefixLength("~user"));
        assertEquals(6, FilenameUtil.getPrefixLength("~user/"));

        assertEquals(0, FilenameUtil.getPrefixLength("a\\b\\c.txt"));
        assertEquals(1, FilenameUtil.getPrefixLength("\\a\\b\\c.txt"));
        assertEquals(2, FilenameUtil.getPrefixLength("C:a\\b\\c.txt"));
        assertEquals(3, FilenameUtil.getPrefixLength("C:\\a\\b\\c.txt"));
        assertEquals(9, FilenameUtil.getPrefixLength("\\\\server\\a\\b\\c.txt"));

        assertEquals(0, FilenameUtil.getPrefixLength("a/b/c.txt"));
        assertEquals(1, FilenameUtil.getPrefixLength("/a/b/c.txt"));
        assertEquals(3, FilenameUtil.getPrefixLength("C:/a/b/c.txt"));
        assertEquals(9, FilenameUtil.getPrefixLength("//server/a/b/c.txt"));
        assertEquals(2, FilenameUtil.getPrefixLength("~/a/b/c.txt"));
        assertEquals(6, FilenameUtil.getPrefixLength("~user/a/b/c.txt"));

        assertEquals(0, FilenameUtil.getPrefixLength("a\\b\\c.txt"));
        assertEquals(1, FilenameUtil.getPrefixLength("\\a\\b\\c.txt"));
        assertEquals(2, FilenameUtil.getPrefixLength("~\\a\\b\\c.txt"));
        assertEquals(6, FilenameUtil.getPrefixLength("~user\\a\\b\\c.txt"));
    }

    @Test
    public void testIndexOfLastSeparator() {
        assertEquals(-1, FilenameUtil.indexOfLastSeparator(null));
        assertEquals(-1, FilenameUtil.indexOfLastSeparator("noseperator.inthispath"));
        assertEquals(3, FilenameUtil.indexOfLastSeparator("a/b/c"));
        assertEquals(3, FilenameUtil.indexOfLastSeparator("a\\b\\c"));
    }

    @Test
    public void testIndexOfExtension() {
        assertEquals(-1, FilenameUtil.indexOfExtension(null));
        assertEquals(-1, FilenameUtil.indexOfExtension("file"));
        assertEquals(4, FilenameUtil.indexOfExtension("file.txt"));
        assertEquals(13, FilenameUtil.indexOfExtension("a.txt/b.txt/c.txt"));
        assertEquals(-1, FilenameUtil.indexOfExtension("a/b/c"));
        assertEquals(-1, FilenameUtil.indexOfExtension("a\\b\\c"));
        assertEquals(-1, FilenameUtil.indexOfExtension("a/b.notextension/c"));
        assertEquals(-1, FilenameUtil.indexOfExtension("a\\b.notextension\\c"));
    }

    // -----------------------------------------------------------------------
    @Test
    public void testGetPrefix() {
        assertEquals(null, FilenameUtil.getPrefix(null));
        assertEquals(null, FilenameUtil.getPrefix(":"));
        assertEquals(null, FilenameUtil.getPrefix("1:\\a\\b\\c.txt"));
        assertEquals(null, FilenameUtil.getPrefix("1:"));
        assertEquals(null, FilenameUtil.getPrefix("1:a"));
        assertEquals(null, FilenameUtil.getPrefix("\\\\\\a\\b\\c.txt"));
        assertEquals(null, FilenameUtil.getPrefix("\\\\a"));

        assertEquals("", FilenameUtil.getPrefix(""));
        assertEquals("\\", FilenameUtil.getPrefix("\\"));
        assertEquals("C:", FilenameUtil.getPrefix("C:"));
        assertEquals("C:\\", FilenameUtil.getPrefix("C:\\"));
        assertEquals("//server/", FilenameUtil.getPrefix("//server/"));
        assertEquals("~/", FilenameUtil.getPrefix("~"));
        assertEquals("~/", FilenameUtil.getPrefix("~/"));
        assertEquals("~user/", FilenameUtil.getPrefix("~user"));
        assertEquals("~user/", FilenameUtil.getPrefix("~user/"));

        assertEquals("", FilenameUtil.getPrefix("a\\b\\c.txt"));
        assertEquals("\\", FilenameUtil.getPrefix("\\a\\b\\c.txt"));
        assertEquals("C:\\", FilenameUtil.getPrefix("C:\\a\\b\\c.txt"));
        assertEquals("\\\\server\\", FilenameUtil.getPrefix("\\\\server\\a\\b\\c.txt"));

        assertEquals("", FilenameUtil.getPrefix("a/b/c.txt"));
        assertEquals("/", FilenameUtil.getPrefix("/a/b/c.txt"));
        assertEquals("C:/", FilenameUtil.getPrefix("C:/a/b/c.txt"));
        assertEquals("//server/", FilenameUtil.getPrefix("//server/a/b/c.txt"));
        assertEquals("~/", FilenameUtil.getPrefix("~/a/b/c.txt"));
        assertEquals("~user/", FilenameUtil.getPrefix("~user/a/b/c.txt"));

        assertEquals("", FilenameUtil.getPrefix("a\\b\\c.txt"));
        assertEquals("\\", FilenameUtil.getPrefix("\\a\\b\\c.txt"));
        assertEquals("~\\", FilenameUtil.getPrefix("~\\a\\b\\c.txt"));
        assertEquals("~user\\", FilenameUtil.getPrefix("~user\\a\\b\\c.txt"));
    }

    @Test
    public void testGetPath() {
        assertEquals(null, FilenameUtil.getPath(null));
        assertEquals("", FilenameUtil.getPath("noseperator.inthispath"));
        assertEquals("a/b/", FilenameUtil.getPath("a/b/c.txt"));
        assertEquals("a/b/", FilenameUtil.getPath("a/b/c"));
        assertEquals("a/b/c/", FilenameUtil.getPath("a/b/c/"));
        assertEquals("a\\b\\", FilenameUtil.getPath("a\\b\\c"));

        assertEquals(null, FilenameUtil.getPath(":"));
        assertEquals(null, FilenameUtil.getPath("1:/a/b/c.txt"));
        assertEquals(null, FilenameUtil.getPath("1:"));
        assertEquals(null, FilenameUtil.getPath("1:a"));
        assertEquals(null, FilenameUtil.getPath("///a/b/c.txt"));
        assertEquals(null, FilenameUtil.getPath("//a"));

        assertEquals("", FilenameUtil.getPath(""));
        assertEquals("", FilenameUtil.getPath("C:"));
        assertEquals("", FilenameUtil.getPath("C:/"));
        assertEquals("", FilenameUtil.getPath("//server/"));
        assertEquals("", FilenameUtil.getPath("~"));
        assertEquals("", FilenameUtil.getPath("~/"));
        assertEquals("", FilenameUtil.getPath("~user"));
        assertEquals("", FilenameUtil.getPath("~user/"));

        assertEquals("a/b/", FilenameUtil.getPath("a/b/c.txt"));
        assertEquals("a/b/", FilenameUtil.getPath("/a/b/c.txt"));
        assertEquals("", FilenameUtil.getPath("C:a"));
        assertEquals("a/b/", FilenameUtil.getPath("C:a/b/c.txt"));
        assertEquals("a/b/", FilenameUtil.getPath("C:/a/b/c.txt"));
        assertEquals("a/b/", FilenameUtil.getPath("//server/a/b/c.txt"));
        assertEquals("a/b/", FilenameUtil.getPath("~/a/b/c.txt"));
        assertEquals("a/b/", FilenameUtil.getPath("~user/a/b/c.txt"));
    }

    @Test
    public void testGetPathNoEndSeparator() {
        assertEquals(null, FilenameUtil.getPath(null));
        assertEquals("", FilenameUtil.getPath("noseperator.inthispath"));
        assertEquals("a/b", FilenameUtil.getPathNoEndSeparator("a/b/c.txt"));
        assertEquals("a/b", FilenameUtil.getPathNoEndSeparator("a/b/c"));
        assertEquals("a/b/c", FilenameUtil.getPathNoEndSeparator("a/b/c/"));
        assertEquals("a\\b", FilenameUtil.getPathNoEndSeparator("a\\b\\c"));

        assertEquals(null, FilenameUtil.getPathNoEndSeparator(":"));
        assertEquals(null, FilenameUtil.getPathNoEndSeparator("1:/a/b/c.txt"));
        assertEquals(null, FilenameUtil.getPathNoEndSeparator("1:"));
        assertEquals(null, FilenameUtil.getPathNoEndSeparator("1:a"));
        assertEquals(null, FilenameUtil.getPathNoEndSeparator("///a/b/c.txt"));
        assertEquals(null, FilenameUtil.getPathNoEndSeparator("//a"));

        assertEquals("", FilenameUtil.getPathNoEndSeparator(""));
        assertEquals("", FilenameUtil.getPathNoEndSeparator("C:"));
        assertEquals("", FilenameUtil.getPathNoEndSeparator("C:/"));
        assertEquals("", FilenameUtil.getPathNoEndSeparator("//server/"));
        assertEquals("", FilenameUtil.getPathNoEndSeparator("~"));
        assertEquals("", FilenameUtil.getPathNoEndSeparator("~/"));
        assertEquals("", FilenameUtil.getPathNoEndSeparator("~user"));
        assertEquals("", FilenameUtil.getPathNoEndSeparator("~user/"));

        assertEquals("a/b", FilenameUtil.getPathNoEndSeparator("a/b/c.txt"));
        assertEquals("a/b", FilenameUtil.getPathNoEndSeparator("/a/b/c.txt"));
        assertEquals("", FilenameUtil.getPathNoEndSeparator("C:a"));
        assertEquals("a/b", FilenameUtil.getPathNoEndSeparator("C:a/b/c.txt"));
        assertEquals("a/b", FilenameUtil.getPathNoEndSeparator("C:/a/b/c.txt"));
        assertEquals("a/b", FilenameUtil.getPathNoEndSeparator("//server/a/b/c.txt"));
        assertEquals("a/b", FilenameUtil.getPathNoEndSeparator("~/a/b/c.txt"));
        assertEquals("a/b", FilenameUtil.getPathNoEndSeparator("~user/a/b/c.txt"));
    }

    @Test
    public void testGetFullPath() {
        assertEquals(null, FilenameUtil.getFullPath(null));
        assertEquals("", FilenameUtil.getFullPath("noseperator.inthispath"));
        assertEquals("a/b/", FilenameUtil.getFullPath("a/b/c.txt"));
        assertEquals("a/b/", FilenameUtil.getFullPath("a/b/c"));
        assertEquals("a/b/c/", FilenameUtil.getFullPath("a/b/c/"));
        assertEquals("a\\b\\", FilenameUtil.getFullPath("a\\b\\c"));

        assertEquals(null, FilenameUtil.getFullPath(":"));
        assertEquals(null, FilenameUtil.getFullPath("1:/a/b/c.txt"));
        assertEquals(null, FilenameUtil.getFullPath("1:"));
        assertEquals(null, FilenameUtil.getFullPath("1:a"));
        assertEquals(null, FilenameUtil.getFullPath("///a/b/c.txt"));
        assertEquals(null, FilenameUtil.getFullPath("//a"));

        assertEquals("", FilenameUtil.getFullPath(""));
        assertEquals("C:", FilenameUtil.getFullPath("C:"));
        assertEquals("C:/", FilenameUtil.getFullPath("C:/"));
        assertEquals("//server/", FilenameUtil.getFullPath("//server/"));
        assertEquals("~/", FilenameUtil.getFullPath("~"));
        assertEquals("~/", FilenameUtil.getFullPath("~/"));
        assertEquals("~user/", FilenameUtil.getFullPath("~user"));
        assertEquals("~user/", FilenameUtil.getFullPath("~user/"));

        assertEquals("a/b/", FilenameUtil.getFullPath("a/b/c.txt"));
        assertEquals("/a/b/", FilenameUtil.getFullPath("/a/b/c.txt"));
        assertEquals("C:", FilenameUtil.getFullPath("C:a"));
        assertEquals("C:a/b/", FilenameUtil.getFullPath("C:a/b/c.txt"));
        assertEquals("C:/a/b/", FilenameUtil.getFullPath("C:/a/b/c.txt"));
        assertEquals("//server/a/b/", FilenameUtil.getFullPath("//server/a/b/c.txt"));
        assertEquals("~/a/b/", FilenameUtil.getFullPath("~/a/b/c.txt"));
        assertEquals("~user/a/b/", FilenameUtil.getFullPath("~user/a/b/c.txt"));
    }

    @Test
    public void testGetFullPathNoEndSeparator() {
        assertEquals(null, FilenameUtil.getFullPathNoEndSeparator(null));
        assertEquals("", FilenameUtil.getFullPathNoEndSeparator("noseperator.inthispath"));
        assertEquals("a/b", FilenameUtil.getFullPathNoEndSeparator("a/b/c.txt"));
        assertEquals("a/b", FilenameUtil.getFullPathNoEndSeparator("a/b/c"));
        assertEquals("a/b/c", FilenameUtil.getFullPathNoEndSeparator("a/b/c/"));
        assertEquals("a\\b", FilenameUtil.getFullPathNoEndSeparator("a\\b\\c"));

        assertEquals(null, FilenameUtil.getFullPathNoEndSeparator(":"));
        assertEquals(null, FilenameUtil.getFullPathNoEndSeparator("1:/a/b/c.txt"));
        assertEquals(null, FilenameUtil.getFullPathNoEndSeparator("1:"));
        assertEquals(null, FilenameUtil.getFullPathNoEndSeparator("1:a"));
        assertEquals(null, FilenameUtil.getFullPathNoEndSeparator("///a/b/c.txt"));
        assertEquals(null, FilenameUtil.getFullPathNoEndSeparator("//a"));

        assertEquals("", FilenameUtil.getFullPathNoEndSeparator(""));
        assertEquals("C:", FilenameUtil.getFullPathNoEndSeparator("C:"));
        assertEquals("C:/", FilenameUtil.getFullPathNoEndSeparator("C:/"));
        assertEquals("//server/", FilenameUtil.getFullPathNoEndSeparator("//server/"));
        assertEquals("~", FilenameUtil.getFullPathNoEndSeparator("~"));
        assertEquals("~/", FilenameUtil.getFullPathNoEndSeparator("~/"));
        assertEquals("~user", FilenameUtil.getFullPathNoEndSeparator("~user"));
        assertEquals("~user/", FilenameUtil.getFullPathNoEndSeparator("~user/"));

        assertEquals("a/b", FilenameUtil.getFullPathNoEndSeparator("a/b/c.txt"));
        assertEquals("/a/b", FilenameUtil.getFullPathNoEndSeparator("/a/b/c.txt"));
        assertEquals("C:", FilenameUtil.getFullPathNoEndSeparator("C:a"));
        assertEquals("C:a/b", FilenameUtil.getFullPathNoEndSeparator("C:a/b/c.txt"));
        assertEquals("C:/a/b", FilenameUtil.getFullPathNoEndSeparator("C:/a/b/c.txt"));
        assertEquals("//server/a/b", FilenameUtil.getFullPathNoEndSeparator("//server/a/b/c.txt"));
        assertEquals("~/a/b", FilenameUtil.getFullPathNoEndSeparator("~/a/b/c.txt"));
        assertEquals("~user/a/b", FilenameUtil.getFullPathNoEndSeparator("~user/a/b/c.txt"));
    }

    @Test
    public void testGetName() {
        assertEquals(null, FilenameUtil.getName(null));
        assertEquals("noseperator.inthispath", FilenameUtil.getName("noseperator.inthispath"));
        assertEquals("c.txt", FilenameUtil.getName("a/b/c.txt"));
        assertEquals("c", FilenameUtil.getName("a/b/c"));
        assertEquals("", FilenameUtil.getName("a/b/c/"));
        assertEquals("c", FilenameUtil.getName("a\\b\\c"));
    }

    @Test
    public void testGetBaseName() {
        assertEquals(null, FilenameUtil.getBaseName(null));
        assertEquals("noseperator", FilenameUtil.getBaseName("noseperator.inthispath"));
        assertEquals("c", FilenameUtil.getBaseName("a/b/c.txt"));
        assertEquals("c", FilenameUtil.getBaseName("a/b/c"));
        assertEquals("", FilenameUtil.getBaseName("a/b/c/"));
        assertEquals("c", FilenameUtil.getBaseName("a\\b\\c"));
        assertEquals("file.txt", FilenameUtil.getBaseName("file.txt.bak"));
    }

    @Test
    public void testGetExtension() {
        assertEquals(null, FilenameUtil.getExtension(null));
        assertEquals("ext", FilenameUtil.getExtension("file.ext"));
        assertEquals("", FilenameUtil.getExtension("README"));
        assertEquals("com", FilenameUtil.getExtension("domain.dot.com"));
        assertEquals("jpeg", FilenameUtil.getExtension("image.jpeg"));
        assertEquals("", FilenameUtil.getExtension("a.b/c"));
        assertEquals("txt", FilenameUtil.getExtension("a.b/c.txt"));
        assertEquals("", FilenameUtil.getExtension("a/b/c"));
        assertEquals("", FilenameUtil.getExtension("a.b\\c"));
        assertEquals("txt", FilenameUtil.getExtension("a.b\\c.txt"));
        assertEquals("", FilenameUtil.getExtension("a\\b\\c"));
        assertEquals("", FilenameUtil.getExtension("C:\\temp\\foo.bar\\README"));
        assertEquals("ext", FilenameUtil.getExtension("../filename.ext"));
    }

    @Test
    public void testRemoveExtension() {
        assertEquals(null, FilenameUtil.removeExtension(null));
        assertEquals("file", FilenameUtil.removeExtension("file.ext"));
        assertEquals("README", FilenameUtil.removeExtension("README"));
        assertEquals("domain.dot", FilenameUtil.removeExtension("domain.dot.com"));
        assertEquals("image", FilenameUtil.removeExtension("image.jpeg"));
        assertEquals("a.b/c", FilenameUtil.removeExtension("a.b/c"));
        assertEquals("a.b/c", FilenameUtil.removeExtension("a.b/c.txt"));
        assertEquals("a/b/c", FilenameUtil.removeExtension("a/b/c"));
        assertEquals("a.b\\c", FilenameUtil.removeExtension("a.b\\c"));
        assertEquals("a.b\\c", FilenameUtil.removeExtension("a.b\\c.txt"));
        assertEquals("a\\b\\c", FilenameUtil.removeExtension("a\\b\\c"));
        assertEquals("C:\\temp\\foo.bar\\README",
                FilenameUtil.removeExtension("C:\\temp\\foo.bar\\README"));
        assertEquals("../filename", FilenameUtil.removeExtension("../filename.ext"));
    }

    // -----------------------------------------------------------------------
    @Test
    public void testEquals() {
        assertEquals(true, FilenameUtil.equals(null, null));
        assertEquals(false, FilenameUtil.equals(null, ""));
        assertEquals(false, FilenameUtil.equals("", null));
        assertEquals(true, FilenameUtil.equals("", ""));
        assertEquals(true, FilenameUtil.equals("file.txt", "file.txt"));
        assertEquals(false, FilenameUtil.equals("file.txt", "FILE.TXT"));
        assertEquals(false, FilenameUtil.equals("a\\b\\file.txt", "a/b/file.txt"));
    }

    @Test
    public void testEqualsOnSystem() {
        assertEquals(true, FilenameUtil.equalsOnSystem(null, null));
        assertEquals(false, FilenameUtil.equalsOnSystem(null, ""));
        assertEquals(false, FilenameUtil.equalsOnSystem("", null));
        assertEquals(true, FilenameUtil.equalsOnSystem("", ""));
        assertEquals(true, FilenameUtil.equalsOnSystem("file.txt", "file.txt"));
        assertEquals(WINDOWS, FilenameUtil.equalsOnSystem("file.txt", "FILE.TXT"));
        assertEquals(false, FilenameUtil.equalsOnSystem("a\\b\\file.txt", "a/b/file.txt"));
    }

    // -----------------------------------------------------------------------
    @Test
    public void testEqualsNormalized() {
        assertEquals(true, FilenameUtil.equalsNormalized(null, null));
        assertEquals(false, FilenameUtil.equalsNormalized(null, ""));
        assertEquals(false, FilenameUtil.equalsNormalized("", null));
        assertEquals(true, FilenameUtil.equalsNormalized("", ""));
        assertEquals(true, FilenameUtil.equalsNormalized("file.txt", "file.txt"));
        assertEquals(false, FilenameUtil.equalsNormalized("file.txt", "FILE.TXT"));
        assertEquals(true, FilenameUtil.equalsNormalized("a\\b\\file.txt", "a/b/file.txt"));
        assertEquals(false, FilenameUtil.equalsNormalized("a/b/", "a/b"));
    }

    @Test
    public void testEqualsNormalizedOnSystem() {
        assertEquals(true, FilenameUtil.equalsNormalizedOnSystem(null, null));
        assertEquals(false, FilenameUtil.equalsNormalizedOnSystem(null, ""));
        assertEquals(false, FilenameUtil.equalsNormalizedOnSystem("", null));
        assertEquals(true, FilenameUtil.equalsNormalizedOnSystem("", ""));
        assertEquals(true, FilenameUtil.equalsNormalizedOnSystem("file.txt", "file.txt"));
        assertEquals(WINDOWS, FilenameUtil.equalsNormalizedOnSystem("file.txt", "FILE.TXT"));
        assertEquals(true, FilenameUtil.equalsNormalizedOnSystem("a\\b\\file.txt", "a/b/file.txt"));
        assertEquals(false, FilenameUtil.equalsNormalizedOnSystem("a/b/", "a/b"));
    }

    // -----------------------------------------------------------------------
    @Test
    public void testIsExtension() {
        assertEquals(false, FilenameUtil.isExtension(null, (String) null));
        assertEquals(false, FilenameUtil.isExtension("file.txt", (String) null));
        assertEquals(true, FilenameUtil.isExtension("file", (String) null));
        assertEquals(false, FilenameUtil.isExtension("file.txt", ""));
        assertEquals(true, FilenameUtil.isExtension("file", ""));
        assertEquals(true, FilenameUtil.isExtension("file.txt", "txt"));
        assertEquals(false, FilenameUtil.isExtension("file.txt", "rtf"));

        assertEquals(false, FilenameUtil.isExtension("a/b/file.txt", (String) null));
        assertEquals(false, FilenameUtil.isExtension("a/b/file.txt", ""));
        assertEquals(true, FilenameUtil.isExtension("a/b/file.txt", "txt"));
        assertEquals(false, FilenameUtil.isExtension("a/b/file.txt", "rtf"));

        assertEquals(false, FilenameUtil.isExtension("a.b/file.txt", (String) null));
        assertEquals(false, FilenameUtil.isExtension("a.b/file.txt", ""));
        assertEquals(true, FilenameUtil.isExtension("a.b/file.txt", "txt"));
        assertEquals(false, FilenameUtil.isExtension("a.b/file.txt", "rtf"));

        assertEquals(false, FilenameUtil.isExtension("a\\b\\file.txt", (String) null));
        assertEquals(false, FilenameUtil.isExtension("a\\b\\file.txt", ""));
        assertEquals(true, FilenameUtil.isExtension("a\\b\\file.txt", "txt"));
        assertEquals(false, FilenameUtil.isExtension("a\\b\\file.txt", "rtf"));

        assertEquals(false, FilenameUtil.isExtension("a.b\\file.txt", (String) null));
        assertEquals(false, FilenameUtil.isExtension("a.b\\file.txt", ""));
        assertEquals(true, FilenameUtil.isExtension("a.b\\file.txt", "txt"));
        assertEquals(false, FilenameUtil.isExtension("a.b\\file.txt", "rtf"));

        assertEquals(false, FilenameUtil.isExtension("a.b\\file.txt", "TXT"));
    }

    @Test
    public void testIsExtensionArray() {
        assertEquals(false, FilenameUtil.isExtension(null, (String[]) null));
        assertEquals(false, FilenameUtil.isExtension("file.txt", (String[]) null));
        assertEquals(true, FilenameUtil.isExtension("file", (String[]) null));
        assertEquals(false, FilenameUtil.isExtension("file.txt", new String[0]));
        assertEquals(true, FilenameUtil.isExtension("file.txt", new String[] { "txt" }));
        assertEquals(false, FilenameUtil.isExtension("file.txt", new String[] { "rtf" }));
        assertEquals(true, FilenameUtil.isExtension("file", new String[] { "rtf", "" }));
        assertEquals(true, FilenameUtil.isExtension("file.txt", new String[] { "rtf", "txt" }));

        assertEquals(false, FilenameUtil.isExtension("a/b/file.txt", (String[]) null));
        assertEquals(false, FilenameUtil.isExtension("a/b/file.txt", new String[0]));
        assertEquals(true, FilenameUtil.isExtension("a/b/file.txt", new String[] { "txt" }));
        assertEquals(false, FilenameUtil.isExtension("a/b/file.txt", new String[] { "rtf" }));
        assertEquals(true, FilenameUtil.isExtension("a/b/file.txt", new String[] { "rtf", "txt" }));

        assertEquals(false, FilenameUtil.isExtension("a.b/file.txt", (String[]) null));
        assertEquals(false, FilenameUtil.isExtension("a.b/file.txt", new String[0]));
        assertEquals(true, FilenameUtil.isExtension("a.b/file.txt", new String[] { "txt" }));
        assertEquals(false, FilenameUtil.isExtension("a.b/file.txt", new String[] { "rtf" }));
        assertEquals(true, FilenameUtil.isExtension("a.b/file.txt", new String[] { "rtf", "txt" }));

        assertEquals(false, FilenameUtil.isExtension("a\\b\\file.txt", (String[]) null));
        assertEquals(false, FilenameUtil.isExtension("a\\b\\file.txt", new String[0]));
        assertEquals(true, FilenameUtil.isExtension("a\\b\\file.txt", new String[] { "txt" }));
        assertEquals(false, FilenameUtil.isExtension("a\\b\\file.txt", new String[] { "rtf" }));
        assertEquals(true,
                FilenameUtil.isExtension("a\\b\\file.txt", new String[] { "rtf", "txt" }));

        assertEquals(false, FilenameUtil.isExtension("a.b\\file.txt", (String[]) null));
        assertEquals(false, FilenameUtil.isExtension("a.b\\file.txt", new String[0]));
        assertEquals(true, FilenameUtil.isExtension("a.b\\file.txt", new String[] { "txt" }));
        assertEquals(false, FilenameUtil.isExtension("a.b\\file.txt", new String[] { "rtf" }));
        assertEquals(true,
                FilenameUtil.isExtension("a.b\\file.txt", new String[] { "rtf", "txt" }));

        assertEquals(false, FilenameUtil.isExtension("a.b\\file.txt", new String[] { "TXT" }));
        assertEquals(false,
                FilenameUtil.isExtension("a.b\\file.txt", new String[] { "TXT", "RTF" }));
    }

    @Test
    public void testIsExtensionCollection() {
        assertEquals(false, FilenameUtil.isExtension(null, (Collection<String>) null));
        assertEquals(false, FilenameUtil.isExtension("file.txt", (Collection<String>) null));
        assertEquals(true, FilenameUtil.isExtension("file", (Collection<String>) null));
        assertEquals(false, FilenameUtil.isExtension("file.txt", new ArrayList<String>()));
        assertEquals(true, FilenameUtil.isExtension("file.txt",
                new ArrayList<String>(Arrays.asList(new String[] { "txt" }))));
        assertEquals(false, FilenameUtil.isExtension("file.txt",
                new ArrayList<String>(Arrays.asList(new String[] { "rtf" }))));
        assertEquals(true, FilenameUtil.isExtension("file",
                new ArrayList<String>(Arrays.asList(new String[] { "rtf", "" }))));
        assertEquals(true, FilenameUtil.isExtension("file.txt",
                new ArrayList<String>(Arrays.asList(new String[] { "rtf", "txt" }))));

        assertEquals(false, FilenameUtil.isExtension("a/b/file.txt", (Collection<String>) null));
        assertEquals(false, FilenameUtil.isExtension("a/b/file.txt", new ArrayList<String>()));
        assertEquals(true, FilenameUtil.isExtension("a/b/file.txt",
                new ArrayList<String>(Arrays.asList(new String[] { "txt" }))));
        assertEquals(false, FilenameUtil.isExtension("a/b/file.txt",
                new ArrayList<String>(Arrays.asList(new String[] { "rtf" }))));
        assertEquals(true, FilenameUtil.isExtension("a/b/file.txt",
                new ArrayList<String>(Arrays.asList(new String[] { "rtf", "txt" }))));

        assertEquals(false, FilenameUtil.isExtension("a.b/file.txt", (Collection<String>) null));
        assertEquals(false, FilenameUtil.isExtension("a.b/file.txt", new ArrayList<String>()));
        assertEquals(true, FilenameUtil.isExtension("a.b/file.txt",
                new ArrayList<String>(Arrays.asList(new String[] { "txt" }))));
        assertEquals(false, FilenameUtil.isExtension("a.b/file.txt",
                new ArrayList<String>(Arrays.asList(new String[] { "rtf" }))));
        assertEquals(true, FilenameUtil.isExtension("a.b/file.txt",
                new ArrayList<String>(Arrays.asList(new String[] { "rtf", "txt" }))));

        assertEquals(false, FilenameUtil.isExtension("a\\b\\file.txt", (Collection<String>) null));
        assertEquals(false, FilenameUtil.isExtension("a\\b\\file.txt", new ArrayList<String>()));
        assertEquals(true, FilenameUtil.isExtension("a\\b\\file.txt",
                new ArrayList<String>(Arrays.asList(new String[] { "txt" }))));
        assertEquals(false, FilenameUtil.isExtension("a\\b\\file.txt",
                new ArrayList<String>(Arrays.asList(new String[] { "rtf" }))));
        assertEquals(true, FilenameUtil.isExtension("a\\b\\file.txt",
                new ArrayList<String>(Arrays.asList(new String[] { "rtf", "txt" }))));

        assertEquals(false, FilenameUtil.isExtension("a.b\\file.txt", (Collection<String>) null));
        assertEquals(false, FilenameUtil.isExtension("a.b\\file.txt", new ArrayList<String>()));
        assertEquals(true, FilenameUtil.isExtension("a.b\\file.txt",
                new ArrayList<String>(Arrays.asList(new String[] { "txt" }))));
        assertEquals(false, FilenameUtil.isExtension("a.b\\file.txt",
                new ArrayList<String>(Arrays.asList(new String[] { "rtf" }))));
        assertEquals(true, FilenameUtil.isExtension("a.b\\file.txt",
                new ArrayList<String>(Arrays.asList(new String[] { "rtf", "txt" }))));

        assertEquals(false, FilenameUtil.isExtension("a.b\\file.txt",
                new ArrayList<String>(Arrays.asList(new String[] { "TXT" }))));
        assertEquals(false, FilenameUtil.isExtension("a.b\\file.txt",
                new ArrayList<String>(Arrays.asList(new String[] { "TXT", "RTF" }))));
    }

}
