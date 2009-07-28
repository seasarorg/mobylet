package org.mobylet.core.dialect.impl;

import java.nio.charset.Charset;

import org.mobylet.core.define.DefCharset;
import org.mobylet.core.dialect.MobyletDialect;
import org.mobylet.core.selector.CharsetSelector;
import org.mobylet.core.util.SingletonUtils;

public abstract class AbstractDialect implements MobyletDialect {

	protected CharsetSelector charsetSelector;

	protected AbstractDialect() {
		charsetSelector = SingletonUtils.get(CharsetSelector.class);
	}

	@Override
	public String getCharsetName() {
		return SingletonUtils.get(CharsetSelector.class).getCharsetName(getCarrier());
	}

	@Override
	public Charset getCharset() {
		return SingletonUtils.get(CharsetSelector.class).getCharset(getCarrier());
	}

	@Override
	public String getNativeCharsetName() {
		if (DefCharset.UTF8.equals(getCharsetName())) {
			return DefCharset.UTF8;
		} else {
			return DefCharset.DEFAULT;
		}
	}


}
