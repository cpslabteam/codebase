package codebase.io.converters.binary;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

    public static Test suite() {
        TestSuite suite = new TestSuite(AllTests.class.getName());
        //$JUnit-BEGIN$
        suite.addTestSuite(TestBooleanConverter.class);
        suite.addTestSuite(TestByteConverter.class);
        suite.addTestSuite(TestByteSkipConverter.class);
        suite.addTestSuite(TestCharacterConverter.class);
        suite.addTestSuite(TestDoubleConverter.class);
        suite.addTestSuite(TestFloatConverter.class);
        suite.addTestSuite(TestIntegerConverter.class);
        suite.addTestSuite(TestLongConverter.class);
        suite.addTestSuite(TestUTFStringConverter.class);
        //$JUnit-END$
        return suite;
    }

}
