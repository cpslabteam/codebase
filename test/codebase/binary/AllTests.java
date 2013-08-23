package codebase.binary;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Runs all tests of the binary package.
 */
public class AllTests {

    public static Test suite() {
        TestSuite suite = new TestSuite(AllTests.class.getName());
        //$JUnit-BEGIN$
        suite.addTestSuite(TestBitVector.class);
        suite.addTestSuite(TestBinary.class);
        //$JUnit-END$
        return suite;
    }

}
