/*
 * Created on 19/Out/2005
 */
package codebase;

import java.util.ArrayList;
import java.util.List;

import codebase.tests.ComparableArrayWrapper;


import junit.framework.TestCase;

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
        List l = new ArrayList();
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
        List l = new ArrayList();
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
        List l = new ArrayList();
        // insert only the Object[]
        l.add(new Object[] {
                "A", "B"});

        // found!
        assertTrue(l.contains(new ComparableArrayWrapper(new Object[] {
                "A", "B"})));
    }

}
