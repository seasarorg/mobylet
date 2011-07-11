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

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.mobylet.core.gps.Geo;
import org.mobylet.core.gps.Marker;
import org.mobylet.core.util.StringUtils;
import org.mobylet.taglibs.MobyletTag;
import org.mobylet.taglibs.utils.JspWriterUtils;
import org.mobylet.view.designer.GoogleMapDesigner;

public class GoogleMapTag extends TagSupport implements MobyletTag {

	private static final long serialVersionUID = -2122566184950353337L;

	public static final String TAG = "img";

	public static final String URL = "http://maps.google.com/staticmap";

	protected String key;

	protected String lat;

	protected String lon;

	protected Integer width;

	protected Integer height;

	protected Integer zoom = 15;

	protected Boolean sensor = false;


	protected String markerSize;
	
	protected String markerColor;
	
	protected String markerAlphaNumericCharacter;


	protected List<Marker> markers;


	@Override
	public int doStartTag() throws JspException {
		//Include
		return EVAL_BODY_INCLUDE;
	}

	@Override
	public int doEndTag() throws JspException {
		try {
			if (StringUtils.isEmpty(key)) {
				return EVAL_PAGE;
			}
			GoogleMapDesigner designer = new GoogleMapDesigner(key);
			if (StringUtils.isNotEmpty(lat) &&
					StringUtils.isNotEmpty(lon)) {
				designer.setCenter(
						new Marker(
								Double.parseDouble(lat),
								Double.parseDouble(lon),
								Geo.WGS84));
			}
			if (width != null && height != null) {
				designer.setWidth(width);
				designer.setHeight(height);
			}
			if (zoom != null) {
				designer.setZoom(zoom);
			}
			if (sensor != null) {
				designer.setSensor(sensor);
			}
			if (markers != null && markers.size() > 0) {
				for (Marker marker : markers) {
					designer.addMarker(marker);
				}
			}
			//Write
			JspWriterUtils.write(
					pageContext.getOut(),
					STAG + TAG + " src=\"" +
					designer.getSrc(markerSize, markerColor, markerAlphaNumericCharacter)
					+ "\"" + ETAG);
			//EvalPage
			recycle();
			return EVAL_PAGE;
		} catch (Exception e) {
			throw new JspException(e);
		}
	}

	protected void recycle() {
		key = null;
		lat = null;
		lon = null;
		width = null;
		height = null;
		markers = null;
		zoom = 15;
		sensor = false;
	}

	public void addMarker(Marker marker) {
		if (markers == null) {
			markers = new ArrayList<Marker>();
		}
		markers.add(marker);
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

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getZoom() {
		return zoom;
	}

	public void setZoom(Integer zoom) {
		this.zoom = zoom;
	}

	public Boolean getSensor() {
		return sensor;
	}

	public void setSensor(Boolean sensor) {
		this.sensor = sensor;
	}

	public String getMarkerSize() {
		return markerSize;
	}

	public void setMarkerSize(String markerSize) {
		this.markerSize = markerSize;
	}

	public String getMarkerColor() {
		return markerColor;
	}

	public void setMarkerColor(String markerColor) {
		this.markerColor = markerColor;
	}

	public String getMarkerAlphaNumericCharacter() {
		return markerAlphaNumericCharacter;
	}

	public void setMarkerAlphaNumericCharacter(String markerAlphaNumericCharacter) {
		this.markerAlphaNumericCharacter = markerAlphaNumericCharacter;
	}

}
