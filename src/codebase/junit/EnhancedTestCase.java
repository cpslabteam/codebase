/*
 * Created on 16/Out/2004
 */
package codebase.junit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import codebase.ArrayUtil;
import codebase.BinaryUtil;
import codebase.DebugUtil;
import codebase.iterators.ArrayIterator;

import junit.framework.TestCase;

/**
 * Base class with new <code>assert</code> primitives for writing test cases.
 * <p>
 * This class offers primitives to simplify writing <tt>junit</tt> tests cases that deal
 * with iterators and arrays.
 * <p>
 * Extend this class instead of {@link junit.framework.TestCase}
 */
public class EnhancedTestCase extends
        TestCase {

    /**
     * A private class used for matching the elements of the expected iterator with the
     * elements of the actual iterator. In order to compare to iterators we need to count
     * the number of occurrences of similar elements. This class wraps an object with a
     * number two reference counters. The number of times the object is seen on the
     * expected iterators of times it is then seen on the actual iterator. If these
     * reference counters are not the same this means that The number of occurrences of
     * the object in the expected and in the actual iterator do not match.
     */
    private static class RefObject extends
            Object {
        /**
         * Number of times the object is seen on the expected iterator.
         */
        private int expectedRefCount;

        /**
         * Number of times the object is seen on the actual iterator.
         */
        private int actualRefCount;

        /**
         * Element we are working on.
         */
        private Object baseElem;

        /**
         * Constructor of a ref object.
         * 
         * @param elem the object to be treated
         */
        RefObject(final Object elem) {
            baseElem = elem;
            expectedRefCount = 1;
        }

        /**
         * Checks if are equal to another object. It compares the underling base elements
         * but not through the java equals() method instead is uses the Debug.equals()
         * method which is capable of handeling arrays.
         * 
         * @param o another entry to compare to
         * @return <code>true</code> if the underling base elements have the same content.
         */
        public boolean equals(final Object o) {
            if (o == null)
                return false;

            if (!(o instanceof RefObject))
                return false;

            final RefObject entry = (RefObject) o;
            return ArrayUtil.isEqualArrayDeep(entry.baseElem, baseElem);
        }

        /**
         * Computes the hash code of the element.
         * 
         * @return the hash code of the base element
         * @see java.lang.Object#hashCode()
         */
        public int hashCode() {
            return baseElem.hashCode();
        }

        /**
         * Informs that this object was found in the expected iterator once more.
         */
        public void incrementExpectedRef() {
            expectedRefCount += 1;
        }

        /**
         * Informs that this object was found in the actual iterator once more.
         */
        public void incrementActualRefCount() {
            actualRefCount += 1;
        }

        /**
         * Check is the number of expected references matches the number of actual
         * references.
         * 
         * @return <code>true</code> if the number of expected references matches the
         *         number of actual references. <code>false</code> otherwise.
         */
        public boolean isOk() {
            return expectedRefCount == actualRefCount;
        }

        /**
         * Produces a string representation for this object.
         * 
         * @return the base object element toghether with the expected and actual numbers
         *         of references.
         */
        public String toString() {
            final String elementRepr = DebugUtil.toString(baseElem);
            return "Element <" + elementRepr + "> expected <" + Integer.toString(expectedRefCount)
                    + "> times; seen <" + Integer.toString(actualRefCount) + "> times.";
        }
    }

    /**
     * Compares two iterators, and check if they are equal element by element.
     * 
     * @param actual the actual iterator
     * @param expected the expected iterator
     */
    public static final void assertEquals(final Iterator<?> expected, final Iterator<?> actual) {
        performBaseNullChecks(expected, actual);
        performIteratorEmptyChecks(expected, actual);

        int rowNum = 0;

        /*
         * We have to disable Checkstyle temporarily so that it does not complain about
         * catching the type Exception. This code is special because it deals with test
         * cases.
         */

        // CHECKSTYLE:OFF
        while (actual.hasNext() && expected.hasNext()) {
            Object actualValue = null;
            try {
                actualValue = actual.next();
            } catch (Exception e) {
                throw new junit.framework.AssertionFailedError(
                        "The <expected> iterator has more elements than <actual>,"
                                + " since next() call failed on <actual> iterator at"
                                + " (0-based) row " + Integer.toString(rowNum));
            }

            Object expectedValue = null;
            try {
                expectedValue = expected.next();
            } catch (Exception e) {
                throw new junit.framework.AssertionFailedError(
                        "The <actual> iterator has more elements that <expected>,"
                                + " since next() call failed on <expected> iterator at"
                                + " (0-based) row " + Integer.toString(rowNum));
            }
            try {
                compareRows(rowNum, expectedValue, actualValue);
            } catch (Exception e) {
                throw new junit.framework.AssertionFailedError(
                        "Iterator row comparison failed: " + e.toString());
            }

            rowNum += 1;
        }
        /*
         * The loop has terminated but maybe only one of the iterators is empty. Check
         * that out.
         */
        try {
            performIteratorEmptyChecks(expected, actual);
        } catch (junit.framework.AssertionFailedError e) {
            throw new junit.framework.AssertionFailedError(
                    "At (0-based) row " + Integer.toString(rowNum) + ": " + e.getMessage());
        }
        // CHECKSTYLE:ON
    }

    /**
     * Checks that two iterators are equal using a bag semantics. In other words it
     * compares the elements of two iterators and furthermore it counts the number of
     * ocurrences of each element.
     * 
     * @param expected the iterator that sets the expected elements
     * @param actual the iterator obtained that is to be compared to expected
     */
    public static final void assertBagEquals(final Iterator<?> expected, final Iterator<?> actual) {
        performBaseNullChecks(expected, actual);
        performIteratorEmptyChecks(expected, actual);

        final java.util.List<RefObject> expectedElements = makeExpectedElems(expected);
        performExistsActualCheck(expectedElements, actual);
        performBagReferenceCheck(expectedElements);
    }

    /**
     * Checks that two object arrays are equal element by element using the
     * {@link Object#equals(Object)} method.
     * 
     * @param expected the expected object array
     * @param actual the result against which the expected is to be checked
     */
    public static final void assertEquals(final Object[] expected, final Object[] actual) {
        performBaseNullChecks(expected, actual);
        performArrayEmptyChecks(expected, actual);
        performArrayComparison(expected, actual);
    }

    /**
     * Produces a safe representation of a char.
     * <p>
     * When an invisible char is displayed, it can cause undesired effects on the tty.
     * 
     * @param c the char to be generated
     * @return a spaces character if the character is not visible.
     */
    private static char safeChar(final char c) {
        final char firstVisibleChar = 32;

        if (c < firstVisibleChar) {
            return ' ';
        } else {
            return c;
        }
    }

    /**
     * Checks that two chars are the same
     * <p>
     * This method is intended to serve as a replacement for JUnit's assert when comparing
     * two chars. JUnit reports a difference between to integer values, which is not very
     * helpful.
     * 
     * @param expected the expected char
     * @param actual the actual char
     */
    public static final void assertEquals(final char expected, final char actual) {
        if (expected != actual) {
            throw new junit.framework.AssertionFailedError(
                    "Expecting <" + safeChar(expected) + "> (" + (int) expected + ", 0x"
                            + BinaryUtil.toHex(expected) + ") but found <" + safeChar(actual)
                            + "> (" + (int) actual + ", 0x" + BinaryUtil.toHex(actual) + ").");
        }
    }

    /**
     * Checks that two arrays of bytes are equal.
     * 
     * @param expected the expected byte array
     * @param actual the result against which the expected is to be checked
     */
    public static final void assertEquals(final byte[] expected, final byte[] actual) {
        performBaseNullChecks(expected, actual);
        performArrayEmptyChecks(ArrayUtil.toByteArray(expected), ArrayUtil.toByteArray(actual));
        performArrayComparison(ArrayUtil.toByteArray(expected), ArrayUtil.toByteArray(actual));
    }

    /**
     * Checks that two arrays of integers are equal.
     * 
     * @param expected the expected integer array
     * @param actual the result against which the expected is to be checked
     */
    public static final void assertEquals(final int[] expected, final int[] actual) {
        performBaseNullChecks(expected, actual);
        performArrayEmptyChecks(ArrayUtil.toIntegerArray(expected),
                ArrayUtil.toIntegerArray(actual));
        performArrayComparison(ArrayUtil.toIntegerArray(expected),
                ArrayUtil.toIntegerArray(actual));
    }

    /**
     * Checks that two arrays of longs are equal.
     * 
     * @param expected the expected long array
     * @param actual the result against which the expected is to be checked
     */
    public static final void assertEquals(final long[] expected, final long[] actual) {
        performBaseNullChecks(expected, actual);
        performArrayEmptyChecks(ArrayUtil.toLongArray(expected), ArrayUtil.toLongArray(actual));
        performArrayComparison(ArrayUtil.toLongArray(expected), ArrayUtil.toLongArray(actual));
    }

    /**
     * Checks that two array of object are equal using bag semantics
     * <p>
     * In a bag allows multiple occurrences of the the same element. The comparison using
     * bag semantics does not make any assumptions on ordering. It is only required that
     * number of copies of the elements are the same in both operands.
     * 
     * @param expected the array of expected elements
     * @param actual the array of actual elements
     */
    public static final void assertBagEquals(final Object[] expected, final Object[] actual) {
        performBaseNullChecks(expected, actual);
        performArrayEmptyChecks(expected, actual);

        final Iterator<?> expectedIterator = new ArrayIterator<Object>(expected);
        final Iterator<?> actualIterator = new ArrayIterator<Object>(actual);
        final java.util.List<RefObject> expectedElements = makeExpectedElems(expectedIterator);
        performExistsActualCheck(expectedElements, actualIterator);
        performBagReferenceCheck(expectedElements);
    }

    /**
     * Checks that the elements of on iterator are contained in the other.
     * 
     * @param expected the iterator with the expected elements
     * @param actual the iterator with the elements that should be contained in the
     *            iterator of expected elements.
     */
    public static final void assertContained(final Iterator<?> expected, final Iterator<?> actual) {
        performBaseNullChecks(expected, actual);
        performIteratorEmptyChecks(expected, actual);
        performIsContainedCheck(expected, actual);
    }

    /**
     * Check that a block of code throws a certain exception.
     * <p>
     * If the code block does not throw any exception the method fails. If the code block
     * throws an exception of a different type (types a checked by comparing the class
     * names literally), the method also fails.
     * 
     * @param block the block of code to be called
     * @param throwable an instance of the exception class
     * @throws junit.framework.AssertionFailedError if the code throws an exception of a
     *             different type of did not throw any exception
     */
    public static void assertThrows(final CodeBlock block, final Throwable throwable) {
        /*
         * We have to disable checkstyle temporarily so that it does not complain about
         * catching the type Throwable. This code is special because it deals with test
         * cases.
         */

        // CHECKSTYLE:OFF
        Throwable exception;
        try {
            block.execute();
            exception = null;
        } catch (Throwable t) {
            exception = t;
        }
        // CHECKSTYLE:ON

        if (exception != null) {
            if (exception.getClass() != throwable.getClass()) {
                fail("Got exception " + exception.getMessage() + "of type "
                        + exception.getClass().getName() + ", but expecting an exception of type "
                        + throwable.getClass().getName());
            }
        } else {
            fail("Expected exception of type " + throwable.getClass().getName() + " did not occur");
        }
    };

    /**
     * Builds a list with of objects with their number of occurrences.
     * <p>
     * Uses the {@link RefObject} class in order to maintain the expected and actual
     * numbers of references. This list will be treated by other methods.
     * 
     * @param iterator an iterator in the expected objects
     * @return a list of RefObject elements
     */
    private static java.util.List<RefObject> makeExpectedElems(final java.util.Iterator<?> iterator) {
        /*
         * Adds all elements to the list
         */
        final java.util.List<RefObject> expectedElements = new java.util.ArrayList<RefObject>();

        while (iterator.hasNext()) {
            final Object element = iterator.next();

            final RefObject refObject = getRefObjectContaining(expectedElements, element);

            if (refObject != null) {
                refObject.incrementExpectedRef();
            } else {
                expectedElements.add(new RefObject(element));
            }
        }
        return expectedElements;
    }

    /**
     * Searches for an object in the list of objects with reference counts.
     * 
     * @param refObjectList the list
     * @param element the object to search for
     * @return the object with the corresponding count of references or <code>null</code>
     *         if not found
     */
    private static RefObject getRefObjectContaining(final List<RefObject> refObjectList,
                                                    final Object element) {
        for (final Iterator<RefObject> it = refObjectList.iterator(); it.hasNext();) {
            final RefObject refObject = (RefObject) it.next();
            if (refObject.baseElem == null) {
                if (element == null) {
                    return refObject;
                }
            } else if (refObject.baseElem.equals(element)) {
                return refObject;
            }
        }
        return null;
    }

    /**
     * Checks that all the elements seen in the actual iterator are expected. For each
     * actual element seen it also increments the number of actual references.
     * 
     * @param expectedElements the list with reference count information on objects
     * @param actualIterator an iterator on the actual objects to be treated
     */
    private static void performExistsActualCheck(final java.util.List<?> expectedElements,
                                                 final java.util.Iterator<?> actualIterator) {
        /*
         * Checks that all elements in the actual iterator are elements expected by the
         * expected iterator
         */
        final int rowNum = 0;
        while (actualIterator.hasNext()) {
            final Object element = actualIterator.next();
            final int elemIndex = expectedElements.indexOf(new RefObject(element));
            if (elemIndex >= 0) {
                ((RefObject) expectedElements.get(elemIndex)).incrementActualRefCount();
            } else {
                throw new junit.framework.AssertionFailedError("The element <"
                        + DebugUtil.toString(element) + ">; at (0-based) row "
                        + Integer.toString(rowNum) + " was not present in the expected iterator");
            }
        }
    }

    /**
     * Verifies that the expected and actual numbers of references of each element in the
     * reference count list are equal. I.e., that they match up to bag semantics.
     * 
     * @param expectedElements the list with reference count information on objects
     */
    private static void performBagReferenceCheck(final java.util.List<RefObject> expectedElements) {
        for (final java.util.Iterator<RefObject> it = expectedElements.listIterator(); it
                .hasNext();) {
            final RefObject entry = (RefObject) it.next();
            if (!entry.isOk()) {
                throw new junit.framework.AssertionFailedError(
                        "Mismatched number of occourrences on the "
                                + "expected and actual iterators. " + DebugUtil.toString(entry));
            }
        }
    }

    /**
     * Compares two rows throwing the appropriate exception in case the elements of the
     * columns do not match.
     * <p>
     * If the elements are arrays we perform a recursive element by element comparison
     * (because we may have arrays of arrays). Otherwise we use the <code>equals</code>
     * method. If this method fails we check that the string representation is the same.
     * <p>
     * <b>NOTE: </b> This is very important because some objects are very difficult to
     * compare recursively.
     * 
     * @param rowNum the number of the row that is being compared
     * @param expectedValue the expected row
     * @param actualValue the actual row
     * @see junit.framework.AssertionFailedError
     */
    private static void compareRows(final int rowNum,
                                    final Object expectedValue,
                                    final Object actualValue) {
        if ((actualValue instanceof Object[]) && (expectedValue instanceof Object[])) {
            try {
                performArrayComparison((Object[]) expectedValue, (Object[]) actualValue);
            } catch (junit.framework.AssertionFailedError e) {
                throw new junit.framework.AssertionFailedError(
                        "At (0-based) row " + Integer.toString(rowNum) + ": " + e.getMessage());
            }
        } else {
            if (!actualValue.equals(expectedValue)) {

                /**
                 * Check that the string representations are not the same
                 */
                if (!actualValue.toString().equals(expectedValue.toString())) {
                    throw new junit.framework.AssertionFailedError(
                            "Expected <" + DebugUtil.toString(expectedValue) + ">; Actual <"
                                    + DebugUtil.toString(actualValue) + ">  at (0-based) row "
                                    + Integer.toString(rowNum));
                }
            }
        }

    }

    /**
     * Verifies the base conditions for iterator comparison.
     * 
     * @param actual the actual object
     * @param expected object that represents the expected result of the test
     * @throws junit.framework.AssertionFailedError if only one of the arguments is null
     */
    private static void performBaseNullChecks(final Object expected, final Object actual) {
        if (actual == null && expected == null) {
            return;
        }

        if (actual == null && expected != null) {
            throw new junit.framework.AssertionFailedError(
                    "<actual> argument is null but <expected> is not");
        }

        if (actual != null && expected == null) {
            throw new junit.framework.AssertionFailedError(
                    "<expected> argument is null but <actual> is not");
        }
    }

    /**
     * Checks that two objects arrays are equal. It compares the length of the arrays and
     * then it performs a positional comparison of each element of the array using the
     * method <code>equals()</code>
     * 
     * @param expectedArray the expected array
     * @param actualArray the row of actual Integer objects
     * @see Object#equals(java.lang.Object)
     */
    private static void performArrayComparison(final Object[] expectedArray,
                                               final Object[] actualArray) {

        if (expectedArray.length != actualArray.length) {
            throw new junit.framework.AssertionFailedError(
                    "Actual and expected do not have the same length: length(expected)="
                            + Integer.toString(expectedArray.length) + ", length(actual)="
                            + Integer.toString(actualArray.length));
        }

        for (int i = 0; i < expectedArray.length; i++) {
            if (!ArrayUtil.isEqualArrayDeep(expectedArray[i], actualArray[i])) {
                throw new junit.framework.AssertionFailedError(
                        "Actual and Expected differ at position " + Integer.toString(i)
                                + ". Expected <" + DebugUtil.toString(expectedArray[i])
                                + ">; Actual value <" + DebugUtil.toString(actualArray[i]) + ">");
            }
        }
    }

    /**
     * Verifies that either the two object arrays have elements or that they both are
     * empty.
     * 
     * @param actual the actual array
     * @param expected the expected array that represents the expected result of the test
     * @throws junit.framework.AssertionFailedError if only one of the arguments is null
     */
    private static void performArrayEmptyChecks(final Object[] expected, final Object[] actual) {
        if (actual.length == expected.length) {
            return;
        }

        if ((actual.length == 0) && (expected.length != 0)) {
            throw new junit.framework.AssertionFailedError(
                    "Actual array is empty but elements are expected");
        }

        if ((actual.length != 0) && (expected.length == 0)) {
            throw new junit.framework.AssertionFailedError(
                    "Actual array has elements but no elements expected");
        }
    }

    /**
     * Verifies that either the two iterators have elements or that they both do not have
     * more elements. Note that this is not the same as being empty because the elements
     * may already have been exhausted fro the iterators.
     * 
     * @param actual the actual iterator
     * @param expected iterator that represents the expected result of the test
     * @throws junit.framework.AssertionFailedError if only one of the arguments is null
     */
    private static void performIteratorEmptyChecks(final Iterator<?> expected,
                                                   final Iterator<?> actual) {
        boolean actualHasNext = false;
        boolean expectedHasNext = false;
        try {
            actualHasNext = actual.hasNext();
        } catch (java.util.NoSuchElementException e) {
            throw new junit.framework.AssertionFailedError(
                    "Actual is empty. actual.hasNext() failed with NoSuchElementException.");
        }
        try {
            expectedHasNext = expected.hasNext();
        } catch (java.util.NoSuchElementException e) {
            throw new junit.framework.AssertionFailedError(
                    "Expected is empty. expected.hasNext() failed with NoSuchElementException.");
        }

        if (actualHasNext && expectedHasNext) {
            return;
        }

        if (!actualHasNext && expectedHasNext) {
            final Object element = expected.next();
            throw new junit.framework.AssertionFailedError(
                    "Actual is empty but rows are expected. Expected element details: <"
                            + DebugUtil.toString(element) + ">.");
        }

        if (actualHasNext && !expectedHasNext) {
            final Object element = actual.next();
            throw new junit.framework.AssertionFailedError(
                    "Actual has rows but no rows are expected. Actual element datails: <"
                            + DebugUtil.toString(element) + ">.");
        }
    }

    /**
     * @param actual the actual iterator
     * @param expected iterator that represents the expected result of the test
     * @throws junit.framework.AssertionFailedError
     */
    private static void performIsContainedCheck(final Iterator<?> expected,
                                                final Iterator<?> actual) {
        final List<Object> expectedElems = new ArrayList<Object>();
        while (expected.hasNext()) {
            final Object elem = expected.next();
            expectedElems.add(elem);
        }
        int rowNum = 0;
        while (actual.hasNext()) {
            final Object elem = actual.next();
            if (!expectedElems.contains(elem)) {
                throw new junit.framework.AssertionFailedError(
                        "Actual element at (0-based) position " + Integer.toString(rowNum)
                                + " does not exist is expected element list. Element datails: <"
                                + DebugUtil.toString(elem) + ">.");
            }
            rowNum += 1;
        }
    }

    /**
     * Checks that a result is near the expected result. In other words checks that
     * <code>abs(expected - actual) &leq; error</code>.
     * 
     * @param expected the expected values
     * @param actual the actual value
     * @param error the error interval
     */
    public static final void assertDelta(final double expected,
                                         final double actual,
                                         final double error) {
        final boolean isOk = java.lang.Math.abs(expected - actual) <= error;
        if (!isOk) {
            throw new junit.framework.AssertionFailedError("Expecting <" + Double.toString(expected)
                    + "> found <" + Double.toString(actual) + "> which is ouside the error bound <"
                    + Double.toString(error) + ">.");
        }
    }

    /**
     * Checks that the result of a function is one-valued.
     * 
     * @param expected the expected scalar value
     * @param actual the actual function result
     */
    public final void assertEqualResults(final Object expected, final Object[] actual) {
        if (actual == null) {
            throw new junit.framework.AssertionFailedError(
                    "The mapper function result is invalid (null). Expecting the element <"
                            + DebugUtil.toString(expected) + ">.");
        }
        if (actual.length < 1) {
            throw new junit.framework.AssertionFailedError(
                    "The function result is empty. Expecting the element <"
                            + DebugUtil.toString(expected) + ">.");
        }
        if (actual.length > 1) {
            throw new junit.framework.AssertionFailedError("The function result has many values <"
                    + ArrayUtil.toString(actual, ", ") + ">. Expecting the singleton element <"
                    + DebugUtil.toString(expected) + ">.");
        }

        final Object result = actual[0];
        if ((result == null) && (expected != null)) {
            throw new junit.framework.AssertionFailedError(
                    "The function returned one null value. Expecting the singleton element <"
                            + DebugUtil.toString(expected) + ">.");
        }
        if (result != null && !result.equals(expected)) {
            throw new junit.framework.AssertionFailedError("The function returned value <"
                    + DebugUtil.toString(result) + "> differs from the expected value <"
                    + DebugUtil.toString(expected) + ">.");
        }
    }

}
