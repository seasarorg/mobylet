package org.mobylet.core.dialect.impl;

import java.util.regex.Pattern;

import org.mobylet.core.Carrier;
import org.mobylet.core.define.DefCharset;
import org.mobylet.core.dialect.MobyletDialect;


public class MobyletAuDialect implements MobyletDialect {

	private static final Pattern REGEX_CARRIER_MATCH =
		Pattern.compile("^KDDI.+");

	private static final Pattern REGEX_DEVICE_MATCH =
		Pattern.compile("^KDDI-[0-9a-zA-Z]+");

	@Override
	public Carrier getCarrier() {
		return Carrier.AU;
	}

	@Override
	public String getCharsetName() {
		return DefCharset.AU;
	}

	@Override
	public Pattern getCarrierMatchRegex() {
		return REGEX_CARRIER_MATCH;
	}

	@Override
	public Pattern getDeviceMatchRegex() {
		return REGEX_DEVICE_MATCH;
	}

}
