/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.mobylet.core.dialect.impl;

import java.util.regex.Pattern;

import org.mobylet.core.Carrier;
import org.mobylet.core.define.DefCharset;
import org.mobylet.core.dialect.MobyletDialect;



public class MobyletSoftbankDialect implements MobyletDialect {

	private static final Pattern REGEX_CARRIER_MATCH =
		Pattern.compile("^(Vodafone|SoftBank|MOT).+");

	private static final Pattern REGEX_DEVICE_MATCH =
		Pattern.compile("^Vodafone/[0-9.]+[/ ]{1}[0-9a-zA-Z]+" + "|"
				+ "^SoftBank/[0-9.]+[/ ]{1}[0-9a-zA-Z]+" + "|"
				+ "^MOT-[0-9a-zA-Z]+");

	private static final String CONTENT_TYPE =
		"text/html; charset=utf-8";


	@Override
	public Carrier getCarrier() {
		return Carrier.SOFTBANK;
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

	@Override
	public String getContentTypeString() {
		return CONTENT_TYPE;
	}

	@Override
	public String getXContentTypeString() {
		return CONTENT_TYPE;
	}

}
