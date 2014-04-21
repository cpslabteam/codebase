package codebase;

import codebase.iterators.ArrayIterator;
import codebase.iterators.EmptyIterator;
import codebase.junit.EnhancedTestCase;

/**
 * Tests the {@link Strings} utility class.
 */
public class TestStrings extends
        EnhancedTestCase {

    public void testCharNameShort() {
        assertEquals("TAB", Strings.charNameShort('\t'));
        assertEquals("CR", Strings.charNameShort('\r'));
        assertEquals(" ", Strings.charNameShort(' '));
        assertEquals("c", Strings.charNameShort('c'));
    }

    public void testCharNameLong() {
        assertEquals("Tab", Strings.charNameLong('\t'));
        assertEquals("Carriage Return", Strings.charNameLong('\r'));
        assertEquals(" ", Strings.charNameLong(' '));
        assertEquals("c", Strings.charNameLong('c'));
    }

    public void testFirstIndexNotOf() {
        assertEquals(Strings.firstIndexNotOf("", '\0'), -1);
        assertEquals(Strings.firstIndexNotOf("ABCD", 'X'), 0);
        assertEquals(Strings.firstIndexNotOf("ABCDX", 'X'), 0);
        assertEquals(Strings.firstIndexNotOf("XBCDX", 'X'), 1);
        assertEquals(Strings.firstIndexNotOf("XXXDX", 'X'), 3);
        assertEquals(Strings.firstIndexNotOf("XXXXX", 'X'), -1);
    }

    public void testLastIndexNotOf() {
        assertEquals(Strings.lastIndexNotOf("", '\0'), -1);
        assertEquals(Strings.lastIndexNotOf("ABCD", 'X'), 3);
        assertEquals(Strings.lastIndexNotOf("ABCDX", 'X'), 3);
        assertEquals(Strings.lastIndexNotOf("XBCDX", 'X'), 3);
        assertEquals(Strings.lastIndexNotOf("XBXXX", 'X'), 1);
        assertEquals(Strings.lastIndexNotOf("XXXXX", 'X'), -1);
    }

    public void testTrimLeft() {
        assertEquals("", Strings.trimLeft(""));
        assertEquals("something", Strings.trimLeft("something"));
        assertEquals("<    >something", Strings.trimLeft("<    >something"));
        assertEquals("something", Strings.trimLeft("         something"));
        assertEquals("something     ", Strings.trimLeft("something     "));
    }

    public void testLTrimChar() {
        assertEquals("", Strings.trimCharLeft("", 's'));
        assertEquals("omething", Strings.trimCharLeft("something", 's'));
        assertEquals("something", Strings.trimCharLeft("something", 't'));
        assertEquals("omething", Strings.trimCharLeft("ssssomething", 's'));
    }

    public void testTrimRight() {
        assertEquals("", Strings.trimRight(""));
        assertEquals("something", Strings.trimRight("something"));
        assertEquals("something<    >", Strings.trimRight("something<    >"));
        assertEquals("something", Strings.trimRight("something         "));
        assertEquals("something         x", Strings.trimRight("something         x"));
        assertEquals("     something", Strings.trimRight("     something     "));
    }

    public void testTrimCharRight() {
        assertEquals("", Strings.trimCharRight("", 's'));
        assertEquals("somethin", Strings.trimCharRight("something", 'g'));
        assertEquals("something", Strings.trimCharRight("something", 't'));
        assertEquals("somethin", Strings.trimCharRight("somethingggg", 'g'));
    }

    public void testRepeatString() {
        assertEquals("", Strings.repeat("", 0));
        assertEquals("", Strings.repeat("a", 0));
        assertEquals("a", Strings.repeat("a", 1));
        assertEquals("aa", Strings.repeat("a", 2));
    }

    public void testRepeatChar() {
        assertEquals("", Strings.repeat('\u0000', 0));
        assertEquals("", Strings.repeat('a', 0));
        assertEquals("a", Strings.repeat('a', 1));
        assertEquals("aa", Strings.repeat('a', 2));
        assertEquals("aaaaaaa", Strings.repeat('a', 7));
    }

    public void testStringify() {
        assertEquals("\"\"", Strings.stringify(""));
        assertEquals("\"X\"", Strings.stringify("X"));
        assertEquals("\"\\\"\"", Strings.stringify("\""));
        assertEquals("\"something\"", Strings.stringify("something"));
    }

    public void testUnstringify() {
        // Empty string
        assertEquals("", Strings.unstringify(""));
        // Single "
        assertEquals("", Strings.unstringify("\""));
        // Balanced "" but void 
        assertEquals("", Strings.unstringify("\"\""));
        // Balanced but extra " is ignored
        assertEquals("", Strings.unstringify("\"\"\""));
        
        // String contains literally \"
        assertEquals("\"", Strings.unstringify("\\\""));
        // String contains literally "\"" (extra ")
        assertEquals("\"", Strings.unstringify("\"\\\"\""));
                
        // String contains literally "X", should be stripped (single character case)
        assertEquals("X", Strings.unstringify("\"X\""));
        // String contains literally "something", should be stripped (multiple character case)
        assertEquals("something", Strings.unstringify("\"something\""));
        
        // "\" (lost \ causing " unbalance)
        assertEquals("\"", Strings.unstringify("\"\\\""));
        
        // "\\" should be interpreted correctly to \ 
        assertEquals("\\", Strings.unstringify("\"\\\\\""));
    }

    public void testStripPrefix() {
        assertEquals("", Strings.stripPrefix("", ""));
        assertEquals("something", Strings.stripPrefix("something", ""));
        assertEquals("", Strings.stripPrefix("something", "something"));
        assertEquals("something", Strings.stripPrefix("theres'something", "theres'"));
        assertEquals("", Strings.stripPrefix("", "something"));
    }

    public void testTrim() {
        assertEquals("", Strings.trim(""));
        assertEquals("something", Strings.trim("something"));
        assertEquals("something", Strings.trim("   something"));
        assertEquals("something", Strings.trim("something   "));
        assertEquals("something", Strings.trim("   something   "));
    }

    public void testTrimChar() {
        assertEquals("", Strings.trimChar("", 's'));
        assertEquals("omething", Strings.trimChar("something", 's'));
        assertEquals("omething", Strings.trimChar("sssssomething", 's'));
        assertEquals("somethin", Strings.trimChar("something", 'g'));
        assertEquals("somethin", Strings.trimChar("somethingggggg", 'g'));
        assertEquals("somethin", Strings.trimChar("gggggggsomethingggggg", 'g'));
    }



    // TODO: Finish compact string tests

    public void xxxCompactString() {
        assertEquals("", Strings.compactFormat("", 1));
        assertEquals("xxx", Strings.compactFormat("xxx", 5));
        assertEquals("yxxz", Strings.compactFormat("yxxz", 5));
        assertEquals("y...z", Strings.compactFormat("yxxxxz", 5));
        assertEquals("D...s", Strings.compactFormat("Dear Friends", 5));
        assertEquals("Dear...nds", Strings.compactFormat("Dear Friends", 10));
    }

    public void testSplit() {
        assertEquals(new ArrayIterator<String>(Strings.split("", "")), new EmptyIterator<String>());
        assertEquals(new ArrayIterator<String>(Strings.split("", "\t")),
                new EmptyIterator<String>());
        assertEquals(new ArrayIterator<String>(Strings.split("a", "a")),
                new EmptyIterator<String>());
        assertEquals(new ArrayIterator<String>(Strings.split("a", " ")), new ArrayIterator<String>(
                new String[] { "a" }));
        assertEquals(new ArrayIterator<String>(Strings.split("abc", "a")),
                new ArrayIterator<String>(new String[] { "bc" }));
        assertEquals(new ArrayIterator<String>(Strings.split("abc", "c")),
                new ArrayIterator<String>(new String[] { "ab" }));
        assertEquals(new ArrayIterator<String>(Strings.split("a b c", " ")),
                new ArrayIterator<String>(new String[] { "a", "b", "c" }));
    }
}
