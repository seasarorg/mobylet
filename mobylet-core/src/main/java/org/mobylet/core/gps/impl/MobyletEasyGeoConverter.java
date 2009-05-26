package org.mobylet.core.gps.impl;

import org.mobylet.core.gps.Geo;
import org.mobylet.core.gps.GeoConverter;
import org.mobylet.core.gps.Gps;

public class MobyletEasyGeoConverter implements GeoConverter {

	@Override
	public Gps toTokyo(Gps in) {
		if (in.getGeo() == Geo.WGS84) {
			Double lat =
				in.getLat() +
				(0.00010696 * in.getLat()) -
				(0.000017467 * in.getLon()) -
				0.0046020;
			Double lon =
				in.getLon() +
				(0.000046047 * in.getLat()) +
				(0.000083049 * in.getLon()) -
				0.010041;
			Gps out = new Gps(lat, lon, Geo.TOKYO);
			out.setAccuracy(in.getAccuracy());
			return out;
		} else {
			return in;
		}
	}

	@Override
	public Gps toWgs84(Gps in) {
		if (in.getGeo() == Geo.TOKYO) {
			Double lat =
				in.getLat() -
				(0.00010695 * in.getLat()) +
				(0.000017464 * in.getLon()) +
				0.0046017;
			Double lon =
				in.getLon() -
				(0.000046038 * in.getLat()) -
				(0.000083043 * in.getLon()) +
				0.010040;
			Gps out = new Gps(lat, lon, Geo.WGS84);
			out.setAccuracy(in.getAccuracy());
			return out;
		} else {
			return in;
		}	}



}
