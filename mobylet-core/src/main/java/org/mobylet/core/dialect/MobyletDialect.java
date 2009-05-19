package org.mobylet.core.dialect;

import java.util.regex.Pattern;

import org.mobylet.core.Carrier;


public interface MobyletDialect {

	public Carrier getCarrier();

	public String getCharsetName();

	public Pattern getDeviceMatchRegex();

}
