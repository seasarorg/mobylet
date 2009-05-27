package org.mobylet.core.gps;

public enum Accuracy {

	G300,

	G50L300,

	L50;

	public static Accuracy getAccuracy(int code) {
		switch (code) {
		case 1:
			return G300;
		case 2:
			return G50L300;
		case 3:
			return L50;
		default:
			return null;
		}
	}

}
