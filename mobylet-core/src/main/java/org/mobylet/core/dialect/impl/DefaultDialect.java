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
import org.mobylet.core.device.DeviceDisplay;
import org.mobylet.core.gps.Gps;
import org.mobylet.core.util.StringUtils;


public class DefaultDialect extends AbstractDialect {

	private static final Pattern REGEX_CARRIER_MATCH =
		Pattern.compile(".+");

	private static final Pattern REGEX_DEVICE_MATCH =
		Pattern.compile(".+");

	protected String contentTypeString = null;


	@Override
	public Carrier getCarrier() {
		return Carrier.OTHER;
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
		if (StringUtils.isEmpty(contentTypeString)) {
			contentTypeString = "text/html; charset=" +
				charsetSelector.getCharsetName(getCarrier());
		}
		return contentTypeString;
	}

	@Override
	public String getXContentTypeString() {
		return getContentTypeString();
	}

	@Override
	public String getGuid() {
		return null;
	}

	@Override
	public String getUid() {
		return null;
	}

	@Override
	public Gps getGps() {
		return null;
	}

	@Override
	public DeviceDisplay getDeviceDisplayByRequestHeader() {
		return null;
	}

}
