package org.mobylet.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.mobylet.core.MobyletRuntimeException;

public class InputToOutputStreamUtils {

	public static void writeAll(InputStream in, OutputStream out) {
		int c = -1;
		try {
			while ((c = in.read()) >= 0) {
				out.write(c);
			}
		} catch (IOException e) {
			throw new MobyletRuntimeException(
					"入力ストリーム情報を出力ストリームに書き込み中に例外発生", e);
		}
	}

}
