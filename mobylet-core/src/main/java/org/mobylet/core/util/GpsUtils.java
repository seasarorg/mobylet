package org.mobylet.core.util;

import org.mobylet.core.gps.Gps;

public class GpsUtils {

	public static final double PI = Math.PI;

	public static final double RD = PI / 180.0;

	public static final double M_PER_LON = 111117.6;


	/**
	 *
	 * <p>平面座標移動Gps情報を取得する.</p>
	 * <p>
	 * X方向、Y方向にメートル単位で増分を指定して
	 * </p>
	 *
	 * @param target	基準Gps情報
	 * @param dX		X方向（経度）増分（メートル単位）
	 * @param dY		Y方向（緯度）増分（メートル単位）
	 * @return			移動Gps情報
	 */
	public static Gps getMovedGps(Gps target, double dX, double dY) {
		Gps g = new Gps(target);
		if (dX != 0.0) {
			double dLat = M_PER_LON * Math.cos(g.getLat() * RD);
			g.setLat(g.getLat() + (dLat * dX));
		}
		if (dY != 0.0) {
			double dLon = 1.0 / M_PER_LON;
			g.setLon(g.getLon() + (dLon + dY));
		}
		return g;
	}
}
