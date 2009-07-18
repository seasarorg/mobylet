package org.mobylet.mail;

import java.nio.charset.Charset;

import org.mobylet.charset.MobyletCharsetPool;
import org.mobylet.core.define.DefCharset;

public interface MailCharset {

	public static final Charset DOCOMO =
		MobyletCharsetPool.getInstance().charsetForName(DefCharset.DOCOMO);

	public static final Charset AU =
		MobyletCharsetPool.getInstance().charsetForName(
				MailConstants.CHARSET_AU_MAIL_SJIS);

	public static final Charset SOFTBANK =
		Charset.forName(DefCharset.UTF8);

	public static final Charset OTHER =
		Charset.forName(MailConstants.CHARSET_ISO_2022_1);

}
