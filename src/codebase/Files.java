/*
 * Created on 3/Jun/2005
 */
package codebase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import javax.swing.filechooser.FileSystemView;

/**
 * Utility operations for files.
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
     * @return the volume name associated with the device or null if no name is
     *         associated with the device
     */
    public static String getVolumeName(final String devicePath) {
        if (devicePath == null) {
            throw new IllegalArgumentException(
                "The volume name should be assigned");
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
     * This method effectively tries to create a file with the specified size.
     * Then it tries to enlarge the file. In the end the file is removed.
     * 
     * @param path the file path
     * @param length the size of the file in bytes
     * @return <code>true</code> if the file can be created,
     *         <code>false</code> if the file already exists or if the file
     *         creation fails, for example, because the file specified is a
     *         directory.
     */
    public static boolean verifyCanCreateFile(final String path,
            final long length) {
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
            file.delete();
            return true;
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
     * If the file does not exits it is created. Otherwise, if it already exists
     * it overwritten.
     * 
     * @param path the path pf the file
     * @param content the content to write
     * @throws IOException if wrtting to the file fails
     */
    public static void writeToFile(final String path, final String content)
            throws IOException {
        final File file = new File(path);
        final RandomAccessFile access;
        access = new RandomAccessFile(file, "rws");
        
        try {
            access.writeBytes(content);
        } finally {
            access.close();
        }
    }
      
    /**
     * Deletes a directory and all its sub-directories and files recursively.
     * 
     * @param dir the directory to be deleted
     * @throws IllegalStateException if a sub-directory could not be deleted
     */
    public static void deltree(final File dir) {
        if (dir.exists()) {
            if (dir.isDirectory()) {
                String[] list = dir.list();
                for (int i = 0; i < list.length; i++) {
                    deltree(new File(dir, list[i]));
                }
            }
            if (!dir.delete()) {
                throw new IllegalStateException("Could not delete " + dir);
            }
        }
    }

    /**
     * Creates absolute path from relative.
     * 
     * @param parentPath root path (directory).
     * @param relativePath relative path (directory or file).
     * @return absolute path if <code>parentPath</code> is not
     *         <code>null</code> and it is absolute.
     */
    public static String makeAbsolutePath(String parentPath, final String relativePath) {
        String absolutePath = relativePath;
        
        if (parentPath != null) {
            parentPath = parentPath.replace('\\', '/');
            
            final File parentFile = new File(parentPath);
            final File relativeFile = new File(relativePath);
            File dir = null;
            // Must create dirs if don't exist
            parentFile.mkdirs();
            if (parentFile.isAbsolute() && (!relativeFile.isAbsolute())) {
                // Check dir
                if (parentFile.isDirectory()) {
                    dir = parentFile;
                } else {
                    dir = parentFile.getParentFile();
                }
                final File absoluteFile = new File(dir, relativeFile.getPath());
                absolutePath = absoluteFile.getAbsolutePath();
                absolutePath = absolutePath.replace('\\', '/');
            }
        }
        return absolutePath;
    }

    /**
     * Creates relative path from absolute. Resolve relative pathnames against
     * parent directory.
     * 
     * @param parentPath parent directory path.
     * @param absolutePath absolute file path (directory or file).
     * @return relative path if parent directory is included in absolutePath,
     *         otherwise return absolute path.
     */
    public static String makeRelativePath(final String parentPath,
             String absolutePath) {
        
        String relativePath = null;
        
        if (parentPath != null && absolutePath != null
                && (parentPath.length() < absolutePath.length())) {
            final File absoluteFile = new File(absolutePath);
            if (absoluteFile.isAbsolute()) {
                File parentFile = new File(parentPath);
                // parentFile must be absolute
                if (parentFile.isAbsolute()) {
                    final String dirPath = parentFile.getAbsolutePath().replace('\\',
                        '/');
                    absolutePath = absolutePath.replace('\\', '/');
                    int dirLength = dirPath.length();
                    if (absolutePath.substring(0, dirLength).equalsIgnoreCase(
                        dirPath)) {
                        // Cut parent path
                        relativePath = absolutePath.substring(dirLength);
                        if (relativePath.startsWith("/")) {
                            relativePath = relativePath.substring(1);
                        }
                    }
                } else {
                    relativePath = absoluteFile.getPath().replace('\\', '/');
                }
            } else {
                relativePath = absoluteFile.getPath().replace('\\', '/');
            }
        } else {
            if (absolutePath != null) {
                relativePath = absolutePath.replace('\\', '/');
            }
            
        }
        return relativePath;
    }
}
