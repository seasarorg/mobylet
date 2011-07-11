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

import javax.servlet.http.HttpServletRequest;

import org.mobylet.core.Carrier;
import org.mobylet.core.define.DefCharset;
import org.mobylet.core.device.DeviceDisplay;
import org.mobylet.core.gps.Geo;
import org.mobylet.core.gps.Gps;
import org.mobylet.core.util.RequestUtils;
import org.mobylet.core.util.StringUtils;


public class MobyletAuDialect extends AbstractDialect {

	private static final Pattern REGEX_CARRIER_MATCH =
		Pattern.compile("^KDDI.+");

	private static final Pattern REGEX_DEVICE_MATCH =
		Pattern.compile("^KDDI-[0-9a-zA-Z]+");


	@Override
	public Carrier getCarrier() {
		return Carrier.AU;
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
	public String getContentCharsetName() {
		if (charsetSelector.isCharsetInstalled()) {
			return DefCharset.SJIS;
		} else {
			return getCharsetName();
		}
	}

	@Override
	public String getUid() {
		return RequestUtils.get().getHeader("X-Up-Subno");
	}

	@Override
	public String getGuid() {
		return getUid();
	}

	@Override
	public Gps getGps() {
		HttpServletRequest request = RequestUtils.get();
		String lat = request.getParameter("lat");
		String lon = request.getParameter("lon");
		String geoString = request.getParameter("datum");
		if (StringUtils.isEmpty(lat) ||
				StringUtils.isEmpty(lon) ||
				StringUtils.isEmpty(geoString)) {
			return null;
		}
		//Geo
		Geo geo = Geo.WGS84;
		if ("1".equals(geoString)) {
			geo = Geo.TOKYO;
		}
		//Gps
		Gps g = new Gps(lat, lon, geo);
		return g;
	}

	@Override
	public DeviceDisplay getDeviceDisplayByRequestHeader() {
		String dpString =
			RequestUtils.get().getHeader("X-UP-DEVCAP-SCREENPIXELS");
		if (StringUtils.isEmpty(dpString)) {
			return null;
		}
		try {
			String[] splitDpString = dpString.split(",");
			return new DeviceDisplay(
					new Integer(splitDpString[0]),
					new Integer(splitDpString[1]));
		} catch (Exception e) {
			return null;
		}
	}

}
