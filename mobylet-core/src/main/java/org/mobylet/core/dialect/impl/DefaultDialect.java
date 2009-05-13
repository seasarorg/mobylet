package org.mobylet.core.dialect.impl;

import org.mobylet.core.Carrier;


public class DefaultDialect extends AbstractMobyletDialect {

	@Override
	public Carrier getCarrier() {
		return Carrier.OTHER;
	}

	@Override
	public String getCharsetName() {
		return CHARSET_NAME_UTF8;
	}

}
