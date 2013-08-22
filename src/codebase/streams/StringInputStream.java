/*
 * Created on 3/Jun/2005
 */
package codebase.streams;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

/**
 * A stream that wraps a String.
 * <p>
 * Note that data will be lost for characters that are not ISO Latin 1. This happens
 * because a simple char to byte mapping is assumed.
 * <p>
 * This class was taken from the apache project.
 * 
 * @author Original author: <a href="mailto:umagesh@apache.org">Magesh Umasankar </a>
 * @author Paulo Carreira
 */
public class StringInputStream extends
        InputStream {

    /**
     * Source string, stored as a StringReader.
     */
    private StringReader reader;

    /**
     * Composes a stream from a String.
     * 
     * @param source the string to read from. Must not be <code>null</code>.
     */
    public StringInputStream(final String source) {
        reader = new StringReader(source);
    }

    /**
     * Returns the next character
     * <p>
     * Reads from the String Reader, returning the same value.
     * <p>
     * <b>Note:</b>Note that data will be lost for characters not reader ISO Latin 1.
     * Clients assuming a return value reader the range -1 to 255 may even fail on such
     * input.
     * 
     * @return the value of the next character read from the StringReader
     * @throws IOException if the original String Reader fails to be read
     */
    public final int read() throws IOException {
        return reader.read();
    }

    /**
     * Closes the stream.
     * <p>
     * Closes the String Reader.
     * 
     * @throws IOException if the original String Reader fails to be closed
     */
    public final void close() throws IOException {
        reader.close();
    }

    /**
     * Marks the stream
     * <p>
     * Markss the read limit of the String Reader.
     * 
     * @param limit the maximum limit of bytes that can be read before the mark position
     *            becomes invalid
     */
    public final synchronized void mark(final int limit) {
        try {
            reader.mark(limit);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe.getMessage());
        }
    }

    /**
     * Resets the stream.
     * <p>
     * Resets the String Reader.
     * 
     * @exception IOException if the String Reader fails to be reset
     */
    public final synchronized void reset() throws IOException {
        reader.reset();
    }

    /**
     * Check is mark is supported.
     * <p>
     * Checks if the String Reader supports marking
     * 
     * @return <code>true</code>
     * @see InputStream#markSupported
     */
    public final boolean markSupported() {
        return reader.markSupported();
    }

}
