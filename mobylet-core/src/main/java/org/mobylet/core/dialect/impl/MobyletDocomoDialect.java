package org.mobylet.core.dialect.impl;

import java.util.regex.Pattern;

import org.mobylet.core.Carrier;
import org.mobylet.core.define.DefCharset;
import org.mobylet.core.dialect.MobyletDialect;


public class MobyletDocomoDialect implements MobyletDialect {

	private static final Pattern REGEX_DEVICE_MATCH =
		Pattern.compile("^DoCoMo/[0-9.]+[/ ]{1}[0-9a-zA-Z_+]+");

	@Override
	public Carrier getCarrier() {
		return Carrier.DOCOMO;
	}

	@Override
	public String getCharsetName() {
		return DefCharset.DOCOMO;
	}

	@Override
	public Pattern getDeviceMatchRegex() {
		return REGEX_DEVICE_MATCH;
	}

}
