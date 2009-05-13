package org.mobylet.core.dialect.impl;

import org.mobylet.core.Carrier;


public class MobyletSoftbankDialect extends AbstractMobyletDialect {

	@Override
	public String getCharsetName(Carrier carrier) {
		return CHARSET_NAME_UTF8;
	}

}
