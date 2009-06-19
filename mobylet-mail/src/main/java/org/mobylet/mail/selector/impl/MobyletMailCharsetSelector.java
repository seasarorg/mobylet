package org.mobylet.mail.selector.impl;

import java.nio.charset.spi.CharsetProvider;

import org.mobylet.charset.MobyletCharsetProvider;
import org.mobylet.core.Carrier;
import org.mobylet.core.define.DefCharset;
import org.mobylet.mail.MailConstants;
import org.mobylet.mail.selector.MailCharsetSelector;

public class MobyletMailCharsetSelector implements MailCharsetSelector {

	protected CharsetProvider provider = new MobyletCharsetProvider();

	@Override
	public String getEncodingCharset(Carrier carrier) {
		if (carrier == null) {
			return null;
		}
		switch (carrier) {
		case DOCOMO:
			return DefCharset.DOCOMO;
		case AU:
			return MailConstants.CHARSET_AU_MAIL_SJIS;
		case SOFTBANK:
			return DefCharset.UTF8;
		default:
			return MailConstants.CHARSET_ISO_2022_1;
		}
	}

	@Override
	public String getNotifyCharset(Carrier carrier) {
		if (carrier == null) {
			return null;
		}
		switch (carrier) {
		case DOCOMO:
			return MailConstants.CHARSET_SJIS;
		case AU:
			return MailConstants.CHARSET_SJIS;
		case SOFTBANK:
			return DefCharset.UTF8;
		default:
			return MailConstants.CHARSET_JIS_STANDARD;
		}
	}

}
