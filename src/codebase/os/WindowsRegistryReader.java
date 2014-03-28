package codebase.os;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

/**
 * Registry Reader. http://www.easydesksoftware.com/regtrick.htm
 * http://www.rgagnon.com/javadetails/java-0480.html
 */
public final class WindowsRegistryReader {

	/**
	 * Registry query.
	 */
	private static final String REGQUERY_UTIL = "reg query ";

	/**
	 * String value.
	 */
	private static final String REGSTR_TOKEN = "REG_SZ";

	/**
	 * DWord value.
	 */
	@SuppressWarnings("unused")
	private static final String REGDWORD_TOKEN = "REG_DWORD";

	/** Prevent instantiation. */
	private WindowsRegistryReader() {
	}

	/**
	 * My Documents folder key.
	 */
	public static final String PERSONAL_FOLDER_CMD = REGQUERY_UTIL
			+ "\"HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\"
			+ "Explorer\\Shell Folders\" /v Personal";

	/**
	 * Desktop folder key.
	 */
	public static final String USER_DESKTOP_FOLDER_CMD = REGQUERY_UTIL
			+ "\"HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\"
			+ "Explorer\\Shell Folders\" /v Desktop";

	/**
	 * Returns the Windows paths.
	 * 
	 * @param folderCmd
	 *            folder command
	 * @return path or null
	 */
	static String getFolderPath(final String folderCmd) {
		try {
			Process process = Runtime.getRuntime().exec(folderCmd);
			StreamReader reader = new StreamReader(process.getInputStream());

			reader.start();
			process.waitFor();
			reader.join();

			String result = reader.getResult();
			int p = result.indexOf(REGSTR_TOKEN);
			if (p == -1)
				return null;

			return result.substring(p + REGSTR_TOKEN.length()).trim();
		} catch (Exception e) {
			return null;
		}
	}

	private static class StreamReader extends Thread {
		private InputStream inputStream;
		private StringWriter stringWritter;

		StreamReader(InputStream is) {
			this.inputStream = is;
			stringWritter = new StringWriter();
		}

		public void run() {
			try {
				int c;
				while ((c = inputStream.read()) != -1)
					stringWritter.write(c);
			} catch (IOException e) {
				/* just return what we read so far */
			}
		}

		String getResult() {
			return stringWritter.toString();
		}
	}

}
