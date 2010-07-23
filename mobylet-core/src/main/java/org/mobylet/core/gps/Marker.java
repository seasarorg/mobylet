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
