/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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
package org.mobylet.charset.codemap;

import org.mobylet.charset.codemap.sjis.MobyletSjisDecodeSc1DB;
import org.mobylet.charset.codemap.sjis.MobyletSjisDecodeSc2DB;
import org.mobylet.charset.codemap.sjis.MobyletSjisEncodeSc0DB;
import org.mobylet.charset.codemap.sjis.MobyletSjisEncodeSc1DB;
import org.mobylet.charset.codemap.sjis.MobyletSjisEncodeSc2DB;
import org.mobylet.charset.codemap.sjis.MobyletSjisEncodeSc3DB;
import org.mobylet.charset.codemap.sjis.MobyletSjisEncodeSc4DB;
import org.mobylet.charset.codemap.sjis.MobyletSjisEncodeSc5DB;

public class MobyletSjisCodeMap {

	public static int toByte(int c) {
		if (0xA2 <= c && c <= 0x451) {
			return MobyletSjisEncodeSc0DB.toByte(c);
		} else if (0x2010 <= c && c <= 0x4FFF) {
			return MobyletSjisEncodeSc1DB.toByte(c);
		} else if (0x5005 <= c && c <= 0x9FA0) {
			return MobyletSjisEncodeSc2DB.toByte(c);
		} else if (0xE000 <= c && c <= 0xE757) {
			return MobyletSjisEncodeSc3DB.toByte(c);
		} else if (0xF929 <= c && c <= 0xFA2D) {
			return MobyletSjisEncodeSc4DB.toByte(c);
		} else if (0xFF01 <= c && c <= 0xFFE5) {
			return MobyletSjisEncodeSc5DB.toByte(c);
		} else {
			return 0x3F3F;
		}
	}

	public static char toChar(int b) {
		if (0x8149 <= b && b <= 0x9FFC) {
			return MobyletSjisDecodeSc1DB.toChar(b);
		} else if (0xE040 <= b && b <= 0xFC4B) {
			return MobyletSjisDecodeSc2DB.toChar(b);
		} else {
			return 0xFFFD;
		}
	}

}
