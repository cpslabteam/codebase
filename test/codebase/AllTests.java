package codebase;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Runs all tests of the main codebase package.
 */
public class AllTests {

    public static Test suite() {
        TestSuite suite = new TestSuite(AllTests.class.getName());
        //$JUnit-BEGIN$
        suite.addTestSuite(TestArrays.class);
        suite.addTestSuite(TestAesUtil.class);
        suite.addTestSuite(TestBase64.class);
        suite.addTestSuite(TestBinary.class);
        suite.addTestSuite(TestFiles.class);
        suite.addTestSuite(TestFilenames.class);
        suite.addTestSuite(TestInformationUnit.class);
        suite.addTestSuite(TestMath.class);
        suite.addTestSuite(TestMD5.class);
        suite.addTestSuite(TestStrings.class);
        //$JUnit-END$
        return suite;
    }

}
