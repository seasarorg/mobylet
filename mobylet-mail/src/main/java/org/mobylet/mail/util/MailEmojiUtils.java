package org.mobylet.mail.util;

import java.io.StringWriter;

import org.mobylet.core.Carrier;
import org.mobylet.core.http.MobyletPrintWriter;

public class MailEmojiUtils {

	public static String convert(Carrier carrier, String source) {
		StringWriter stringWriter = new StringWriter(1024);
		MobyletPrintWriter printWriter = new MobyletPrintWriter(stringWriter, carrier);
		printWriter.write(source);
		printWriter.flush();
		return stringWriter.toString();
	}

}
