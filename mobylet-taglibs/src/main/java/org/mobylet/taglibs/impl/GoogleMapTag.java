package org.mobylet.taglibs.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.mobylet.core.Mobylet;
import org.mobylet.core.MobyletFactory;
import org.mobylet.core.device.DeviceDisplay;
import org.mobylet.core.gps.GeoConverter;
import org.mobylet.core.gps.Gps;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.core.util.StringUtils;
import org.mobylet.taglibs.MobyletTag;
import org.mobylet.taglibs.utils.JspWriterUtils;
import org.mobylet.taglibs.utils.UrlUtils;

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


	protected List<Marker> markers;


	@Override
	public int doStartTag() throws JspException {
		//Include
		return EVAL_BODY_INCLUDE;
	}

	@Override
	public int doEndTag() throws JspException {
		Mobylet m = MobyletFactory.getInstance();
		//現在地
		if (StringUtils.isEmpty(lat) &&
				StringUtils.isEmpty(lon)) {
			Gps gps =
				SingletonUtils.get(GeoConverter.class).toWgs84(m.getGps());
			setLat(String.valueOf(gps.getLat()));
			setLon(String.valueOf(gps.getLon()));
		}
		if(markers == null) {
			Marker marker = new Marker();
			marker.setLat(getLat());
			marker.setLon(getLon());
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
		url = UrlUtils.addParameter(url, "center", getLat() + "," + getLon(), false);
		url = UrlUtils.addParameter(url, "zoom", "" + getZoom());
		url = UrlUtils.addParameter(url, "size", "" + getWidth() + "x" + getHeight());
		url = UrlUtils.addParameter(url, "sensor", "" + getSensor());
		for (Marker marker : markers) {
			url = UrlUtils.addParameter(
					url, "markers", marker.getLat() + "," + marker.getLon(), false);
		}
		//Write
		JspWriterUtils.write(
				pageContext.getOut(),
				STAG + TAG + " src=\"" + url + "\"" + ETAG);
		//EvalPage
		recycle();
		return EVAL_PAGE;
	}

	protected void recycle() {
		markers = null;
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

	/**
	 * マーカクラス
	 *
	 * @author stakeuchi
	 */
	public static class Marker {

		protected String lat;

		protected String lon;

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

}
