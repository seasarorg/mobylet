package org.mobylet.taglibs.impl;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.mobylet.taglibs.impl.GoogleMapTag.Marker;

public class GoogleMapMarkerTag extends SimpleTagSupport {

	protected String lat;

	protected String lon;

	@Override
	@SuppressWarnings("unchecked")
	public void doTag() throws JspException, IOException {
		GoogleMapTag googleMapTag =
			(GoogleMapTag)findAncestorWithClass(this, GoogleMapTag.class);
		Marker marker = new Marker();
		marker.setLat(lat);
		marker.setLon(lon);
		googleMapTag.addMarker(marker);
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
