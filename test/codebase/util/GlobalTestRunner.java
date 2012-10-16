package codebase.util;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Runs all the tests.
 * <p>
 * This class can be run either as JUnit test case or as a stand alone Java file.
 * <p>
 * Each package also contains a file named <tt>AllTests.java</tt> which we can run to
 * deploy all the tests of a particular package. During development, we recommend the
 * tests of each package to be continually strengthened, documented and run (several dozen
 * times a day).
 * <p>
 * Each time a new test case is added to a new package, the corresponding
 * <tt>AllTests.java</tt> file needs to be adjusted.
 */
public class GlobalTestRunner {

    public static Test suite() {
        TestSuite suite = new TestSuite(GlobalTestRunner.class.getName());
        //$JUnit-BEGIN$
        suite.addTest(codebase.util.AllTests.suite());

        // Individual package tests
        suite.addTest(codebase.util.binary.AllTests.suite());
        suite.addTest(codebase.util.iterators.AllTests.suite());
        suite.addTest(codebase.util.junit.AllTests.suite());
        suite.addTest(codebase.util.streams.AllTests.suite());
        
        //$JUnit-END$
        return suite;
    }

    /**
     * Kick of the tests.
     * 
     * @param args ignored.
     */
    public static void main(String[] args) {
        /*
         * Note that there no effective way of communicating with the test cases other 
         * than using the Java system properties. If we need to pass parameters to some
         * of our test cases that's how we should go about it.
         */
        junit.textui.TestRunner.main(new String[] { GlobalTestRunner.class.getName() });
    }
}
