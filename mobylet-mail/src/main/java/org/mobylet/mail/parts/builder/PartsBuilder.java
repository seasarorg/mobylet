package org.mobylet.mail.parts.builder;

import org.mobylet.core.Carrier;
import org.mobylet.mail.parts.MailPart;

public interface PartsBuilder {

	public MailPart buildPart(Carrier carrier, String source);

}
