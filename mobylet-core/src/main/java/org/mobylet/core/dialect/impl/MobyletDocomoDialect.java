package org.mobylet.core.dialect.impl;

import org.mobylet.core.Carrier;


public class MobyletDocomoDialect extends AbstractMobyletDialect {

	@Override
	public Carrier getCarrier() {
		return Carrier.DOCOMO;
	}

	@Override
	public String getCharsetName() {
		return CHARSET_NAME_DOCOMO;
	}
}
