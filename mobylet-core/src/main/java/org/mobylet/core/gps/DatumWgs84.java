package org.mobylet.core.gps;


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