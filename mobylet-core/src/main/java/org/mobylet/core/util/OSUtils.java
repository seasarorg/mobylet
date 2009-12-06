package org.mobylet.core.util;


public class OSUtils {

	private static Boolean IS_WINDOWS = null;

	public static boolean isWindows() {
		if (IS_WINDOWS == null) {
			String osName = System.getProperty("os.name");
			if (StringUtils.isNotEmpty(osName)) {
				IS_WINDOWS = osName.startsWith("Windows");
			} else {
				IS_WINDOWS = false;
			}
		}
		return IS_WINDOWS.booleanValue();
	}
}
