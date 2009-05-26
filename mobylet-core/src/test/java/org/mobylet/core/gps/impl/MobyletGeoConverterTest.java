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
		SingletonUtils.initialize(MobyletSingletonHolder.class);
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
}
