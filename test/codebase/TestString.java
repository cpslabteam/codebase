package codebase;

import codebase.Strings;
import codebase.iterators.ArrayIterator;
import codebase.iterators.EmptyIterator;
import codebase.junit.EnhancedTestCase;
import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * @author Paulo Carreira TODO To change the\ template for this generated
 *         typeNumber comment go to Window - Preferences - Java - Code Style -
 *         Code Templates
 */
public class TestString
        extends EnhancedTestCase {

    public void testCharNameShort() {
        assertEquals(Strings.charNameShort('\t'), "TAB");
        assertEquals(Strings.charNameShort('\r'), "CR");
        assertEquals(Strings.charNameShort(' '), " ");
        assertEquals(Strings.charNameShort('c'), "c");
    }
    
    public void testCharNameLong() {
        assertEquals(Strings.charNameLong('\t'), "Tab");
        assertEquals(Strings.charNameLong('\r'), "Carriage Return");
        assertEquals(Strings.charNameLong(' '), " ");
        assertEquals(Strings.charNameLong('c'), "c");
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
        assertEquals(Strings.trimLeft(""), "");
        assertEquals(Strings.trimLeft("something"), "something");
        assertEquals(Strings.trimLeft("<    >something"), "<    >something");
        assertEquals(Strings.trimLeft("         something"), "something");
        assertEquals(Strings.trimLeft("something     "), "something     ");
    }

    public void testLTrimChar() {
        assertEquals(Strings.trimCharLeft("", 's'), "");
        assertEquals(Strings.trimCharLeft("something", 's'), "omething");
        assertEquals(Strings.trimCharLeft("something", 't'), "something");
        assertEquals(Strings.trimCharLeft("ssssomething", 's'), "omething");
    }

    public void testTrimRight() {
        assertEquals(Strings.trimRight(""), "");
        assertEquals(Strings.trimRight("something"), "something");
        assertEquals(Strings.trimRight("something<    >"), "something<    >");
        assertEquals(Strings.trimRight("something         "), "something");
        assertEquals(Strings.trimRight("something         x"),
            "something         x");
        assertEquals(Strings.trimRight("     something     "),
            "     something");
    }

    public void testTrimCharRight() {
        assertEquals(Strings.trimCharRight("", 's'), "");
        assertEquals(Strings.trimCharRight("something", 'g'), "somethin");
        assertEquals(Strings.trimCharRight("something", 't'), "something");
        assertEquals(Strings.trimCharRight("somethingggg", 'g'), "somethin");
    }

    public void testRepeatString() {
        assertEquals(Strings.repeat("", 0), "");
        assertEquals(Strings.repeat("a", 0), "");
        assertEquals(Strings.repeat("a", 1), "a");
        assertEquals(Strings.repeat("a", 2), "aa");
    }
    
    public void testRepeatChar() {
        assertEquals(Strings.repeat('\u0000', 0), "");
        assertEquals(Strings.repeat('a', 0), "");
        assertEquals(Strings.repeat('a', 1), "a");
        assertEquals(Strings.repeat('a', 2), "aa");
        assertEquals(Strings.repeat('a', 7), "aaaaaaa");
    }

    public void testStringify() {
        assertEquals(Strings.stringify(""), "\"\"");
        assertEquals(Strings.stringify("something"), "\"something\"");
    }

    public void testStripPrefix() {
        assertEquals(Strings.stripPrefix("", ""), "");
        Assert
            .assertEquals(Strings.stripPrefix("something", ""), "something");
        Assert
            .assertEquals(Strings.stripPrefix("something", "something"), "");
        assertEquals(Strings.stripPrefix("theres'something", "theres'"),
            "something");
        assertEquals(Strings.stripPrefix("", "something"), "");
    }

    public void testTrim() {
        assertEquals(Strings.trim(""), "");
        assertEquals(Strings.trim("something"), "something");
        assertEquals(Strings.trim("   something"), "something");
        assertEquals(Strings.trim("something   "), "something");
        assertEquals(Strings.trim("   something   "), "something");
    }

    public void testTrimChar() {
        assertEquals(Strings.trimChar("", 's'), "");
        assertEquals(Strings.trimChar("something", 's'), "omething");
        assertEquals(Strings.trimChar("sssssomething", 's'), "omething");
        assertEquals(Strings.trimChar("something", 'g'), "somethin");
        assertEquals(Strings.trimChar("somethingggggg", 'g'), "somethin");
        assertEquals(Strings.trimChar("gggggggsomethingggggg", 'g'),
            "somethin");
    }

    /**
     * TODO: Finish compact string tests
     *
     */
    public void xxxCompactString() {
        assertEquals(Strings.compactFormat("", 1), "");
        assertEquals(Strings.compactFormat("xxx", 5), "xxx");
        assertEquals(Strings.compactFormat("yxxz", 5), "yxxz");
        assertEquals(Strings.compactFormat("yxxxxz", 5), "y...z");
        Assert
            .assertEquals(Strings.compactFormat("Dear Friends", 5), "D...s");
        assertEquals(Strings.compactFormat("Dear Friends", 10), "Dear...nds");
    }

    public void testSplit() {
        assertEquals(new ArrayIterator(Strings.split("", "")),
            new EmptyIterator());
        assertEquals(new ArrayIterator(Strings.split("", "\t")),
            new EmptyIterator());
        assertEquals(new ArrayIterator(Strings.split("a", "a")),
            new EmptyIterator());
        assertEquals(new ArrayIterator(Strings.split("a", " ")),
            new ArrayIterator(new String[] {"a"}));
        assertEquals(new ArrayIterator(Strings.split("abc", "a")),
            new ArrayIterator(new String[] {"bc"}));
        assertEquals(new ArrayIterator(Strings.split("abc", "c")),
            new ArrayIterator(new String[] {"ab"}));
        assertEquals(new ArrayIterator(Strings.split("a b c", " ")),
            new ArrayIterator(new String[] {
                    "a", "b", "c"}));
    }
}
