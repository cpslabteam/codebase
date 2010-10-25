/*
 * Created on 26/Mar/2005
 */
package codebase.tests;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

/**
 * Tests the {@link codebase.tests.EnhancedTestCase} class. Tests the method
 * AssertBagEquals() of the foundation test case extensions class
 */
public class TestEnhancedTestCaseAssertBagEqualsArray
        extends TestCase {

    EnhancedTestCase enhancedTestCase;

    public void setUp() {
        enhancedTestCase = new EnhancedTestCase();
    }

    /**
     * Tests that that {@link EnhancedTestCase#assertEquals(char, char)}
     * behaves correctly.
     */
    public void testAssertEqualsChar() {
        enhancedTestCase.assertEquals((char) 0, (char) 0);
        enhancedTestCase.assertEquals('A', 'A');
    }

    /**
     * Tests that that {@link EnhancedTestCase#assertEquals(char, char)}
     * identifies correctly different chars.
     */
    public void testAssertNotEqualsChar() {
        try {
            enhancedTestCase.assertEquals('A', 'B');
        } catch (AssertionFailedError e) {
            return;
        }
        fail("The char cannot be equal");
    }

    public void testAssertEqualsIntArray() {
        enhancedTestCase.assertEquals(new int[] {}, new int[] {});
    }

    public void testAssertEqualsSameIntArray() {
        enhancedTestCase.assertEquals(new int[] {
                1, 2, 3}, new int[] {
                1, 2, 3});
    }

    public void testAssertNotEqualsIntArray() {
        try {
            enhancedTestCase.assertEquals(new int[] {
                    1, 2}, new int[] {
                    1, 3, 4});
        } catch (AssertionFailedError e) {
            return;
        }
        fail("The Bags can not be equal");
    }

    public void testAssertBagEquals() {
        enhancedTestCase.assertBagEquals(new Object[] {}, new Object[] {});
    }

    public void testAssertBagEqualsVoid() {
        enhancedTestCase.assertBagEquals(new Object[] {new Integer(1)},
            new Object[] {new Integer(1)});
    }

    public void testAssertBagEqualsSameList() {
        enhancedTestCase.assertBagEquals(new Object[] {
                new Integer(1), new Integer(2), new Integer(3)},
            new Object[] {
                    new Integer(1), new Integer(2), new Integer(3)});
    }

    public void testAssertBagEqualsDouble() {
        enhancedTestCase.assertBagEquals(new Object[] {
                new Integer(1), new Integer(1)}, new Object[] {
                new Integer(1), new Integer(1)});
    }

    public void testAssertBagEqualsDoubleSwitched() {
        enhancedTestCase.assertBagEquals(new Object[] {
                new Integer(1), new Integer(2), new Integer(3),
                new Integer(1)}, new Object[] {
                new Integer(1), new Integer(2), new Integer(1),
                new Integer(3)});
    }

    public void testAssertBagEqualsDoubleSwitchedMultiple() {
        enhancedTestCase.assertBagEquals(new Object[] {
                new Integer(1), new Integer(2), new Integer(3),
                new Integer(1), new Integer(4), new Integer(5),
                new Integer(2)}, new Object[] {
                new Integer(1), new Integer(2), new Integer(1),
                new Integer(3), new Integer(4), new Integer(2),
                new Integer(5)});
    }

    /**
     * A regression test introduced because of a failure to check arrays
     * correcly.
     */
    public final void testAssertBagEqualsArrayElements() {
        final Object[] strArrays = new Object[] {
                new Object[] {
                        new String("a5"), new String("b5")}, new Object[] {
                        new String("a6"), new String("b6")}};

        // Check equal to itself
        enhancedTestCase.assertBagEquals(strArrays, strArrays);

        // Check that it is equal of the an array with the inverted elements
        enhancedTestCase.assertBagEquals(strArrays, new Object[] {
                new Object[] {
                        new String("a6"), new String("b6")}, new Object[] {
                        new String("a5"), new String("b5")}});
    }

    /**
     * Compares an empty bag with a bag with one element
     */
    public void testAssertBagNotEqualsVoidVsOne() {
        try {
            enhancedTestCase.assertBagEquals(new Object[] {new Integer(1)},
                new Object[] {});
        } catch (AssertionFailedError e) {
            return;
        }
        fail("The Bags can not be equal");
    }

    public void testAssertBagNotEqualsMultipleVsSingle() {
        try {
            enhancedTestCase.assertBagEquals(new Object[] {
                    new Integer(1), new Integer(1)},
                new Object[] {new Integer(1)});
        } catch (AssertionFailedError e) {
            return;
        }
        fail("The Bags can not be equal");
    }

    public void testAssertBagNotEqualsSingleVsMultiple() {
        try {
            enhancedTestCase.assertBagEquals(new Object[] {new Integer(1)},
                new Object[] {
                        new Integer(1), new Integer(1)});
        } catch (AssertionFailedError e) {
            return;
        }
        fail("The Bags can not be equal");
    }

    public void testAssertBagNotEqualsDifferent() {
        try {
            enhancedTestCase.assertBagEquals(new Object[] {
                    new Integer(1), new Integer(2)}, new Object[] {
                    new Integer(2), new Integer(3)});
        } catch (AssertionFailedError e) {
            return;
        }
        fail("The Bags can not be equal");
    }

    public void testAssertThrows() {
        // OK. If an exception is thrown.
        enhancedTestCase.assertThrows(new CodeBlock() {
            public void execute() {
                throw new IllegalArgumentException();
            }
        }, new IllegalArgumentException());

        // Check that it complains if no exception is thrown
        try {
            enhancedTestCase.assertThrows(new CodeBlock() {
                public void execute() {
                    return;
                }
            }, new IllegalArgumentException());
        } catch (AssertionFailedError e) {
            return;
        }
        fail("AssertThrows must fail if noe exception is thrown");
    }
}