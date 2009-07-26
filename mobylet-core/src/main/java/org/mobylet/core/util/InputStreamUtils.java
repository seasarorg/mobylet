package org.mobylet.core.util;

import java.io.IOException;
import java.io.InputStream;

import org.mobylet.core.MobyletRuntimeException;

public class InputStreamUtils {

	public static byte[] getAllBytes(InputStream inputStream) {
		if (inputStream == null) {
			return new byte[0];
		}
		try {
			return getBytes(inputStream, inputStream.available());
		} catch (Exception e) {
			throw new MobyletRuntimeException("入力ストリームからの読み込みエラー", e);
		}
	}

	public static byte[] getBytes(InputStream inputStream, int length) {
		if (inputStream == null) {
			return new byte[0];
		}
		byte[] data = null;
		try {
			if (length != 0) {
				data = new byte[length];
			} else {
				data = new byte[inputStream.available()];
			}
			inputStream.read(data);
		} catch (Exception e) {
			throw new MobyletRuntimeException("入力ストリームからの読み込みエラー", e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					//NOP
				}
			}
		}
		return data;
	}

	public static void closeQuietly(InputStream in) {
		if (in != null) {
			try {
				in.close();
			} catch (IOException e) {
				//NOP
			}
		}
	}
}
