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
package org.mobylet.charset;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;

import org.mobylet.charset.codemap.MobyletSjisCodeMap;

public abstract class MobyletDecoder extends CharsetDecoder {

	protected MobyletDecoder(Charset charset) {
		super(charset, 0.5F, 1.0F);
	}

	protected char decodeStandardDouble(int b1, int b2) {
		return MobyletSjisCodeMap.toChar((b1 << 8) + b2);
	}

	protected abstract char decodeEmojiDouble(int b1, int b2);

	protected abstract short getMinEmojiFirstCode();

	protected abstract short getMaxEmojiFirstCode();

	@Override
	protected CoderResult decodeLoop(ByteBuffer in, CharBuffer out) {
		int pos = in.position();
		try {
			while (in.hasRemaining()) {
				if (out.remaining() == 0) {
					return CoderResult.OVERFLOW;
				}
				int b1 = in.get() & 0xFF;
				//1byte
				if (b1 <= 0x80) {
					out.put((char)b1);
					pos++;
				//1byte-kana
				} else if (0xA0 <= b1 && b1 <= 0xDF) {
					int kana = (b1 | 0xFF00) - 0x40;
					if (b1 == 0xA0) {
						kana = 0xF8F0;
					}
					out.put((char)kana);
					pos++;
				//emoji
				} else if (getMinEmojiFirstCode() <= b1 && b1 <= getMaxEmojiFirstCode()) {
					if (in.hasRemaining()) {
						int b2 = in.get() & 0xFF;
						char c = decodeEmojiDouble(b1, b2);
						if (c != 0xFFFD) {
							out.put(c);
							pos += 2;
						} else {
							out.put(decodeStandardDouble(b1, b2));
							pos++;
							in.position(in.position() - 1);
						}
					}
				//2bytes
				} else {
					if (in.hasRemaining()) {
						int b2 = in.get() & 0xFF;
						out.put(decodeStandardDouble(b1, b2));
						pos += 2;
					}
				}
			}
		} finally {
			in.position(pos);
		}
		return CoderResult.UNDERFLOW;
	}

}
