package org.mobylet.core.dialect.impl;

import org.mobylet.core.Carrier;
import org.mobylet.core.define.DefCharset;


public class DefaultDialect extends AbstractMobyletDialect {

	@Override
	public Carrier getCarrier() {
		return Carrier.OTHER;
	}

	@Override
	public String getCharsetName() {
		return DefCharset.UTF8;
	}

}
