/*
 * Created on 5/Mar/2005
 */
package codebase.iterators;

/**
 * The interface of a proxy for iterators that allow item manipulation.
 */
public interface ItemManipulatorIteratorProxy
        extends ItemManipulatorIterator {
    
    /**
     * Returns the iterator being managed on behalf of this proxy.
     * 
     * @return an item manipulator iterator
     */
    ItemManipulatorIterator request();
}
