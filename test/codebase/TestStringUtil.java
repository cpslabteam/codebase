package codebase;

import codebase.iterators.ArrayIterator;
import codebase.iterators.EmptyIterator;
import codebase.junit.EnhancedTestCase;

/**
 * Tests the {@link StringUtil} utility class.
 */
public class TestStringUtil extends
        EnhancedTestCase {

    public void testCharNameShort() {
        assertEquals("TAB", StringUtil.charNameShort('\t'));
        assertEquals("CR", StringUtil.charNameShort('\r'));
        assertEquals(" ", StringUtil.charNameShort(' '));
        assertEquals("c", StringUtil.charNameShort('c'));
    }

    public void testCharNameLong() {
        assertEquals("Tab", StringUtil.charNameLong('\t'));
        assertEquals("Carriage Return", StringUtil.charNameLong('\r'));
        assertEquals(" ", StringUtil.charNameLong(' '));
        assertEquals("c", StringUtil.charNameLong('c'));
    }

    public void testFirstIndexNotOf() {
        assertEquals(StringUtil.firstIndexNotOf("", '\0'), -1);
        assertEquals(StringUtil.firstIndexNotOf("ABCD", 'X'), 0);
        assertEquals(StringUtil.firstIndexNotOf("ABCDX", 'X'), 0);
        assertEquals(StringUtil.firstIndexNotOf("XBCDX", 'X'), 1);
        assertEquals(StringUtil.firstIndexNotOf("XXXDX", 'X'), 3);
        assertEquals(StringUtil.firstIndexNotOf("XXXXX", 'X'), -1);
    }

    public void testLastIndexNotOf() {
        assertEquals(StringUtil.lastIndexNotOf("", '\0'), -1);
        assertEquals(StringUtil.lastIndexNotOf("ABCD", 'X'), 3);
        assertEquals(StringUtil.lastIndexNotOf("ABCDX", 'X'), 3);
        assertEquals(StringUtil.lastIndexNotOf("XBCDX", 'X'), 3);
        assertEquals(StringUtil.lastIndexNotOf("XBXXX", 'X'), 1);
        assertEquals(StringUtil.lastIndexNotOf("XXXXX", 'X'), -1);
    }

    public void testTrimLeft() {
        assertEquals("", StringUtil.trimLeft(""));
        assertEquals("something", StringUtil.trimLeft("something"));
        assertEquals("<    >something", StringUtil.trimLeft("<    >something"));
        assertEquals("something", StringUtil.trimLeft("         something"));
        assertEquals("something     ", StringUtil.trimLeft("something     "));
    }

    public void testLTrimChar() {
        assertEquals("", StringUtil.trimCharLeft("", 's'));
        assertEquals("omething", StringUtil.trimCharLeft("something", 's'));
        assertEquals("something", StringUtil.trimCharLeft("something", 't'));
        assertEquals("omething", StringUtil.trimCharLeft("ssssomething", 's'));
    }

    public void testTrimRight() {
        assertEquals("", StringUtil.trimRight(""));
        assertEquals("something", StringUtil.trimRight("something"));
        assertEquals("something<    >", StringUtil.trimRight("something<    >"));
        assertEquals("something", StringUtil.trimRight("something         "));
        assertEquals("something         x", StringUtil.trimRight("something         x"));
        assertEquals("     something", StringUtil.trimRight("     something     "));
    }

    public void testTrimCharRight() {
        assertEquals("", StringUtil.trimCharRight("", 's'));
        assertEquals("somethin", StringUtil.trimCharRight("something", 'g'));
        assertEquals("something", StringUtil.trimCharRight("something", 't'));
        assertEquals("somethin", StringUtil.trimCharRight("somethingggg", 'g'));
    }

    public void testRepeatString() {
        assertEquals("", StringUtil.repeat("", 0));
        assertEquals("", StringUtil.repeat("a", 0));
        assertEquals("a", StringUtil.repeat("a", 1));
        assertEquals("aa", StringUtil.repeat("a", 2));
    }

    public void testRepeatChar() {
        assertEquals("", StringUtil.repeat('\u0000', 0));
        assertEquals("", StringUtil.repeat('a', 0));
        assertEquals("a", StringUtil.repeat('a', 1));
        assertEquals("aa", StringUtil.repeat('a', 2));
        assertEquals("aaaaaaa", StringUtil.repeat('a', 7));
    }

    public void testStringify() {
        assertEquals("\"\"", StringUtil.stringify(""));
        assertEquals("\"X\"", StringUtil.stringify("X"));
        assertEquals("\"\\\"\"", StringUtil.stringify("\""));
        assertEquals("\"something\"", StringUtil.stringify("something"));
    }

    public void testUnstringify() {
        // Empty string
        assertEquals("", StringUtil.unstringify(""));
        // Single "
        assertEquals("", StringUtil.unstringify("\""));
        // Balanced "" but void 
        assertEquals("", StringUtil.unstringify("\"\""));
        // Balanced but extra " is ignored
        assertEquals("", StringUtil.unstringify("\"\"\""));
        
        // String contains literally \"
        assertEquals("\"", StringUtil.unstringify("\\\""));
        // String contains literally "\"" (extra ")
        assertEquals("\"", StringUtil.unstringify("\"\\\"\""));
                
        // String contains literally "X", should be stripped (single character case)
        assertEquals("X", StringUtil.unstringify("\"X\""));
        // String contains literally "something", should be stripped (multiple character case)
        assertEquals("something", StringUtil.unstringify("\"something\""));
        
        // "\" (lost \ causing " unbalance)
        assertEquals("\"", StringUtil.unstringify("\"\\\""));
        
        // "\\" should be interpreted correctly to \ 
        assertEquals("\\", StringUtil.unstringify("\"\\\\\""));
    }

    public void testStripPrefix() {
        assertEquals("", StringUtil.stripPrefix("", ""));
        assertEquals("something", StringUtil.stripPrefix("something", ""));
        assertEquals("", StringUtil.stripPrefix("something", "something"));
        assertEquals("something", StringUtil.stripPrefix("theres'something", "theres'"));
        assertEquals("", StringUtil.stripPrefix("", "something"));
    }

    public void testTrim() {
        assertEquals("", StringUtil.trim(""));
        assertEquals("something", StringUtil.trim("something"));
        assertEquals("something", StringUtil.trim("   something"));
        assertEquals("something", StringUtil.trim("something   "));
        assertEquals("something", StringUtil.trim("   something   "));
    }

    public void testTrimChar() {
        assertEquals("", StringUtil.trimChar("", 's'));
        assertEquals("omething", StringUtil.trimChar("something", 's'));
        assertEquals("omething", StringUtil.trimChar("sssssomething", 's'));
        assertEquals("somethin", StringUtil.trimChar("something", 'g'));
        assertEquals("somethin", StringUtil.trimChar("somethingggggg", 'g'));
        assertEquals("somethin", StringUtil.trimChar("gggggggsomethingggggg", 'g'));
    }



    // TODO: Finish compact string tests

    public void xxxCompactString() {
        assertEquals("", StringUtil.compactFormat("", 1));
        assertEquals("xxx", StringUtil.compactFormat("xxx", 5));
        assertEquals("yxxz", StringUtil.compactFormat("yxxz", 5));
        assertEquals("y...z", StringUtil.compactFormat("yxxxxz", 5));
        assertEquals("D...s", StringUtil.compactFormat("Dear Friends", 5));
        assertEquals("Dear...nds", StringUtil.compactFormat("Dear Friends", 10));
    }

    public void testSplit() {
        assertEquals(new ArrayIterator<String>(StringUtil.split("", "")), new EmptyIterator<String>());
        assertEquals(new ArrayIterator<String>(StringUtil.split("", "\t")),
                new EmptyIterator<String>());
        assertEquals(new ArrayIterator<String>(StringUtil.split("a", "a")),
                new EmptyIterator<String>());
        assertEquals(new ArrayIterator<String>(StringUtil.split("a", " ")), new ArrayIterator<String>(
                new String[] { "a" }));
        assertEquals(new ArrayIterator<String>(StringUtil.split("abc", "a")),
                new ArrayIterator<String>(new String[] { "bc" }));
        assertEquals(new ArrayIterator<String>(StringUtil.split("abc", "c")),
                new ArrayIterator<String>(new String[] { "ab" }));
        assertEquals(new ArrayIterator<String>(StringUtil.split("a b c", " ")),
                new ArrayIterator<String>(new String[] { "a", "b", "c" }));
    }
}
