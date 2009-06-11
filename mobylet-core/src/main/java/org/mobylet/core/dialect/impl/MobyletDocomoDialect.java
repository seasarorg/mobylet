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

import javax.servlet.http.HttpServletRequest;

import org.mobylet.core.Carrier;
import org.mobylet.core.device.DeviceDisplay;
import org.mobylet.core.gps.Accuracy;
import org.mobylet.core.gps.Geo;
import org.mobylet.core.gps.Gps;
import org.mobylet.core.util.RequestUtils;
import org.mobylet.core.util.StringUtils;


public class MobyletDocomoDialect extends AbstractDialect {

	private static final Pattern REGEX_CARRIER_MATCH =
		Pattern.compile("^DoCoMo.+");

	private static final Pattern REGEX_DEVICE_MATCH =
		Pattern.compile("^DoCoMo/[0-9.]+[/ ]{1}[0-9a-zA-Z_+]+");

	protected String contentTypeString = null;

	protected String xContentTypeString = null;


	@Override
	public Carrier getCarrier() {
		return Carrier.DOCOMO;
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
			if (charsetSelector.isCharsetInstalled()) {
				contentTypeString = "text/html; charset=shift_jis";
			} else {
				contentTypeString = "text/html; charset=" +
					charsetSelector.getCharsetName(getCarrier());
			}
		}
		return contentTypeString;
	}

	@Override
	public String getXContentTypeString() {
		if (StringUtils.isEmpty(xContentTypeString)) {
			if (charsetSelector.isCharsetInstalled()) {
				xContentTypeString = "application/xhtml+xml; charset=shift_jis";
			} else {
				xContentTypeString = "application/xhtml+xml; charset=" +
					charsetSelector.getCharsetName(getCarrier());
			}
		}
		return xContentTypeString;
	}

	@Override
	public String getUid() {
		return RequestUtils.get().getParameter("uid");
	}

	@Override
	public String getGuid() {
		return RequestUtils.get().getHeader("X-DCMGUID");
	}

	@Override
	public Gps getGps() {
		HttpServletRequest request = RequestUtils.get();
		String lat = request.getParameter("lat");
		String lon = request.getParameter("lon");
		String geoString = request.getParameter("geo");
		String accString = request.getParameter("x-acc");
		if (StringUtils.isEmpty(lat) ||
				StringUtils.isEmpty(lon) ||
				StringUtils.isEmpty(geoString) ||
				StringUtils.isEmpty(accString)) {
			return null;
		}
		//Geo
		geoString = Geo.WGS84.name();
		Geo geo = Geo.valueOf(geoString.toUpperCase());
		//Gps
		Gps g = new Gps(lat, lon, geo);
		//Accuracy
		Accuracy acc = Accuracy.getAccuracy(Integer.parseInt(accString));
		g.setAccuracy(acc);
		return g;
	}

	@Override
	public DeviceDisplay getDeviceDisplayByRequestHeader() {
		return null;
	}

}
