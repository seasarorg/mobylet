/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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

import java.io.ByteArrayOutputStream;

public class Base64Utils {

	public static final char[] c =
		"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
		.toCharArray();

	public static String encode(byte[] data) {
		StringBuilder buf = new StringBuilder(data.length * 3 / 2);
		int end = data.length - 3;
		int i = 0;
		int breakNumber = 0;
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
			if (breakNumber++ >= 14) {
				breakNumber = 0;
				buf.append("\r\n");
			}
		}
		if (i == end + 1) {
			int d =
				((((int)data[i]) & 0xFF) << 16)
				| ((((int)data[i + 1]) & 0xFF) << 8);
			buf.append(c[(d >> 18) & 0x3F]);
			buf.append(c[(d >> 12) & 0x3F]);
			buf.append(c[(d >> 6) & 0x3F]);
			buf.append("=");
		}
		else if (i == end + 2) {
			int d = (((int)data[i]) & 0xFF) << 16;
			buf.append(c[(d >> 18) & 0x3F]);
			buf.append(c[(d >> 12) & 0x3F]);
			buf.append("==");
		}
		return buf.toString();
	}

	public static byte[] decode(String base64String) {
		if (StringUtils.isEmpty(base64String)) {
			return new byte[0];
		}
		int i = 0;
		String s = base64String.replaceAll("\\s", "");
		int len = s.length();
		ByteArrayOutputStream os =
			new ByteArrayOutputStream(s.length() * 2 / 3 + 1);
		while (true) {
			if (i == len) {
				break;
			}
			int tri =
				(decode(s.charAt(i)) << 18)
				+ (decode(s.charAt(i + 1)) << 12)
				+ (decode(s.charAt(i + 2)) << 6)
				+ (decode(s.charAt(i + 3)));
			os.write((tri >> 16) & 0xFF);
			if (s.charAt(i + 2) == '=')
				break;
			os.write((tri >> 8) & 0xFF);
			if (s.charAt(i + 3) == '=')
				break;
			os.write(tri & 0xFF);
			i += 4;
		}
		return os.toByteArray();
	}

	private static int decode(char c) {
		if (c >= 'A' && c <= 'Z') {
			return ((int)c) - 65;
		} else if (c >= 'a' && c <= 'z') {
			return ((int)c) - 97 + 26;
		} else if (c >= '0' && c <= '9') {
			return ((int)c) - 48 + 26 + 26;
		} else {
			switch (c) {
			case '+' :
				return 62;
			case '/' :
				return 63;
			case '=' :
				return 0;
			default :
				throw new RuntimeException(
						"解析不可能 char = " + (int)c);
			}
		}
	}

}