/*
 * Created on 20/Out/2005
 */
package codebase.iterators;

import java.util.Iterator;


/**
 * The interface of iterator that can be reset.
 * <p>
 * This interface specifies an iterator that can be reset to the first element.
 */
public interface ResetableIterator
        extends Iterator, Resetable {
}
