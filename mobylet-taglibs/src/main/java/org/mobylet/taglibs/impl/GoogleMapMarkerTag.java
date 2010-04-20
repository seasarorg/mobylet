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
package org.mobylet.taglibs.impl;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.mobylet.core.gps.Geo;
import org.mobylet.core.gps.Gps;

public class GoogleMapMarkerTag extends SimpleTagSupport {

	protected String lat;

	protected String lon;

	@Override
	public void doTag() throws JspException, IOException {
		try {
			GoogleMapTag googleMapTag =
				(GoogleMapTag)findAncestorWithClass(this, GoogleMapTag.class);
			Gps marker = new Gps(
					Double.parseDouble(lat),
					Double.parseDouble(lon),
					Geo.WGS84);
			googleMapTag.addMarker(marker);
		} catch (Exception e) {
			throw new JspException(e);
		}
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}
}
