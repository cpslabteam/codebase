package codebase.util;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

    public static Test suite() {
        TestSuite suite = new TestSuite(AllTests.class.getName());
        //$JUnit-BEGIN$
        suite.addTestSuite(TestConversions.class);
        suite.addTestSuite(TestString.class);
        suite.addTestSuite(TestMath.class);
        suite.addTestSuite(TestMD5.class);
        suite.addTestSuite(TestFilenames.class);
        suite.addTestSuite(TestComparableArrayWrapper.class);
        suite.addTestSuite(TestArrays.class);
        suite.addTestSuite(TestFiles.class);
        //$JUnit-END$
        return suite;
    }

}
