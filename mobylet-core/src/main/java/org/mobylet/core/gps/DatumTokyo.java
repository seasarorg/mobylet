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
package org.mobylet.core.gps;


/**
*
* TOKYOデータム
*
* @author stakeuchi
*
*/
public interface DatumTokyo {

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
