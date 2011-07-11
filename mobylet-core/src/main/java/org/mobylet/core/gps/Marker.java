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
package org.mobylet.core.gps;

public class Marker extends Gps {

	protected Size size;

	protected Color color;

	protected String alphaNumericCharacter;


	public Marker(String strLat, String strLon, Geo geo) {
		super(strLat, strLon, geo);
	}

	public Marker(Double lat, Double lon, Geo geo) {
		super(lat, lon, geo);
	}

	public Marker(Gps gps) {
		super(gps);
	}


	public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public String getAlphaNumericCharacter() {
		return alphaNumericCharacter;
	}

	public void setAlphaNumericCharacter(String alphaNumericCharacter) {
		this.alphaNumericCharacter = alphaNumericCharacter;
	}


	public static enum Size {
		TINY,
		MID,
		SMALL;
		
		@Override
		public String toString() {
			return super.toString().toLowerCase();
		}
	}

	public static enum Color {
		BLACK,
		BROWN,
		GREEN,
		PURPLE,
		YELLOW,
		BLUE,
		GRAY,
		ORANGE,
		RED,
		WHITE;
		
		@Override
		public String toString() {
			return super.toString().toLowerCase();
		}
	}
}
