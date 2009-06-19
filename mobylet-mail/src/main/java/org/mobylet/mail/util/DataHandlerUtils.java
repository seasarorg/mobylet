package org.mobylet.mail.util;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;

import org.mobylet.core.Carrier;
import org.mobylet.core.MobyletRuntimeException;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.mail.MailConstants;
import org.mobylet.mail.selector.MailCharsetSelector;

public class DataHandlerUtils {

	public static DataHandler getDataHandler(Carrier carrier, String source) {
		//Get-Charset
		MailCharsetSelector charsetSelector =
			SingletonUtils.get(MailCharsetSelector.class);
		String encodingCharset = charsetSelector.getEncodingCharset(carrier);
		//Build
		if (source == null) {
			source = "";
		}
		//Convert-Emoji
		ByteBuffer buf = null;
		try {
			buf = ByteBuffer.wrap(
					MailEmojiUtils.convert(carrier, source).getBytes(encodingCharset));
		} catch (UnsupportedEncodingException e) {
			throw new MobyletRuntimeException(
					"Unsupported Charset = " + encodingCharset, e);
		}
		DataSource dataSource =
			new ByteArrayDataSource(
					buf.array(), MailConstants.APPLICATION_OCTETSTREAM
			);
		return new DataHandler(dataSource);
	}

}
