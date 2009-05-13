package org.mobylet.core.dialect.impl;

import org.mobylet.core.Carrier;



public class MobyletSoftbankDialect extends AbstractMobyletDialect {

	@Override
	public Carrier getCarrier() {
		return Carrier.SOFTBANK;
	}

	@Override
	public String getCharsetName() {
		return CHARSET_NAME_UTF8;
	}

}
