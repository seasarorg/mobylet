package org.mobylet.core.dialect.impl;

import org.mobylet.core.Carrier;
import org.mobylet.core.define.DefCharset;



public class MobyletSoftbankDialect extends AbstractMobyletDialect {

	@Override
	public Carrier getCarrier() {
		return Carrier.SOFTBANK;
	}

	@Override
	public String getCharsetName() {
		return DefCharset.UTF8;
	}

}
