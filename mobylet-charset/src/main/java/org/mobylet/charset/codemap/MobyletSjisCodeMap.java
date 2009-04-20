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
