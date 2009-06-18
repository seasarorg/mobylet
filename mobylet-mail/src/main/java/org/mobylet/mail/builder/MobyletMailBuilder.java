package org.mobylet.mail.builder;

import org.mobylet.mail.message.MobyletMessage;

public interface MobyletMailBuilder {

	public MobyletMessage build(MobyletMessage message);

}
