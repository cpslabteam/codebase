package codebase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.Writer;
import java.nio.channels.FileChannel;

import javax.swing.filechooser.FileSystemView;

import codebase.os.SysUtil;

/**
 * Utility class for generic operations on files.
 * 
 * @since 3/Jun/2005
 */
public final class FileUtil {

    private static final int DEFAULT_READ_BUFFER_SIZE = 16 * 1024;
    private static final double ONE_KB_BYTES = 1024.0;

    /**
     * Prevent instantiations of this class.
     */
    private FileUtil() {
    }

    /**
     * Gets the volume name.
     * 
     * @param devicePath the path of the the device e.g. <code>C:</code>
     * @return the volume name associated with the device or null if no name is associated
     *         with the device
     */
    public static String getVolumeName(final String devicePath) {
        if (devicePath == null) {
            throw new IllegalArgumentException("The volume name should be assigned");
        }
        final FileSystemView view = FileSystemView.getFileSystemView();
        final File dir = new File(devicePath);
        final String name = view.getSystemDisplayName(dir);
        if (name == null) {
            return null;
        }
        final String trimedName = name.trim();
        if (trimedName.length() < 1) {
            return null;
        }

        /*
         * take the clean volume name by striping from the format: Volname (E:)
         */
        final int index = trimedName.lastIndexOf(" (");

        final String cleanName;
        if (index > 0) {
            cleanName = trimedName.substring(0, index);
        } else {
            cleanName = trimedName;
        }
        return cleanName;
    }

    /**
     * Checks if a file with a specified size can be created on a specified path.
     * <p>
     * This method effectively tries to create a file with the specified size. Then it
     * tries to enlarge the file. In the end the file is removed.
     * 
     * @param path the file path
     * @param length the size of the file in bytes
     * @return <code>true</code> if the file can be created, <code>false</code> if the
     *         file already exists or if the file creation fails, for example, because the
     *         file specified is a directory.
     */
    public static boolean verifyCanCreateFile(final String path, final long length) {
        final File file = new File(path);
        if (!file.exists()) {
            final RandomAccessFile access;
            try {
                access = new RandomAccessFile(file, "rws");
            } catch (FileNotFoundException e) {
                return false;
            }

            try {
                access.setLength(length);
            } catch (IOException e) {
                return false;
            } finally {
                try {
                    access.close();
                } catch (IOException e) {
                    return false;
                }
            }

            return file.delete();
        }
        return false;
    }

    /**
     * Reads a file into a string.
     * 
     * @param path the path of the file
     * @return the text read from the file
     * @throws IOException if a problem occurs while reading the
     */
    public static String readFromFile(final String path) throws IOException {
        final RandomAccessFile access = new RandomAccessFile(path, "r");

        final StringBuffer result = new StringBuffer();
        try {
            while (access.getFilePointer() < access.length()) {
                result.append(access.readLine() + "\n");
            }
        } finally {
            access.close();
        }

        return result.toString();
    }

    /**
     * Dumps a string into a file.
     * <p>
     * If the file does not exits it is created. Otherwise, if it already exists it
     * overwritten.
     * 
     * @param path the path to the file
     * @param content the content to write
     * @throws IOException if writting to the file fails
     */
    public static void writeToFile(final String path, final String content) throws IOException {
        final File file = new File(path);
        final RandomAccessFile access = new RandomAccessFile(file, "rws");

        try {
            access.writeBytes(content);
        } finally {
            access.close();
        }
    }

