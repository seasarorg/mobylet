package org.mobylet.charset;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

import junit.framework.TestCase;

public class MobyletAuCharsetTest extends TestCase {

	public void test_encodeMatchWindows31J() {
		//## ARRANGE ##
		Charset charset = new MobyletAuCharset();
		ByteBuffer encodeM = charset.encode("あいABCう");
		ByteBuffer encodeW = Charset.forName("windows-31j").encode("あいABCう");

		//## ACT ##
		byte[] bEncodeM = encodeM.array();
		byte[] bEncodeW = encodeW.array();

		//## ASSERT ##
		for (int i=0; i<bEncodeM.length; i++) {
			assertEquals(bEncodeM[i], bEncodeW[i]);
		}
	}

	public void test_encodeMatchWindows31J2() {
		//## ARRANGE ##
		Charset charset = new MobyletAuCharset();
		String testString = "今日、魑魅魍魎として酷い一日ﾃﾞｼﾀ。";
		ByteBuffer encodeM = charset.encode(testString);
		ByteBuffer encodeW = Charset.forName("windows-31j").encode(testString);

		//## ACT ##
		byte[] bEncodeM = encodeM.array();
		byte[] bEncodeW = encodeW.array();

		//## ASSERT ##
		for (int i=0; i<bEncodeM.length; i++) {
			assertEquals(bEncodeM[i], bEncodeW[i]);
		}
	}
	public void test_encodeMatchEmoji() {
		//## ARRANGE ##
		Charset charset = new MobyletAuCharset();
		ByteBuffer encodeM = charset.encode(String.valueOf((char)0xE901));

		//## ACT ##
		byte[] bEncodeM = encodeM.array();

		//## ASSERT ##
		assertEquals(bEncodeM.length, 2);
		assertEquals(bEncodeM[0] & 0xFF, 0xF6);
		assertEquals(bEncodeM[1] & 0xFF, 0x59);
	}

	public void test_decodeMatchWindows31J() {
		//## ARRANGE ##
		Charset charset = new MobyletAuCharset();
		CharBuffer decodeM = charset.decode(
				ByteBuffer.wrap("あいうえお".getBytes(new MobyletDocomoCharset())));
		CharBuffer decodeW = charset.decode(
				ByteBuffer.wrap("あいうえお".getBytes(Charset.forName("windows-31j"))));

		//## ACT ##
		String sDecodeM = new String(decodeM.array());
		String sDecodeW = new String(decodeW.array());

		//## ASSERT ##
		assertEquals(sDecodeM, sDecodeW);
	}

	public void test_decodeMatchWindows31J2() {
		//## ARRANGE ##
		Charset charset = new MobyletAuCharset();
		String testString = "今日は奇奇怪怪＆魑魅魍魎ﾄｼﾃ最悪な一日だったかも？しれません。";
		CharBuffer decodeM = charset.decode(
				ByteBuffer.wrap(testString.getBytes(new MobyletDocomoCharset())));
		CharBuffer decodeW = charset.decode(
				ByteBuffer.wrap(testString.getBytes(Charset.forName("windows-31j"))));

		//## ACT ##
		String sDecodeM = new String(decodeM.array());
		String sDecodeW = new String(decodeW.array());

		//## ASSERT ##
		assertEquals(sDecodeM, sDecodeW);
	}

	public void test_decodeMatchEmoji() {
		//## ARRANGE ##
		Charset charset = new MobyletAuCharset();
		CharBuffer decode = charset.decode(ByteBuffer.wrap(new byte[]{(byte)0xF6, (byte)0x59}));

		//## ACT ##
		char[] cDecode = decode.array();

		//## ASSERT ##
		assertEquals(cDecode.length, 1);
		assertEquals(cDecode[0], (char)0xE901);
	}

}
