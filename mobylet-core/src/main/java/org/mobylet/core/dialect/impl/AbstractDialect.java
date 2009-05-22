package org.mobylet.core.dialect.impl;

import org.mobylet.core.dialect.MobyletDialect;
import org.mobylet.core.selector.CharsetSelector;
import org.mobylet.core.util.SingletonUtils;

public abstract class AbstractDialect implements MobyletDialect {

	@Override
	public String getCharsetName() {
		return SingletonUtils.get(CharsetSelector.class).getCharsetName(getCarrier());
	}

}
