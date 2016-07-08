package codebase.streams;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Runs all tests of the stream package.
 */
public class AllTests {

    public static Test suite() {
        TestSuite suite = new TestSuite(AllTests.class.getName());
        //$JUnit-BEGIN$
        suite.addTestSuite(TestConstantInputStream.class);
        suite.addTestSuite(TestDelayedOutputStream.class);
        suite.addTestSuite(TestTimeoutInputStream.class);
        suite.addTestSuite(TestTimeoutOutputStream.class);
        //$JUnit-END$
        return suite;
    }
}
