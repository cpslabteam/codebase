/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2013 Paulo Carreira
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details.
 *
 * You should have received a copy of the GNU General Public License version 2
 * along with this work; if not, see http://www.gnu.org/licenses/
 * 
 *
 * Linking this work statically or dynamically with other modules is making a
 * combined work based on this work. Thus, the terms and conditions of the GNU
 * General Public License cover the whole combination.
 *
 * As a special exception, the copyright holders of this work give you permission
 * to link this work with independent modules to produce an executable,
 * regardless of the license terms of these independent modules, and to copy and
 * distribute the resulting executable under terms of your choice, provided that
 * you also meet, for each linked independent module, the terms and conditions of
 * the license of that module. An independent module is a module which is not
 * derived from or based on this work. If you modify this work, you may extend
 * this exception to your version of the work, but you are not obligated to do so.
 * If you do not wish to do so, delete this exception statement from your version.
 *
 * Please visit http://neilcsmith.net if you need additional information or
 * have any questions.
 */

/**
 * A library of general purpose utility classes.
 * <p>
 * The fundamental idea of <tt>codebase</tt> is to avoid replicating basic utility
 * functionality over and over again. In practice, programmers often need to replicate the
 * code of utility classes and, moreover, have a tendency to regard this code as of lesser
 * importance. An obvious consequence is that these classes don't get as thoroughly tested
 * as they often should, often compromising code quality. Therefore, the underlying idea
 * is to put here functionality that are commonly used in the code to avoid that each
 * project/developer to implement its own version and have it throughly tested.
 * <p>
 * The main goals of <tt>codebase</tt> are to:
 * <ol>
 * <li>Contributing to increasing the overall code quality by offering a library of high
 * quality utility classes (tested and revised)</li>
 * <li>Simplifying the dependencies of applications (using <tt>codebase</tt> prevents
 * depending on other libraries)</li>
 * <li>Setting a minimum standard for code quality (the code is available in open source
 * to be analyzed).</li>
 * </ol>
 * <p>
 * The classes included in the main package are general purpose classes used for, among
 * other, parsing command line arguments, file handling, cyphering, string and binary
 * operations. Utility classes for more <i>specialized functionality</i> are organized
 * into <i>appropriate sub-packages</i>.
 * <p>
 * Unless otherwise stated, the classes herein are not threaded safe without external
 * synchronization.
 * <p>
 * <p>
 * Other open source alternatives to <tt>codebase</tt> are <a
 * href="http://commons.apache.org">Apache Commons</a> and <a
 * href="https://code.google.com/p/guava-libraries">Google Guava</a>. Although similar in
 * spirit, we decided to keep maintaining <tt>codebase</tt> because it has a lower
 * footprint and better suited to our purposes.
 */
package codebase;

