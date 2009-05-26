package org.mobylet.core.gps.impl;

import org.mobylet.core.gps.Geo;
import org.mobylet.core.gps.GeoConverter;
import org.mobylet.core.gps.Gps;

public class MobyletGeoConverter implements GeoConverter {

	public static final double PI = Math.PI;

	public static final double RD = PI / 180.0;


	@Override
	public Gps toTokyo(Gps in) {
		if (in.getGeo() == Geo.WGS84) {
			Rectangular r = ellip2Rect(in,
					DatumWgs84.semimajorAxis,
					DatumWgs84.eccentricity);
			r = new Rectangular(
					r.getX() + DatumWgs84.DX2TOKYO,
					r.getY() + DatumWgs84.DY2TOKYO,
					r.getZ() + DatumWgs84.DZ2TOKYO);
			return rect2Ellip(r,
					DatumWgs84.semimajorAxis,
					DatumWgs84.eccentricity);
		} else {
			return in;
		}
	}

	@Override
	public Gps toWgs84(Gps in) {
		if (in.getGeo() == Geo.TOKYO) {
			Rectangular r = ellip2Rect(in,
					DatumTokyo.semimajorAxis,
					DatumTokyo.eccentricity);
			r = new Rectangular(
					r.getX() + DatumTokyo.DX2WGS84,
					r.getY() + DatumTokyo.DY2WGS84,
					r.getZ() + DatumTokyo.DZ2WGS84);
			return rect2Ellip(r,
					DatumTokyo.semimajorAxis,
					DatumTokyo.eccentricity);
		} else {
			return in;
		}
	}


	/**
	 * 楕円体座標→直交座標変換
	 *
	 * @param g
	 * @param sa
	 * @param ecc
	 * @return
	 */
	protected Rectangular ellip2Rect(Gps g, double sa, double ecc) {
		//緯度変換
		double rLat = g.getLat() * RD;
		double sinLat = Math.sin(rLat);
		double cosLat = Math.cos(rLat);
		//経度変換
		double rLon = g.getLon() * RD;
		double sinLon = Math.sin(rLon);
		double cosLon = Math.cos(rLon);

		double rn = sa / Math.sqrt(1 - (ecc * sinLat * sinLat));

		double x = (rn + g.getHeight()) * cosLat * cosLon;
		double y = (rn + g.getHeight()) * cosLat * sinLon;
		double z = (rn * (1 - ecc) + g.getHeight()) * sinLat;

		return new Rectangular(x, y, z);
	}

	/**
	 * 直交座標→楕円体座標変換
	 *
	 * @param r
	 * @param sa
	 * @param ecc
	 * @return
	 */
	protected Gps rect2Ellip(Rectangular r, double sa, double ecc) {
		double bpa = Math.sqrt(1 - ecc);
		double p = Math.sqrt((r.getX() * r.getX()) + (r.getY() * r.getY()));
		double t = Math.atan2(r.getZ(), p * bpa);
		double sinT = Math.sin(t);
		double cosT = Math.cos(t);

		double rLat = Math.atan2(
				r.getZ() + (ecc * sa / bpa) * Math.pow(sinT, 3.0),
				p - ecc * sa * Math.pow(cosT, 3.0));
		double rLon = Math.atan2(r.getY(), r.getX());

		double sinLat = Math.sin(rLat);
		double rn = sa / Math.sqrt(1 - ecc * sinLat * sinLat);

		double h = p / Math.cos(rLat) - rn;

		Gps g = new Gps(rLat / RD, rLon / RD, null);
		g.setHeight(h);
		return g;
	}

	/**
	 * 直交座標データ
	 *
	 * @author stakeuchi
	 */
	public class Rectangular {

		public double x;

		public double y;

		public double z;

		public Rectangular(double x, double y, double z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}

		public double getX() {
			return x;
		}

		public double getY() {
			return y;
		}

		public double getZ() {
			return z;
		}
	}

	/**
	 *
	 * WGS84データム
	 *
	 * @author stakeuchi
	 *
	 */
	public interface DatumWgs84 {

		/**
		 * 赤道半径
		 */
		public static final double semimajorAxis = 6378137.0;

		/**
		 * 扁平率
		 */
		public static final double flattening = 1.0 / 298.257223;

		/**
		 * 第1離心率
		 */
		public static final double eccentricity =
			(2.0 * flattening) - (flattening * flattening);

		/**
		 * 並行移動量（toTokyo）
		 */
		public static final double DX2TOKYO = 148.0;
		public static final double DY2TOKYO = -507.0;
		public static final double DZ2TOKYO = -681.0;

	}

	/**
	 *
	 * TOKYOデータム
	 *
	 * @author stakeuchi
	 *
	 */	public interface DatumTokyo {

		/**
		 * 赤道半径
		 */
		public static final double semimajorAxis = 6377397.155;

		/**
		 * 扁平率
		 */
		public static final double flattening = 1.0 / 299.152813;

		/**
		 * 第1離心率
		 */
		public static final double eccentricity =
			(2.0 * flattening) - (flattening * flattening);

		/**
		 * 並行移動量（toTokyo）
		 */
		public static final double DX2WGS84 = -DatumWgs84.DX2TOKYO;
		public static final double DY2WGS84 = -DatumWgs84.DY2TOKYO;
		public static final double DZ2WGS84 = -DatumWgs84.DZ2TOKYO;

	}

}
