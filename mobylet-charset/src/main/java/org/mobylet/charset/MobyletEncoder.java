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
package org.mobylet.charset;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;

import org.mobylet.charset.codemap.MobyletSjisCodeMap;

public abstract class MobyletEncoder extends CharsetEncoder {

	protected MobyletEncoder(Charset cs) {
		super(cs, 2.0F, 2.0F);
	}

	protected byte encodeSingle(char c) {
		return (byte)c;
	}

	protected byte[] encodeStandard(char c) {
		int bi = MobyletSjisCodeMap.toByte(c);
		if (bi > 0xFF) {
			byte[] b = new byte[2];
			b[0] = (byte)((bi & 0xFF00) >> 8);
			b[1] = (byte)(bi & 0x00FF);
			return b;
		} else {
			return new byte[]{(byte)bi};
		}
	}

	protected abstract byte[] encodeEmojiDouble(char c);

	protected abstract int getMinEmojiCode();

	protected abstract int getMaxEmojiCode();

	@Override
	protected CoderResult encodeLoop(CharBuffer in, ByteBuffer out) {
		int pos = in.position();
		try {
			while (in.hasRemaining()) {
				if (out.remaining() < 2) {
					return CoderResult.OVERFLOW;
				}
				char c = in.get();
				if (c < 0xA2) {
					//ASCII
					out.put(encodeSingle(c));
				} else if (getMinEmojiCode() <= c && c <= getMaxEmojiCode()) {
					//絵文字
					out.put(encodeEmojiDouble(c));
				} else {
					//その他
					out.put(encodeStandard(c));
				}
				pos++;
			}
		} finally {
			in.position(pos);
		}
		return CoderResult.UNDERFLOW;
	}

}
