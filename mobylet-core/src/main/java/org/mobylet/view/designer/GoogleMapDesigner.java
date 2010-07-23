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
package org.mobylet.view.designer;

import java.util.ArrayList;
import java.util.List;

import org.mobylet.core.Carrier;
import org.mobylet.core.Mobylet;
import org.mobylet.core.MobyletFactory;
import org.mobylet.core.device.DeviceDisplay;
import org.mobylet.core.gps.GeoConverter;
import org.mobylet.core.gps.Gps;
import org.mobylet.core.gps.Marker;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.core.util.StringUtils;
import org.mobylet.core.util.UrlUtils;


public class GoogleMapDesigner {


	public static final String URL = "http://maps.google.com/staticmap";

	public static final Integer DEFAULT_WIDTH = 240;

	public static final Integer DEFAULT_HEIGHT = 240;

	public static final Integer MAX_WIDTH = 640;

	public static final Integer MAX_HEIGHT = 640;

	protected String key;

	protected Gps center;

	protected Integer width;

	protected Integer height;

	protected Integer zoom = 15;

	protected Boolean sensor = false;


	protected List<Marker> markers;



	public static GoogleMapDesigner getDesigner(String key) {
		return new GoogleMapDesigner(key);
	}

	public GoogleMapDesigner(String key) {
		this.key = key;
		this.markers = new ArrayList<Marker>();
	}


	public String getSrc(String markerSize, String markerColor, String markerAlphaNumericCharacter) {
		Mobylet m = MobyletFactory.getInstance();
		//現在地
		if (center == null ||
				StringUtils.isEmpty(center.getStrLat()) ||
				StringUtils.isEmpty(center.getStrLon())) {
			Gps gps = m.getGps();
			if (gps != null) {
				gps = SingletonUtils.get(GeoConverter.class).toWgs84(gps);
				setCenter(gps);
			} else {
				return "";
			}
		}
		if(markers == null ||
				markers.size() == 0) {
			Marker marker = new Marker(center);
			if (StringUtils.isNotEmpty(markerSize)) {
				marker.setSize(Marker.Size.valueOf(markerSize));
			}
			if (StringUtils.isNotEmpty(markerColor)) {
				marker.setColor(Marker.Color.valueOf(markerColor));
			}
			if (StringUtils.isNotEmpty(markerAlphaNumericCharacter)) {
				marker.setAlphaNumericCharacter(markerAlphaNumericCharacter);
			}
			addMarker(marker);
		}
		//画面全面
		if (width == null &&
				height == null) {
			DeviceDisplay dp = m.getDisplay();
			if(dp != null) {
				setWidth(dp.getWidth());
				setHeight(dp.getHeight());
			}else {
				if(Carrier.OTHER == m.getCarrier()) {
					setWidth(MAX_WIDTH);
					setHeight(MAX_HEIGHT);
				}else {
					setWidth(DEFAULT_WIDTH);
					setHeight(DEFAULT_HEIGHT);
				}
			}
		}
		//URL構築
		String url = URL;
		if(m.getCarrier() != Carrier.OTHER){
			url = UrlUtils.addParameter(url, "maptype", "mobile");
		}
		url = UrlUtils.addParameter(url, "key", getKey());
		url = UrlUtils.addParameter(url, "center", center.getLat() + "," + center.getLon(), false);
		url = UrlUtils.addParameter(url, "zoom", "" + getZoom());
		url = UrlUtils.addParameter(url, "size", "" + getWidth() + "x" + getHeight());
		url = UrlUtils.addParameter(url, "sensor", "" + getSensor());
		for (Marker marker : markers) {
			Gps markerGps = SingletonUtils.get(GeoConverter.class).toWgs84(marker);
			String parameter =
				markerGps.getLat() + "," + markerGps.getLon();
			if (marker.getSize() != null ||
					marker.getColor() != null ||
					marker.getAlphaNumericCharacter() != null) {
				parameter =
					parameter + ","
					+ (marker.getSize() != null ? "" + marker.getSize() : "")
					+ (marker.getColor() != null ? "" + marker.getColor() : "")
					+ (marker.getAlphaNumericCharacter() != null ? "" + marker.getAlphaNumericCharacter() : "");
			}
			url = UrlUtils.addParameter(
					url, "markers", parameter, false);
		}
		return url;
	}


	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Gps getCenter() {
		return center;
	}

	public void setCenter(Gps center) {
		this.center = center;
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

	public List<Marker> getMarkers() {
		return markers;
	}

	public void setMarkers(List<Marker> markers) {
		this.markers = markers;
	}

	public void addMarker(Marker marker) {
		this.markers.add(marker);
	}
}
