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

import java.io.CharArrayWriter;
import java.nio.charset.Charset;
import java.util.BitSet;


public class UrlEncoder {

	static BitSet dontNeedEncoding;
	static final int caseDiff = ('a' - 'A');

	static {
		dontNeedEncoding = new BitSet(256);
		int i;
		for (i = 'a'; i <= 'z'; i++) {
			dontNeedEncoding.set(i);
		}
		for (i = 'A'; i <= 'Z'; i++) {
			dontNeedEncoding.set(i);
		}
		for (i = '0'; i <= '9'; i++) {
			dontNeedEncoding.set(i);
		}
		dontNeedEncoding.set(' ');
		dontNeedEncoding.set('-');
		dontNeedEncoding.set('_');
		dontNeedEncoding.set('.');
		dontNeedEncoding.set('*');
	}

	public static String encode(String s, Charset charset) {
		boolean needToChange = false;
		StringBuilder buf = new StringBuilder(s.length());
		CharArrayWriter charArrayWriter = new CharArrayWriter();
		for (int i = 0; i < s.length();) {
			int c = (int) s.charAt(i);
			if (dontNeedEncoding.get(c)) {
				if (c == ' ') {
					c = '+';
					needToChange = true;
				}
				buf.append((char)c);
				i++;
			} else {
				do {
					charArrayWriter.write(c);
					if (c >= 0xD800 && c <= 0xDBFF) {
						if ( (i+1) < s.length()) {
							int d = (int) s.charAt(i+1);
							if (d >= 0xDC00 && d <= 0xDFFF) {
								charArrayWriter.write(d);
								i++;
							}
						}
					}
					i++;
				} while (i < s.length() && !dontNeedEncoding.get((c = (int) s.charAt(i))));
				charArrayWriter.flush();
				String str = new String(charArrayWriter.toCharArray());
				byte[] ba = str.getBytes(charset);
				for (int j = 0; j < ba.length; j++) {
					buf.append('%');
					char ch = Character.forDigit((ba[j] >> 4) & 0xF, 16);
					if (Character.isLetter(ch)) {
						ch -= caseDiff;
					}
					buf.append(ch);
					ch = Character.forDigit(ba[j] & 0xF, 16);
					if (Character.isLetter(ch)) {
						ch -= caseDiff;
					}
					buf.append(ch);
				}
				charArrayWriter.reset();
				needToChange = true;
			}
		}
		return (needToChange? buf.toString() : s);
	}

}
