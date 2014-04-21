package codebase.nodestore;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Runs all tests of the iterators package.
 */
public class AllTests {

    public static Test suite() {
        TestSuite suite = new TestSuite(AllTests.class.getName());
        //$JUnit-BEGIN$
        suite.addTestSuite(TestNodeDisplayConverter.class);
        suite.addTestSuite(TestNodeDisplayConverterIntegerated.class);
        //$JUnit-END$
        return suite;
    }
}
