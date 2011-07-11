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
package org.mobylet.taglibs.impl;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.mobylet.core.gps.Geo;
import org.mobylet.core.gps.Marker;
import org.mobylet.core.util.StringUtils;

public class GoogleMapMarkerTag extends SimpleTagSupport {

	protected String lat;

	protected String lon;

	protected String size;

	protected String color;

	protected String alphaNumericCharacter;


	@Override
	public void doTag() throws JspException, IOException {
		try {
			GoogleMapTag googleMapTag =
				(GoogleMapTag)findAncestorWithClass(this, GoogleMapTag.class);
			Marker marker = new Marker(
					Double.parseDouble(lat),
					Double.parseDouble(lon),
					Geo.WGS84);
			if (StringUtils.isNotEmpty(size)) {
				marker.setSize(Marker.Size.valueOf(size));
			}
			if (StringUtils.isNotEmpty(color)) {
				marker.setColor(Marker.Color.valueOf(color));
			}
			if (StringUtils.isNotEmpty(alphaNumericCharacter)) {
				marker.setAlphaNumericCharacter(alphaNumericCharacter);
			}
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

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getAlphaNumericCharacter() {
		return alphaNumericCharacter;
	}

	public void setAlphaNumericCharacter(String alphaNumericCharacter) {
		this.alphaNumericCharacter = alphaNumericCharacter;
	}

}
