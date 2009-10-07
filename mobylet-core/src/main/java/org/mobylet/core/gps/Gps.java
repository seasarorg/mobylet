package org.mobylet.core.gps;

import org.mobylet.core.MobyletRuntimeException;
import org.mobylet.core.util.StringUtils;

public class Gps {

	public static final char PLS = '+';

	public static final char MNS = '-';

	public static final char N = 'N';

	public static final char S = 'S';

	public static final char E = 'E';

	public static final char W = 'W';


	/**
	 * 緯度（度分秒形式）
	 */
	protected String strLat;

	/**
	 * 緯度（浮動小数点形式）
	 */
	protected Double lat;


	/**
	 * 経度（度分秒形式）
	 */
	protected String strLon;

	/**
	 * 経度（浮動小数点形式）
	 */
	protected Double lon;


	/**
	 * 高度
	 */
	protected Double height = 0.0;

	/**
	 * 測地系
	 */
	protected Geo geo;

	/**
	 * 精度
	 */
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

	public Gps(Gps gps) {
		setLat(gps.getLat());
		setLon(gps.getLon());
		setGeo(gps.getGeo());
	}

	public String getStrLat() {
		if (StringUtils.isEmpty(strLat) && lat != null) {
			double s = (3600 * lat) % 60;
			int m = (int)(((3600 * lat - s) / 60) % 60);
			double dd = (((3600 * lat - s) / 60) - m) / 60;
			int d = (int)dd;
			strLat = String.valueOf(dd >= 0 ? PLS : MNS) + d + "." + m + "." + s;
		}
		return strLat;
	}

	public void setStrLat(String strLat) {
		this.strLat = strLat;
	}

	public Double getLat() {
		if (lat == null && StringUtils.isNotEmpty(strLat)) {
			try {
				boolean isPlus = true;
				int p1st = -1;
				int p2nd = -1;
				if (strLat.charAt(0) == MNS ||
						strLat.charAt(0) == S ||
						strLat.charAt(0) == W) {
					isPlus = false;
					p1st = strLat.indexOf(".");
					lat = Double.parseDouble(strLat.substring(1, p1st));
				} else if (strLat.charAt(0) == PLS ||
						strLat.charAt(0) == N ||
						strLat.charAt(0) == E) {
					isPlus = true;
					p1st = strLat.indexOf(".");
					lat = Double.parseDouble(strLat.substring(1, p1st));
				} else {
					isPlus = true;
					p1st = strLat.indexOf(".");
					lat = Double.parseDouble(strLat.substring(0, p1st));
				}
				p2nd = strLat.indexOf(".", p1st + 1);
				lat += Double.parseDouble(strLat.substring(p1st+1, p2nd)) / 60.0;
				lat += Double.parseDouble(strLat.substring(p2nd+1)) / 3600.0;
				lat = isPlus ? lat : -lat;
			} catch (Exception e) {
				throw new MobyletRuntimeException(
						"緯度情報の形式が不正です LAT="+strLat, e);
			}
		}
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public String getStrLon() {
		if (StringUtils.isEmpty(strLon) && lon != null) {
			double s = (3600 * lon) % 60;
			int m = (int)(((3600 * lon - s) / 60) % 60);
			double dd = (((3600 * lon - s) / 60) - m) / 60;
			int d = (int)dd;
			strLon = String.valueOf(dd >= 0 ? PLS : MNS) + d + "." + m + "." + s;
		}
		return strLon;
	}

	public void setStrLon(String strLon) {
		this.strLon = strLon;
	}

	public Double getLon() {
		if (lon == null && StringUtils.isNotEmpty(strLon)) {
			try {
				boolean isPlus = true;
				int p1st = -1;
				int p2nd = -1;
				if (strLon.charAt(0) == MNS ||
						strLon.charAt(0) == S ||
						strLon.charAt(0) == W) {
					isPlus = false;
					p1st = strLon.indexOf(".");
					lon = Double.parseDouble(strLon.substring(1, p1st));
				} else if (strLon.charAt(0) == PLS ||
						strLon.charAt(0) == N ||
						strLon.charAt(0) == E) {
					isPlus = true;
					p1st = strLon.indexOf(".");
					lon = Double.parseDouble(strLon.substring(1, p1st));
				} else {
					isPlus = true;
					p1st = strLon.indexOf(".");
					lon = Double.parseDouble(strLon.substring(0, p1st));
				}
				p2nd = strLon.indexOf(".", p1st + 1);
				lon += Double.parseDouble(strLon.substring(p1st+1, p2nd)) / 60.0;
				lon += Double.parseDouble(strLon.substring(p2nd+1)) / 3600.0;
				lon = isPlus ? lon : -lon;
			} catch (Exception e) {
				throw new MobyletRuntimeException(
						"経度情報の形式が不正です LAT="+strLat, e);
			}
		}
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

	@Override
	public String toString() {
		return "GEO="+getGeo()+
			" LAT="+getStrLat()+
			" LON="+getStrLon()+
			" HEIGHT="+getHeight();
	}

}
