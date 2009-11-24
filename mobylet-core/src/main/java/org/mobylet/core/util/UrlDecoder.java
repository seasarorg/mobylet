package org.mobylet.core.util;

import java.nio.charset.Charset;

public class UrlDecoder {

	private static final String HEX = "0123456789abcdefABCDEF";


	public static String decode(String s, Charset charset) {
		boolean needToChange = false;
		int numChars = s.length();
		StringBuilder buf =
			new StringBuilder(numChars > 500 ? numChars / 2 : numChars);
		int i = 0;
		char c;
		byte[] bytes = null;
		while (i < numChars) {
			c = s.charAt(i);
			switch (c) {
			case '+':
				buf.append(' ');
				i++;
				needToChange = true;
				break;
			case '%':
				try {
					if (bytes == null)
						bytes = new byte[(numChars-i)/2];
					int pos = 0;
					while (c=='%' &&
							(i+1) < numChars &&
							HEX.indexOf(s.charAt(i+1)) >= 0) {
						int index = 2;
						if ((i+2) < numChars &&
								HEX.indexOf(s.charAt(i+2)) >= 0) {
							index = 3;
						}
						bytes[pos] =
							(byte)Integer.parseInt(s.substring(i+1,i+index),16);
						pos++;
						i+= index;
						if (i < numChars) {
							c = s.charAt(i);
							if (c!='%') {
								if (c=='+') {
									c = ' ';
								}
								bytes[pos] = (byte)c;
								pos++;
								i++;
								if (i < numChars) {
									c = s.charAt(i);
								}
							}
						}
					}
					if ((i < numChars) && (c=='%')) {
						throw new IllegalArgumentException(
						"URLDecoder: Incomplete trailing escape (%) pattern");
					}
					buf.append(new String(bytes, 0, pos, charset));
				} catch (NumberFormatException e) {
					throw new IllegalArgumentException(
							"URLDecoder: Illegal hex characters in escape (%) pattern - "
							+ e.getMessage());
				}
				needToChange = true;
				break;
			default:
				buf.append(c);
				i++;
				break;
			}
		}
		return (needToChange? buf.toString() : s);
	}
}