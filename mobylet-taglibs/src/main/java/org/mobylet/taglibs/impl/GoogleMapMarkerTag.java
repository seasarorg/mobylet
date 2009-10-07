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
