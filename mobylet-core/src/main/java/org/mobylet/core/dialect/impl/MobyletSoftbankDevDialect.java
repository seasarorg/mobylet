/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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



public class MobyletSoftbankDevDialect extends MobyletSoftbankDialect {

	private static final Pattern REGEX_CARRIER_MATCH_DEV =
		Pattern.compile("^(Vodafone|SoftBank|MOT|Semulator).+");

	private static final Pattern REGEX_DEVICE_MATCH_DEV =
		Pattern.compile("^Vodafone/[0-9.]+[/ ]{1}[0-9a-zA-Z]+" + "|"
				+ "^SoftBank/[0-9.]+[/ ]{1}[0-9a-zA-Z]+" + "|"
				+ "^Semulator/[0-9.]+[/ ]{1}[0-9a-zA-Z]+" + "|"
				+ "^MOT-[0-9a-zA-Z]+");

	@Override
	public Pattern getCarrierMatchRegex() {
		return REGEX_CARRIER_MATCH_DEV;
	}

	@Override
	public Pattern getDeviceMatchRegex() {
		return REGEX_DEVICE_MATCH_DEV;
	}

}
