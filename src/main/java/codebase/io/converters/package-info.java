/**
 * Provides I/O converters to read and write objects in a structured way.
 * <p>
 * A {@link codebase.io.converters.Converter} is an object that knows how to read and
 * write objects of a give type from and to {@link java.io.DataInput} and
 * {@link java.io.DataOutput}, respectively.
 * <p>
 * Converters can be created form other converters enabling to read and write complex
 * objects.
 * <p>
 * It is also possible for an certain object to have multiple converters. For example a
 * display and binary converters, that "serialize" the object in display and binary form.
 * <p>
 * Another important aspect of converters is that that they follow one of two fundamental
 * abstractions. They can be <i>fixed size</i> and <i>variable size</i>. The practical
 * consequence is that a fixed size converter consumes and produces the same amount of
 * bytes and this number of bytes is known up-front. In contrast, a variable size
 * converter does not know up-front how many bytes it will consume.
 */
package codebase.io.converters;