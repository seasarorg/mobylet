package org.mobylet.core.dialect.impl;

import org.mobylet.core.Carrier;
import org.mobylet.core.define.DefCharset;


public class MobyletAuDialect extends AbstractMobyletDialect {

	@Override
	public Carrier getCarrier() {
		return Carrier.AU;
	}

	@Override
	public String getCharsetName() {
		return DefCharset.AU;
	}

}
