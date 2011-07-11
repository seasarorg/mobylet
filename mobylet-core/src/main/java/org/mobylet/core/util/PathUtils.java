/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.mobylet.core.util;

import java.io.File;

public class PathUtils {

	public static final String KEYWORD_STARTS_CLIMB = ".." + File.separator;

	public static final String KEYWORD_MIDDLE_CLIMB = File.separator + KEYWORD_STARTS_CLIMB;

	public static final char[] c =
		"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789()"
		.toCharArray();

	public static String getUniqueFilePath(String path) {
		byte[] data = path.getBytes();
		StringBuilder buf = new StringBuilder(data.length * 3 / 2);
		int end = data.length - 3;
		int i = 0;
		while (i <= end) {
			int d =
				((((int)data[i]) & 0xFF) << 16)
				| ((((int)data[i + 1]) & 0xFF) << 8)
				| (((int)data[i + 2]) & 0xFF);
			buf.append(c[(d >> 18) & 0x3F]);
			buf.append(c[(d >> 12) & 0x3F]);
			buf.append(c[(d >> 6) & 0x3F]);
			buf.append(c[d & 0x3F]);
			i += 3;
		}
		if (i == end + 1) {
			int d =
				((((int)data[i]) & 0xFF) << 16)
				| ((((int)data[i + 1]) & 0xFF) << 8);
			buf.append(c[(d >> 18) & 0x3F]);
			buf.append(c[(d >> 12) & 0x3F]);
			buf.append(c[(d >> 6) & 0x3F]);
			buf.append("_");
		}
		else if (i == end + 2) {
			int d = (((int)data[i]) & 0xFF) << 16;
			buf.append(c[(d >> 18) & 0x3F]);
			buf.append(c[(d >> 12) & 0x3F]);
			buf.append("__");
		}
		return buf.toString();
	}

	public static boolean isNetworkPath(String path) {
		if (StringUtils.isEmpty(path)) {
			return false;
		}
		if (path.startsWith("http:") ||
				path.startsWith("https:")) {
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
