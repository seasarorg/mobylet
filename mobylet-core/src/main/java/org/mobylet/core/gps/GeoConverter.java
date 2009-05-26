package org.mobylet.core.gps;

public interface GeoConverter {

	public Gps toWgs84(Gps in);

	public Gps toTokyo(Gps in);

}
