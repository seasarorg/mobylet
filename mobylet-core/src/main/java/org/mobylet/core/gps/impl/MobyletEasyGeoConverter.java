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
