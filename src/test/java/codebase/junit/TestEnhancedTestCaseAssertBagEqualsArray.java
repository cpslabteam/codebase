/*
 * Created on 26/Mar/2005
 */
package codebase.junit;

import org.junit.Test;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

/**
 * Tests the {@link codebase.junit.EnhancedTestCase} class. Tests the method
 * AssertBagEquals() of the foundation test case extensions class
 */
public class TestEnhancedTestCaseAssertBagEqualsArray extends TestCase {

    public void setUp() {
    }

    /**
     * Tests that that {@link EnhancedTestCase#assertEquals(char, char)} behaves
     * correctly.
     */
    @Test
    public void testAssertEqualsChar() {
        EnhancedTestCase.assertEquals((char) 0, (char) 0);
        EnhancedTestCase.assertEquals('A', 'A');
    }

    /**
     * Tests that that {@link EnhancedTestCase#assertEquals(char, char)} identifies
     * correctly different chars.
     */
    @Test
    public void testAssertNotEqualsChar() {
        try {
            EnhancedTestCase.assertEquals('A', 'B');
        } catch (AssertionFailedError e) {
            return;
        }
        fail("The char cannot be equal");
    }

    @Test
    public void testAssertEqualsIntArray() {
        EnhancedTestCase.assertEquals(new int[] {}, new int[] {});
    }

    @Test
    public void testAssertEqualsSameIntArray() {
        EnhancedTestCase.assertEquals(new int[] { 1, 2, 3 }, new int[] { 1, 2, 3 });
    }

    @Test
    public void testAssertNotEqualsIntArray() {
        try {
            EnhancedTestCase.assertEquals(new int[] { 1, 2 }, new int[] { 1, 3, 4 });
        } catch (AssertionFailedError e) {
            return;
        }
        fail("The Bags can not be equal");
    }

    @Test
    public void testAssertBagEquals() {
        EnhancedTestCase.assertBagEquals(new Object[] {}, new Object[] {});
    }

    @Test
    public void testAssertBagEqualsVoid() {
        EnhancedTestCase.assertBagEquals(new Object[] { Integer.valueOf(1) },
                new Object[] { Integer.valueOf(1) });
    }

    @Test
    public void testAssertBagEqualsSameList() {
        EnhancedTestCase.assertBagEquals(
                new Object[] { Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3) },
                new Object[] { Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3) });
    }

    @Test
    public void testAssertBagEqualsDouble() {
        EnhancedTestCase.assertBagEquals(new Object[] { Integer.valueOf(1), Integer.valueOf(1) },
                new Object[] { Integer.valueOf(1), Integer.valueOf(1) });
    }

    @Test
    public void testAssertBagEqualsDoubleSwitched() {
        EnhancedTestCase.assertBagEquals(
                new Object[] { Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3),
                        Integer.valueOf(1) },
                new Object[] { Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(1),
                        Integer.valueOf(3) });
    }

    @Test
    public void testAssertBagEqualsDoubleSwitchedMultiple() {
        EnhancedTestCase.assertBagEquals(
                new Object[] { Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3),
                        Integer.valueOf(1), Integer.valueOf(4), Integer.valueOf(5),
                        Integer.valueOf(2) },
                new Object[] { Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(1),
                        Integer.valueOf(3), Integer.valueOf(4), Integer.valueOf(2),
                        Integer.valueOf(5) });
    }

    /**
     * A regression test introduced because of a failure to check arrays correctly.
     */
    public final void testAssertBagEqualsArrayElements() {
        final Object[] strArrays =
            new Object[] { new Object[] { String.valueOf("a5"), String.valueOf("b5") },
                    new Object[] { String.valueOf("a6"), String.valueOf("b6") } };

        // Check equal to itself
        EnhancedTestCase.assertBagEquals(strArrays, strArrays);

        // Check that it is equal of the an array with the inverted elements
        EnhancedTestCase.assertBagEquals(strArrays,
                new Object[] { new Object[] { String.valueOf("a6"), String.valueOf("b6") },
                        new Object[] { String.valueOf("a5"), String.valueOf("b5") } });
    }

    /**
     * Compares an empty bag with a bag with one element.
     */
    @Test
    public void testAssertBagNotEqualsVoidVsOne() {
        try {
            EnhancedTestCase.assertBagEquals(new Object[] { Integer.valueOf(1) }, new Object[] {});
        } catch (AssertionFailedError e) {
            return;
        }
        fail("The Bags can not be equal");
    }

    @Test
    public void testAssertBagNotEqualsMultipleVsSingle() {
        try {
            EnhancedTestCase.assertBagEquals(
                    new Object[] { Integer.valueOf(1), Integer.valueOf(1) },
                    new Object[] { Integer.valueOf(1) });
        } catch (AssertionFailedError e) {
            return;
        }
        fail("The Bags can not be equal");
    }

    @Test
    public void testAssertBagNotEqualsSingleVsMultiple() {
        try {
            EnhancedTestCase.assertBagEquals(new Object[] { Integer.valueOf(1) },
                    new Object[] { Integer.valueOf(1), Integer.valueOf(1) });
        } catch (AssertionFailedError e) {
            return;
        }
        fail("The Bags can not be equal");
    }

    @Test
    public void testAssertBagNotEqualsDifferent() {
        try {
            EnhancedTestCase.assertBagEquals(
                    new Object[] { Integer.valueOf(1), Integer.valueOf(2) },
                    new Object[] { Integer.valueOf(2), Integer.valueOf(3) });
        } catch (AssertionFailedError e) {
            return;
        }
        fail("The Bags can not be equal");
    }

    @Test
    public void testAssertThrows() {
        // OK. If an exception is thrown.
        EnhancedTestCase.assertThrows(new CodeBlock() {
            public void execute() {
                throw new IllegalArgumentException();
            }
        }, new IllegalArgumentException());

        // Check that it complains if no exception is thrown
        try {
            EnhancedTestCase.assertThrows(new CodeBlock() {
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
