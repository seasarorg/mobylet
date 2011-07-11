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

import java.nio.charset.Charset;

public class UrlDecoder {

	private static final String HEX = "0123456789abcdefABCDEF";


	public static String decode(String s, Charset charset) {
		boolean needToChange = false;
		int numChars = s.length();
		StringBuilder buf =
			new StringBuilder(numChars > 500 ? numChars / 2 : numChars);
		int i = 0;
		char c;
		byte[] bytes = null;
		while (i < numChars) {
			c = s.charAt(i);
			switch (c) {
			case '+':
				buf.append(' ');
				i++;
				needToChange = true;
				break;
			case '%':
				try {
					if (bytes == null)
						bytes = new byte[(numChars-i)/2];
					int pos = 0;
					while (c=='%' &&
							(i+1) < numChars) {
						if (HEX.indexOf(s.charAt(i+1)) >= 0) {
							int index = 2;
							if ((i+2) < numChars &&
									HEX.indexOf(s.charAt(i+2)) >= 0) {
								index = 3;
							}
							bytes[pos] =
								(byte)Integer.parseInt(s.substring(i+1,i+index),16);
							pos++;
							i+= index;
							if (i < numChars) {
								c = s.charAt(i);
								if (c!='%') {
									if (c=='+') {
										c = ' ';
									}
									bytes[pos] = (byte)c;
									pos++;
									i++;
									if (i < numChars) {
										c = s.charAt(i);
									}
								}
							}
						} else {
							//IllegalPattern('%' only)
							bytes[pos] = (byte)c;
							pos++;
							i++;
							if (i < numChars) {
								c = s.charAt(i);
							}
						}
					}
					if ((i < numChars) && (c=='%')) {
						throw new IllegalArgumentException(
						"URLDecoder: Incomplete trailing escape (%) pattern i = " + i + "[" + s + "]");
					}
					buf.append(new String(bytes, 0, pos, charset));
				} catch (NumberFormatException e) {
					throw new IllegalArgumentException(
							"URLDecoder: Illegal hex characters in escape (%) pattern - "
							+ e.getMessage());
				}
				needToChange = true;
				break;
			default:
				buf.append(c);
				i++;
				break;
			}
		}
		return (needToChange? buf.toString() : s);
	}
}