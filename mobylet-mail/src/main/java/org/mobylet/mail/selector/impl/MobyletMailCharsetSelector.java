package org.mobylet.mail.selector.impl;

import java.nio.charset.Charset;

import org.mobylet.core.Carrier;
import org.mobylet.core.define.DefCharset;
import org.mobylet.mail.MailCharset;
import org.mobylet.mail.MailConstants;
import org.mobylet.mail.selector.MailCharsetSelector;

public class MobyletMailCharsetSelector implements MailCharsetSelector {

	@Override
	public Charset getEncodingCharset(Carrier carrier) {
		if (carrier == null) {
			return null;
		}
		switch (carrier) {
		case DOCOMO:
			return MailCharset.DOCOMO;
		case AU:
			return MailCharset.AU;
		case SOFTBANK:
			return MailCharset.SOFTBANK;
		default:
			return MailCharset.OTHER;
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
