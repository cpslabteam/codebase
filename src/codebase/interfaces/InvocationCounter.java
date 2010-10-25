package codebase.interfaces;

/*
 * Created on 16/Dez/2004
 *  
 */
/**
 * Interface for object that count invocations.
 * <p>
 * This interface specifices the the methods of objects that need to count
 * invocations. Like for example functions.
 */
public interface InvocationCounter {
    
    /**
     * Returns the number of invocations performed so far, that have beed
     * previously registered through the countInvocation method.
     * 
     * @return the value of the invocation counter.
     */
    long getInvocationCount();
}
