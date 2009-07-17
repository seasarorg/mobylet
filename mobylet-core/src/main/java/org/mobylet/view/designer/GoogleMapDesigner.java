package org.mobylet.view.designer;

import java.util.ArrayList;
import java.util.List;

import org.mobylet.core.Mobylet;
import org.mobylet.core.MobyletFactory;
import org.mobylet.core.device.DeviceDisplay;
import org.mobylet.core.gps.GeoConverter;
import org.mobylet.core.gps.Gps;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.core.util.StringUtils;
import org.mobylet.core.util.UrlUtils;


public class GoogleMapDesigner {


	public static final String URL = "http://maps.google.com/staticmap";

	protected String key;

	protected Gps center;

	protected Integer width;

	protected Integer height;

	protected Integer zoom = 15;

	protected Boolean sensor = false;


	protected List<Gps> markers;



	public static GoogleMapDesigner getDesigner(String key) {
		return new GoogleMapDesigner(key);
	}

	public GoogleMapDesigner(String key) {
		this.key = key;
		this.markers = new ArrayList<Gps>();
	}


	public String getSrc() {
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
			Gps marker = new Gps(center);
			addMarker(marker);
		}
		//画面全面
		if (width == null &&
				height == null) {
			DeviceDisplay dp = m.getDisplay();
			setWidth(dp.getWidth());
			setHeight(dp.getHeight());
		}
		//URL構築
		String url = URL;
		url = UrlUtils.addParameter(url, "maptype", "mobile");
		url = UrlUtils.addParameter(url, "key", getKey());
		url = UrlUtils.addParameter(url, "center", center.getLat() + "," + center.getLon(), false);
		url = UrlUtils.addParameter(url, "zoom", "" + getZoom());
		url = UrlUtils.addParameter(url, "size", "" + getWidth() + "x" + getHeight());
		url = UrlUtils.addParameter(url, "sensor", "" + getSensor());
		for (Gps marker : markers) {
			marker = SingletonUtils.get(GeoConverter.class).toWgs84(marker);
			url = UrlUtils.addParameter(
					url, "markers", marker.getLat() + "," + marker.getLon(), false);
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

	public List<Gps> getMarkers() {
		return markers;
	}

	public void setMarkers(List<Gps> markers) {
		this.markers = markers;
	}

	public void addMarker(Gps marker) {
		this.markers.add(marker);
	}
}
