package org.mobylet.core.util;

import java.io.IOException;
import java.io.OutputStream;

public class OutputStreamUtils {

	public static void closeQuietly(OutputStream out) {
		if (out != null) {
			try {
				out.close();
			} catch (IOException e) {
				//NOP
			}
		}
	}
}