    /**
     * Convenience function to delete a directory.
     * 
     * @param path the path to delete
     * @throws IOException If the operation fails.
     */
    public static void deleteDirectory(File path) throws IOException {
        if (!path.exists())
            return;

        final File[] files = path.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory())
                    deleteDirectory(file);
                else if (!file.delete()) {
                    throw new IOException(String.format("Unable to delete file '%s'", file));
                }
            }
        }

        if (!path.delete())
            throw new IOException(String.format("Unable to delete directory '%s'", path));
    }


    /**
     * Creates absolute path from relative path.
     * 
     * @param parentPath absolute root path (directory), should not be empty or nor
     *            <code>null</code>
     * @param relativePath relative path (directory or file), should not be empty nor
     *            <code>null</code>
     * @return absolute path if <code>parentPath</code> is absolute; returns
     *         <code>null</code> if the
     */
    public static String getAbsolutePath(final String parentPath, final String relativePath) {
        if (parentPath == null || relativePath == null)
            return null;

        if (parentPath.isEmpty() || relativePath.isEmpty()) {
            return null;
        }

        final File parentFile = new File(FilenameUtil.normalize(parentPath));
        final File relativeFile = new File(FilenameUtil.normalize(relativePath));
        File dir = null;

        if (parentFile.isAbsolute() && (!relativeFile.isAbsolute())) {
            if (parentFile.isDirectory()) {
                dir = parentFile;
            } else {
                dir = parentFile.getParentFile();
            }

            final File absoluteFile = new File(dir, relativeFile.getPath());
            return FilenameUtil.normalize(absoluteFile.getAbsolutePath());
        }
        return relativePath;
    }

    /**
     * Creates relative path from absolute from a pair of paths.
     * <p>
     * Resolve relative pathnames against parent directory.
     * 
     * @param parentPath parent directory path.
     * @param absolutePath absolute file path (directory or file).
     * @return relative path if parent directory is included in absolutePath, otherwise
     *         return absolute path.
     */
    public static String getRelativePath(final String parentPath, final String absolutePath) {
        if (parentPath == null || absolutePath == null
                || (parentPath.length() > absolutePath.length())) {
            return null;
        }

        String relativePath = null;
        final File absoluteFile = new File(absolutePath);
        if (absoluteFile.isAbsolute()) {
            final File parentFile = new File(parentPath);

            // parentFile must be absolute
            if (parentFile.isAbsolute()) {
                final String dirPath = FilenameUtil.normalize(parentFile.getAbsolutePath());

                final String normalizedAbsolutePath = FilenameUtil.normalize(absolutePath);
                int dirLength = dirPath.length();

                if (normalizedAbsolutePath.substring(0, dirLength).equalsIgnoreCase(dirPath)) {
                    // Cut parent path
                    relativePath = normalizedAbsolutePath.substring(dirLength);
                    if (relativePath.startsWith("/")) {
                        relativePath = relativePath.substring(1);
                    }
                }
            } else {
                relativePath = FilenameUtil.normalize(absoluteFile.getPath());
            }
        } else {
            relativePath = FilenameUtil.normalize(absoluteFile.getPath());
        }

        return relativePath;
    }

    /**
     * Copies data from one stream to another, until the input stream ends. Uses an
     * internal 16K buffer.
     * 
     * @param in Stream to read from
     * @param out Stream to write to
     * @return The number of bytes copied
     * @throws IOException if there is some problem reading or writing
     */
    public static long copyStream(InputStream in, OutputStream out) throws IOException {
        long total = 0;
        int len;
        byte[] buffer = new byte[DEFAULT_READ_BUFFER_SIZE];

        while ((len = in.read(buffer)) >= 0) {
            out.write(buffer, 0, len);
            total += len;
        }

        return total;
    }

    /**
     * Convenience function to delete a file.
     * 
     * @param path Path to delete
     * @throws IOException If the operation fails.
     */
    public static void deleteFile(String path) throws IOException {
        if (!new File(path).delete())
            throw new IOException(String.format("Unable to delete file '%s'", path));
    }

    /**
     * Convenience function to copy a file.
     * <p>
     * If the destination file exists it is overwritten.
     * 
     * @param origPath Original path
     * @param destPath Destination path
     * @throws IOException If the operation fails during the data copy phase or,
     *             {@link FileNotFoundException} if either file exists but is a directory
     *             rather than a regular file, does not exist but cannot be read/created,
     *             or cannot be opened for any other reason.
     */
    public static void copyFile(String origPath, String destPath) throws IOException {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(origPath);
            fos = new FileOutputStream(destPath);
            final FileChannel in = fis.getChannel();
            final FileChannel out = fos.getChannel();
            in.transferTo(0, in.size(), out);
        } finally {
            if (fis != null)
                fis.close();
            if (fos != null)
                fos.close();
        }
    }

    /**
     * Returns the specified resource as a stream. Useful to read files from inside the
     * jar.
     * 
     * @param resourcePath resource path
     * @return resource input stream
     */
    public static InputStream getResourceAsStream(final String resourcePath) {
        // todo: Double check this code.

        /*
         * Forces static initialization
         */
        final FileUtil placebo = new FileUtil();
        final ClassLoader cl = placebo.getClass().getClassLoader();

        if (cl == null) {
            // A system class.
            return ClassLoader.getSystemResourceAsStream(resourcePath);
        }
        return cl.getResourceAsStream(resourcePath);
    }

    /**
     * Gets the size of all the files in a given path.
     * <p>
     * If the path is a directory the size is the sum of the size of all the files in the
     * directory and sub-directories. If the path is a file, the size is the size of the
     * file.
     * </p>
     * 
     * @param path path
     * @return path size in bytes
     */
    public static long getSize(final File path) {
        long size = 0;
        if (path.isFile()) {
            size = path.length();
        } else {
            final File[] files = path.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        size += file.length();
                    } else {
                        size += getSize(file);
                    }
                }
            }
        }

        return size;
    }

    /**
     * Gets the size of the path in kbytes.
     * <p>
     * If the path is a directory the size is the sum of the size of all the files in the
     * directory and sub-directories. If the path is a file, the size is the size of the
     * file.
     * </p>
     * 
     * @param path path
     * @return size in megabytes
     */
    public static double getSizeInKbytes(final File path) {
        return getSize(path) / ONE_KB_BYTES;
    }

    /**
     * Gets the size of the path in megabytes.
     * <p>
     * If the path is a directory the size is the sum of the size of all the files in the
     * directory and sub-directories. If the path is a file, the size is the size of the
     * file.
     * </p>
     * 
     * @param path path
     * @return size in megabytes
     */
    public static double getSizeInMegabytes(final File path) {
        return getSize(path) / (ONE_KB_BYTES * ONE_KB_BYTES);
    }

    /**
     * Reads the contents of a text file into a String.
     * <p>
     * Notes: - Assumes file uses the system default encoding - Newlines are converted to
     * the system default
     * 
     * @param path File to read
     * @return File contents
     * @throws IOException in case of failure
     */
    public static String readTextFile(String path) throws IOException {
        StringBuilder contents = new StringBuilder();

        final Reader reader =
            new InputStreamReader(new FileInputStream(path), StringUtil.DEFAULT_STRING_ENCODING);
        final BufferedReader input = new BufferedReader(reader);


        final String lineSep = StringUtil.NL;
        try {
            String line;
            while ((line = input.readLine()) != null) {
                contents.append(line);
                contents.append(lineSep);
            }
        } finally {
            input.close();
        }

        return contents.toString();
    }

    /**
     * Convenience function to rename (move) a file.
     * <p>
     * The rename works even if the destination file exists, however the operation is not
     * atomic on Windows, which means that it's possible for the rename to fail in such a
     * way that the destination file is deleted but the original is not renamed.
     * 
     * @param origPath Original path
     * @param destPath Destination path
     * @throws IOException If the operation fails.
     */
    public static void renameFile(String origPath, String destPath) throws IOException {
        File orig = new File(origPath);
        File dest = new File(destPath);

        // On windows, we must manually delete the destination file if it exists
        if (SysUtil.getOperatingSystem() == SysUtil.OS.WINDOWS && dest.exists()) {
            if (!dest.delete()) {
                throw new IOException(String.format(
                        "Target file '%1$s' is in the way and cannot be deleted", orig.toString()));
            }
        }

        if (!orig.renameTo(dest))
            throw new IOException(String.format("Unable to rename file from '%1$s' to '%2$s'",
                    orig.toString(), dest.toString()));
    }

    /**
     * Writes the contents of a String to a file.
     * <p>
     * Notes: file will have the system default encoding
     * 
     * @param path File to write
     * @param text File contents
     * @throws IOException in case of failure
     */
    public static void writeTextFile(String path, String text) throws IOException {
        final Writer writer =
            new OutputStreamWriter(new FileOutputStream(path), StringUtil.DEFAULT_STRING_ENCODING);
        
        writer.write(text);
        writer.close();
    }
}
