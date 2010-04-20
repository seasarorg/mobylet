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

import java.nio.CharBuffer;

public class StringUtils {

	public static boolean isEmpty(String s) {
		return s == null || s.length() == 0;
	}

	public static boolean isNotEmpty(String s) {
		return !isEmpty(s);
	}

	public static boolean equals(String s1, String s2) {
		return s1 == null ? s2 == null : s1.equals(s2);
	}

	public static String escape(String src) {
		if (isNotEmpty(src)) {
			return src.replaceAll("\\\\", "\\\\\\\\");
		}
		return src;
	}

	public static String toHan(String src) {
		if (StringUtils.isEmpty(src)) {
			return src;
		}
		StringBuilder sBuf = new StringBuilder();
		CharBuffer cBuf = CharBuffer.allocate(src.length()).append(src);
		cBuf.position(0);
		while(cBuf.hasRemaining()) {
			char ch = cBuf.get();
			if (0xFF01 <= ch && ch <= 0xFF5d) {	//alphabet
				if(0xFF1C == ch || 0xFF1E == ch) {	//＜＞
					sBuf.append(ch);
				} else {
					sBuf.append((char)(ch - 0xFEE0));
				}
			}
			//special
			else if(0xFFE5 == ch) sBuf.append((char)0x005C);	//full-backslash
			else if (0x30FC == ch ||
					0x2010 == ch ||
					0x2015 == ch) sBuf.append((char)0x002D);	//full-hyphen
			else if(0x309B == ch) sBuf.append((char)0xFF9E);	//「゛」
			else if(0x309C == ch) sBuf.append((char)0xFF9F);	//「゜」
			else if(0x30FB == ch) sBuf.append((char)0xFF65);	//「・」
			//kana
			else if (0x30A1 <= ch && ch <= 0x30F6) {
				//ア行
				if (ch <= 0x30AA) {
					//大文字
					if ((ch & 0x01) == 0) {
						sBuf.append((char)((ch / 2) + 0xE720));
					//小文字
					} else {
						sBuf.append((char)((ch / 2) + 0xE717));
					}
				//カ行、サ行、タ行（ヂまで）
				} else if (ch <= 0x30C2) {
					sBuf.append((char)(((ch - 1) / 2) + 0xE721));
					//濁点
					if ((ch & 0x01) == 0) {
						sBuf.append((char)0xFF9E);
					}
				//ッ（小文字）
				} else if (ch == 0x30C3) {
					sBuf.append((char)0xFF6F);
				//タ行（ツ～ド）
				} else if (ch <= 0x30C9) {
					sBuf.append((char)((ch / 2) + 0xE720));
					//濁点
					if ((ch & 0x01) == 1) {
						sBuf.append((char)0xFF9E);
					}
				//ナ行
				} else if (ch <= 0x30CE) {
					sBuf.append((char)(ch + 0xCEBB));
				//ハ行
				} else if (ch <= 0x30DD) {
					switch (ch % 3) {
					case 0:
						sBuf.append((char)((ch / 3) + 0xEF45));
						break;
					case 1:
						sBuf.append((char)((ch / 3) + 0xEF45));
						sBuf.append((char)0xFF9E);
						break;
					case 2:
						sBuf.append((char)((ch / 3) + 0xEF45));
						sBuf.append((char)0xFF9F);
						break;
					}
				//マ行
				} else if (ch <= 0x30E2) {
					sBuf.append((char)(ch + 0xCEB1));
				//ヤ行
				} else if (ch <= 0x30E8) {
					//大文字
					if ((ch & 0x01) == 0) {
						sBuf.append((char)((ch / 2) + 0xE722));
					//小文字
					} else {
						sBuf.append((char)((ch / 2) + 0xE6FB));
					}
				//ラ行
				} else if (ch <= 0x30ED) { // 0x30E2→0x30EDに修正
					sBuf.append((char)(ch + 0xCEAE));
				//残り
				} else {
					switch (ch) {
					//小文字の「ヮ」
					case 0x30EE:
					//大文字の「ワ」
					case 0x30EF:
						sBuf.append((char)0xFF9C);
						break;
					//ヰ
					case 0x30F0:
						sBuf.append((char)0xFF72);
						break;
					//ヱ
					case 0x30F1:
						sBuf.append((char)0xFF74);
						break;
					//ヲ
					case 0x30F2:
						sBuf.append((char)0xFF66);
						break;
					//ン
					case 0x30F3:
						sBuf.append((char)0xFF9D);
						break;
					//ヴ
					case 0x30F4:
						sBuf.append((char)0xFF73);
						sBuf.append((char)0xFF9E);
						break;
					//ヵ
					case 0x30F5:
						sBuf.append((char)0xFF76);
						break;
					//ヶ
					case 0x30F6:
						sBuf.append((char)0xFF79);
						break;
					}
				}
			} else {
				sBuf.append(ch);
			}
		}
		return sBuf.toString();
	}
}
