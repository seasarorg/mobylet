package org.mobylet.taglibs.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.mobylet.core.gps.Geo;
import org.mobylet.core.gps.Gps;
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


	protected List<Gps> markers;


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
			GoogleMapDesigner designer = GoogleMapDesigner.getDesigner(key);
			if (StringUtils.isNotEmpty(lat) &&
					StringUtils.isNotEmpty(lon)) {
				designer.setCenter(
						new Gps(
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
				for (Gps marker : markers) {
					designer.addMarker(marker);
				}
			}
			//Write
			JspWriterUtils.write(
					pageContext.getOut(),
					STAG + TAG + " src=\"" + designer.getSrc() + "\"" + ETAG);
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

	public void addMarker(Gps marker) {
		if (markers == null) {
			markers = new ArrayList<Gps>();
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

}
