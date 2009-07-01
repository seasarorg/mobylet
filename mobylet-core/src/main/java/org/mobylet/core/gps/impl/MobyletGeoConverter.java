package org.mobylet.core.gps.impl;

import org.mobylet.core.gps.DatumTokyo;
import org.mobylet.core.gps.DatumWgs84;
import org.mobylet.core.gps.Geo;
import org.mobylet.core.gps.GeoConverter;
import org.mobylet.core.gps.Gps;
import org.mobylet.core.util.GpsUtils;

public class MobyletGeoConverter implements GeoConverter {


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
			Gps g = rect2Ellip(r,
					DatumTokyo.semimajorAxis,
					DatumTokyo.eccentricity);
			g.setGeo(Geo.TOKYO);
			return g;
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
			Gps g = rect2Ellip(r,
					DatumWgs84.semimajorAxis,
					DatumWgs84.eccentricity);
			g.setGeo(Geo.WGS84);
			return g;
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
		double rLat = g.getLat() * GpsUtils.RD;
		double sinLat = Math.sin(rLat);
		double cosLat = Math.cos(rLat);
		//経度変換
		double rLon = g.getLon() * GpsUtils.RD;
		double sinLon = Math.sin(rLon);
		double cosLon = Math.cos(rLon);

		double rn = sa / Math.sqrt(1.0 - (ecc * sinLat * sinLat));

		double x = (rn + g.getHeight()) * cosLat * cosLon;
		double y = (rn + g.getHeight()) * cosLat * sinLon;
		double z = (rn * (1.0 - ecc) + g.getHeight()) * sinLat;

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
		double bpa = Math.sqrt(1.0 - ecc);
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

		Gps g = new Gps(rLat / GpsUtils.RD, rLon / GpsUtils.RD, null);
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




}
