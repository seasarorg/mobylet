package org.mobylet.mail.selector;

import java.nio.charset.Charset;

import org.mobylet.core.Carrier;

public interface MailCharsetSelector {

	public Charset getEncodingCharset(Carrier carrier);

	public String getNotifyCharset(Carrier carrier);

}
