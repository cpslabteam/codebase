package codebase.junit;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Runs all tests of the junit package.
 */
public class AllTests {

    public static Test suite() {
        TestSuite suite = new TestSuite(AllTests.class.getName());
        //$JUnit-BEGIN$
        suite.addTestSuite(TestComparableArrayWrapper.class);
        suite.addTestSuite(TestEnhancedTestCaseAssertBagEqualsArray.class);
        //$JUnit-END$
        return suite;
    }

}
