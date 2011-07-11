/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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
			double dLon = M_PER_LON * Math.cos(g.getLat() * RD);
			g.setLon(g.getLon() + (dX / dLon));
		}
		if (dY != 0.0) {
			double dLat = 1.0 / M_PER_LON;
			g.setLat(g.getLat() + (dLat * dY));
		}
		return g;
	}
}
