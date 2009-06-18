package org.mobylet.mail.parts.builder;

import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;

import org.mobylet.core.Carrier;
import org.mobylet.core.MobyletRuntimeException;
import org.mobylet.core.http.MobyletPrintWriter;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.mail.MailConstants;
import org.mobylet.mail.parts.MailPart;
import org.mobylet.mail.selector.MailCharsetSelector;

public class HtmlPartsBuilder implements PartsBuilder, MailConstants {

	@Override
	public MailPart buildPart(Carrier carrier, String source) {
		//Get-Charset
		MailCharsetSelector charsetSelector =
			SingletonUtils.get(MailCharsetSelector.class);
		String encodingCharset = charsetSelector.getEncodingCharset(carrier);
		String notifyCharset = charsetSelector.getNotifyCharset(carrier);
		//Build
		if (source == null) {
			source = "";
		}
		//Convert-Emoji
		StringWriter stringWriter = new StringWriter(1024);
		MobyletPrintWriter printWriter = new MobyletPrintWriter(stringWriter, carrier);
		printWriter.write(source);
		printWriter.flush();
		ByteBuffer buf = null;
		try {
			buf = ByteBuffer.wrap(stringWriter.toString().getBytes(encodingCharset));
		} catch (UnsupportedEncodingException e) {
			throw new MobyletRuntimeException("Unsupported Charset = " + encodingCharset, e);
		}
		DataSource dataSource =
			new ByteArrayDataSource(
					buf.array(), APPLICATION_OCTETSTREAM
			);
		MailPart part = new MailPart(new DataHandler(dataSource));
		part.addHeader(TRANSFER_ENCODING, ENCODING_7BIT);
		part.addHeader(CONTENT_TYPE,
				TEXT_HTML + "; charset=\"" + notifyCharset + "\"");
		return part;
	}


}
