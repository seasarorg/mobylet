package org.mobylet.core.emoji;

import java.util.HashMap;
import java.util.Map;

import org.mobylet.core.Carrier;

public class Emoji {

	protected Carrier carrier;

	protected char c;

	protected Map<Carrier, Emoji> map;


	public Emoji(Carrier carrier, char c) {
		this.carrier = carrier;
		this.c = c;
	}

	public void relate(Carrier carrier, Emoji emoji) {
		if (map == null) {
			map = new HashMap<Carrier, Emoji>();
		}
		map.put(carrier, emoji);
	}

	public Emoji getRelated(Carrier carrier) {
		return map.get(carrier);
	}

	public Carrier getCarrier() {
		return carrier;
	}

	public char getCode() {
		return c;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Emoji) {
			return this.carrier == ((Emoji)obj).carrier
				&& this.c == ((Emoji)obj).c;
		} else {
			return super.equals(obj);
		}
	}
}
