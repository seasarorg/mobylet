package org.mobylet.taglibs.utils;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;

import org.mobylet.taglibs.MobyletRenderingException;

public class JspWriterUtils {

	public static void write(JspWriter writer, String str) {
		try {
			writer.print(str);
		} catch (IOException e) {
			throw new MobyletRenderingException(
					"レスポンス出力中にIO例外が発生しました", e);
		}
	}
}
