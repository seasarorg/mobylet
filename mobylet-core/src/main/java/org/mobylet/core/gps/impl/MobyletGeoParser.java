package org.mobylet.core.gps.impl;

import javax.servlet.http.HttpServletRequest;

import org.mobylet.core.gps.Gps;
import org.mobylet.core.gps.GeoParser;
import org.mobylet.core.util.RequestUtils;

public class MobyletGeoParser implements GeoParser {

	@Override
	public Gps parse() {
		//TODO
		HttpServletRequest request = RequestUtils.get();
		request.getParameter("lat");
		return null;
	}

}
