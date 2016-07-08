package codebase.apputils;

import java.io.File;
import java.io.IOException;

/**
 * Manages whether and application or component has had a clean start up or not.
 * <p>
 * This class works by creating a lock file and then deleting the file when shutting down.
 * If the file exists when starting up this indicates that there was a problem with the
 * the last shutdown.
 */
public class CrashDetector {

    /**
     * The name of the lock file used.
     */
    public static final String CRASH_LOCK_FILE_NAME = "runlock.tmp";

    /**
     * The path of the lock file.
     */
    private final String lockFilePath;

    /**
     * Indicates whether that startup method has been called.
     */
    private boolean startupCalled;

    /**
     * Creates a crash detector object given a directory.
     * <p>
     * Note that clients of this class have the responsibility of defining the place where
     * the lock file should be created.
     * 
     * @param directory the directory where the file is to be created.
     */
    public CrashDetector(final String directory) {
        lockFilePath = codebase.FilenameUtil.concat(directory,
                CRASH_LOCK_FILE_NAME);
    }

    /**
     * Checks if the last startup was clean.
     * 
     * @return <code>true</code> if the lock file does not exist and <code>false</code> if
     *         a lock file exists.
     * @throws IllegalStateException if the startup method has already been called.
     * @see #startup()
     */
    public boolean wasClean() {
        if (startupCalled) {
            throw new IllegalStateException(
                    "This method must be called before startup()");
        } else {
            final File f = new File(lockFilePath);
            return !f.exists();
        }
    }

    /**
     * Informs that a new startup has taken place.
     * 
     * @return <code>true</code> if the crash detector was able to create the lock file.
     *         Returns <code>false</code> otherwise.
     */
    public boolean startup() {
        startupCalled = true;
        File f = new File(lockFilePath);
        try {
            return f.createNewFile();
        } catch (IOException e) {
            // A problem here indicates that we should not rely on the
            // lock file
            return false;
        }
    }

    /**
     * Informs that shutdown has taken place.
     * 
     * @return <code>true</code> if the lock file was successfully removed. Returns
     *         <code>false</code> if the lock file could not be removed.
     * @throws IllegalStateException if the startup method was not called yet
     * @see #startup()
     */
    public boolean shutdown() {
        if (!startupCalled) {
            throw new IllegalStateException("startup() should be called first");
        }
        final File f = new File(lockFilePath);
        return f.delete();
    }
}
