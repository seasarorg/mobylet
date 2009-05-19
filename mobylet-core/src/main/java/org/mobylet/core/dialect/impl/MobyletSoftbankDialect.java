package org.mobylet.core.dialect.impl;

import java.util.regex.Pattern;

import org.mobylet.core.Carrier;
import org.mobylet.core.define.DefCharset;
import org.mobylet.core.dialect.MobyletDialect;



public class MobyletSoftbankDialect implements MobyletDialect {

	private static final Pattern REGEX_DEVICE_MATCH =
		Pattern.compile("^Vodafone/[0-9.]+[/ ]{1}[0-9a-zA-Z]+" + "|"
				+ "^SoftBank/[0-9.]+[/ ]{1}[0-9a-zA-Z]+" + "|"
				+ "^MOT-[0-9a-zA-Z]+");

	@Override
	public Carrier getCarrier() {
		return Carrier.SOFTBANK;
	}

	@Override
	public String getCharsetName() {
		return DefCharset.UTF8;
	}

	@Override
	public Pattern getDeviceMatchRegex() {
		return REGEX_DEVICE_MATCH;
	}

}
