package org.mobylet.core.gps.impl;

import junit.framework.TestCase;

import org.mobylet.core.gps.Geo;
import org.mobylet.core.gps.GeoConverter;
import org.mobylet.core.gps.Gps;
import org.mobylet.core.holder.impl.MobyletSingletonHolder;
import org.mobylet.core.util.SingletonUtils;

public class MobyletGeoConverterTest extends TestCase {

	public void test_toWgs84() {
		//## ARRANGE ##
		if (!SingletonUtils.isInitialized()) {
			SingletonUtils.initialize(MobyletSingletonHolder.class);
		}
		SingletonUtils.put(new MobyletGeoConverter());

		//## ACT ##
		GeoConverter converter = SingletonUtils.get(GeoConverter.class);
		Gps in = new Gps(
				"35.20.39.984328",
				"138.35.8.086122",
				Geo.TOKYO);
		in.setHeight(697.681000);
		Gps wgs84 = converter.toWgs84(in);

		//## ASSERT ##
		assertEquals(wgs84.getStrLat(), "+35.20.51.66233592687058");
		assertEquals(wgs84.getStrLon(), "+138.34.56.90647422760958");
		assertEquals(wgs84.getHeight(), 738.1091920230538);
	}

	public void test_toTokyo() {
		//## ARRANGE ##
		if (!SingletonUtils.isInitialized()) {
			SingletonUtils.initialize(MobyletSingletonHolder.class);
		}
		SingletonUtils.put(new MobyletGeoConverter());

		//## ACT ##
		GeoConverter converter = SingletonUtils.get(GeoConverter.class);
		Gps in = new Gps(
				"35.20.51.66233592687058",
				"138.34.56.90647422760958",
				Geo.WGS84);
		in.setHeight(738.1091920230538);
		Gps tokyo = converter.toTokyo(in);

		//## ASSERT ##
		assertEquals(tokyo.getStrLat(), "+35.20.39.9843280002533");
		assertEquals(tokyo.getStrLon(), "+138.35.8.086121999891475");
		assertEquals(tokyo.getHeight(), 697.6810000063851);
	}
}
