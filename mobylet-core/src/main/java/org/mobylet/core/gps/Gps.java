package org.mobylet.core.gps;

public class Gps {

	protected String strLat;

	protected Double lat;


	protected String strLon;

	protected Double lon;


	protected Double height = 0.0;


	protected Geo geo;

	protected Accuracy accuracy;


	public Gps(String strLat, String strLon, Geo geo) {
		setStrLat(strLat);
		setStrLon(strLon);
		setGeo(geo);
	}

	public Gps(Double lat, Double lon, Geo geo) {
		setLat(lat);
		setLon(lon);
		setGeo(geo);
	}

	public String getStrLat() {
		return strLat;
	}

	public void setStrLat(String strLat) {
		this.strLat = strLat;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public String getStrLon() {
		return strLon;
	}

	public void setStrLon(String strLon) {
		this.strLon = strLon;
	}

	public Double getLon() {
		return lon;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public Geo getGeo() {
		return geo;
	}

	public void setGeo(Geo geo) {
		this.geo = geo;
	}

	public Accuracy getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(Accuracy accuracy) {
		this.accuracy = accuracy;
	}


}
