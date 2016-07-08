/*
 * Created on 7/Fev/2005
 */
package codebase.streams;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Utilities for managing streams.
 */
public final class Streams {

    /**
     * Avoid anyone from instantiating this class.
     */
    private Streams() {
    }

    /**
     * Creates a new buffered file-based data output stream.
     * 
     * @param fileName the name and path of the file to be created
     * @param bufferSize the size in bytes of the output buffer
     * @return a new buffered file output stream based with the specified size
     * @throws IOException if an error occurs while creating the file output stream
     */
    public static OutputStream createFileOutputStream(final String fileName, final int bufferSize)
            throws java.io.IOException {
        final FileOutputStream fileWriteStream = new FileOutputStream(fileName);
        final BufferedOutputStream fileOutputStream =
            new BufferedOutputStream(fileWriteStream, bufferSize);
        return fileOutputStream;
    }

    /**
     * Creates a new buffered file data input stream.
     * 
     * @param fileName the name and path of the file to be read
     * @param bufferSize the size in bytes of the input buffer
     * @return a new buffered file input stream based with the specified size
     * @throws IOException if an error ocours while opening the file output stream
     */
    public static InputStream createFileInputStream(final String fileName, final int bufferSize)
            throws IOException {
        final FileInputStream fileInputStream = new FileInputStream(fileName);
        final BufferedInputStream bufferedInput =
            new BufferedInputStream(fileInputStream, bufferSize);
        return bufferedInput;
    }

}
