/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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



public class MobyletSoftbankDialect extends AbstractDialect {

	private static final Pattern REGEX_CARRIER_MATCH =
		Pattern.compile("^(Vodafone|SoftBank|MOT).+");

	private static final Pattern REGEX_DEVICE_MATCH =
		Pattern.compile("^Vodafone/[0-9.]+[/ ]{1}[0-9a-zA-Z]+" + "|"
				+ "^SoftBank/[0-9.]+[/ ]{1}[0-9a-zA-Z]+" + "|"
				+ "^MOT-[0-9a-zA-Z]+");

	@Override
	public Carrier getCarrier() {
		return Carrier.SOFTBANK;
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
	public String getUid() {
		return RequestUtils.get().getHeader("x-jphone-uid");
	}

	@Override
	public String getGuid() {
		return getUid();
	}

	@Override
	public Gps getGps() {
		HttpServletRequest request = RequestUtils.get();
		String pos = request.getParameter("pos");
		String geoString = request.getParameter("geo");
		String accString = request.getParameter("x-acr");
		if (StringUtils.isEmpty(pos) ||
				StringUtils.isEmpty(geoString) ||
				StringUtils.isEmpty(accString)) {
			return null;
		}
		//Geo
		geoString = Geo.WGS84.name();
		Geo geo = Geo.valueOf(geoString.toUpperCase());
		//Gps
		String[] positions = pos.split("[EW]{1}");
		Gps g = new Gps(positions[0], positions[1], geo);
		//Accuracy
		Accuracy acc = Accuracy.getAccuracy(Integer.parseInt(accString));
		g.setAccuracy(acc);
		return g;
	}

	@Override
	public DeviceDisplay getDeviceDisplayByRequestHeader() {
		String dpString =
			RequestUtils.get().getHeader("x-s-display-info");
		if (StringUtils.isEmpty(dpString)) {
			dpString =
				RequestUtils.get().getHeader("x-jphone-display");
			try {
				String[] splitDpString = dpString.split("[*]");
				return new DeviceDisplay(
						new Integer(splitDpString[0]),
						new Integer(splitDpString[1]));
			} catch (Exception e) {
				return null;
			}
		} else {
			try {
				return new DeviceDisplay(
						new Integer(dpString.substring(
								0,
								dpString.indexOf('*'))),
						new Integer(dpString.substring(
								dpString.indexOf('*')+1,
								dpString.indexOf('/')))
						);
			} catch (Exception e) {
				return null;
			}
		}
	}

}
