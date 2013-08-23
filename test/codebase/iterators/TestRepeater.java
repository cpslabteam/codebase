/*
 * Created on 3/Set/2005
 */
package codebase.iterators;

import java.util.Iterator;

import codebase.junit.CodeBlock;
import codebase.junit.EnhancedTestCase;


/**
 * Tests the {@link Repeater} iterator class.
 */
public class TestRepeater extends
        EnhancedTestCase {

    /**
     * Tests a repeater with zero repetitions.
     */
    public final void testZero() {
        assertEquals(new EmptyIterator<Object>(), new Repeater<Object>(new Object(), 0));
    }

    /**
     * Tests a repeater with one repetitions.
     */
    public final void testOne() {
        final Object o = new Object();
        final Iterator<Object> input = new ArrayIterator<Object>(new Object[] { o });

        assertEquals(input, new Repeater<Object>(o, 1));
    }

    /**
     * Tests the reset of a repeater with two repetitions.
     */
    public final void testReset() {
        final Object o = new Object();
        final Iterator<Object> input = new ArrayIterator<Object>(new Object[] { o, o });
        final Repeater<Object> r = new Repeater<Object>(o, 2);

        assertEquals(r.next(), o);
        r.reset();

        assertEquals(input, r);
    }

    /**
     * Tests that peek work correctly.
     */
    public final void testPeek() {
        final Object o = new Object();
        final Repeater<Object> r = new Repeater<Object>(o, 2);

        assertTrue(r.supportsPeek());

        assertEquals(r.peek(), o);
        assertEquals(r.next(), o);
        assertEquals(r.peek(), o);
        assertEquals(r.next(), o);

        assertThrows(new CodeBlock() {
            public void execute() {
                r.peek();
            }
        }, new IllegalStateException());
    }

    /**
     * Tests that remove removes elements.
     */
    public final void testRemove() {
        final Object o = new Object();
        final Repeater<Object> r = new Repeater<Object>(o, 3);

        assertEquals(r.next(), o);
        r.remove();
        assertEquals(r.peek(), o);
        assertEquals(r.next(), o);

        assertThrows(new CodeBlock() {
            public void execute() {
                r.peek();
            }
        }, new IllegalStateException());
    }

    /**
     * Tests a repeater with many repetitions.
     */
    public final void testMany() {
        final Object o = new Object();
        final Iterator<Object> input = new ArrayIterator<Object>(
                new Object[] { o, o, o, o, o, o, o });

        assertEquals(input, new Repeater<Object>(o, 7));
    }

    /**
     * Tests an unlimited repeater.
     */
    public final void testUnlimited() {
        final Object o = new Object();

        final Iterator<Object> repeater = new Repeater<Object>(o);
        for (int i = 0; i < 120; i++) {
            assertSame(o, repeater.next());
        }
    }

}
