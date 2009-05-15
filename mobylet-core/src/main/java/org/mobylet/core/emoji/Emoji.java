package org.mobylet.core.emoji;

import java.util.HashMap;
import java.util.Map;

import org.mobylet.core.Carrier;
import org.mobylet.core.util.StringUtils;

public class Emoji {

	protected Carrier carrier;

	protected char[] c = new char[]{0x00};

	protected char[] relatedChars;

	protected Map<Carrier, Emoji> map;


	public Emoji(Carrier carrier, char c) {
		this(carrier, null);
		this.c[0] = c;
	}

	public Emoji(Carrier carrier, String relatedString) {
		this.carrier = carrier;
		if (StringUtils.isNotEmpty(relatedString)) {
			this.relatedChars = relatedString.toCharArray();
		}
		map = new HashMap<Carrier, Emoji>();
		relate(this.carrier, this);
	}

	public void relate(Carrier carrier, Emoji emoji) {
		map.put(carrier, emoji);
	}

	public Emoji getRelated(Carrier carrier) {
		return map.get(carrier);
	}

	public Carrier getCarrier() {
		return carrier;
	}

	public char[] getCodes() {
		if (c[0] != 0x00) {
			return c;
		} else {
			return relatedChars;
		}
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
