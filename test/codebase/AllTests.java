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
        suite.addTestSuite(TestArrayUtil.class);
        suite.addTestSuite(TestAesUtil.class);
        suite.addTestSuite(TestBase64.class);
        suite.addTestSuite(TestBinaryUtil.class);
        suite.addTestSuite(TestFileUtil.class);
        suite.addTestSuite(TestFilenameUtil.class);
        suite.addTestSuite(TestInformationUnit.class);
        suite.addTestSuite(TestMathUtil.class);
        suite.addTestSuite(TestMD5.class);
        suite.addTestSuite(TestStringUtil.class);
        //$JUnit-END$
        return suite;
    }

}
