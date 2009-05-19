package org.mobylet.core.dialect.impl;

import java.util.regex.Pattern;

import org.mobylet.core.Carrier;
import org.mobylet.core.define.DefCharset;
import org.mobylet.core.dialect.MobyletDialect;


public class DefaultDialect implements MobyletDialect {

	private static final Pattern REGEX_CARRIER_MATCH =
		Pattern.compile(".+");

	private static final Pattern REGEX_DEVICE_MATCH =
		Pattern.compile(".+");


	@Override
	public Carrier getCarrier() {
		return Carrier.OTHER;
	}

	@Override
	public String getCharsetName() {
		return DefCharset.UTF8;
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
