package codebase.io.converters.display;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Runs all tests of the iterators package.
 */
public class AllTests {

    public static Test suite() {
        TestSuite suite = new TestSuite(AllTests.class.getName());
        //$JUnit-BEGIN$
        suite.addTestSuite(TestIntegerDisplayConverter.class);
        suite.addTestSuite(TestStringLitteralConverter.class);
        //$JUnit-END$
        return suite;
    }
}
