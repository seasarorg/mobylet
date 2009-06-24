package org.mobylet.core.util;

import java.io.File;

public class PathUtils {

	public static final String KEYWORD_STARTS_CLIMB = ".." + File.separator;

	public static final String KEYWORD_MIDDLE_CLIMB = File.separator + KEYWORD_STARTS_CLIMB;


	public static boolean isNetworkPath(String path) {
		if (StringUtils.isEmpty(path)) {
			return false;
		}
		if (path.startsWith("http:") ||
				path.startsWith("https:") ||
				path.startsWith("ftp:") ||
				path.startsWith("smtp:") ||
				path.startsWith("pop:")) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isClimbPath(String path) {
		if (StringUtils.isEmpty(path)) {
			return false;
		}
		return path.startsWith(KEYWORD_STARTS_CLIMB) ||
				path.indexOf(KEYWORD_MIDDLE_CLIMB) > 0;
	}
}
