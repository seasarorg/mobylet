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
