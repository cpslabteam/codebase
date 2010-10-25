package codebase.timing;

import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author mog
 * @version $Id: TestTime.java,v 1.1 2005/08/02 13:40:32 datafusion Exp $
 */
public class TestTime
        extends TestCase {
    
    
    public void testParseSeconds() {
        assertEquals(1000, TimeFormat.parseTime("1s"));
        assertEquals(1000, TimeFormat.parseTime("1S"));
    }
    
    public void testParseMillis() {
        assertEquals(1, TimeFormat.parseTime("1ms"));
    }
    
    public void testParse() {
        assertEquals(1, TimeFormat.parseTime("1"));
    }
    
    public void testParseMinutes() {
        assertEquals(60000, TimeFormat.parseTime("1m"));
    }
}
