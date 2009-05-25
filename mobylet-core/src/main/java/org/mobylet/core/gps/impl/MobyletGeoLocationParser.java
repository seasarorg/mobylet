package org.mobylet.core.gps.impl;

import javax.servlet.http.HttpServletRequest;

import org.mobylet.core.gps.GeoLocation;
import org.mobylet.core.gps.GeoLocationParser;
import org.mobylet.core.util.RequestUtils;

public class MobyletGeoLocationParser implements GeoLocationParser {

	@Override
	public GeoLocation parse() {
		//TODO
		HttpServletRequest request = RequestUtils.get();
		request.getParameter("lat");
		return null;
	}

}
