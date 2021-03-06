package codebase.os;

import java.io.File;

import javax.swing.filechooser.FileSystemView;

/**
 * Operating system related utilities.
 */
public final class SysUtil {
    /**
     * Prevent the instantiation of this utility class.
     */
    private SysUtil() {
    }

    /**
     * Known operating systems.
     */
    public enum OS {
        /**
         * Some OS we cannot identify, maybe it will run there.
         */
        UNKNOWN,
        /**
         * Regular MS Windows.
         */
        WINDOWS,
        /**
         * Linux.
         */
        LINUX,
        /**
         * MAC OS X.
         */
        MAC_OS,
    }

    private static final OS DETECTED_OPERATING_SYSTEM;

    static {
        String osname = System.getProperty("os.name").toLowerCase();
        if (osname.startsWith("windows"))
            DETECTED_OPERATING_SYSTEM = OS.WINDOWS;
        else if (osname.startsWith("linux"))
            DETECTED_OPERATING_SYSTEM = OS.LINUX;
        else if (osname.startsWith("mac os"))
            DETECTED_OPERATING_SYSTEM = OS.MAC_OS;
        else
            DETECTED_OPERATING_SYSTEM = OS.UNKNOWN;
    }

    /**
     * @return the operating system we are running on.
     */
    public static OS getOperatingSystem() {
        return DETECTED_OPERATING_SYSTEM;
    }

    /**
     * @return the name of the first COM (serial) port, or "???" if it can't be determined
     */
    public static String getFirstCOMPort() {
        switch (getOperatingSystem()) {
            case WINDOWS:
                return "COM1";
            case LINUX:
                return "/dev/ttyS0";
            case MAC_OS:
                return "/dev/ttys0";
            default:
                return "???";
        }
    }

    /**
     * Returns the temporary directory path.
     * 
     * @return path
     */
    public static String getTempPath() {
        return System.getProperty("java.io.tmpdir");
    }

    /**
     * Returns the desktop folder path.<br/>
     * If the desktop folder path is not available, the user home will be returned.
     * 
     * @return path
     */
    public static String getDesktopPath() {
        FileSystemView filesys = FileSystemView.getFileSystemView();
        String result = filesys.getHomeDirectory().getAbsolutePath();

        if (getOperatingSystem() != OS.WINDOWS) {
            File desktopDir = new File(result + File.separator + "Desktop");
            if (desktopDir.exists()) {
                result = desktopDir.getAbsolutePath();
            }
        }

        return result;
    }

    /**
     * Returns the application install path.
     * <p>
     * WARNING: This information is obtained indirectly by finding the location of the the
     * .class file for this class, and working back from there. This means that the
     * function is not generic, and may need to be changed if the directory structure used
     * by the application changes.
     * 
     * @return Path to the application install directory.
     */
    public static String getApplicationPath() {
        File path =
            new File(SysUtil.class.getProtectionDomain().getCodeSource().getLocation().getPath());

        if (path.getName().endsWith(".jar")) {
            /*
             * When running from the install, strip out the jar file and "plugins"
             * directory from the path
             */
            path = path.getParentFile().getParentFile();
        } else if (path.getName().equals("bin")) {
            /*
             * When running from the IDE, strip the "bin" directory from the path
             */
            path = path.getParentFile();
        }

        return path.toString();
    }
}
