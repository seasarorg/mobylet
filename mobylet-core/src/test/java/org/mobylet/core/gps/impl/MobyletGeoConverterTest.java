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
