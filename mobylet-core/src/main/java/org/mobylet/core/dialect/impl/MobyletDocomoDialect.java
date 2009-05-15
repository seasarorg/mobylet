package org.mobylet.core.dialect.impl;

import org.mobylet.core.Carrier;
import org.mobylet.core.define.DefCharset;


public class MobyletDocomoDialect extends AbstractMobyletDialect {

	@Override
	public Carrier getCarrier() {
		return Carrier.DOCOMO;
	}

	@Override
	public String getCharsetName() {
		return DefCharset.DOCOMO;
	}
}
