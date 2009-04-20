package org.mobylet.charset;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

import org.mobylet.charset.codemap.MobyletDocomoCodeMap;

public class MobyletDocomoCharset extends Charset {


	protected MobyletDocomoCharset() {
        super("x-mobylet-docomo", new String[0]);
	}

	@Override
	public boolean contains(Charset charset) {
		return charset.name().equals("US-ASCII") ||
				(charset instanceof MobyletDocomoCharset);
	}

	@Override
	public CharsetDecoder newDecoder() {
		return new Decoder(this);
	}

	@Override
	public CharsetEncoder newEncoder() {
		return new Encoder(this);
	}


	public class Encoder extends MobyletEncoder {

		protected Encoder(Charset charset) {
			super(charset);
		}

		@Override
		protected byte[] encodeEmojiDouble(char c) {
			int bi = MobyletDocomoCodeMap.toByte(c);
			if (bi > 0xFF) {
				byte[] b = new byte[2];
				b[0] = (byte)((bi & 0xFF00) >> 8);
				b[1] = (byte)(bi & 0x00FF);
				return b;
			} else {
				return new byte[]{(byte)bi};
			}
		}

		@Override
		protected int getMaxEmojiCode() {
			return 0xE757;
		}

		@Override
		protected int getMinEmojiCode() {
			return 0xE63E;
		}

	}


	public class Decoder extends MobyletDecoder {

		protected Decoder(Charset charset) {
			super(charset);
		}

		@Override
		protected char decodeEmojiDouble(int b1, int b2) {
			return MobyletDocomoCodeMap.toChar((b1 << 8) + b2);
		}

		@Override
		protected short getMaxEmojiFirstCode() {
			return 0xF9;
		}

		@Override
		protected short getMinEmojiFirstCode() {
			return 0xF8;
		}

	}

}
