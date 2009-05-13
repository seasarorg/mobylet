package org.mobylet.core.dialect.impl;

import org.mobylet.core.Carrier;


public class MobyletAuDialect extends AbstractMobyletDialect {

	@Override
	public Carrier getCarrier() {
		return Carrier.AU;
	}

	@Override
	public String getCharsetName() {
		return CHARSET_NAME_AU;
	}

}
