package codebase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import javax.swing.filechooser.FileSystemView;

/**
 * Utility class for generic operations on files.
 * 
 * @since 3/Jun/2005
 */
public final class Files {

    /**
     * Prevent instantiations of this class.
     */
    private Files() {
    }

    /**
     * Gets the volume name.
     * 
     * @param devicePath the path of the de device e.g. <code>C:</code>
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
     * Checks if a file with a specified size can be created on a specified path
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
                access.close();
            } catch (IOException e) {
                return false;
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
     * Recursively deletes a directory and all its sub-directories.
     * 
     * @param dir the directory to be deleted
     * @throws IllegalStateException if a sub-directory could not be deleted
     */
    public static void delTree(final File dir) {
        if (dir.exists()) {
            if (dir.isDirectory()) {
                final String[] list = dir.list();
                for (int i = 0; i < list.length; i++) {
                    delTree(new File(dir, list[i]));
                }
            }
            if (!dir.delete()) {
                throw new IllegalStateException("Could not delete " + dir);
            }
        }
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

        final File parentFile = new File(Filenames.normalize(parentPath));
        final File relativeFile = new File(Filenames.normalize(relativePath));
        File dir = null;

        if (parentFile.isAbsolute() && (!relativeFile.isAbsolute())) {
            if (parentFile.isDirectory()) {
                dir = parentFile;
            } else {
                dir = parentFile.getParentFile();
            }

            final File absoluteFile = new File(dir, relativeFile.getPath());
            return Filenames.normalize(absoluteFile.getAbsolutePath());
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
                final String dirPath = Filenames.normalize(parentFile.getAbsolutePath());

                final String normalizedAbsolutePath = Filenames.normalize(absolutePath);
                int dirLength = dirPath.length();

                if (normalizedAbsolutePath.substring(0, dirLength).equalsIgnoreCase(dirPath)) {
                    // Cut parent path
                    relativePath = normalizedAbsolutePath.substring(dirLength);
                    if (relativePath.startsWith("/")) {
                        relativePath = relativePath.substring(1);
                    }
                }
            } else {
                relativePath = Filenames.normalize(absoluteFile.getPath());
            }
        } else {
            relativePath = Filenames.normalize(absoluteFile.getPath());
        }
        
        return relativePath;
    }
}
