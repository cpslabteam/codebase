/*
 * Created on 19/Out/2005
 */
package codebase.util;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import codebase.util.junit.ComparableArrayWrapper;

/**
 * Tests the ComparableArrayWrapper class
 */
public class TestComparableArrayWrapper
        extends TestCase {

    /**
     * Tests that normal Object[] equals do not work. An object array list
     * cannot be search by an object array.
     */
    public final void testObjectArray() {
        List<Object> l = new ArrayList<Object>();
        l.add(new Object[] {
                "A", "B"});

        // big azar, can find the object
        assertTrue(!l.contains(new Object[] {
                "A", "B"}));
    }

    /**
     * Tests that a list of {@link ComparableArrayWrapper} objects is serached
     * successfully
     */
    public final void testComparableArrayWrapper() {
        List<Object> l = new ArrayList<Object>();
        l.add(new ComparableArrayWrapper(new Object[] {
                "A", "B"}));
        l.add(new ComparableArrayWrapper(new Object[] {
                "C", "D"}));

        // found!
        assertTrue(l.contains(new ComparableArrayWrapper(new Object[] {
                "A", "B"})));
    }

    /**
     * Tests that a list of <code>Object[]</code> objects is serached
     * successfully
     */
    public final void testNoWrapperWithWrapper() {
        List<Object> l = new ArrayList<Object>();
        // insert only the Object[]
        l.add(new Object[] {
                "A", "B"});

        // found!
        assertTrue(l.contains(new ComparableArrayWrapper(new Object[] {
                "A", "B"})));
    }

}
