package org.mobylet.mail.selector;

import org.mobylet.core.Carrier;

public interface MailCharsetSelector {

	public String getEncodingCharset(Carrier carrier);

	public String getNotifyCharset(Carrier carrier);

}
