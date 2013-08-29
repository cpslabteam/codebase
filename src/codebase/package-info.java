/**
 * General purpose utility classes.
 * <p>
 * The fundamental idea of <tt>codebase</tt> is to avoid replicating basic utility
 * functionality over and over again. In practice, programmers often need to replicate the
 * code of utility classes and, moreover, have a tendency to regard this code as of lesser
 * importance. An obvious consequence is that these classes don't get as thoroughly tested
 * as they often should, often compromising code quality.
 * <p>
 * The main goals of <tt>codebase</tt> are to:
 * <ol>
 * <li>Contributing to increasing the overall code quality by offering a library of high
 * quality utility classes (tested and revised)</li>
 * <li>Simplify the dependencies of applications (using <tt>codebase</tt> prevents
 * depending on other libraries)</li>
 * <li>Setting a minimum standard for code quality (the code is available in open source
 * to be analyzed).</li>
 * </ol>
 * <p>
 * The classes included in the main package are general purpose classes used for, among
 * other, parsing command line arguments, file handling, cyphering, string and binary
 * operations.
 * <p>
 * Utility classes for more specialized functionality are organized into appropriate
 * sub-packages.
 * <p>
 * Unless otherwise stated, the classes herein are not multithreaded safe without external
 * synchronization.
 * <p>
 * There are at least two other open source alternatives to <tt>codebase</tt>: Apache
 * Commons and Google xxx.
 */

// todo: add license files
package codebase;