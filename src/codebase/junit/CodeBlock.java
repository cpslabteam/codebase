/*
 * Created on 22/Out/2005
 */
package codebase.junit;


/**
 * Interface of blocks of code called in a deferred (or lazzy) way.
 * <p>
 * Since java performs eager evaluation, it is dificult to create generic operations that
 * execute code in a lazzy fashion. Another face of the problem is that we cannot create a
 * method that executes a generic code block.
 * <p>
 * If one needs to implement a method <code>m(code)</code> that executes the parameter
 * <code><i>block</i></code> in a lazzy fashion we may use this interface and implement
 * the method {@link #execute()}.
 */
public interface CodeBlock {

    /**
     * Method that executes the code block.
     */
    void execute();
}
