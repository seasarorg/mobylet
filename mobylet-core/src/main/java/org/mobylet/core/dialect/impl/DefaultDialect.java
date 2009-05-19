package org.mobylet.core.dialect.impl;

import java.util.regex.Pattern;

import org.mobylet.core.Carrier;
import org.mobylet.core.define.DefCharset;
import org.mobylet.core.dialect.MobyletDialect;


public class DefaultDialect implements MobyletDialect {

	@Override
	public Carrier getCarrier() {
		return Carrier.OTHER;
	}

	@Override
	public String getCharsetName() {
		return DefCharset.UTF8;
	}

	@Override
	public Pattern getDeviceMatchRegex() {
		return null;
	}

}
